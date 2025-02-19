package com.valrTechassessment.entity

import com.valrTechassessment.entity.currency.CurrencyPairEntity
import com.valrTechassessment.entity.currency.CurrencyRepository
import com.valrTechassessment.entity.orderbook.OrderBookRepository
import com.valrTechassessment.entity.orderbook.clientModels.MockOrderBook
import com.valrTechassessment.entity.orderbook.clientModels.OrderBookEntity
import com.valrTechassessment.entity.tradeHistory.clientModels.TradeHistoryClientDto
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class H2DatabaseSeeder(
    private val currencyRepository: CurrencyRepository,
    private val orderBookRepository: OrderBookRepository
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

            val totalCurrencyPairsStrings = mockOrderBook.asks.map { it.currencyPair }
                .plus(mockOrderBook.bids.map { it.currencyPair })

            val currencyPairsEntity = currencyRepository.findAllBySymbolIn(totalCurrencyPairsStrings)

            val orderbooks = currencyPairsEntity.map { currencyPair ->
                OrderBookEntity(
                    currencyPair = currencyPair.symbol,
                    bids = mockOrderBook.bids.filter { it.currencyPair == currencyPair.symbol },
                    asks = mockOrderBook.asks.filter { it.currencyPair == currencyPair.symbol },
                    lastChange = OffsetDateTime.now(),
                    sequenceNumber = OrderBookSequencer.next()
                )
            }
            println(orderbooks.size)
            orderBookRepository.saveAll(orderbooks)

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