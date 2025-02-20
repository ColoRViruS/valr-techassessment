package com.valrTechassessment.component

import com.valrTechassessment.entity.H2DatabaseSeeder
import com.valrTechassessment.entity.currency.CurrencyRepository
import com.valrTechassessment.entity.orderbook.OrderBookRepository
import com.valrTechassessment.entity.tradeHistory.TradeHistoryRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LimitOrderComponentTest() {
    private lateinit var h2DatabaseSeeder: H2DatabaseSeeder
    private lateinit var orderBookComponent: OrderBookComponent
    private lateinit var orderBookRepository: OrderBookRepository
    private lateinit var tradeHistoryRepository: TradeHistoryRepository

    @BeforeAll
    fun beforeAllSetup(
        @Autowired currencyRepository: CurrencyRepository,
        @Autowired orderBookRepository: OrderBookRepository,
        @Autowired tradeHistoryRepository: TradeHistoryRepository,
    ) {
        this.orderBookRepository = orderBookRepository
        h2DatabaseSeeder = H2DatabaseSeeder(
            currencyRepository = currencyRepository,
            orderBookRepository = orderBookRepository,
            tradeHistoryRepository = tradeHistoryRepository
        )
    }

    @BeforeEach
    fun setup() {
        orderBookComponent = OrderBookComponent(orderBookRepository)
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