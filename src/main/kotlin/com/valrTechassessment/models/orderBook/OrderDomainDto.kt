package com.valrTechassessment.models.orderBook

import com.valrTechassessment.Orders
import com.valrTechassessment.models.BuySellSideEnum

data class OrderDomainDto(
    val orderSide: BuySellSideEnum,
    val orderQuantity: String,
    val orderPrice: String,
    val orderCurrencyPair: String,
    val orderCountInt: Int
) {

    fun toResponseDto() = Orders().apply {
        side = orderSide.side
        quantity = orderQuantity
        price = orderPrice
        currencyPair = orderCurrencyPair
        orderCount = orderCountInt
    }

}