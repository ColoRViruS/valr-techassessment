package com.valrTechassessment.entity.orderbook.clientModels

import com.valrTechassessment.entity.currency.CurrencyPairEntity
import com.valrTechassessment.entity.tradeHistory.serializer.OffsetDateTimeSerializer
import com.valrTechassessment.service.models.orderBook.OrderBookDomainDto
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Entity
data class OrderBookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val currencyPair: String,
    @OneToMany(
        mappedBy = "orderBookId",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    val asks: List<OrdersClientDto>,
    @OneToMany(
        mappedBy = "orderBookId",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    val bids: List<OrdersClientDto>,
    @Serializable(with = OffsetDateTimeSerializer::class)
    var lastChange: OffsetDateTime,
    var sequenceNumber: Long
) {

    fun toDomain() = OrderBookDomainDto(
        asksList = asks.map { it.toDomain() },
        bidsMap = bids.map { it.toDomain() },
        orderBooklastChange = lastChange,
        orderBookSequenceNumber = sequenceNumber
    )

    constructor() : this(
        id = null,
        currencyPair = "",
        asks = listOf(),
        bids = listOf(),
        lastChange = OffsetDateTime.now(),
        sequenceNumber = 0
    )
}
