package com.valrTechassessment

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.test.Test

class LoggerTest {

    val logger = LoggerFactory.getLogger("CurrancyComponent")

    @Test
    fun main() {
        logger.info("This is an info message")
        logger.warn("This is a warning")
        logger.error("This is an error")
        logger.debug("This is an error")
    }
}