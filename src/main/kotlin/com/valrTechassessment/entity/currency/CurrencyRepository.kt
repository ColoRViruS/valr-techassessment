package com.valrTechassessment.entity.currency

import org.springframework.data.jpa.repository.JpaRepository

interface CurrencyRepository : JpaRepository<CurrencyPairEntity, Int> {
}