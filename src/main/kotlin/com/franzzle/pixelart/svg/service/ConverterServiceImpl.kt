package com.franzzle.pixelart.svg.service

import com.franzzle.pixelart.svg.service.ScanDirection.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import java.awt.image.BufferedImage
import java.io.InputStream
import java.util.*
import java.util.function.BiPredicate
import java.util.stream.IntStream
import javax.imageio.ImageIO

@Service
class ConverterServiceImpl(@Autowired private val svgService: SvgService) : ConverterService
{
    override fun convert(inputStream: InputStream?): String? {
        val bufferedImage = ImageIO.read(inputStream)
        inputStream!!.close()
        val svgDoc = svgService.createSvgDoc(bufferedImage.width, bufferedImage.height)
        val svgHorizontal = generateSvgDocumentFromRaster(bufferedImage,svgDoc, HORIZONTAL)
        val svgVertical = generateSvgDocumentFromRaster(bufferedImage,svgDoc, VERTICAL)

        val horizontalLinesShorterThenVertical = BiPredicate<Document, Document> { svgHorDoc, svgVertDoc ->
            val elementsByTagName = svgHorDoc.getElementsByTagName("rect")
            elementsByTagName.length < svgVertDoc.getElementsByTagName("rect").length
        }

        if (horizontalLinesShorterThenVertical.test(svgHorizontal, svgVertical)) {
            return svgService.serializeDocumentToSvgFormat(svgVertical)
        }
        return svgService.serializeDocumentToSvgFormat(svgHorizontal)
    }

    private fun generateSvgDocumentFromRaster(image: BufferedImage,
                                              svg: Document,
                                              scanDirection: ScanDirection): Document {
        when (scanDirection){
            HORIZONTAL -> {
                IntStream.range(0, image.height).forEach { y: Int ->
                    var numberOfConsecutivePixels: Int
                    var x = 0
                    while (x < image.width) {
                        numberOfConsecutivePixels = createLine(image, svg, x, y, scanDirection)
                        x += numberOfConsecutivePixels
                    }
                }
            }
            VERTICAL -> {
                IntStream.range(0, image.width).forEach { x: Int ->
                    var numberOfConsecutivePixels: Int
                    var y = 0
                    while (y < image.height) {
                        numberOfConsecutivePixels = createLine(image, svg, x, y, scanDirection)
                        y += numberOfConsecutivePixels
                    }
                }
            }
        }
        return svg

    }


    private fun createLine(image: BufferedImage,
                           svg: Document,
                           x: Int,
                           y: Int,
                           direction: ScanDirection): Int {
        val rgba = getPixelColors(image, x, y)
        var delta = 1
        while (isSimilarPixel(image, rgba, x, y, delta, direction)) {
            delta++
        }
        createRectElement(svg, rgba, x, y, delta, direction)
        return delta
    }

    private fun getPixelColors(image: BufferedImage,
                               x: Int, y: Int): IntArray {
        val pixel = image.getRGB(x, y)
        val alpha = pixel shr 24 and 0xff
        val red = pixel shr 16 and 0xff
        val green = pixel shr 8 and 0xff
        val blue = pixel and 0xff
        return intArrayOf(red, green, blue, alpha)
    }

    private fun isSimilarPixel(image: BufferedImage,
                               rgba: IntArray,
                               x: Int,
                               y: Int,
                               delta: Int,
                               direction: ScanDirection): Boolean {
        return if (direction == HORIZONTAL) {
            val res = x + delta
            res < image.width && Objects.deepEquals(rgba, getPixelColors(image, res, y))
        } else {
            val res = y + delta
            res < image.height && Objects.deepEquals(rgba, getPixelColors(image, x, res))
        }
    }

    private fun createRectElement(svg: Document,
                                  rgba: IntArray,
                                  x: Int,
                                  y: Int,
                                  width: Int,
                                  direction: ScanDirection) {
        val rectWidth = if (direction == VERTICAL) 1 else width
        val rectHeight = if (direction == VERTICAL) width else 1
        val rect = svg.createElement("rect")
        rect.setAttribute("x", x.toString())
        rect.setAttribute("y", y.toString())
        rect.setAttribute("width", rectWidth.toString())
        rect.setAttribute("height", rectHeight.toString())
        rect.setAttribute("fill", "rgb(" + rgba[0] + "," + rgba[1] + "," + rgba[2] + "," + rgba[3] + ")")
        val alpha = rgba[3]
        if (alpha > 0) {
            rect.setAttribute("fill-opacity", alpha.toString())
        }
        svg.documentElement.appendChild(rect)
    }

}
