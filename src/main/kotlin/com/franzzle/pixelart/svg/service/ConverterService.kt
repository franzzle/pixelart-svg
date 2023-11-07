package com.franzzle.pixelart.svg.service

import java.io.InputStream

interface ConverterService {
    /**
     * Convert the incoming stream into a SVG string
     */
    fun convert(inputStream: InputStream?): String?
}
