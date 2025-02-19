package com.valrTechassessment.entity.tradeHistory

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TradeHistoryRepository : JpaRepository<TradeHistoryEntity, Int> {

    fun findByCurrencyPair(
        currencyPair: String,
        pageable: Pageable = PageRequest.of(
            1,
            Int.MAX_VALUE
        )
    ): Page<TradeHistoryEntity>
}