package com.franzzle.pixelart.svg;

import com.franzzle.pixelart.svg.service.ConverterService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@RestController
@RequestMapping("/conversion")
@Tag(name = "conversion", description = "API to handle and conversion of a png to svg")
class ImageToSvgController  (
    @Autowired
    private val converterService: ConverterService
) {
    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "Upload a PNG image", description = "Upload a PNG image to the server.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successful response"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    )
    fun uploadImage(@RequestBody imageFile: MultipartFile): ResponseEntity<String> {
        try {

            val imageInputStream = imageFile.resource.inputStream;
            val convert = converterService.convert(imageInputStream)
            // You can process the image here
            // For example, save it to a file or perform some operations
            // Be sure to handle exceptions and validation appropriately
            return ResponseEntity.ok(convert)
        } catch (e: IOException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image.")
        }
    }
}
