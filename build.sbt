name := """cryto-exchange"""
organization := "edu.ncsu"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += javaJdbc
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.23.1"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.16"
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.5"