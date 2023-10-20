package com.franzzle.pixelart.svg

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * How to setup
 * https://springdoc.org/migrating-from-springfox.html
 * https://springdoc.org/index.html#how-can-i-hide-an-operation-or-a-controller-from-documentation
 */
@Configuration
class OpenAPIConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        val version = Info().title("Your API Title").version("1.0")
        return OpenAPI()
            .info(version)
    }
}