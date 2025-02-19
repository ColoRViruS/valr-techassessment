package com.valrTechassessment.component.currency

import com.valrTechassessment.entity.currency.CurrencyRepository
import com.valrTechassessment.service.models.currency.CurrencyPairs
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CurrencyComponent(
    private val currencyRepository: CurrencyRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    private var currencyPairCache = emptyList<CurrencyPairs>()
    private var currencyPairSymbolsFlatMap = emptyList<String>()

    fun validateCurrencyPair(currencyPairString: String): Boolean {

        if (currencyPairCache.isEmpty()) getPopulateCurrencyCache()

        return currencyPairSymbolsFlatMap.any { currencyPairSymbol -> currencyPairSymbol == currencyPairString }

    }

    private fun getPopulateCurrencyCache() {
        logger.info("currencyPairCache empty. retrieving new list")
        currencyPairCache = currencyRepository.findAll().map { it.toDomain() }
        currencyPairSymbolsFlatMap = currencyPairCache.map { it.symbol }
        logger.info("currencyPairCache count. ${currencyPairCache.size}")
    }
}