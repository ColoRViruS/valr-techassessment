package com.valrTechassessment.entity.orderbook.clientModels

import com.valrTechassessment.service.models.BuySellSideEnum
import com.valrTechassessment.service.models.orderBook.OrderDomainDto
import kotlinx.serialization.Serializable

@Serializable
data class MockOrders(
    val id: Int? = null,
    val side: BuySellSideEnum,
    val quantity: Double,
    val price: String,
    val currencyPair: String,
    val orderCount: Int
) {
    fun toDomain() =
        OrderDomainDto(
            orderId = id,
            orderSide = side,
            orderQuantity = quantity,
            orderPrice = price,
            orderCurrencyPair = currencyPair,
            orderCountInt = orderCount,
        )

    fun toBidsOrdersEntity() =
        BidsOrdersEntity(
            id = null,
            side = side,
            quantity = quantity,
            price = price,
            currencyPair = currencyPair,
            orderCount = orderCount
        )

    fun toSellOrdersEntity() =
        AsksOrdersEntity(
            id = null,
            side = side,
            quantity = quantity,
            price = price,
            currencyPair = currencyPair,
            orderCount = orderCount
        )

}