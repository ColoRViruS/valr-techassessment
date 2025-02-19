package com.valrTechassessment.service.models.currency

data class CurrencyPairs(
    val symbol: String,
    val baseCurrency: String,
    val quoteCurrency: String,
    val active: Boolean,
){

}
