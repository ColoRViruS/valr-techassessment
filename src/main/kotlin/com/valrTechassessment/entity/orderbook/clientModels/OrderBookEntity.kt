package com.valrTechassessment.entity.orderbook.clientModels

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
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Entity
data class OrderBookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val currencyPair: String,
    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "order_book_id")
    val asks: List<SellOrdersEntity>,
    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "order_book_id")
    val bids: List<BidsOrdersEntity>,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val lastChange: OffsetDateTime,
    val sequenceNumber: Long
) {

    fun toDomain() = OrderBookDomainDto(
        asksList = asks.map { it.toDomain() },
        bidsList = bids.map { it.toDomain() },
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
