package com.valrTechassessment.component

import com.valrTechassessment.clients.currency.CurrencyClient
import com.valrTechassessment.models.CurrencyPairs
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CurrancyComponent(
    private val currencyClient: CurrencyClient
) {
    val logger: Logger = LoggerFactory.getLogger("CurrancyComponent")

    var currencyPairCache = emptyList<CurrencyPairs>()

    fun validateCurrencyPair(currencyPairString: String): Boolean {
        if (currencyPairCache.isEmpty()) {
            logger.debug("currencyPairCache empty. retrieving new list")
            currencyPairCache = currencyClient.getCurrencyPairsList()
        }

        return currencyPairCache.any { currencyPair -> currencyPair.symbol == currencyPairString }

    }

}