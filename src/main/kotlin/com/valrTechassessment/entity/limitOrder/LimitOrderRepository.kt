package com.valrTechassessment.entity.limitOrder

import org.springframework.data.jpa.repository.JpaRepository

interface LimitOrderRepository : JpaRepository<LimitOrderEntity, Int> {
}