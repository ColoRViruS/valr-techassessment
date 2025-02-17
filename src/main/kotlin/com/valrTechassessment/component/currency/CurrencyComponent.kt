package com.valrTechassessment.component.currency

import com.valrTechassessment.models.currency.CurrencyPairs
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CurrencyComponent(
    private val currencyClient: CurrencyClientInterface
) {
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    private var currencyPairCache = emptyList<CurrencyPairs>()
    private var currencyPairSymbolsFlatMap = emptyList<String>()

    fun validateCurrencyPair(currencyPairString: String): Boolean  {

        if (currencyPairCache.isEmpty()) getPopulateCurrencyCache()

        return currencyPairSymbolsFlatMap.any { currencyPairSymbol -> currencyPairSymbol == currencyPairString }

    }

    private fun getPopulateCurrencyCache() {
        logger.info("currencyPairCache empty. retrieving new list")
        currencyPairCache = currencyClient.getCurrencyPairsList()
        currencyPairSymbolsFlatMap = currencyPairCache.map { it.symbol }
        logger.info("currencyPairCache count. ${currencyPairCache.size}")
    }
}