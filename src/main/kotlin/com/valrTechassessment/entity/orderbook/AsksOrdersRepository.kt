package com.valrTechassessment.entity.orderbook

import com.valrTechassessment.entity.orderbook.clientModels.AsksOrdersEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AsksOrdersRepository : JpaRepository<AsksOrdersEntity, Int> {
}