package com.valrTechassessment.component.orderbook

import com.valrTechassessment.models.orderBook.OrderBookDomainDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class OrderBookComponent(
    private val orderBookClientInterface: OrderBookClientInterface
) {
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    fun getOrderBook(
        currencyPair: String
    ): OrderBookDomainDto {

        logger.info("Getting Orderbook for currency pair: $currencyPair")
        return orderBookClientInterface.getOrderbook(currencyPair)
    }
}