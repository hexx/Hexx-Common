package com.github.hexx.common

import scala.io.Source

import resource._
import org.htmlcleaner.{HtmlCleaner => JHtmlCleaner, XmlSerializer, CompactXmlSerializer, PrettyXmlSerializer}

object Exec {
  def execZsh(command: String) = stringFromProcess(Runtime.getRuntime.exec(Array("zsh", "-c", command)))

  private def stringFromProcess(process: Process) = managed(process.getInputStream).acquireAndGet(Source.fromInputStream(_).mkString)
}

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
  def nkf(text: Array[Byte], args: String*): String = {
    val process = Runtime.getRuntime.exec(Array("nkf") ++ args)
    managed(process.getOutputStream).acquireAndGet(_ write text)
    managed(process.getInputStream).acquireAndGet(Source.fromInputStream(_).mkString)
  }
}
