package com.valrTechassessment.entity.tradeHistory

import com.valrTechassessment.entity.tradeHistory.clientModels.TradeHistoryClientDto
import com.valrTechassessment.component.tradeHistory.TradeHistoryClientInterface
import com.valrTechassessment.service.models.tradeHistory.TradeHistoryDomainDto
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository

@Repository
class TradeHistoryClient : TradeHistoryClientInterface {

    private val currencyToTradeHistoryMap = mutableMapOf<String, MutableList<TradeHistoryClientDto>>()
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    init {
        seedMockTradeHistoryMap()
    }

    override fun getTradeHistory(
        currencyPair: String,
        limit: Int,
        skip: Int
    ): List<TradeHistoryDomainDto> {
        logger.info("Getting Trade History for currency pair: $currencyPair")
        val tradeHistoryList = currencyToTradeHistoryMap.getOrPut(key = currencyPair) {
            mutableListOf()
        }.sortedByDescending { it.tradedAt }

        val skippedTradeHistory = if (skip != -1) {
            tradeHistoryList.drop(skip)
        } else tradeHistoryList

        val limitedTradeHistory = if (limit != -1) {
            skippedTradeHistory.take(limit)
        } else skippedTradeHistory

        return limitedTradeHistory.map { it.toDomain() }
    }

    private fun seedMockTradeHistoryMap() {
        try {
            val inputStream = ClassPathResource("TradeHistorySample.json").inputStream
            val fileContent = inputStream.bufferedReader().use { it.readText() }

            val mockTradeHistory = Json.decodeFromString<List<TradeHistoryClientDto>>(fileContent)
            mockTradeHistory.forEach { tradeHistory ->
                val tradeHistoryList = currencyToTradeHistoryMap.getOrPut(key = tradeHistory.currencyPair) {
                    mutableListOf()
                }
                tradeHistoryList.add(tradeHistory)
            }
        } catch (e: Exception) {
            logger.warn(e.message)
            throw e
        }
    }
}