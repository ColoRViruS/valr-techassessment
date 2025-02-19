package com.valrTechassessment.entity.orderbook

import com.valrTechassessment.entity.orderbook.clientModels.OrderBookEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderBookRepository : JpaRepository<OrderBookEntity, Int> {
}