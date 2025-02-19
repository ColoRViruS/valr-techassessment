package com.valrTechassessment.component

import com.valrTechassessment.entity.H2DatabaseSeeder
import com.valrTechassessment.entity.currency.CurrencyRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test
import kotlin.test.assertFalse

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CurrencyComponentTest() {
    private lateinit var h2DatabaseSeeder: H2DatabaseSeeder
    private lateinit var currencyRepository: CurrencyRepository
    private lateinit var currencyComponent: CurrencyComponent

    @BeforeAll
    fun beforeAllSetup(
        @Autowired currencyRepository: CurrencyRepository,
    ) {
        this.currencyRepository = currencyRepository
        h2DatabaseSeeder = H2DatabaseSeeder(currencyRepository = currencyRepository)
    }

    @BeforeEach
    fun setup() {
        currencyComponent = CurrencyComponent(currencyRepository)
    }

    @Test
    fun `validate a Currency Pair successful`() {
        //given
        val currencyPair = "BTCZAR"
        //when
        val resultBoolean = currencyComponent.validateCurrencyPair(currencyPair)
        //then
        assert(resultBoolean)
    }

    @Test
    fun `validate a Currency Pair negative `() {
        //given
        val currencyPair = "ZARBTC"
        //when
        val resultBoolean = currencyComponent.validateCurrencyPair(currencyPair)
        //then
        assertFalse(resultBoolean)
    }
}