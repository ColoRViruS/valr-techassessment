package com.valrTechassessment.component

import com.valrTechassessment.clients.currency.CurrencyClient
import com.valrTechassessment.component.currency.CurrencyComponent
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class CurrencyComponentTest {

    private lateinit var currencyClient: CurrencyClient
    private lateinit var currencyComponent: CurrencyComponent

    @BeforeEach
    fun setup() {
        currencyClient = CurrencyClient()
        currencyComponent = CurrencyComponent(currencyClient)
    }

    @Test
    fun `validate a Currency Pair successful `() {
        //given
        val currencyPair = "BTCZAR"
        //when
        val resultBoolean = currencyComponent.validateCurrencyPair(currencyPair)
        //then
        assert(resultBoolean)

    }


}