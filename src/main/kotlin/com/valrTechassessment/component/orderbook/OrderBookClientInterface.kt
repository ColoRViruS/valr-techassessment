package com.valrTechassessment.component.orderbook

import com.valrTechassessment.models.orderBook.OrderBookDomainDto

interface OrderBookClientInterface {

    fun getOrderbook(currencyPair: String): OrderBookDomainDto

}