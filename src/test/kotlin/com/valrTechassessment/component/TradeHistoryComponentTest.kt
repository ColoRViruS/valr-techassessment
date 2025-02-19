package com.valrTechassessment.component

import com.valrTechassessment.entity.H2DatabaseSeeder
import com.valrTechassessment.entity.currency.CurrencyRepository
import com.valrTechassessment.entity.orderbook.OrderBookRepository
import com.valrTechassessment.entity.tradeHistory.TradeHistoryRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.assertTrue

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TradeHistoryComponentTest {

    private lateinit var h2DatabaseSeeder: H2DatabaseSeeder
    private lateinit var tradeHistoryRepository: TradeHistoryRepository
    private lateinit var tradeHistoryComponent: TradeHistoryComponent

    @BeforeAll
    fun beforeAllSetup(
        @Autowired tradeHistoryRepository: TradeHistoryRepository
    ) {
        this.tradeHistoryRepository = tradeHistoryRepository
        h2DatabaseSeeder = H2DatabaseSeeder(
            tradeHistoryRepository = tradeHistoryRepository,
        )
    }

    @BeforeEach
    fun setup() {
        tradeHistoryComponent = TradeHistoryComponent(tradeHistoryRepository)
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