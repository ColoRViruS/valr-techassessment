package com.valrTechassessment.controller

import com.valrTechassessment.TradeHistoryDetails
import com.valrTechassessment.TradehistoryApiClient
import com.valrTechassessment.service.TradeHistoryService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class TradeHistoryController(
    private val tradeHistoryService: TradeHistoryService
) : TradehistoryApiClient {

    override fun getTradeHistory(
        currencyPair: String,
        pageSize: Int?,
        pageNumber: Int?
    ): ResponseEntity<List<TradeHistoryDetails>> {
        return ResponseEntity.ok(
            tradeHistoryService.getTradeHistory(
                currencyPair = currencyPair,
                limit = pageSize,
                skip = pageNumber
            )
        )
    }
}