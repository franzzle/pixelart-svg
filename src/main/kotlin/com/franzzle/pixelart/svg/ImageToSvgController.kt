package com.franzzle.pixelart.svg;

import io.swagger.v3.oas.annotations.Operation
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
@RequestMapping("/api")
class ImageToSvgController {

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "Upload a PNG image", description = "Upload a PNG image to the server.")
    fun uploadImage(@RequestBody imageFile: MultipartFile): ResponseEntity<String> {
        try {
            val bytes = imageFile.bytes
            // You can process the image here
            // For example, save it to a file or perform some operations
            // Be sure to handle exceptions and validation appropriately
            return ResponseEntity.ok("Image uploaded successfully!")
        } catch (e: IOException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image.")
        }
    }
}
