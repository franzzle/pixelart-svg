package com.franzzle.pixelart.svg.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConverterServiceImplTest {
    @Test
    fun convert(){
        val converterServiceImpl = ConverterServiceImpl(SvgServiceImpl())
        val convert = converterServiceImpl.convert(this.javaClass.getResourceAsStream("/test.png"))
        println(convert)
    }

    @Test
    fun loop(){
        assertEquals(2, getTwo())
    }

    fun getTwo(){
        listOf(1,2,3,4,5,6).forEach it@{
            if (it == 2) {
                return@it
            }
        }
    }
}