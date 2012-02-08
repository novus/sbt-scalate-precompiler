import sbt._

class PrecompilerPluginProject(info: ProjectInfo) extends PluginProject(info) {
  val precompiler = "pragmagica" % "scalate-precompiler_2.9.1" % "0.0.11"
  override def managedStyle = ManagedStyle.Maven

  val publishTo = Resolver.sftp("repo.novus.com", "repo.novus.com", "/nv/repo/%s".format(
    if (projectVersion.value.toString.endsWith("-SNAPSHOT")) "snapshots"
    else "releases"
  )) as (System.getProperty("user.name"))
}


