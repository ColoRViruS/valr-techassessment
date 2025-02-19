package com.valrTechassessment.entity.orderbook

import com.valrTechassessment.entity.orderbook.clientModels.MockOrderBook
import com.valrTechassessment.entity.orderbook.clientModels.OrderBookClientDto
import com.valrTechassessment.entity.orderbook.clientModels.Sequencer
import com.valrTechassessment.component.orderbook.OrderBookClientInterface
import com.valrTechassessment.service.models.orderBook.OrderBookDomainDto
import com.valrTechassessment.service.models.orderBook.OrderDomainDto
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.UUID

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

        val sortedMap = currencyPairOrderBook.copy(
            asks = currencyPairOrderBook.asks.entries.sortedBy { it.value.price }.associate { it.toPair() }.toMutableMap(),
            bids = currencyPairOrderBook.bids.entries.sortedByDescending { it.value.price }.associate { it.toPair() }.toMutableMap(),
        )
        return sortedMap.toDomain()
    }

    override fun addToOrderbook(order: OrderDomainDto) {
        TODO("Not yet implemented")
    }

    override fun removeOrder(uuid: UUID) {
        TODO("Not yet implemented")
    }

    private fun emptyOrderbook() =
        OrderBookClientDto(
            asks = mutableMapOf(),
            bids = mutableMapOf(),
            lastChange = OffsetDateTime.now(),
            sequenceNumber = sequencer.next()
        )

    private fun seedMockOrderbookMap() {
        try {
            val inputStream = ClassPathResource("OrderbookSample.json").inputStream
            val fileContent = inputStream.bufferedReader().use { it.readText() }

            val mockOrderBook = Json.decodeFromString<MockOrderBook>(fileContent)

            mockOrderBook.asks.forEach { orders ->
                val clientOrderBook = currencyToOrderBookMap.getOrPut(key = orders.currencyPair) {
                    emptyOrderbook()
                }
                clientOrderBook.asks[UUID.fromString(orders.uuid)] = orders
            }

            mockOrderBook.bids.forEach { orders ->
                val clientOrderBook = currencyToOrderBookMap.getOrPut(key = orders.currencyPair) {
                    emptyOrderbook()
                }
                clientOrderBook.bids[UUID.fromString(orders.uuid)] = orders
            }
        } catch (e: Exception) {
            logger.warn(e.message)
            throw e
        }
    }
}