package com.valrTechassessment.models.orderBook

import com.valrTechassessment.GetOrderBookResponse
import java.time.OffsetDateTime

data class OrderBookDomainDto(
    val asks: List<OrderDomainDto>,
    val bids: List<OrderDomainDto>,
    val orderBooklastChange: OffsetDateTime,
    val orderBookSequenceNumber: Long
) {
    fun toResponseDto() = GetOrderBookResponse().apply {
        asksList = asks.map { it.toResponseDto() }
        bidsList = bids.map { it.toResponseDto() }
        lastChange = orderBooklastChange
        sequenceNumber = orderBookSequenceNumber
    }
}
