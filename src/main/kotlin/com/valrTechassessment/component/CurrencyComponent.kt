package com.valrTechassessment.component

import com.valrTechassessment.clients.currency.CurrencyClient
import com.valrTechassessment.models.CurrencyPairs
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CurrencyComponent(
    private val currencyClient: CurrencyClient
) {
    private val logger = LoggerFactory.getLogger("CurrancyComponent")

    var currencyPairCache = emptyList<CurrencyPairs>()

    fun validateCurrencyPair(currencyPairString: String): Boolean {
        if (currencyPairCache.isEmpty()) {
            logger.info("currencyPairCache empty. retrieving new list")
            currencyPairCache = currencyClient.getCurrencyPairsList()
            logger.info("currencyPairCache count. ${currencyPairCache.size}")
        }

        return currencyPairCache.any { currencyPair -> currencyPair.symbol == currencyPairString }

    }

}