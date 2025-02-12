package com.valrTechassessment.exception

import com.valrTechassessment.ClientFriendlyException
import com.valrTechassessment.ValrTechassessmentApplication
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice(basePackageClasses = [ValrTechassessmentApplication::class])
class ExceptionHandler {

    private val logger = LoggerFactory.getLogger("ExceptionHandler")

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    @ResponseBody
    fun handleUnhandledErrors(throwable: Throwable): ClientFriendlyException {
        logger.error(
            "Unhandled Exception",
            throwable
        )
        return ClientFriendlyException().reason("Something went wrong. Please contact support.")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingDataException::class)
    @ResponseBody
    fun handleMissingData(throwable: MissingDataException): ClientFriendlyException {
        logger.error(
            "Request is missing Data",
            throwable
        )
        return ClientFriendlyException().reason("Request is missing data")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCurrencyPairException::class)
    @ResponseBody
    fun handleMissingData(throwable: InvalidCurrencyPairException): ClientFriendlyException {
        logger.error(
            "The currency pair is invalid",
            throwable
        )
        return ClientFriendlyException().reason("Request is missing data")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    @ResponseBody
    fun handleBadReqeust(throwable: BadRequestException): ClientFriendlyException {
        logger.error(
            "Bad Request",
            throwable
        )
        return ClientFriendlyException().reason("Bad Request")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseBody
    fun illegalArgumentException(throwable: IllegalArgumentException): ClientFriendlyException {
        logger.error(
            "IllegalArgument",
            throwable
        )
        return ClientFriendlyException().reason(throwable.message)
    }
}