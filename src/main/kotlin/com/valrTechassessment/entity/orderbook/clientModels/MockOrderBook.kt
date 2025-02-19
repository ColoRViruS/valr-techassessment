package com.valrTechassessment.entity.orderbook.clientModels

import kotlinx.serialization.Serializable

@Serializable
data class MockOrderBook(
    val asks: List<MockOrders>,
    val bids: List<MockOrders>
)
