package com.valrTechassessment.entity.orderbook

import com.valrTechassessment.entity.orderbook.clientModels.BidsOrdersEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BidsOrdersRepository : JpaRepository<BidsOrdersEntity, Int> {
}