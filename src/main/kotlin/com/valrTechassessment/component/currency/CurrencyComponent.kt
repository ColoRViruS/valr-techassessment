package com.valrTechassessment.component.currency

import com.valrTechassessment.models.currency.CurrencyPairs
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CurrencyComponent(
    private val currencyClient: CurrencyClientInterface
) {
    private val logger = LoggerFactory.getLogger("CurrancyComponent")

    private var currencyPairCache = emptyList<CurrencyPairs>()

    fun validateCurrencyPair(currencyPairString: String): Boolean {
        if (currencyPairCache.isEmpty()) {
            logger.info("currencyPairCache empty. retrieving new list")
            currencyPairCache = currencyClient.getCurrencyPairsList()
            logger.info("currencyPairCache count. ${currencyPairCache.size}")
        }

        return currencyPairCache.any { currencyPair -> currencyPair.symbol == currencyPairString }

    }

}