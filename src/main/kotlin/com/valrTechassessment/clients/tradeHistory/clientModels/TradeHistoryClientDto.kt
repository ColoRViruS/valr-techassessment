package com.valrTechassessment.clients.tradeHistory.clientModels

import com.valrTechassessment.clients.tradeHistory.serializer.OffsetDateTimeSerializer
import com.valrTechassessment.models.BuySellSideEnum
import com.valrTechassessment.models.tradeHistory.TradeHistoryDomainDto
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class TradeHistoryClientDto(
    val price: String,
    val quantity: String,
    val currencyPair: String,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val tradedAt: OffsetDateTime,
    val takerSide: BuySellSideEnum,
    val sequenceId: Long,
    val id: String,
    val quoteVolume: String
) {
    fun toDomain() = TradeHistoryDomainDto(
        tradePrice = price,
        tradeQuantity = quantity,
        tradeCurrencyPair = currencyPair,
        tradedAtDate = tradedAt,
        tradeTakerSide = takerSide,
        tradeSequenceId = sequenceId,
        tradeId = id,
        tradeQuoteVolume = quoteVolume
    )
}
