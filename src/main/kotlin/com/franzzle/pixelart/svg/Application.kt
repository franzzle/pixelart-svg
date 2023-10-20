package com.franzzle.pixelart.svg

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {
	@Bean
	open fun customOpenAPI(): OpenAPI {
		val version = Info().title("Your API Title").version("1.0")
		return OpenAPI()
				.info(version)
	}
}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}


