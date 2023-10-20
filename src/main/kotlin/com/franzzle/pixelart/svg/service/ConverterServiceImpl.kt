package com.franzzle.pixelart.svg.service

import java.io.InputStream
import javax.imageio.ImageIO

class ConverterServiceImpl : ConverterService {
    override fun convert(inputStream: InputStream?): String? {
        val bufferedImage = ImageIO.read(inputStream)
        System.out.println("Width : " + bufferedImage.width + " and Height : " + bufferedImage.height)
        return "<svg></svg>"
    }
}
