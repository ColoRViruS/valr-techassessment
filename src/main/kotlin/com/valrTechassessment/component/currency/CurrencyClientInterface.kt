package com.valrTechassessment.component.currency

import com.valrTechassessment.models.currency.CurrencyPairs

interface CurrencyClientInterface {
    fun getCurrencyPairsList(): List<CurrencyPairs>
}