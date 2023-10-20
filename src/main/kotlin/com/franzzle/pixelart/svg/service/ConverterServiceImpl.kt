package com.franzzle.pixelart.svg.service

import com.franzzle.pixelart.svg.Converter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import java.io.InputStream
import javax.imageio.ImageIO

@Service
class ConverterServiceImpl(
    @Autowired
    private val anotherService: SvgService
) : ConverterService {

    override fun convert(inputStream: InputStream?): String? {
        val bufferedImage = ImageIO.read(inputStream)
        System.out.println("Width : " + bufferedImage.width + " and Height : " + bufferedImage.height)
        val createSvgDoc = anotherService.createSvgDoc(bufferedImage.width, bufferedImage.height)
        return anotherService.serializeDocumentToString(createSvgDoc)
    }

    private fun createRectElement(svg: Document, rgba: IntArray, x: Int, y: Int, width: Int, direction: Int) {
        val rectWidth = if (direction == Converter.DIRECTION_VERTICAL) 1 else width
        val rectHeight = if (direction == Converter.DIRECTION_VERTICAL) width else 1
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

}
