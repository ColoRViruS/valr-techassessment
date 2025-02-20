package com.valrTechassessment.component.orderLimits

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
    private val tradeHistoryRepository: TradeHistoryRepository

) {
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    fun handleLimitOrder(createLimitOrderDomainDto: CreateLimitOrderDomainDto): Boolean {
        val orderBook = orderBookRepository.findByCurrencyPair(createLimitOrderDomainDto.pair).toDomain()
        val orderbookList = when (createLimitOrderDomainDto.side) {
            //Get Asks under limit order price
            BuySellSideEnum.BUY -> orderBook.asksList.filter { orders -> orders.orderPrice <= createLimitOrderDomainDto.price }
            //Get Bids over limit order price
            BuySellSideEnum.SELL -> orderBook.bidsList.filter { orders -> orders.orderPrice >= createLimitOrderDomainDto.price }
        }
        logger.info("handleLimitOrder : $createLimitOrderDomainDto")
        logger.info("handleLimitOrder timeInForce : ${createLimitOrderDomainDto.timeInForce}")
        return when (createLimitOrderDomainDto.timeInForce) {
            TimeInForceDomainEnum.GTC -> handleGTCLimitOrder(
                listOfOrdersToProcess = orderbookList,
                limitOrderDomainDto = createLimitOrderDomainDto
            )

            TimeInForceDomainEnum.IOC -> handleIOCLimitOrder(
                listOfOrdersToProcess = orderbookList,
                limitOrderDomainDto = createLimitOrderDomainDto
            )

            TimeInForceDomainEnum.FOK -> handleFOKLimitOrder(
                listOfOrdersToProcess = orderbookList,
                limitOrderDomainDto = createLimitOrderDomainDto
            )
        }

    }

    /**
     *  Fill or Kill -> The order must be completely filled immediately, or it is canceled
     */
    private fun handleFOKLimitOrder(
        listOfOrdersToProcess: List<OrderDomainDto>,
        limitOrderDomainDto: CreateLimitOrderDomainDto
    ): Boolean {
        val quantityAvailable = listOfOrdersToProcess.sumOf { it.orderQuantity }
        val canCompleteOrder = (quantityAvailable >= limitOrderDomainDto.quantity)
        logger.info("handle FOK LimitOrder canCompleteOrder : $canCompleteOrder")
        if (!canCompleteOrder) return false

        takeOrdersFromOrderBook(
            listOfOrdersToProcess = listOfOrdersToProcess,
            limitOrderDomainDto = limitOrderDomainDto
        )
        return true
    }

    /**
     * Immediate or Cancel -> The order must be partially or fully filled immediately; any unfilled portion is canceled
     */
    private fun handleIOCLimitOrder(
        listOfOrdersToProcess: List<OrderDomainDto>,
        limitOrderDomainDto: CreateLimitOrderDomainDto
    ): Boolean {

        val canPartiallyComplete = (listOfOrdersToProcess.isNotEmpty())
        logger.info("handle IOC LimitOrder canPartiallyComplete : $canPartiallyComplete")
        if (!canPartiallyComplete) return false

        takeOrdersFromOrderBook(
            listOfOrdersToProcess = listOfOrdersToProcess,
            limitOrderDomainDto = limitOrderDomainDto
        )

        return true
    }

    /**
     * Good Till Canceled -> The order remains open until fully executed or manually canceled.
     */
    fun handleGTCLimitOrder(
        listOfOrdersToProcess: List<OrderDomainDto>,
        limitOrderDomainDto: CreateLimitOrderDomainDto
    ): Boolean {

        val canPartiallyComplete = (listOfOrdersToProcess.isNotEmpty())
        logger.info("handle GTC LimitOrder canPartiallyComplete : $canPartiallyComplete")
        val quantityNotProvisioned = if (canPartiallyComplete) {
            takeOrdersFromOrderBook(
                listOfOrdersToProcess = listOfOrdersToProcess,
                limitOrderDomainDto = limitOrderDomainDto
            )
        } else {
            limitOrderDomainDto.quantity
        }
        if (quantityNotProvisioned > 0.0) addRemainingToOrderBook(
            limitOrderDomainDto,
            quantityNotProvisioned
        )
        return true
    }

    /**
     * remove orders matching the criteria
     * (lower price for buying or higher price for selling)
     * from the orderbook and add it to a list of orders that is being taken.
     * Creates Transaction History
     */
    fun takeOrdersFromOrderBook(
        listOfOrdersToProcess: List<OrderDomainDto>,
        limitOrderDomainDto: CreateLimitOrderDomainDto
    ): Double {
        logger.info("takeOrdersFromOrderBook listOfOrdersToProcess size : ${listOfOrdersToProcess.size}")
        logger.info("takeOrdersFromOrderBook listOfOrdersToProcess : $listOfOrdersToProcess, limitOrderDomainDto : $limitOrderDomainDto")
        //Running total Quantity that still needs to be provisioned
        var limitOrderQuantityLeft = limitOrderDomainDto.quantity
        //List of Orders being taken
        val takingOrders = mutableListOf<OrderDomainDto>()

        for (order in listOfOrdersToProcess) {
            //If Available order is smaller or equel than limit order quantity
            if (order.orderQuantity <= limitOrderQuantityLeft) {
                takingOrders.add(order)
                limitOrderQuantityLeft = limitOrderQuantityLeft.minus(order.orderQuantity)
                //If Available order is bigger than limit order quantity, need to split order
            } else {
                splitOrder(
                    order = order,
                    orderQuatity = limitOrderQuantityLeft
                )
                limitOrderQuantityLeft = 0.0
            }
            //If full quantity has been provisioned
            if (limitOrderQuantityLeft == 0.0) break
        }

        //Remove taken Orders from orderbook
        removeOrdersFromRepository(
            limitOrderDomainDto = limitOrderDomainDto,
            takingOrders = takingOrders
        )
        //Create Transaction History
        createTransactionHistory(
            limitOrderDomainDto = limitOrderDomainDto,
            totalQuantityNotFufilled = limitOrderQuantityLeft
        )
        return limitOrderQuantityLeft
    }

    /**
     * removes orders being taken
     */
    fun removeOrdersFromRepository(
        limitOrderDomainDto: CreateLimitOrderDomainDto,
        takingOrders: MutableList<OrderDomainDto>
    ) {
        logger.info("removeOrdersFromRepository limitOrderDomainDto.side : ${limitOrderDomainDto.side}")
        val orderbook = orderBookRepository.findByCurrencyPair(limitOrderDomainDto.pair)
        val takingOrderIds = takingOrders.map { takingOrder -> takingOrder.orderId }
        logger.info("removeOrdersFromRepository takingOrderIds : $takingOrderIds")
        when (limitOrderDomainDto.side) {
            BuySellSideEnum.BUY -> orderbook.asks.removeIf { takingOrderIds.contains(it.id) }
            BuySellSideEnum.SELL -> orderbook.bids.removeIf { takingOrderIds.contains(it.id) }
        }
        orderBookRepository.save(orderbook)
    }

    /**
     * splits an order that is being partially taken into two orders so the untaken part can stay in the orderbook
     */
    fun splitOrder(
        order: OrderDomainDto,
        orderQuatity: Double
    ) {
        logger.info("splitOrder order: $order")
        val splitOrderBuying = order.copy(
            orderId = null,
            orderQuantity = orderQuatity
        )
        val splitOrderLeftOver = order.copy(
            orderId = null,
            orderQuantity = order.orderQuantity.minus(orderQuatity)
        )
        val orderbook = orderBookRepository.findByCurrencyPair(order.orderCurrencyPair)
        when (order.orderSide) {
            BuySellSideEnum.SELL -> {
                orderbook.asks.removeIf { it.id == order.orderId }
                orderbook.asks.add(splitOrderLeftOver.toSellOrdersEntity())
                splitOrderBuying.toSellOrdersEntity().toDomain()
            }

            BuySellSideEnum.BUY -> {
                orderbook.bids.removeIf { it.id == order.orderId }
                orderbook.bids.add(splitOrderLeftOver.toBidsOrdersEntity())

                splitOrderBuying.toSellOrdersEntity().toDomain()
            }
        }
        orderBookRepository.save(orderbook)
    }

    /**
     * Adds unprovisioned buy/sell order into order book
     */
    fun addRemainingToOrderBook(
        limitOrderDomainDto: CreateLimitOrderDomainDto,
        limitOrderQuantityLeft: Double
    ) {
        logger.info("addRemainingToOrderBook limitOrderQuantityLeft: $limitOrderQuantityLeft, limitOrderDomainDto.side : ${limitOrderDomainDto.side}")
        logger.info("addRemainingToOrderBook limitOrderDomainDto: $limitOrderDomainDto")
        val orderbook = orderBookRepository.findByCurrencyPair(limitOrderDomainDto.pair)
        when (limitOrderDomainDto.side) {
            BuySellSideEnum.BUY -> {
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

    /**
     * Creates Transaction History
     */
    fun createTransactionHistory(
        limitOrderDomainDto: CreateLimitOrderDomainDto,
        totalQuantityNotFufilled: Double
    ) {
        logger.info("createTransactionHistory: totalQuantityNotFufilled: $totalQuantityNotFufilled")
        logger.info("createTransactionHistory: limitOrderDomainDto: $limitOrderDomainDto")
        val price = limitOrderDomainDto.price.toDouble()
        val quantity = limitOrderDomainDto.quantity.minus(totalQuantityNotFufilled)
        val quoteVolume = price * quantity
        val tradeHistoryEntity = TradeHistoryEntity(
            entityId = null,
            price = limitOrderDomainDto.price,
            quantity = quantity.toBigDecimal().toPlainString(),
            currencyPair = limitOrderDomainDto.pair,
            tradedAt = OffsetDateTime.now(),
            takerSide = limitOrderDomainDto.side,
            sequenceId = TradeHistorySequencer.next(),
            tradeId = UUID.randomUUID().toString(),
            quoteVolume = quoteVolume.toBigDecimal().toPlainString()
        )

        tradeHistoryRepository.save(tradeHistoryEntity)
    }

}