package com.valrTechassessment.component.orderbook

import com.valrTechassessment.clients.orderbook.clientModels.AskOrdersResponse
import com.valrTechassessment.models.orderBook.OrderBook

interface OrderBookClientInterface {

    fun getOrderbook(currencyPair: String): OrderBook

    fun addOrder(
        currencyPair: String,
        askOrders: AskOrdersResponse
    )

    fun deleteOrder() {

    }
}