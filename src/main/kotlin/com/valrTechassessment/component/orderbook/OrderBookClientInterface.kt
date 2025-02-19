package com.valrTechassessment.component.orderbook

import com.valrTechassessment.service.models.orderBook.OrderBookDomainDto
import com.valrTechassessment.service.models.orderBook.OrderDomainDto
import java.util.*

interface OrderBookClientInterface {

    fun getOrderbook(currencyPair: String): OrderBookDomainDto

    fun addToOrderbook(order: OrderDomainDto)

    fun removeOrder(uuid: UUID)

    fun removeUuidList(list: List<UUID>)

}