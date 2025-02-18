package com.valrTechassessment.clients.currency

import com.valrTechassessment.clients.currency.clientModels.CurrencyPairClientDto
import com.valrTechassessment.component.currency.CurrencyClientInterface
import com.valrTechassessment.models.currency.CurrencyPairs
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository

@Repository
class CurrencyClient : CurrencyClientInterface {

    private val logger: Logger = LoggerFactory.getLogger(this::class.simpleName)
    private val currencyPairsRepo = mutableListOf<CurrencyPairClientDto>()

    init {
        seedMockCurrencyPairsCache()
    }

    override fun getCurrencyPairsList(): List<CurrencyPairs> {
        if (currencyPairsRepo.isEmpty()) seedMockCurrencyPairsCache()
        return currencyPairsRepo.map { it.toDomain() }
    }

    private fun seedMockCurrencyPairsCache() {
        //mock instead of Calling /v1/public/pairs or cache
        try {
            val inputStream = ClassPathResource("CurrencyPairsSample.json").inputStream
            val fileContent = inputStream.bufferedReader().use { it.readText() }

            val mockCurrencyPairList = Json.decodeFromString<List<CurrencyPairClientDto>>(fileContent)
            currencyPairsRepo.addAll(mockCurrencyPairList)
        } catch (e: Exception) {
            logger.warn(e.message)
            throw e
        }
    }

}