package com.franzzle.pixelart.svg.service

import org.springframework.stereotype.Service
import org.w3c.dom.Document
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

@Service
class SvgServiceImpl : SvgService{
    override fun createSvgDoc(width: Int, height: Int): Document {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val doc: Document = builder.newDocument()

        val svg = doc.createElement("svg")
        svg.setAttribute("xmlns", "http://www.w3.org/2000/svg")
        svg.setAttribute("shape-rendering", "crispEdges")
        svg.setAttribute("width", width.toString())
        svg.setAttribute("height", height.toString())
        svg.setAttribute("viewBox", "0 0 $width $height")
        doc.appendChild(svg)
        return doc
    }

    override fun serializeDocumentToString(document: Document): String {
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
