package com.valrTechassessment.entity.currency

import org.springframework.data.jpa.repository.JpaRepository

interface CurrencyRepository : JpaRepository<CurrencyPairEntity, Int> {
    fun findAllBySymbolIn(symbols: List<String>): List<CurrencyPairEntity>
}