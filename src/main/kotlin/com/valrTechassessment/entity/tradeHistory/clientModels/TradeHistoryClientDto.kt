package com.valrTechassessment.entity.tradeHistory.clientModels

import com.valrTechassessment.entity.tradeHistory.serializer.OffsetDateTimeSerializer
import com.valrTechassessment.service.models.BuySellSideEnum
import com.valrTechassessment.service.models.tradeHistory.TradeHistoryDomainDto
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
