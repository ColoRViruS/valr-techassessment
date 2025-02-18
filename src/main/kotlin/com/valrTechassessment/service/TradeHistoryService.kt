package com.valrTechassessment.service

import com.valrTechassessment.TradeHistoryDetails
import com.valrTechassessment.component.currency.CurrencyComponent
import com.valrTechassessment.component.tradeHistory.TradeHistoryComponent
import com.valrTechassessment.exception.InvalidCurrencyPairException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TradeHistoryService(
    private val currencyComponent: CurrencyComponent,
    private val tradeHistoryComponent: TradeHistoryComponent
) {

    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    fun getTradeHistory(
        currencyPair: String,
        limit: Int?,
        skip: Int?
    ): List<TradeHistoryDetails> {
        logger.info("getTradeHistory request: $currencyPair, limit: $limit, skip: $skip")
        val validCurrencyPair = currencyComponent.validateCurrencyPair(currencyPair.trim())
        if (!validCurrencyPair) throw InvalidCurrencyPairException()

        val tradeHistory = tradeHistoryComponent.getTradeHistory(
            currencyPair = currencyPair,
            limit = limit ?: -1,
            skip = skip ?: -1
        )

        return tradeHistory.map { it.toResponseDto() }

    }

}