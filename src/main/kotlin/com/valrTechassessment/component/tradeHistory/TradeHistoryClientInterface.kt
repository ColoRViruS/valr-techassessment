package com.valrTechassessment.component.tradeHistory

import com.valrTechassessment.models.tradeHistory.TradeHistoryDomainDto

interface TradeHistoryClientInterface {

    fun getTradeHistory(
        currencyPair: String,
        limit: Int,
        skip: Int
    ): List<TradeHistoryDomainDto>

}