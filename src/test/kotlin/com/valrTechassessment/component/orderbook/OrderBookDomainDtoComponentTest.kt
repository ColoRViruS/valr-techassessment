package com.valrTechassessment.component.orderbook

import com.valrTechassessment.clients.orderbook.OrderBookClient
import com.valrTechassessment.clients.orderbook.clientModels.OrderBookClientDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertInstanceOf

class OrderBookDomainDtoComponentTest {

    private lateinit var orderBookClient: OrderBookClientInterface
    private lateinit var orderBookComponent: OrderBookComponent

    @BeforeEach
    fun setup() {
        orderBookClient = OrderBookClient()
        orderBookComponent = OrderBookComponent(orderBookClient)
    }

    @Test
    fun `get Orderbook`() {
        //given
        val currencyPair = "BTCZAR"
        //when
        assertDoesNotThrow {
            orderBookComponent.getOrderBook(currencyPair)
        }
    }
}