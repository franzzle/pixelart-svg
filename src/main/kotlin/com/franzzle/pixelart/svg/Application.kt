package com.franzzle.pixelart.svg

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@OpenAPIDefinition
@ComponentScan(basePackages = ["com.franzzle.pixelart"])
class Application {

}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}


