package com.valrTechassessment.clients.orderbook.clientModels

class Sequencer {
    private var current = 1

    fun next(): Long {
        return current++.toLong()
    }
}