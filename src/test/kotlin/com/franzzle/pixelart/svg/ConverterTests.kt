package com.franzzle.pixelart.svg

import org.junit.jupiter.api.Test
import org.springframework.test.util.AssertionErrors.assertNotNull
import java.io.File
import javax.imageio.ImageIO

class ConverterTests {
    @Test
    fun loadImage() {
        val resource = javaClass.classLoader.getResource("test.png")?.file
        val file = File(resource)
        if(file.exists()){
            val bufferedImage = ImageIO.read(file)
            assertNotNull("Should not be null", bufferedImage != null)
        }
    }
}


