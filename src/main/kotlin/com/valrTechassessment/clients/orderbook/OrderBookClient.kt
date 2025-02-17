package com.valrTechassessment.clients.orderbook

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.valrTechassessment.clients.orderbook.clientModels.OrderBookClientDto
import com.valrTechassessment.clients.orderbook.clientModels.MockOrderBook
import com.valrTechassessment.clients.orderbook.clientModels.Sequencer
import com.valrTechassessment.component.orderbook.OrderBookClientInterface
import com.valrTechassessment.models.orderBook.OrderBookDomainDto
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class OrderBookClient : OrderBookClientInterface {

    private val currencyToOrderBookMap = mutableMapOf<String, OrderBookClientDto>()
    private val logger = LoggerFactory.getLogger(this::class.simpleName)
    private val sequencer = Sequencer()

    init {
        seedMockOrderbookMap()
    }

    override fun getOrderbook(
        currencyPair: String
    ): OrderBookDomainDto {

        logger.info("Getting Orderbook for currency pair: $currencyPair")
        val currencyPairOrderBook = currencyToOrderBookMap.getOrPut(key = currencyPair) {
            emptyOrderbook()
        }
        return currencyPairOrderBook.toDomain()
    }

    private fun emptyOrderbook() =
        OrderBookClientDto(
            asks = mutableListOf(),
            bids = mutableListOf(),
            lastChange = OffsetDateTime.now(),
            sequenceNumber = sequencer.next()
        )

    private fun seedMockOrderbookMap() {
        val fileContent = try {
            val inputStream = ClassPathResource("OrderbookSample.json").inputStream
            inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            logger.debug(e.message)
            throw e
        }

        val mockOrderBook = Json.decodeFromString<MockOrderBook>(fileContent)

        mockOrderBook.asks.forEach { orders ->
            val clientOrderBook = currencyToOrderBookMap.getOrPut(key = orders.currencyPair) {
                emptyOrderbook()
            }
            clientOrderBook.asks.add(orders)
        }

        mockOrderBook.bids.forEach { orders ->
            val clientOrderBook = currencyToOrderBookMap.getOrPut(key = orders.currencyPair) {
                emptyOrderbook()
            }
            clientOrderBook.asks.add(orders)
        }
    }
}