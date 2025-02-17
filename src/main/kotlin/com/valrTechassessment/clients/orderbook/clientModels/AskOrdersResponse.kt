package com.valrTechassessment.clients.orderbook.clientModels

import com.valrTechassessment.models.orderBook.AskOrdersbook

data class AskOrdersResponse(
    val side: AskOrdersbook.Side,
    val quantity: String,
    val price: String,
    val currencyPair: String,
    val orderCount: Int
) {
    fun toDomain() = AskOrdersbook(
        side = side,
        quantity = quantity,
        price = price,
        currencyPair = currencyPair,
        orderCount = orderCount,
    )
}