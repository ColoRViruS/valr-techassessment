package com.valrTechassessment.component.orderbook

import com.valrTechassessment.SellBuySide
import com.valrTechassessment.service.models.limitOrder.CreateLimitOrderDomainDto
import com.valrTechassessment.service.models.limitOrder.TimeInForceDomainEnum
import com.valrTechassessment.service.models.orderBook.OrderBookDomainDto
import com.valrTechassessment.service.models.orderBook.OrderDomainDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class OrderBookComponent(
    private val orderBookClientInterface: OrderBookClientInterface
) {
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    fun getOrderBook(
        currencyPair: String
    ): OrderBookDomainDto {

        logger.info("Getting Orderbook for currency pair: $currencyPair")

        return orderBookClientInterface.getOrderbook(currencyPair)
    }

    fun handleLimitOrder(createLimitOrderDomainDto: CreateLimitOrderDomainDto) {

        val orderBook = orderBookClientInterface.getOrderbook(createLimitOrderDomainDto.pair)


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
            SellBuySide.BUY -> {
                val asksUnderPrice = orderBook.asksMap.filter { orders -> orders.value.orderPrice <= limitOrder.price }
                gtcHandleList(
                    asksUnderPrice,
                    limitOrder.quantity
                )
            }

            SellBuySide.SELL -> {
                val bidsUnderPrice = orderBook.bidsMap.filter { orders -> orders.value.orderPrice <= limitOrder.price }
                gtcHandleList(
                    bidsUnderPrice,
                    limitOrder.quantity
                )

            }
        }
    }

    fun gtcHandleList(
        asksUnderPrice: Map<UUID, OrderDomainDto>,
        limitOrderQuantity: Double
    ) {
        var limitOrderQuantityLeft = limitOrderQuantity
        val buyingOrders = mutableListOf<OrderDomainDto>()

        asksUnderPrice.forEach { (uuid, order) ->
            if (order.orderQuantity <= limitOrderQuantityLeft) {
                buyingOrders.add(uuid)
                limitOrderQuantityLeft.minus(order.orderQuantity)
                orderBookClientInterface.removeOrder(uuid = uuid)
            } else {
                val splitOrderBuying = order.copy(
                    orderUUID = UUID.randomUUID(),
                    orderQuantity = limitOrderQuantityLeft
                )
                val splitOrderLeft = order.copy(
                    orderUUID = UUID.randomUUID(),
                    orderQuantity = order.orderQuantity.minus(limitOrderQuantityLeft)
                )
                buyingOrders.add(splitOrderBuying)
                orderBookClientInterface.removeUuidList(uuid = uuid)
                orderBookClientInterface.addToOrderbook(order = splitOrderLeft)
            }
        }
    }
}