package com.franzzle.pixelart.svg.service

open class SVGConversionException(cause: Throwable? = null) : RuntimeException("serializing to SVG went wrong", cause)