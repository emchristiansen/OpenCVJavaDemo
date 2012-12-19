import sbt._
import Keys._

import sbtassembly.Plugin._
import AssemblyKeys._

object OpenCVJavaDemoBuild extends Build {
  def extraResolvers = Seq(
    resolvers ++= Seq(
      "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
      "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
//      "repo.codahale.com" at "http://repo.codahale.com",
//      "maven.twttr.com" at "http://maven.twttr.com",
      "spray-io" at "http://repo.spray.io/",
      "typesafe-releases" at "http://repo.typesafe.com/typesafe/repo"
    )
  )

  def extraLibraryDependencies = Seq(
    libraryDependencies ++= Seq(
      "org.apache.commons" % "commons-math3" % "3.0",
      "commons-io" % "commons-io" % "2.4",
      "com.frugalmechanic" % "scala-optparse" % "1.1",
      "org.scalatest" %% "scalatest" % "2.0.M5" % "test",
      "org.scalacheck" %% "scalacheck" % "1.10.0" % "test",
      "org.scala-tools" %% "scala-stm" % "0.6",
      "com.twitter" % "util-eval" % "6.0.1",
      "com.chuusai" %% "shapeless" % "1.2.3",
      "org.clapper" %% "grizzled-scala" % "1.0.13",
      "org.scalanlp" %% "breeze-math" % "0.1",
      "org.scalaz" %% "scalaz-core" % "7.0-SNAPSHOT",
      "io.spray" %% "spray-json" % "1.2.2" cross CrossVersion.full,
      "junit" % "junit" % "4.11" % "test",
      "org.spark-project" % "spark-core_2.9.2" % "0.6.1",
      "org.imgscalr" % "imgscalr-lib" % "4.2"
    )
  )

  def scalaSettings = Seq(
    scalaVersion := "2.9.2",
    scalacOptions ++= Seq(
      "-optimize",
      "-unchecked",
      "-deprecation"
      // "-feature",
      // "-language:implicitConversions",
      // "-language:reflectiveCalls",
      // "-language:postfixOps"
    )
  )

  def libSettings = 
    Project.defaultSettings ++ 
    extraResolvers ++ 
    extraLibraryDependencies ++ 
    scalaSettings ++ 
    assemblySettings

  lazy val root = {
    val settings = libSettings ++ Seq(name := "OpenCVJavaDemo")
    Project(id = "OpenCVJavaDemo", base = file("."), settings = settings)
  }
}
