package com.franzzle.pixelart.svg

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
@Tag(name = "status", description = "API to handle the status of things")
class StatusController {

    @Operation(summary = "Get status", description = "Get Status")
    @GetMapping("/status")
    fun statusCheck(): String? {
        return "Working..."
    }
}