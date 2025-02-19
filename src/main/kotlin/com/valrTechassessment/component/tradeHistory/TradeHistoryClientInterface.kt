package com.valrTechassessment.component.tradeHistory

import com.valrTechassessment.service.models.tradeHistory.TradeHistoryDomainDto

interface TradeHistoryClientInterface {

    fun getTradeHistory(
        currencyPair: String,
        limit: Int,
        skip: Int
    ): List<TradeHistoryDomainDto>

}