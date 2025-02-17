package com.valrTechassessment.clients.currency

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.valrTechassessment.clients.currency.clientModels.CurrencyPairResponse
import com.valrTechassessment.component.currency.CurrencyClientInterface
import com.valrTechassessment.models.currency.CurrencyPairs
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository

@Repository
class CurrencyClient : CurrencyClientInterface {

    private val logger: Logger = LoggerFactory.getLogger("CurrencyClient")

    override fun getCurrencyPairsList(): List<CurrencyPairs> {
        //mock instead of Calling /v1/public/pairs or cache
        val fileContent = try {
            val inputStream = ClassPathResource("CurrencyPairsSample.json").inputStream
            inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            logger.debug(e.message)
            throw e
        }

        val objectMapper = jacksonObjectMapper()
        val list: List<CurrencyPairResponse> = objectMapper.readValue(fileContent)
        return list.map { it.toDomain() }
    }
}