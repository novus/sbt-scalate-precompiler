sbtPlugin := true

organization := "com.novus"

name := "sbt-scalate-precompiler"

version := "0.1.1-SNAPSHOT"

resolvers ++= Seq(
  "Novus Snapshots Repository" at "http://repo.novus.com/snapshots/",
  "Novus Snapshots Releases" at "http://repo.novus.com/releases/"
)

libraryDependencies += "org.fusesource.scalate" % "scalate-core" % "1.4.1"
