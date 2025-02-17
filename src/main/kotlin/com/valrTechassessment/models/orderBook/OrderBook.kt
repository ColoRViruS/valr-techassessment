package com.valrTechassessment.models.orderBook

import com.valrTechassessment.GetOrderBookResponse
import com.valrTechassessment.Orders
import java.time.OffsetDateTime

data class OrderBook(
    val asks: List<AskOrdersbook>,
    val lastChange: OffsetDateTime,
    val sequenceNumber: Long
){
    fun toDto(): GetOrderBookResponse = GetOrderBookResponse().apply {
        asksList = asks.map { Orders() }
        lastChange = this@OrderBook.lastChange
        sequenceNumber = this@OrderBook.sequenceNumber
    }
}
