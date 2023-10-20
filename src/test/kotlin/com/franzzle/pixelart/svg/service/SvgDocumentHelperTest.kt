package com.franzzle.pixelart.svg.service

import org.junit.jupiter.api.Test
import org.w3c.dom.Document
import java.io.StringWriter
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


class SvgDocumentHelperTest {
    @Test
    fun createSvgDoc() {
        val svgDocumentHelper = SvgDocumentHelper
        val createSvgDoc: Document = svgDocumentHelper.createSvgDoc(60, 40);

        println(serializeDocumentToString(createSvgDoc))

    }

    fun serializeDocumentToString(document: Document): String {
        try {
            val transformerFactory: TransformerFactory = TransformerFactory.newInstance()
            val transformer: Transformer = transformerFactory.newTransformer()
            val writer = StringWriter()
            transformer.transform(DOMSource(document), StreamResult(writer))
            return writer.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return "" // Handle the exception appropriately in your code
        }
    }
}