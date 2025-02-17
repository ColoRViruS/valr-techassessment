package com.valrTechassessment.component.orderbook

import com.valrTechassessment.clients.currency.CurrencyClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class OrderBookComponent(
    private val currencyClient: CurrencyClient
) {
    private val logger = LoggerFactory.getLogger("OrderBookComponent")



}