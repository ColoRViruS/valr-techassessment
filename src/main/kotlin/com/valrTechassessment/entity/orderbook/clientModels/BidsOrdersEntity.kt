package com.valrTechassessment.entity.orderbook.clientModels

import com.valrTechassessment.service.models.BuySellSideEnum
import com.valrTechassessment.service.models.orderBook.OrderDomainDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class BidsOrdersEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val side: BuySellSideEnum,
    val quantity: Double,
    val price: String,
    val currencyPair: String,
    val orderCount: Int
) {
    constructor() : this(
        id = null,
        side = BuySellSideEnum.BUY,
        quantity = 0.0,
        price = "",
        currencyPair = "",
        orderCount = 0
    )

    fun toDomain() =
        OrderDomainDto(
            orderId = id,
            orderSide = side,
            orderQuantity = quantity,
            orderPrice = price,
            orderCurrencyPair = currencyPair,
            orderCountInt = orderCount,
        )


}