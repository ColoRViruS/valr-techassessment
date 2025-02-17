package com.valrTechassessment.clients.orderbook.clientModels

import com.valrTechassessment.models.orderBook.OrderDomainDto
import kotlinx.serialization.Serializable

@Serializable
data class OrdersClientDto(
    val side: OrderDomainDto.OrderSide,
    val quantity: String,
    val price: String,
    val currencyPair: String,
    val orderCount: Int
) {
    fun toDomain() = OrderDomainDto(
        orderSide = side,
        orderQuantity = quantity,
        orderPrice = price,
        orderCurrencyPair = currencyPair,
        orderCountInt = orderCount,
    )
}