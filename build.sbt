sbtPlugin := true

organization := "com.novus"

name := "sbt-scalate-precompiler"

version := "0.1.2-SNAPSHOT"

resolvers ++= Seq(
  "Novus Snapshots Repository" at "http://repo.novus.com/snapshots/",
  "Novus Snapshots Releases" at "http://repo.novus.com/releases/"
)

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-jdk14" % "1.6.1",
  "org.fusesource.scalate" % "scalate-core" % "1.5.3"
)

publishTo <<= (version) { version: String =>
  val r = Resolver.sftp("repo.novus.com", "repo.novus.com", "/nv/repo/%s".format(
    if (version.trim().toString.endsWith("-SNAPSHOT")) "snapshots" else "releases"
    )) as (System.getProperty("user.name"))
  Some(r)
}

credentials += Credentials(Path.userHome / ".ivy2" / ".novus_nexus")
