package com.valrTechassessment.component

import com.valrTechassessment.SellBuySide
import com.valrTechassessment.entity.limitOrder.LimitOrderRepository
import com.valrTechassessment.entity.orderbook.OrderBookRepository
import com.valrTechassessment.service.models.limitOrder.CreateLimitOrderDomainDto
import com.valrTechassessment.service.models.limitOrder.TimeInForceDomainEnum
import com.valrTechassessment.service.models.orderBook.OrderBookDomainDto
import com.valrTechassessment.service.models.orderBook.OrderDomainDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LimitOrderComponent(
    private val orderBookRepository: OrderBookRepository,
    private val limitOrderRepository: LimitOrderRepository,

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
            SellBuySide.BUY -> {
                val asksUnderPrice = orderBook.asksList.filter { orders -> orders.orderPrice <= limitOrder.price }
                gtcHandleList(
                    asksUnderPrice,
                    limitOrder.quantity
                )
            }

            SellBuySide.SELL -> {
                val bidsUnderPrice = orderBook.bidsList.filter { orders -> orders.orderPrice <= limitOrder.price }
                gtcHandleList(
                    bidsUnderPrice,
                    limitOrder.quantity
                )

            }
        }
    }

    fun gtcHandleList(
        asksUnderPrice: List<OrderDomainDto>,
        limitOrderQuantity: Double
    ) {
        var limitOrderQuantityLeft = limitOrderQuantity
        val takingOrders = mutableListOf<OrderDomainDto>()
        asksUnderPrice.forEach { order ->
            if (order.orderQuantity <= limitOrderQuantityLeft) {
                takingOrders.add(order)
                limitOrderQuantityLeft.minus(order.orderQuantity)
            } else {
                val splitOrderBuying = order.copy(
                    orderId = null,
                    orderQuantity = limitOrderQuantityLeft
                )
                val splitOrderLeftOver = order.copy(
                    orderId = null,
                    orderQuantity = order.orderQuantity.minus(limitOrderQuantityLeft)
                )
                takingOrders.add(splitOrderBuying)
//                orderBookClientInterface.removeUuidList()
//                orderBookClientInterface.addToOrderbook(order = splitOrderLeft)
            }
        }

//        orderBookRepository.delete()
    }
}