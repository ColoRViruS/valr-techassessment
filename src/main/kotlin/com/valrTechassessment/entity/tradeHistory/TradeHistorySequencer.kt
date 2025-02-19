package com.valrTechassessment.entity.tradeHistory

object TradeHistorySequencer {
    private var current = 1

    fun next(): Long {
        return current++.toLong()
    }
}