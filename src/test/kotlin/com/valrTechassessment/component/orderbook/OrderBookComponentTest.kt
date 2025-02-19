package com.valrTechassessment.component.orderbook

import com.valrTechassessment.component.OrderBookComponent
import com.valrTechassessment.entity.orderbook.OrderBookClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class OrderBookComponentTest {

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