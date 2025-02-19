package com.valrTechassessment.component

import com.valrTechassessment.entity.currency.CurrencyRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CurrencyComponent(
    private val currencyRepository: CurrencyRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    private var currencyPairSymbolsFlatMap = emptyList<String>()

    fun validateCurrencyPair(currencyPairString: String): Boolean {

        if (currencyPairSymbolsFlatMap.isEmpty()) getPopulateCurrencyCache()

        return currencyPairSymbolsFlatMap.any { currencyPairSymbol -> currencyPairSymbol == currencyPairString }

    }

    private fun getPopulateCurrencyCache() {
        logger.info("currencyPairCache empty. retrieving new list")
        val fullCurrencyMap = currencyRepository.findAll()
        currencyPairSymbolsFlatMap = fullCurrencyMap.map { it.symbol }
        logger.info("currencyPairCache count. ${currencyPairSymbolsFlatMap.size}")
    }
}