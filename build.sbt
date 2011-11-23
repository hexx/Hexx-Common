organization := "com.github.hexx"

name := "Hexx Common"

version := "0.0.1"

scalaVersion := "2.9.1"

scalacOptions += "-deprecation"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "1.6.1" % "test",
  "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.2",
  "com.github.jsuereth.scala-arm" %% "scala-arm" % "1.0"
)
