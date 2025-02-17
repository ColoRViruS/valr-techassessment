package com.valrTechassessment.clients.orderbook.clientModels

import com.valrTechassessment.models.orderBook.OrderBookDomainDto
import java.time.OffsetDateTime

data class OrderBookClientDto(
    val asks: MutableList<OrdersClientDto>,
    val bids: MutableList<OrdersClientDto>,
    var lastChange: OffsetDateTime,
    var sequenceNumber: Long
) {

    fun toDomain() = OrderBookDomainDto(
        asks = asks.map { it.toDomain() },
        bids = bids.map { it.toDomain() },
        orderBooklastChange = lastChange,
        orderBookSequenceNumber = sequenceNumber
    )
}
