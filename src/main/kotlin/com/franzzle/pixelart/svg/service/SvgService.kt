package com.franzzle.pixelart.svg.service

import org.w3c.dom.Document

interface SvgService {
    /**
     *  Create a svg document with the given width and height
     */
    fun createSvgDoc(width: Int, height: Int): Document

    /**
     * Serialize a document to svg in string format
     */
    fun serializeDocumentToSvgFormat(document: Document): String
}