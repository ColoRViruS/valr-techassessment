package com.valrTechassessment.service.models.orderBook

import com.valrTechassessment.GetOrderBookResponse
import java.time.OffsetDateTime
import java.util.UUID

data class OrderBookDomainDto(
    val asksMap: Map<UUID, OrderDomainDto>,
    val bidsMap: Map<UUID, OrderDomainDto>,
    val orderBooklastChange: OffsetDateTime,
    val orderBookSequenceNumber: Long
) {
    fun toResponseDto() = GetOrderBookResponse().apply {
        asks = asksMap.values.map { it.toResponseDto() }
        bids = asksMap.values.map { it.toResponseDto() }
        lastChange = orderBooklastChange
        sequenceNumber = orderBookSequenceNumber
    }
}
