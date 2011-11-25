package com.github.hexx.common

import java.io.InputStream
import java.io.ByteArrayInputStream
import java.io.FileInputStream

import scala.io.Source
import scala.sys.process._

import resource._
import org.htmlcleaner.{HtmlCleaner => JHtmlCleaner, XmlSerializer, CompactXmlSerializer, PrettyXmlSerializer}

object HTMLCleaner {
  val cleaner = new JHtmlCleaner
  
  def clean(html: String, serializer: XmlSerializer = new CompactXmlSerializer(cleaner.getProperties), eraseNBSP: Boolean = true): String = {
    var clean_html = html
    if (eraseNBSP) {
      clean_html = "&nbsp;".r.replaceAllIn(clean_html, "")
    }
    clean_html = serializer.getAsString(cleaner.clean(clean_html))
    clean_html
  }

  def cleanPretty(html: String, eraseNBSP: Boolean = true) = clean(html, serializer = new PrettyXmlSerializer(cleaner.getProperties), eraseNBSP)
}

object NKF {
  def fromInputStream(inputStream: InputStream, args: String*): String = Process("nkf", args) #< inputStream !!

  def fromByteArray(text: Array[Byte], args: String*): String = {
    managed(new ByteArrayInputStream(text)).acquireAndGet(fromInputStream(_, args:_*))
  }

  def fromFile(name: String, args: String*) = {
    managed(new FileInputStream(name)).acquireAndGet(fromInputStream(_, args:_*))
  }
}
