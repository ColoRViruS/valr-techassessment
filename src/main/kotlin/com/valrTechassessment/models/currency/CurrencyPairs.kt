package com.valrTechassessment.models.currency

data class CurrencyPairs(
    val symbol: String,
    val baseCurrency: String,
    val quoteCurrency: String,
    val active: Boolean,
){

}
