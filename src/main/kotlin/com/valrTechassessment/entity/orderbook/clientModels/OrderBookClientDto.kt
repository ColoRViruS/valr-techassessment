package com.valrTechassessment.entity.orderbook.clientModels

import com.valrTechassessment.service.models.orderBook.OrderBookDomainDto
import java.time.OffsetDateTime
import java.util.UUID

data class OrderBookClientDto(
    val asks: MutableMap<UUID, OrdersClientDto>,
    val bids: MutableMap<UUID, OrdersClientDto>,
    var lastChange: OffsetDateTime,
    var sequenceNumber: Long
) {

    fun toDomain() = OrderBookDomainDto(
        asksMap = asks.mapValues { it.value.toDomain() },
        bidsMap = bids.mapValues { it.value.toDomain() },
        orderBooklastChange = lastChange,
        orderBookSequenceNumber = sequenceNumber
    )
}
