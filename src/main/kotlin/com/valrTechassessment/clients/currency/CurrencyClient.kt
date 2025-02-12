package com.valrTechassessment.clients.currency

import org.springframework.stereotype.Repository
import org.springframework.core.io.ClassPathResource
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.valrTechassessment.clients.currency.clientModels.CurrencyPairResponse
import com.valrTechassessment.models.CurrencyPairs
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Repository
class CurrencyClient {

    val logger= LoggerFactory.getLogger("CurrancyComponent")

    fun getCurrencyPairsList(): List<CurrencyPairs> {
        //Simulate Calling /v1/public/pairs
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