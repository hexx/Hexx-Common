import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

import com.github.hexx.common.HTMLCleaner
import com.github.hexx.common.NKF

class CommonSpec extends WordSpec with MustMatchers {
  "HTMLCleaner" should {
    val html_sample = "<html></html>"
    val html_minimum = """<?xml version="1.0" encoding="UTF-8"?>
<html><head /><body></body></html>"""

    "最小のHTML変換" in {
      HTMLCleaner.clean(html_sample) must be === html_minimum
    }
  }
  "NKF" should {
    "アルファベットは変換しない" in {
      NKF.fromByteArray("abc\n".getBytes) must be === "abc\n"
    }
    "UTF-8の文字列は変換しない" in {
      NKF.fromByteArray("ほげ\n".getBytes, "-w") must be === "ほげ\n"
    }
  }
}
