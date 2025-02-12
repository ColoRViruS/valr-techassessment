package com.valrTechassessment.models

data class AskOrders(
    val side: Side,
    val quantity: String,
    val price: String,
    val currencyPair: String,
    val orderCount: Int
) {
    enum class Side(
        val string: String
    ) {
        SELL("sell"),
        BUY("buy")
    }
}