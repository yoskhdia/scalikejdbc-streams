import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import scalariform.formatter.preferences._

name := "scalikejdbc-streams"

version := "0.0.3-SNAPSHOT"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8", "2.12.1")

organization := "com.github.yoskhdia"

licenses := Seq("Apache-2.0" -> url("https://opensource.org/licenses/Apache-2.0"))

scalacOptions ++= Seq(
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Xfatal-warnings",
  "-Xlint",
  "-feature",
  "-unchecked",
  "-deprecation",
  "-encoding", "utf8",
  "-Ywarn-adapted-args"
)

val scalikeJdbcVersion = "2.5.0"

libraryDependencies ++= Seq(
  "org.reactivestreams" % "reactive-streams" % "1.0.0",
  "org.scalikejdbc" %% "scalikejdbc" % scalikeJdbcVersion,
  "ch.qos.logback" % "logback-classic" % "1.1.7" % "test",
  "com.h2database" % "h2" % "1.4.193" % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

// Plugins Settings

scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
