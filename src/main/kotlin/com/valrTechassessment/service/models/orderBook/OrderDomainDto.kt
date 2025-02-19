package com.valrTechassessment.service.models.orderBook

import com.valrTechassessment.Orders
import com.valrTechassessment.service.models.BuySellSideEnum
import java.util.UUID

data class OrderDomainDto(
    val orderUUID: UUID,
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

}