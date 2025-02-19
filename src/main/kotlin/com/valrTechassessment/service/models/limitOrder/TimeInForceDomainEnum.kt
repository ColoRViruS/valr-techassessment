package com.valrTechassessment.service.models.limitOrder

import com.valrTechassessment.TimeInForce

enum class TimeInForceDomainEnum(
    private val requestTimeInForce: TimeInForce
) {
    GTC(TimeInForce.GTC),
    IOC(TimeInForce.IOC),
    FOK(TimeInForce.FOK);

    companion object {
        fun fromRequest(requestTimeInForce: TimeInForce?) =
            if (requestTimeInForce == null) {
                GTC
            } else {
                TimeInForceDomainEnum.entries.find { it.requestTimeInForce == requestTimeInForce } ?: GTC
            }
    }
}



