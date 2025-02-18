package com.valrTechassessment.clients.orderbook.clientModels

import kotlinx.serialization.Serializable

@Serializable
data class MockOrderBook(
    val asks: List<OrdersClientDto>,
    val bids: List<OrdersClientDto>
)
