package com.valrTechassessment.models

import com.valrTechassessment.GetOrderBookResponse
import com.valrTechassessment.Orders
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

data class OrderBook(
    val asks: List<AskOrders>,
    val lastChange: OffsetDateTime,
    val sequenceNumber: Long
)

fun OrderBook.toDto(): GetOrderBookResponse = GetOrderBookResponse().apply {
    asksList = asks.map { Orders() }
    lastChange = this@toDto.lastChange
    sequenceNumber = this@toDto.sequenceNumber
}
