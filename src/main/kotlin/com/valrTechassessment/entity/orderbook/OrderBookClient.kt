package com.valrTechassessment.entity.orderbook

import com.valrTechassessment.entity.orderbook.clientModels.OrderBookEntity
import com.valrTechassessment.service.models.orderBook.OrderDomainDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class OrderBookClient {

    private val currencyToOrderBookMap = mutableMapOf<String, OrderBookEntity>()
    private val logger = LoggerFactory.getLogger(this::class.simpleName)


//    fun getOrderbook(
//        currencyPair: String
//    ): OrderBookDomainDto {
//
//        logger.info("Getting Orderbook for currency pair: $currencyPair")
////        val currencyPairOrderBook = currencyToOrderBookMap.getOrPut(key = currencyPair) {
//////            emptyOrderbook()
////        }
//
//        val sortedMap = currencyPairOrderBook.copy(
//            asks = currencyPairOrderBook.asks.sortedBy { it.price },
//            bids = currencyPairOrderBook.bids.sortedByDescending { it.price }
//        )
//        return sortedMap.toDomain()
//    }

    fun addToOrderbook(order: OrderDomainDto) {
        TODO("Not yet implemented")
    }

    fun removeOrder(uuid: UUID) {
        TODO("Not yet implemented")
    }

    fun removeUuidList(list: List<UUID>) {
        TODO("Not yet implemented")
    }


}