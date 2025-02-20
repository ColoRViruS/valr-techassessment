package com.valrTechassessment.service

import com.valrTechassessment.PostLimitOrderResponse
import com.valrTechassessment.component.CurrencyComponent
import com.valrTechassessment.component.orderLimits.LimitOrderComponent
import com.valrTechassessment.exception.InvalidCurrencyPairException
import com.valrTechassessment.exception.LimitOrderNotAcceptedException
import com.valrTechassessment.service.models.limitOrder.CreateLimitOrderDomainDto
import com.valrTechassessment.service.models.limitOrder.TimeInForceDomainEnum
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LimitOrderService(
    private val currencyComponent: CurrencyComponent,
    private val limitOrderComponent: LimitOrderComponent,
) {

    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    fun createLimitOrder(
        createLimitOrderDomainDto: CreateLimitOrderDomainDto
    ): Boolean {
        logger.info("createLimitOrder request: ${createLimitOrderDomainDto.pair}")
        val validCurrencyPair = currencyComponent.validateCurrencyPair(createLimitOrderDomainDto.pair.trim())
        if (!validCurrencyPair) throw InvalidCurrencyPairException()

        val accepted = limitOrderComponent.handleLimitOrder(createLimitOrderDomainDto)
        return accepted
    }


}