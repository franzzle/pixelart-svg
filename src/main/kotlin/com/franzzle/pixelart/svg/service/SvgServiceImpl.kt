package com.franzzle.pixelart.svg.service

import org.springframework.stereotype.Service
import org.w3c.dom.Document
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

private const val W3C_NAME_SPACE_SVG = "http://www.w3.org/2000/svg"

@Service
class SvgServiceImpl : SvgService{
    override fun createSvgDoc(width: Int, height: Int): Document {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val doc: Document = builder.newDocument()

        val svg = doc.createElement("svg")
        svg.setAttribute("xmlns", W3C_NAME_SPACE_SVG)
        svg.setAttribute("shape-rendering", "crispEdges")
        svg.setAttribute("width", width.toString())
        svg.setAttribute("height", height.toString())
        svg.setAttribute("viewBox", "0 0 $width $height")
        doc.appendChild(svg)
        return doc
    }

    override fun serializeDocumentToSvgFormat(document: Document): String {
        val transformerFactory: TransformerFactory = TransformerFactory.newInstance()
        val transformer: Transformer = transformerFactory.newTransformer()
        return try {
            val writer = StringWriter()
            transformer.transform(DOMSource(document), StreamResult(writer))
            writer.toString()
        } catch (e: Exception) {
            throw SVGConversionException(e.cause);
        }
    }
}
