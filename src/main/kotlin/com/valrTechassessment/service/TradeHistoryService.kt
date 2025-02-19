package com.valrTechassessment.service

import com.valrTechassessment.TradeHistoryDetails
import com.valrTechassessment.component.CurrencyComponent
import com.valrTechassessment.component.TradeHistoryComponent
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
            limit = limit,
            skip = skip
        )

        return tradeHistory.map { it.toResponseDto() }

    }

}