package com.valrTechassessment.component.currency

import com.valrTechassessment.service.models.currency.CurrencyPairs

interface CurrencyClientInterface {
    fun getCurrencyPairsList(): List<CurrencyPairs>
}