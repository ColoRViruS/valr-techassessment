package com.valrTechassessment.controller

import com.valrTechassessment.EchoTestApi
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class EchoTestController() : EchoTestApi {
    override fun echoTest(): ResponseEntity<String> {
        return ResponseEntity.ok("Echo Test Success")
    }
}