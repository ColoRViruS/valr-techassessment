package com.valrTechassessment.entity.orderbook

import com.valrTechassessment.service.models.orderBook.OrderBookDomainDto
import org.springframework.data.jpa.repository.JpaRepository

interface OrderBookRepository : JpaRepository<OrderBookEntity, Int> {
    fun findByCurrencyPair(currencyPair: String): OrderBookEntity
}