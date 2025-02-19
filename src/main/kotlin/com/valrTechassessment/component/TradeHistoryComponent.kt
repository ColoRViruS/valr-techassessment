package com.valrTechassessment.component

import com.valrTechassessment.component.tradeHistory.TradeHistoryClientInterface
import com.valrTechassessment.service.models.tradeHistory.TradeHistoryDomainDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class TradeHistoryComponent(
    private val tradeHistoryClientInterface: TradeHistoryClientInterface
) {
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    fun getTradeHistory(
        currencyPair: String,
        limit: Int = -1,
        skip: Int = -1
    ): List<TradeHistoryDomainDto> {

        logger.info("Getting Trade History for currency pair: $currencyPair")

        val tradeHistory = tradeHistoryClientInterface.getTradeHistory(
            currencyPair = currencyPair,
            limit = limit,
            skip = skip
        )

        return tradeHistory
    }
}