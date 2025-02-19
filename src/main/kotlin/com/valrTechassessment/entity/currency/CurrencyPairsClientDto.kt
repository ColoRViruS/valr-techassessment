package com.valrTechassessment.entity.currency

import com.valrTechassessment.service.models.currency.CurrencyPairs
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyPairRepoDto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
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
    val initialMarginFraction: String? = null,
    val maintenanceMarginFraction: String? = null,
    val autoCloseMarginFraction: String?= null
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

