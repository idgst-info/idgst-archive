name := """idgst"""
organization := "info.idgst"

description:= "This is a micro service which is responsible only for retrieving Digests "

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice

libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "0.12.6-play26"

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.10.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "info.idgst.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "info.idgst.binders._"
