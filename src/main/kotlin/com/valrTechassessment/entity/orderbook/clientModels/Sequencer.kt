package com.valrTechassessment.entity.orderbook.clientModels

class Sequencer {
    private var current = 1

    fun next(): Long {
        return current++.toLong()
    }
}