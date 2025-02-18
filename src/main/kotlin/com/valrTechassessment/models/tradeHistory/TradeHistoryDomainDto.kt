package com.valrTechassessment.models.tradeHistory

import com.valrTechassessment.TradeHistoryDetails
import com.valrTechassessment.models.BuySellSideEnum
import java.time.OffsetDateTime

data class TradeHistoryDomainDto(
    val tradePrice: String,
    val tradeQuantity: String,
    val tradeCurrencyPair: String,
    val tradedAtDate: OffsetDateTime?,
    val tradeTakerSide: BuySellSideEnum,
    val tradeSequenceId: Long,
    val tradeId: String,
    val tradeQuoteVolume: String
) {
    fun toResponseDto() = TradeHistoryDetails().apply {
        price = tradePrice
        quantity = tradeQuantity
        currencyPair = tradeCurrencyPair
        tradedAt = tradedAtDate
        takerSide = tradeTakerSide.side
        sequenceId = tradeSequenceId
        id = tradeId
        quoteVolume = tradeQuoteVolume
    }
}
