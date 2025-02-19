package com.valrTechassessment.entity.orderbook.clientModels

import com.valrTechassessment.service.models.BuySellSideEnum
import com.valrTechassessment.service.models.orderBook.OrderDomainDto
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class OrdersClientDto(
    val uuid: String = UUID.randomUUID().toString(),
    val side: BuySellSideEnum,
    val quantity: Double,
    val price: String,
    val currencyPair: String,
    val orderCount: Int
) {
    fun toDomain(): OrderDomainDto {
        val orderUuid = UUID.fromString(uuid)
        return OrderDomainDto(
            orderUUID = orderUuid,
            orderSide = side,
            orderQuantity = quantity,
            orderPrice = price,
            orderCurrencyPair = currencyPair,
            orderCountInt = orderCount,
        )
    }
}