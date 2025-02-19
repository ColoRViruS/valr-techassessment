package com.valrTechassessment.entity.limitOrder

import com.valrTechassessment.SellBuySide
import com.valrTechassessment.service.models.BuySellSideEnum
import com.valrTechassessment.service.models.limitOrder.TimeInForceDomainEnum
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class LimitOrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
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
) {
    constructor() : this(
        id = null,
        side = BuySellSideEnum.SELL,
        quantity = 0.0,
        price = "",
        pair = "",
        postOnly = false,
        customerOrderId = "",
        timeInForce = TimeInForceDomainEnum.GTC,
        allowMargin = true,
        reduceOnly = true,
        conditionalOrderData = "",
    )
}