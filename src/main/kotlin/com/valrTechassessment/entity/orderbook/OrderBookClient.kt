package com.valrTechassessment.entity.orderbook

import com.valrTechassessment.entity.orderbook.clientModels.OrderBookEntity
import com.valrTechassessment.entity.orderbook.clientModels.Sequencer
import com.valrTechassessment.component.orderbook.OrderBookClientInterface
import com.valrTechassessment.service.models.orderBook.OrderBookDomainDto
import com.valrTechassessment.service.models.orderBook.OrderDomainDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.UUID

@Repository
class OrderBookClient : OrderBookClientInterface {

    private val currencyToOrderBookMap = mutableMapOf<String, OrderBookEntity>()
    private val logger = LoggerFactory.getLogger(this::class.simpleName)
    private val sequencer = Sequencer()

    init {

    }

    override fun getOrderbook(
        currencyPair: String
    ): OrderBookDomainDto {

        logger.info("Getting Orderbook for currency pair: $currencyPair")
        val currencyPairOrderBook = currencyToOrderBookMap.getOrPut(key = currencyPair) {
            emptyOrderbook()
        }

        val sortedMap = currencyPairOrderBook.copy(
            asks = currencyPairOrderBook.asks.sortedBy { it.price },
            bids = currencyPairOrderBook.bids.sortedByDescending { it.price }
        )
        return sortedMap.toDomain()
    }

    override fun addToOrderbook(order: OrderDomainDto) {
        TODO("Not yet implemented")
    }

    override fun removeOrder(uuid: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeUuidList(list: List<UUID>) {
        TODO("Not yet implemented")
    }

    private fun emptyOrderbook() =
        OrderBookEntity(
            asks = mutableListOf(),
            bids = mutableListOf(),
            lastChange = OffsetDateTime.now(),
            sequenceNumber = sequencer.next()
        )

}