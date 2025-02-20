package com.valrTechassessment.component

import com.valrTechassessment.entity.H2DatabaseSeeder
import com.valrTechassessment.entity.currency.CurrencyRepository
import com.valrTechassessment.entity.orderbook.AsksOrdersRepository
import com.valrTechassessment.entity.orderbook.OrderBookRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderBookComponentTest() {
    private lateinit var h2DatabaseSeeder: H2DatabaseSeeder
    private lateinit var orderBookRepository: OrderBookRepository
    private lateinit var asksOrdersRepository: AsksOrdersRepository
    private lateinit var orderBookComponent: OrderBookComponent

    @BeforeAll
    fun beforeAllSetup(
        @Autowired currencyRepository: CurrencyRepository,
        @Autowired orderBookRepository: OrderBookRepository,
        @Autowired asksOrdersRepository: AsksOrdersRepository,
    ) {
        this.orderBookRepository = orderBookRepository
        this.asksOrdersRepository = asksOrdersRepository
        h2DatabaseSeeder = H2DatabaseSeeder(
            currencyRepository = currencyRepository,
            orderBookRepository = orderBookRepository
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

    @Test
    fun `test repository Delete by ID`() {
        val orderId = 1

        println(asksOrdersRepository.findAll().size)
        println(asksOrdersRepository.findById(1))
        println(asksOrdersRepository.deleteById(1))
        println(asksOrdersRepository.findById(1))

    }
}