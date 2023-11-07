package com.franzzle.pixelart.svg.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.w3c.dom.Document


class SvgServiceImplTest {
    @DisplayName("Create svg document as string")
    @Test
    fun createSvgDoc() {
        val svgServiceImpl = SvgServiceImpl()
        val createSvgDoc: Document = svgServiceImpl.createSvgDoc(60, 40)

        val message = svgServiceImpl.serializeDocumentToSvgFormat(createSvgDoc)
        Assertions.assertTrue(message.contains("<svg"))
    }

}