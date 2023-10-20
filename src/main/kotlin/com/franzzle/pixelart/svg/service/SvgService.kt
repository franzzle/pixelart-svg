package com.franzzle.pixelart.svg.service

import org.w3c.dom.Document

interface SvgService {
    fun createSvgDoc(width: Int, height: Int): Document
    fun serializeDocumentToString(document: Document): String
}