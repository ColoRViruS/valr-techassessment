package com.valrTechassessment.entity.tradeHistory

import com.valrTechassessment.entity.orderbook.OrderBookEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TradeHistoryRepository : JpaRepository<TradeHistoryEntity, Int> {

    fun findByCurrencyPair(
        currencyPair: String,
        pageable: Pageable = PageRequest.of(
            0,
            Int.MAX_VALUE
        )
    ): Page<TradeHistoryEntity>
}