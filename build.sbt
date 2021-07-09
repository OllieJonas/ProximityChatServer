name := "ProximityChatServer"

version := "1.0"

scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.15"
lazy val akkaHttpVersion = "10.2.4"
lazy val kafkaVersion = "0.11"

libraryDependencies ++= Seq(

  // Akka
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,

  // Logging
  "ch.qos.logback" % "logback-classic" % "1.2.3",

  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,

  // HTTP
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

  // Kafka
  "com.typesafe.akka" %% "akka-stream-kafka" % kafkaVersion
)
