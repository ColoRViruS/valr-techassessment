package com.valrTechassessment.component

import com.valrTechassessment.entity.orderbook.OrderBookRepository
import com.valrTechassessment.service.models.orderBook.OrderBookDomainDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class OrderBookComponent(
    private val orderBookRepository: OrderBookRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    fun getOrderBook(
        currencyPair: String
    ): OrderBookDomainDto {

        logger.info("Getting Orderbook for currency pair: $currencyPair")


        logger.info("asksOrdersRepository.to(): $orderBookRepository")
        val orderbook = orderBookRepository.findByCurrencyPair(currencyPair).toDomain()
        logger.info("orderbook.asksList.to(): ${orderbook.asksList}")
        logger.info("orderbook.asksList.to(): ${orderbook.bidsList}")
            return orderbook
    }
}