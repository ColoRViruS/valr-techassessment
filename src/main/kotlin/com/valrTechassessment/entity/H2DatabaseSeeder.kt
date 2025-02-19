package com.valrTechassessment.entity

import com.valrTechassessment.entity.currency.CurrencyPairEntity
import com.valrTechassessment.entity.currency.CurrencyRepository
import com.valrTechassessment.entity.orderbook.clientModels.MockOrderBook
import com.valrTechassessment.entity.tradeHistory.clientModels.TradeHistoryClientDto
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class H2DatabaseSeeder(
    private val currencyRepository: CurrencyRepository,
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.simpleName)

    init {
        seedMockCurrencyPairsCache()
        seedMockOrderbookMap()
    }

    private fun seedMockCurrencyPairsCache() {
        //mock instead of Calling /v1/public/pairs or cache
        try {
            val inputStream = ClassPathResource("CurrencyPairsSample.json").inputStream
            val fileContent = inputStream.bufferedReader().use { it.readText() }

            val mockCurrencyPairList = Json.decodeFromString<List<CurrencyPairEntity>>(fileContent)
            currencyRepository.saveAll(mockCurrencyPairList)
            println()
        } catch (e: Exception) {
            logger.warn(e.message)
            throw e
        }
    }

    private fun seedMockOrderbookMap() {
        try {
            val inputStream = ClassPathResource("OrderbookSample.json").inputStream
            val fileContent = inputStream.bufferedReader().use { it.readText() }

            val mockOrderBook = Json.decodeFromString<MockOrderBook>(fileContent)

//            mockOrderBook.asks.forEach { orders ->
//                val clientOrderBook = currencyToOrderBookMap.getOrPut(key = orders.currencyPair) {
//                    emptyOrderbook()
//                }
//                clientOrderBook.asks[UUID.fromString(orders.uuid)] = orders
//            }
//
//            mockOrderBook.bids.forEach { orders ->
//                val clientOrderBook = currencyToOrderBookMap.getOrPut(key = orders.currencyPair) {
//                    emptyOrderbook()
//                }
//                clientOrderBook.bids[UUID.fromString(orders.uuid)] = orders
//            }
        } catch (e: Exception) {
            logger.warn(e.message)
            throw e
        }
    }

    private fun seedMockTradeHistoryMap() {
        try {
            val inputStream = ClassPathResource("TradeHistorySample.json").inputStream
            val fileContent = inputStream.bufferedReader().use { it.readText() }

            val mockTradeHistory = Json.decodeFromString<List<TradeHistoryClientDto>>(fileContent)
//            mockTradeHistory.forEach { tradeHistory ->
//                val tradeHistoryList = currencyToTradeHistoryMap.getOrPut(key = tradeHistory.currencyPair) {
//                    mutableListOf()
//                }
//                tradeHistoryList.add(tradeHistory)
//            }
        } catch (e: Exception) {
            logger.warn(e.message)
            throw e
        }
    }
}