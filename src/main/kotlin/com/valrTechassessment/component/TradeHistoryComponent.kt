package com.valrTechassessment.component

import com.valrTechassessment.entity.tradeHistory.TradeHistoryRepository
import com.valrTechassessment.service.models.tradeHistory.TradeHistoryDomainDto
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class TradeHistoryComponent(
    private val tradeHistoryRepository: TradeHistoryRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    fun getTradeHistory(
        currencyPair: String,
        limit: Int = -1,
        skip: Int = -1
    ): List<TradeHistoryDomainDto> {

        logger.info("Getting Trade History for currency pair: $currencyPair")


        val tradeHistory = if (limit == -1) {
            tradeHistoryRepository.findByCurrencyPair(currencyPair = currencyPair)
        } else {
            val pageable = PageRequest.of(
                skip,
                limit
            )
            tradeHistoryRepository.findByCurrencyPair(
                currencyPair = currencyPair,
                pageable = pageable
            )
        }

        return tradeHistory.content.map { it.toDomain() }
    }
}