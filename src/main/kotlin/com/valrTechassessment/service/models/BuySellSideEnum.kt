package com.valrTechassessment.service.models

import com.valrTechassessment.SellBuySide
import kotlinx.serialization.SerialName

enum class BuySellSideEnum(
    val side: SellBuySide,
    val string: String
) {
    @SerialName("sell")
    SELL(
        SellBuySide.SELL,
        "sell"
    ),

    @SerialName("buy")
    BUY(
        SellBuySide.BUY,
        "buy"
    );

    companion object {
        fun fromRequest(requestSellBuySide: SellBuySide) = BuySellSideEnum.entries.find { it.side == requestSellBuySide }!!

    }
}