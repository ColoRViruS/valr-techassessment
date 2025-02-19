package com.valrTechassessment.entity

object OrderBookSequencer {
    private var current = 1

    fun next(): Long {
        return current++.toLong()
    }
}