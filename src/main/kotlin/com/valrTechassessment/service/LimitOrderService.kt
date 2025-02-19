package com.valrTechassessment.service

import com.valrTechassessment.PostLimitOrderResponse
import com.valrTechassessment.component.currency.CurrencyComponent
import com.valrTechassessment.component.orderbook.OrderBookComponent
import com.valrTechassessment.component.tradeHistory.TradeHistoryComponent
import com.valrTechassessment.exception.InvalidCurrencyPairException
import com.valrTechassessment.service.models.limitOrder.CreateLimitOrderDomainDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LimitOrderService(
    private val currencyComponent: CurrencyComponent,
    private val orderBookComponent: OrderBookComponent,
    private val tradeHistoryComponent: TradeHistoryComponent,
) {

    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    fun createLimitOrder(
        createLimitOrderDomainDto: CreateLimitOrderDomainDto
    ): PostLimitOrderResponse {
        logger.info("createLimitOrder request: ${createLimitOrderDomainDto.pair}")
        val validCurrencyPair = currencyComponent.validateCurrencyPair(createLimitOrderDomainDto.pair.trim())
        if (!validCurrencyPair) throw InvalidCurrencyPairException()

        orderBookComponent.handleLimitOrder(createLimitOrderDomainDto)

        return PostLimitOrderResponse()
    }

}