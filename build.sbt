lazy val scalaTestVersion   = "3.1.0"
lazy val scalaParserVersion = "1.1.2"

lazy val scala213               = "2.13.1"
lazy val scala212               = "2.12.8"
lazy val supportedScalaVersions = List(scala213, scala212)

name := "sparser"
organization := "com.github.alirezameskin"
homepage := Some(url("https://github.com/alirezameskin/sparser"))
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

scalaVersion := scala213
crossScalaVersions := supportedScalaVersions

coverageEnabled := true

resolvers += Resolver.sonatypeRepo("releases")
bintrayOrganization := Some("meskin")
bintrayRepository := "sparser"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % scalaParserVersion,
  "org.scalatest"          %% "scalatest"                % scalaTestVersion % Test
)
