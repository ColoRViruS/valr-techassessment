package com.valrTechassessment.component

import com.valrTechassessment.SellBuySide
import com.valrTechassessment.entity.limitOrder.LimitOrderRepository
import com.valrTechassessment.entity.orderbook.AsksOrdersRepository
import com.valrTechassessment.entity.orderbook.BidsOrdersRepository
import com.valrTechassessment.entity.orderbook.OrderBookRepository
import com.valrTechassessment.entity.orderbook.clientModels.BidsOrdersEntity
import com.valrTechassessment.entity.orderbook.clientModels.AsksOrdersEntity
import com.valrTechassessment.entity.tradeHistory.TradeHistoryEntity
import com.valrTechassessment.entity.tradeHistory.TradeHistoryRepository
import com.valrTechassessment.entity.tradeHistory.TradeHistorySequencer
import com.valrTechassessment.service.models.BuySellSideEnum
import com.valrTechassessment.service.models.limitOrder.CreateLimitOrderDomainDto
import com.valrTechassessment.service.models.limitOrder.TimeInForceDomainEnum
import com.valrTechassessment.service.models.orderBook.OrderBookDomainDto
import com.valrTechassessment.service.models.orderBook.OrderDomainDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.UUID

@Component
class LimitOrderComponent(
    private val orderBookRepository: OrderBookRepository,
    private val asksOrdersRepository: AsksOrdersRepository,
    private val bidsOrdersRepository: BidsOrdersRepository,
    private val limitOrderRepository: LimitOrderRepository,
    private val tradeHistoryRepository: TradeHistoryRepository

) {
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    fun handleLimitOrder(createLimitOrderDomainDto: CreateLimitOrderDomainDto) {

        val orderBook = orderBookRepository.findByCurrencyPair(createLimitOrderDomainDto.pair).toDomain()

        //Immediate or Cancel -> The order must be partially or fully filled immediately; any unfilled portion is canceled
        //Fill or Kill -> The order must be completely filled immediately, or it is canceled

        when (createLimitOrderDomainDto.timeInForce) {
            TimeInForceDomainEnum.GTC -> gtc(
                limitOrder = createLimitOrderDomainDto,
                orderBook = orderBook
            )

            TimeInForceDomainEnum.IOC -> TODO()

            TimeInForceDomainEnum.FOK -> TODO()
        }
    }

    /**
     * Good Till Canceled -> The order remains open until fully executed or manually canceled.
     */
    private fun gtc(
        limitOrder: CreateLimitOrderDomainDto,
        orderBook: OrderBookDomainDto
    ) {

        when (limitOrder.side) {
            BuySellSideEnum.BUY -> {
                val asksUnderPrice = orderBook.asksList.filter { orders -> orders.orderPrice <= limitOrder.price }
                gtcHandleList(
                    asksUnderPrice,
                    limitOrder
                )
            }

            BuySellSideEnum.SELL -> {
                val bidsOverPrice = orderBook.bidsList.filter { orders -> orders.orderPrice >= limitOrder.price }
                gtcHandleList(
                    bidsOverPrice,
                    limitOrder
                )

            }
        }
    }

    fun gtcHandleList(
        listOfOrderUnderPrice: List<OrderDomainDto>,
        createLimitOrderDomainDto: CreateLimitOrderDomainDto
    ) {
        var limitOrderQuantityLeft = createLimitOrderDomainDto.quantity
        val takingOrders = mutableListOf<OrderDomainDto>()
        for (order in listOfOrderUnderPrice) {
            if (order.orderQuantity <= limitOrderQuantityLeft) {
                takingOrders.add(order)
                limitOrderQuantityLeft.minus(order.orderQuantity)
            } else {
                val splitOrderTakingOrderId = splitOrder(
                    order = order,
                    orderQuatity = limitOrderQuantityLeft
                )
                takingOrders.add(splitOrderTakingOrderId)
            }
            if (limitOrderQuantityLeft == 0.0) break
        }

        if (limitOrderQuantityLeft > 0) addRemainingToOrderBook(
            createLimitOrderDomainDto,
            limitOrderQuantityLeft
        )

        when (createLimitOrderDomainDto.side) {
            BuySellSideEnum.BUY -> asksOrdersRepository.deleteAllById(takingOrders.map { it.orderId })
            BuySellSideEnum.SELL -> bidsOrdersRepository.deleteAllById(takingOrders.map { it.orderId })
        }

        val price = createLimitOrderDomainDto.price.toDouble()
        val quantity = createLimitOrderDomainDto.quantity.minus(limitOrderQuantityLeft)
        val quoteVolume = price * quantity
        val tradeHistoryEntity = TradeHistoryEntity(
            entityId = null,
            price = createLimitOrderDomainDto.price,
            quantity = quantity.toBigDecimal().toPlainString(),
            currencyPair = createLimitOrderDomainDto.pair,
            tradedAt = OffsetDateTime.now(),
            takerSide = createLimitOrderDomainDto.side,
            sequenceId = TradeHistorySequencer.next(),
            tradeId = UUID.randomUUID().toString(),
            quoteVolume = quoteVolume.toBigDecimal().toPlainString()
        )

        tradeHistoryRepository.save(tradeHistoryEntity)
    }

    fun splitOrder(
        order: OrderDomainDto,
        orderQuatity: Double
    ): OrderDomainDto {
        val splitOrderBuying = order.copy(
            orderId = null,
            orderQuantity = orderQuatity
        )
        val splitOrderLeftOver = order.copy(
            orderId = null,
            orderQuantity = order.orderQuantity.minus(orderQuatity)
        )

        val takingOrder = when (order.orderSide) {
            BuySellSideEnum.SELL -> {
                asksOrdersRepository.deleteById(order.orderId!!)
                asksOrdersRepository.save(splitOrderLeftOver.toSellOrdersEntity())
                asksOrdersRepository.save(splitOrderBuying.toSellOrdersEntity()).toDomain()
            }

            BuySellSideEnum.BUY -> {
                bidsOrdersRepository.deleteById(order.orderId!!)
                bidsOrdersRepository.save(splitOrderLeftOver.toBidsOrdersEntity())
                bidsOrdersRepository.save(splitOrderBuying.toBidsOrdersEntity()).toDomain()
            }
        }
        return takingOrder
    }

    fun addRemainingToOrderBook(
        limitOrderDomainDto: CreateLimitOrderDomainDto,
        limitOrderQuantityLeft: Double
    ) {
        val orderbook = orderBookRepository.findByCurrencyPair(limitOrderDomainDto.pair)
        when (limitOrderDomainDto.side) {
            BuySellSideEnum.BUY-> {
                val newBidOrder = BidsOrdersEntity(
                    side = BuySellSideEnum.BUY,
                    quantity = limitOrderQuantityLeft,
                    price = limitOrderDomainDto.price,
                    currencyPair = limitOrderDomainDto.pair,
                    orderCount = 1
                )
                orderbook.bids.add(newBidOrder)
            }

            BuySellSideEnum.SELL -> {
                val newBidOrder = AsksOrdersEntity(
                    side = BuySellSideEnum.SELL,
                    quantity = limitOrderQuantityLeft,
                    price = limitOrderDomainDto.price,
                    currencyPair = limitOrderDomainDto.pair,
                    orderCount = 1
                )
                orderbook.asks.add(newBidOrder)
            }
        }
        orderBookRepository.save(orderbook)
    }

}