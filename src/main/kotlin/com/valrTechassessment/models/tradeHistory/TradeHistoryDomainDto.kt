package com.valrTechassessment.models.tradeHistory

import java.time.OffsetDateTime

data class TradeHistoryDomainDto(
    val price: String,
    val quantity: String,
    val currencyPair: String,
    val tradedAt: OffsetDateTime?,
    val takerSide: String,
    val sequenceId: Long,
    val id: String,
    val quoteVolume: String
)
