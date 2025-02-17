package com.valrTechassessment.controller

import com.valrTechassessment.GetOrderBookResponse
import com.valrTechassessment.OrderbookApiClient
import com.valrTechassessment.service.OrderbookService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class OrderbookController(
    private val orderbookService: OrderbookService
) : OrderbookApiClient {

    override fun getOrderbook(currencyPair: String): ResponseEntity<GetOrderBookResponse> {
        val orderbook = orderbookService.getOrderbook(currencyPair = currencyPair)
        return ResponseEntity.ok(orderbook)

    }
}