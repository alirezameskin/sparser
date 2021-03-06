lazy val scalaTestVersion   = "3.1.0"
lazy val scalaParserVersion = "1.1.2"

lazy val scala213               = "2.13.1"
lazy val scala212               = "2.12.8"
lazy val supportedScalaVersions = List(scala213, scala212)

ThisBuild / organization := "com.github.alirezameskin"
ThisBuild / homepage := Some(url("https://github.com/alirezameskin/sparser"))
ThisBuild / licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

ThisBuild / scalaVersion := scala212
ThisBuild / crossScalaVersions := supportedScalaVersions

lazy val root = project
  .in(file("."))
  .aggregate(sparser.js, sparser.jvm)
  .dependsOn(sparser.js, sparser.jvm)
  .settings(
    publish := {},
    publishLocal := {},
    bintrayUnpublish := {}
  )

lazy val sparser = crossProject(JSPlatform, JVMPlatform)
  .in(file("."))
  .settings(Settings.commonSettings)
  .settings(
    name := "sparser",
    resolvers += Resolver.sonatypeRepo("releases"),
    bintrayOrganization := Some("meskin"),
    bintrayRepository := "sparser",
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-parser-combinators" % scalaParserVersion,
      "org.scalatest"          %% "scalatest"                % scalaTestVersion % Test
    )
  )
  .jvmSettings(
    coverageEnabled in (Test, compile) := true,
    coverageEnabled in (Compile, compile) := false
  )
  .jsSettings(
    libraryDependencies += "org.scala-lang.modules" %%% "scala-parser-combinators" % scalaParserVersion
  )
