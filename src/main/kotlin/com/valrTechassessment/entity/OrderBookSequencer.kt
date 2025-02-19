package com.valrTechassessment.entity

object OrderBookSequencer {
    private var current = 1441109758534356994

    fun next(): Long {
        return current++.toLong()
    }
}