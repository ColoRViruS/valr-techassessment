package com.valrTechassessment.controller

import com.valrTechassessment.LimitorderApiClient
import com.valrTechassessment.PostLimitOrderRequest
import com.valrTechassessment.service.LimitOrderService
import com.valrTechassessment.service.models.limitOrder.toDomain
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class LimitOrderController(
    private val limitOrderService: LimitOrderService
) : LimitorderApiClient {

    override fun postLimitOrder(
        X_VALR_API_KEY: String,
        postLimitOrderRequest: PostLimitOrderRequest
    ): ResponseEntity<Void>? {
        val accepted = limitOrderService.createLimitOrder(
            createLimitOrderDomainDto = postLimitOrderRequest.toDomain()
        )
        return ResponseEntity(
            if (accepted) HttpStatus.ACCEPTED else {
                HttpStatus.NOT_ACCEPTABLE
            }
        )
    }
}