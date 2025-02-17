package com.valrTechassessment.clients.orderbook

import com.valrTechassessment.clients.orderbook.clientModels.AskOrdersResponse
import com.valrTechassessment.clients.orderbook.clientModels.OrderBookResponse
import com.valrTechassessment.clients.orderbook.clientModels.Sequencer
import com.valrTechassessment.component.orderbook.OrderBookClientInterface
import com.valrTechassessment.models.orderBook.OrderBook
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class OrderBookClient : OrderBookClientInterface {

    private val logger = LoggerFactory.getLogger("OrderBookClient")
    val sequencer = Sequencer()

    private val currencyToOrderBookMap = mutableMapOf<String, OrderBookResponse>()

    override fun getOrderbook(currencyPair: String): OrderBook {

        val currencyPairOrderBook = currencyToOrderBookMap.getOrPut(currencyPair) {
            emptyOrderbook()
        }

        return currencyPairOrderBook.toDomain()
    }

    override fun addOrder(
        currencyPair: String,
        askOrders: AskOrdersResponse
    ) {
        val currencyPairOrderBook = currencyToOrderBookMap.getOrPut(currencyPair) {
            emptyOrderbook()
        }

        currencyPairOrderBook.asks.add(askOrders)
        currencyPairOrderBook.lastChange = OffsetDateTime.now()
        currencyPairOrderBook.sequenceNumber = sequencer.next()
    }

    override fun deleteOrder() {

    }

    fun emptyOrderbook() =
        OrderBookResponse(
            asks = mutableListOf(),
            lastChange = OffsetDateTime.now(),
            sequenceNumber = sequencer.next()
        )

}