package com.franzzle.pixelart.svg.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.util.AssertionErrors
import java.io.File
import javax.imageio.ImageIO
import kotlin.streams.asStream

class ConverterServiceImplTest {
    @DisplayName("Convert png to a svg and verify if it contains expected svg tags")
    @Test
    fun convertToSvg(){
        expectImageToBePresent("test.png")
        val converterServiceImpl = ConverterServiceImpl(SvgServiceImpl())
        val svgOutput = converterServiceImpl.convert(this.javaClass.getResourceAsStream("/test.png"))
        assertTrue(svgOutput!!.contains("<svg"),"svg tag not found")
        assertTrue(svgOutput!!.contains("crispEdges"),"crispEdges not found")

        assertTrue(svgOutput.matches(".*<svg.*>.*</svg>.*".toRegex(RegexOption.DOT_MATCHES_ALL)), "svg tag not found")  ;

        val findAll = Regex("<rect.*?/>").findAll(svgOutput)
        val countBlackLines = findAll.asStream()
            .filter({ it.value.contains("rgb(0,0,0,0)") })
            .count()

        assertEquals(219, countBlackLines);
    }

    private fun expectImageToBePresent(pathImage: String) {
        val resource = javaClass.classLoader.getResource(pathImage)?.file
        val file = File(resource)
        if (file.exists()) {
            val bufferedImage = ImageIO.read(file)
            AssertionErrors.assertNotNull("Should not be null", bufferedImage != null)
        }
    }
}