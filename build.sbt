sbtPlugin := true

organization := "com.novus"

name := "sbt-scalate-precompiler"

version := "0.1.1-SNAPSHOT"

resolvers ++= Seq(
  "Novus Snapshots Repository" at "http://repo.novus.com/snapshots/",
  "Novus Snapshots Releases" at "http://repo.novus.com/releases/"
)

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-jdk14" % "1.6.1",
  "org.fusesource.scalate" % "scalate-core" % "1.4.1"
)
