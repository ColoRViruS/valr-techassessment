package com.valrTechassessment.service.models.limitOrder

import com.valrTechassessment.PostLimitOrderRequest
import com.valrTechassessment.service.models.BuySellSideEnum

data class CreateLimitOrderDomainDto(
    val side: BuySellSideEnum,
    val quantity: Double,
    val price: String,
    val pair: String,
    val postOnly: Boolean? = null,
    val customerOrderId: String? = null,
    val timeInForce: TimeInForceDomainEnum = TimeInForceDomainEnum.GTC,
    val allowMargin: Boolean? = null,
    val reduceOnly: Boolean? = null,
    val conditionalOrderData: String? = null
)

fun PostLimitOrderRequest.toDomain(): CreateLimitOrderDomainDto {
    val timeInForceEnum = TimeInForceDomainEnum.fromRequest(timeInForce)
    val quantityDouble = quantity.toDouble()
    val sellBuySide = BuySellSideEnum.fromRequest(side)
    return CreateLimitOrderDomainDto(
        side = sellBuySide,
        quantity = quantityDouble,
        price = price,
        pair = pair,
        postOnly = postOnly,
        customerOrderId = customerOrderId,
        timeInForce = timeInForceEnum,
        allowMargin = allowMargin,
        reduceOnly = reduceOnly,
        conditionalOrderData = conditionalOrderData
    )
}