package com.franzzle.pixelart.svg

import org.w3c.dom.Document
import org.w3c.dom.bootstrap.DOMImplementationRegistry
import org.w3c.dom.ls.DOMImplementationLS
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.*
import java.util.stream.IntStream
import javax.imageio.ImageIO
import javax.xml.parsers.ParserConfigurationException

class Converter {
    var loadedImagePath: String? = null
        private set
    private var image: BufferedImage? = null
    private var width = 0
    private var height = 0
    private var threshold = 0f
    fun destroy() {
        flushImageSettings()
    }

    private fun flushImageSettings() {
        if (image != null) {
            image!!.flush()
            image = null
            width = 0
            height = 0
        }
    }

    @Throws(IOException::class)
    private fun setImageSettings() {
        flushImageSettings()
        require(!(loadedImagePath == null || loadedImagePath!!.isEmpty())) { "You must use the `loadImage` method first" }
        image = ImageIO.read(File(loadedImagePath))
        //TODO 40x60 for now, fix later
        width = 40
        height = 60
    }

    fun getThreshold(): Float {
        return threshold
    }

    fun setThreshold(threshold: Float) {
        require(!(threshold < 0 || threshold > 255)) { "The submitted threshold is invalid, value must be between 0 and 255" }
        this.threshold = threshold
    }

    fun loadImage(path: String?): Converter {
        require(!(path == null || path.isEmpty())) { "Supplied URL / path is invalid" }
        loadedImagePath = path
        return this
    }

    @Throws(ParserConfigurationException::class)
    fun generateSVG(): String {
        val svg = toXML()
        val domImplementationLS = svg.implementation.getFeature("LS", "3.0") as DOMImplementationLS
        val lsSerializer = domImplementationLS.createLSSerializer()
        return lsSerializer.writeToString(svg)
    }

    @Throws(ParserConfigurationException::class, IOException::class)
    fun saveSVG(path: String?): Int {
        val svg = toXML()
        val domImplementationLS = svg.implementation.getFeature("LS", "3.0") as DOMImplementationLS
        val lsSerializer = domImplementationLS.createLSSerializer()
        lsSerializer.writeToURI(svg, path)
        return 0
    }

    @Throws(ParserConfigurationException::class)
    fun toXML(): Document {
        try {
            setImageSettings()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        var svgh = generateSvgFromRaster(DIRECTION_HORIZONTAL)
        val svgv = generateSvgFromRaster(DIRECTION_VERTICAL)
        if (svgh.getElementsByTagName("rect").length < svgv.getElementsByTagName("rect").length) {
            svgh = svgv
        }
        flushImageSettings()
        return svgh
    }

    @Throws(ParserConfigurationException::class)
    private fun generateSvgFromRaster(direction: Int): Document {
        val svg = createSvgDocument()
        if (direction == DIRECTION_HORIZONTAL) {
            IntStream.range(0, height).forEach { y: Int ->
                var numberOfConsecutivePixels: Int
                var x = 0
                while (x < width) {
                    numberOfConsecutivePixels = createLine(svg, x, y, direction)
                    x += numberOfConsecutivePixels
                }
            }
        } else {
            IntStream.range(0, width).forEach { x: Int ->
                var numberOfConsecutivePixels: Int
                var y = 0
                while (y < height) {
                    numberOfConsecutivePixels = createLine(svg, x, y, direction)
                    y += numberOfConsecutivePixels
                }
            }
        }
        return svg
    }

    @Throws(ParserConfigurationException::class)
    private fun createSvgDocument(): Document {
        try {
            var registry = DOMImplementationRegistry.newInstance()
            val impl = registry.getDOMImplementation("XML 3.0")
            //TODO Fix
            val documentType = impl.createDocumentType("", "", "")
            val doc = impl.createDocument("", "", documentType)
            val svg = doc.createElement("svg")
            svg.setAttribute("xmlns", "http://www.w3.org/2000/svg")
            svg.setAttribute("shape-rendering", "crispEdges")
            svg.setAttribute("width", Integer.toString(width))
            svg.setAttribute("height", Integer.toString(height))
            svg.setAttribute("viewBox", "0 0 $width $height")
            doc.appendChild(svg)
            return doc
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }
    }

    private fun createLine(svg: Document, x: Int, y: Int, direction: Int): Int {
        val rgba = getPixelColors(x, y)
        var delta = 1
        while (isSimilarPixel(rgba, x, y, delta, direction)) {
            delta++
        }
        createRectElement(svg, rgba, x, y, delta, direction)
        return delta
    }

    private fun getPixelColors(x: Int, y: Int): IntArray {
        val pixel = image!!.getRGB(x, y)
        val red = pixel shr 16 and 0xff
        val green = pixel shr 8 and 0xff
        val blue = pixel and 0xff
        return intArrayOf(red, green, blue)
    }

    private fun isSimilarPixel(rgba: IntArray, x: Int, y: Int, delta: Int, direction: Int): Boolean {
        return if (direction == DIRECTION_HORIZONTAL) {
            val res = x + delta
            res < width && Objects.deepEquals(rgba, getPixelColors(res, y))
        } else {
            val res = y + delta
            res < height && Objects.deepEquals(rgba, getPixelColors(x, res))
        }
    }

    private fun createRectElement(svg: Document, rgba: IntArray, x: Int, y: Int, width: Int, direction: Int) {
        val rectWidth = if (direction == DIRECTION_VERTICAL) 1 else width
        val rectHeight = if (direction == DIRECTION_VERTICAL) width else 1
        val rect = svg.createElement("rect")
        rect.setAttribute("x", Integer.toString(x))
        rect.setAttribute("y", Integer.toString(y))
        rect.setAttribute("width", Integer.toString(rectWidth))
        rect.setAttribute("height", Integer.toString(rectHeight))
        rect.setAttribute("fill", "rgb(" + rgba[0] + "," + rgba[1] + "," + rgba[2] + ")")
        val alpha = rgba[3]
        if (alpha > 0) {
            rect.setAttribute("fill-opacity", ((128 - alpha) / 128.0).toString())
        }
        svg.documentElement.appendChild(rect)
    }

    private fun checkThreshold(colorA: IntArray, colorB: IntArray): Boolean {
        val distance = Math.sqrt(
            Math.pow((colorB[0] - colorA[0]).toDouble(), 2.0) +
                    Math.pow((colorB[1] - colorA[1]).toDouble(), 2.0) +
                    Math.pow((colorB[2] - colorA[2]).toDouble(), 2.0)
        )
        return threshold > distance
    }

    companion object {
        const val DIRECTION_HORIZONTAL = 1
        const val DIRECTION_VERTICAL = 2
    }
}
