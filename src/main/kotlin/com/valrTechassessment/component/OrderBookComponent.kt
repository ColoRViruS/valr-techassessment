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
        val some = orderBookRepository.findAll()

        return orderBookRepository.findByCurrencyPair(currencyPair).toDomain()
    }
}