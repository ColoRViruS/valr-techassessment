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

    companion object {
        const val DEFAULT_LIMIT = 40
        const val DEFAULT_SKIP = 0
    }

    fun getTradeHistory(
        currencyPair: String,
        limit: Int? = null,
        skip: Int? = null
    ): List<TradeHistoryDomainDto> {

        logger.info("getTradeHistory currency pair:: $currencyPair, limit: $limit, skip: $skip")

        val pageable = PageRequest.of(
            skip ?: DEFAULT_SKIP,
            limit ?: DEFAULT_LIMIT
        )
        println(pageable)
        val tradeHistory = tradeHistoryRepository.findByCurrencyPair(
            currencyPair = currencyPair,
            pageable = pageable
        )
        return tradeHistory.content.sortedBy { it.tradedAt }.map { it.toDomain() }
    }
}