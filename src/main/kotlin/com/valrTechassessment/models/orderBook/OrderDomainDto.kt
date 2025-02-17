package com.valrTechassessment.models.orderBook

import com.valrTechassessment.Orders
import com.valrTechassessment.Orders.SideEnum
import kotlinx.serialization.SerialName

data class OrderDomainDto(
    val orderSide: OrderSide,
    val orderQuantity: String,
    val orderPrice: String,
    val orderCurrencyPair: String,
    val orderCountInt: Int
) {
    enum class OrderSide(
        val side: SideEnum,
        val string: String
    ) {
        @SerialName("sell")
        SELL(
            SideEnum.SELL,
            "sell"
        ),
        @SerialName("buy")
        BUY(
            SideEnum.BUY,
            "buy"
        )
    }

    fun toResponseDto() = Orders().apply {
        side = orderSide.side
        quantity = orderQuantity
        price = orderPrice
        currencyPair = orderCurrencyPair
        orderCount = orderCountInt
    }

}