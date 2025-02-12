package com.valrTechassessment.service

import com.valrTechassessment.GetOrderBookResponse
import com.valrTechassessment.component.CurrencyComponent
import com.valrTechassessment.exception.InvalidCurrencyPairException
import com.valrTechassessment.models.OrderBook
import com.valrTechassessment.models.toDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class OrderbookService(
    private val currencyComponent: CurrencyComponent
) {
    private val logger = LoggerFactory.getLogger("OrderbookService")

    fun getOrderbook(currencyPair: String): GetOrderBookResponse {
        logger.info("getOrderbook request: $currencyPair")
        val validCurrencyPair = currencyComponent.validateCurrencyPair(currencyPair.trim())
        if (validCurrencyPair) throw InvalidCurrencyPairException()



        return OrderBook(
            emptyList(),
            lastChange = OffsetDateTime.now(),
            sequenceNumber = 123
        ).toDto()

    }

}