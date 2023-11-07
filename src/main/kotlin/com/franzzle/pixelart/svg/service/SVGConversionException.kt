package com.franzzle.pixelart.svg.service

open class SVGConversionException(cause: Throwable? = null) : RuntimeException("serialzing to SVG went wrong", cause)