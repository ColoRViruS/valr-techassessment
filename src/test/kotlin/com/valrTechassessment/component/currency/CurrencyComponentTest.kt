package com.valrTechassessment.component.currency

import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertFalse

class CurrencyComponentTest {

    private lateinit var currencyClient: CurrencyClientInterface
    private lateinit var currencyComponent: CurrencyComponent

    @BeforeEach
    fun setup() {
//        currencyClient = CurrencyClient()
//        currencyComponent = CurrencyComponent(currencyClient)
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