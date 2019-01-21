name := "DEV"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.13",
  "com.typesafe.akka" %% "akka-stream" % "2.5.13",
  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "net.sf.okapi.lib" % "okapi-lib-xliff2" % "1.1.8",
  "io.spray" %% "spray-json" % "1.3.2"

)
