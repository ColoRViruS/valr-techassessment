package com.valrTechassessment.models.orderBook

data class AskOrdersbook(
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