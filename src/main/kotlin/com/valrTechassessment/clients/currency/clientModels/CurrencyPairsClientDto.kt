package com.valrTechassessment.clients.currency.clientModels

import com.valrTechassessment.models.currency.CurrencyPairs

data class CurrencyPairClientDto(
    val symbol: String,
    val baseCurrency: String,
    val quoteCurrency: String,
    val shortName: String,
    val active: Boolean,
    val minBaseAmount: String,
    val maxBaseAmount: String,
    val minQuoteAmount: String,
    val maxQuoteAmount: String,
    val tickSize: String,
    val baseDecimalPlaces: String,
    val marginTradingAllowed: Boolean,
    val currencyPairType: String,
    val initialMarginFraction: String?,
    val maintenanceMarginFraction: String?,
    val autoCloseMarginFraction: String?
) {
    fun toDomain(): CurrencyPairs {
        return CurrencyPairs(
            symbol = symbol,
            baseCurrency = baseCurrency,
            quoteCurrency = quoteCurrency,
            active = active
        )
    }
}

