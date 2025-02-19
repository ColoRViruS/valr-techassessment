package com.valrTechassessment.component.tradeHistory

import com.valrTechassessment.component.TradeHistoryComponent
import com.valrTechassessment.entity.tradeHistory.TradeHistoryClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertTrue

class TradeHistoryComponentTest {

    private lateinit var tradeHistoryClient: TradeHistoryClientInterface
    private lateinit var tradeHistoryComponent: TradeHistoryComponent

    @BeforeEach
    fun setup() {
        tradeHistoryClient = TradeHistoryClient()
        tradeHistoryComponent = TradeHistoryComponent(tradeHistoryClient)
    }

    @Test
    fun `get Trade History no limit no skip`() {
        //given
        val currencyPair = "BTCZAR"
        //when

        assertDoesNotThrow {
            tradeHistoryComponent.getTradeHistory(
                currencyPair = currencyPair
            )
        }
    }

    @Test
    fun `get Trade History with limit no skip`() {
        //given
        val currencyPair = "BTCZAR"
        val limit = 10
        //when
        val tradeHistory = tradeHistoryComponent.getTradeHistory(
            currencyPair = currencyPair,
            limit = limit
        )
        //then
        assertTrue { tradeHistory.size <= limit }
    }

    @Test
    fun `get Trade History no limit with skip`() {
        //given
        val currencyPair = "BTCZAR"
        val skip = 10
        val limit = 10
        //when
        val tradeHistory = tradeHistoryComponent.getTradeHistory(
            currencyPair = currencyPair,
            skip = skip,
            limit = limit
        )
        //then
        assertTrue { tradeHistory.size <= limit }
    }

    @Test
    fun `get Trade History with limit with skip`() {
        //given
        val currencyPair = "BTCZAR"
        val skip = 10
        //when
        assertDoesNotThrow {
            tradeHistoryComponent.getTradeHistory(
                currencyPair = currencyPair,
                skip = skip
            )
        }
    }
}