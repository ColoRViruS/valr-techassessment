package com.valrTechassessment.clients.currency

import org.springframework.stereotype.Repository
import org.springframework.core.io.ClassPathResource
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.valrTechassessment.clients.currency.clientModels.CurrencyPairResponse
import com.valrTechassessment.models.CurrencyPairs

@Repository
class CurrencyClient {
    fun getCurrencyPairsList(): List<CurrencyPairs> {
        //Simulate Calling /v1/public/pairs

        val resource = ClassPathResource("currency.json")
        val inputStream = resource.inputStream

        val objectMapper = jacksonObjectMapper()
        val list: List<CurrencyPairResponse> = objectMapper.readValue(inputStream)
        return list.map { it.toDomain() }
    }
}