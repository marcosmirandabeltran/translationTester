import sbt.IO

name := "DEV"

version := "0.1"

scalaVersion := "2.11.8"

val akkaV: String = "2.5.19"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaV,
  "com.typesafe.akka" %% "akka-stream" % akkaV,
  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "net.sf.okapi.lib" % "okapi-lib-xliff2" % "1.1.8",
  "io.spray" %% "spray-json" % "1.3.5",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.7",
  "ch.qos.logback"          % "logback-classic"          % "1.1.2" % "runtime",
  "net.logstash.logback"    % "logstash-logback-encoder" % "4.5.1",
  "org.slf4j"               % "slf4j-api"                % "1.7.20",
  "com.typesafe.akka"       %% "akka-testkit"            % akkaV % "test",
  "org.scalatest"           %% "scalatest"               % "2.2.6" % "test"

)

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "routes"
  )




