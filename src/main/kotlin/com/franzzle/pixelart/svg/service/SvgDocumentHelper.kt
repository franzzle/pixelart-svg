package com.franzzle.pixelart.svg.service

import org.w3c.dom.Document
import org.w3c.dom.bootstrap.DOMImplementationRegistry
import javax.xml.parsers.DocumentBuilderFactory

class SvgDocumentHelper {
    companion object {
        open fun createSvgDoc(width: Int, height: Int) : Document {
            try {
//                var registry = DOMImplementationRegistry.newInstance()
//                val impl = registry.getDOMImplementation("XML 3.0")
//                //TODO Fix
//                val documentType = impl.createDocumentType("", "", "")
//                val doc = impl.createDocument("", "", documentType)

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
            } catch (e: ClassNotFoundException) {
                throw RuntimeException(e)
            } catch (e: InstantiationException) {
                throw RuntimeException(e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            }
        }
    }
}
