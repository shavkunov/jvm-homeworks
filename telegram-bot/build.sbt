name := "telegram-bot"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test"

// https://mvnrepository.com/artifact/io.fastjson/boon
libraryDependencies += "io.fastjson" % "boon" % "0.34"
libraryDependencies +=  "info.mukel" %% "telegrambot4s" % "3.0.9"