package com.valrTechassessment.clients.orderbook.clientModels

import com.valrTechassessment.models.orderBook.OrderBook
import java.time.OffsetDateTime

data class OrderBookResponse(
    val asks: MutableList<AskOrdersResponse>,
    var lastChange: OffsetDateTime,
    var sequenceNumber: Long
) {

    fun toDomain() = OrderBook(
        asks = asks.map { it.toDomain() },
        lastChange = lastChange,
        sequenceNumber = sequenceNumber
    )
}
