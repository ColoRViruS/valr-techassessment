package com.valrTechassessment.service.models.limitOrder

import com.valrTechassessment.PostLimitOrderRequest
import com.valrTechassessment.SellBuySide

data class CreateLimitOrderDomainDto(
    val side: SellBuySide,
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
    return CreateLimitOrderDomainDto(
        side = side,
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