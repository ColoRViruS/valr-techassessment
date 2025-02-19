package com.valrTechassessment.entity.orderbook

import com.valrTechassessment.entity.orderbook.clientModels.BidsOrdersEntity
import com.valrTechassessment.entity.orderbook.clientModels.AsksOrdersEntity
import com.valrTechassessment.entity.tradeHistory.serializer.OffsetDateTimeSerializer
import com.valrTechassessment.service.models.orderBook.OrderBookDomainDto
import jakarta.persistence.*
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
    val asks: MutableList<AsksOrdersEntity>,
    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "order_book_id")
    val bids: MutableList<BidsOrdersEntity>,
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
        asks = mutableListOf(),
        bids = mutableListOf(),
        lastChange = OffsetDateTime.now(),
        sequenceNumber = 0
    )
}
