package com.valrTechassessment.entity.currency

import com.valrTechassessment.component.currency.CurrencyClientInterface
import com.valrTechassessment.service.models.currency.CurrencyPairs
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository

@Repository
class CurrencyClient(
    currencyRepository: CurrencyRepository,

) : CurrencyClientInterface {

    private val logger: Logger = LoggerFactory.getLogger(this::class.simpleName)
    private val currencyPairsRepo = mutableListOf<CurrencyPairRepoDto>()

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

            val mockCurrencyPairList = Json.decodeFromString<List<CurrencyPairRepoDto>>(fileContent)
            currencyPairsRepo.addAll(mockCurrencyPairList)
        } catch (e: Exception) {
            logger.warn(e.message)
            throw e
        }
    }

}