package com.valrTechassessment.service.models.orderBook

import com.valrTechassessment.GetOrderBookResponse
import java.time.OffsetDateTime

data class OrderBookDomainDto(
    val asksList: List<OrderDomainDto>,
    val bidsMap: List<OrderDomainDto>,
    val orderBooklastChange: OffsetDateTime,
    val orderBookSequenceNumber: Long
) {
    fun toResponseDto() = GetOrderBookResponse().apply {
        asks = asksList.map { it.toResponseDto() }
        bids = asksList.map { it.toResponseDto() }
        lastChange = orderBooklastChange
        sequenceNumber = orderBookSequenceNumber
    }
}
