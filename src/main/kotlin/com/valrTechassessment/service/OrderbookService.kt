package com.valrTechassessment.service

import com.valrTechassessment.GetOrderBookResponse
import com.valrTechassessment.component.currency.CurrencyComponent
import com.valrTechassessment.component.orderbook.OrderBookComponent
import com.valrTechassessment.exception.InvalidCurrencyPairException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderbookService(
    private val currencyComponent: CurrencyComponent,
    private val orderBookComponent: OrderBookComponent
) {

    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    fun getOrderbook(currencyPair: String): GetOrderBookResponse {
        logger.info("getOrderbook request: $currencyPair")
        val validCurrencyPair = currencyComponent.validateCurrencyPair(currencyPair.trim())
        if (!validCurrencyPair) throw InvalidCurrencyPairException()

        val orderbook = orderBookComponent.getOrderBook(currencyPair)

        return orderbook.toResponseDto()

    }

}