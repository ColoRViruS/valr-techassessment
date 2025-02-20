package com.valrTechassessment.service.models.orderBook

import com.valrTechassessment.Orders
import com.valrTechassessment.entity.orderbook.clientModels.AsksOrdersEntity
import com.valrTechassessment.entity.orderbook.clientModels.BidsOrdersEntity
import com.valrTechassessment.service.models.BuySellSideEnum

data class OrderDomainDto(
    val orderId: Int?,
    val orderSide: BuySellSideEnum,
    val orderQuantity: Double,
    val orderPrice: String,
    val orderCurrencyPair: String,
    val orderCountInt: Int
) {

    fun toResponseDto() =
        Orders().apply {
            side = orderSide.side
            quantity = orderQuantity.toBigDecimal().toPlainString()
            price = orderPrice
            currencyPair = orderCurrencyPair
            orderCount = orderCountInt
        }



    fun toSellOrdersEntity() =
        AsksOrdersEntity(
            id = orderId,
            side = orderSide,
            quantity = orderQuantity,
            price = orderPrice,
            currencyPair = orderCurrencyPair,
            orderCount = orderCountInt,

            )

    fun toBidsOrdersEntity() =
        BidsOrdersEntity(
            id = orderId,
            side = orderSide,
            quantity = orderQuantity,
            price = orderPrice,
            currencyPair = orderCurrencyPair,
            orderCount = orderCountInt,

            )
}
