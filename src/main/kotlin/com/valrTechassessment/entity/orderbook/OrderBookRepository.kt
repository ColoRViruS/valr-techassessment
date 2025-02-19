package com.valrTechassessment.entity.orderbook

import org.springframework.data.jpa.repository.JpaRepository

interface OrderBookRepository : JpaRepository<OrderBookEntity, Int> {
    fun findByCurrencyPair(currencyPair: String): OrderBookEntity
}