package com.franzzle.pixelart.svg.service

import java.io.InputStream

interface ConverterService {
    fun convert(inputStream: InputStream?): String?
}
