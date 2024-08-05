name := "SmartRecommendationSystem"

version := "0.1"

scalaVersion := "3.4.2"

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "2.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test

testFrameworks += new TestFramework("org.scalatest.tools.Framework")

