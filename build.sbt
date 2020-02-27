lazy val scalaTestVersion   = "3.1.0"
lazy val scalaParserVersion = "1.1.2"

lazy val scala213               = "2.13.1"
lazy val scala212               = "2.12.8"
lazy val supportedScalaVersions = List(scala213, scala212)

name := "sparser"
scalaVersion := scala213
organization := "com.github.alirezameskin"
crossScalaVersions := supportedScalaVersions

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % scalaParserVersion,
  "org.scalatest"          %% "scalatest"                % scalaTestVersion % Test
)
