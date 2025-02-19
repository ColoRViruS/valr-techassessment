package com.valrTechassessment.entity.tradeHistory

import com.valrTechassessment.entity.tradeHistory.serializer.OffsetDateTimeSerializer
import com.valrTechassessment.service.models.BuySellSideEnum
import com.valrTechassessment.service.models.tradeHistory.TradeHistoryDomainDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
@Entity
data class TradeHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val entityId: Int? = null,
    val price: String,
    val quantity: String,
    val currencyPair: String,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val tradedAt: OffsetDateTime,
    val takerSide: BuySellSideEnum,
    val sequenceId: Long,
    @SerialName("id")
    val tradeId: String,
    val quoteVolume: String
) {

    constructor() : this(
        entityId = null,
        price = "",
        quantity = "",
        currencyPair = "",
        tradedAt = OffsetDateTime.now(),
        takerSide = BuySellSideEnum.BUY,
        sequenceId = 0L,
        tradeId = "",
        quoteVolume = ""
    )

    fun toDomain() = TradeHistoryDomainDto(
        tradePrice = price,
        tradeQuantity = quantity,
        tradeCurrencyPair = currencyPair,
        tradedAtDate = tradedAt,
        tradeTakerSide = takerSide,
        tradeSequenceId = sequenceId,
        tradeId = tradeId,
        tradeQuoteVolume = quoteVolume
    )
}