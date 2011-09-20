import sbt._
import Keys._

import java.io.File

object ScalatePlugin extends Plugin {
  val Scalate             = config("scalate") extend(Compile)
  val importsFile         = SettingKey[Option[File]]("imports-file", "Imports inserted into compiled template.")
  val generatedDirectory  = SettingKey[File]("generated-directory", "Output directory for precompiled templates.")
  val templateRoots       = SettingKey[Seq[File]]("template-roots", "Directorires containing Scalate templates.")
  val precompile          = TaskKey[File]("precompile", "Precompile Scalate templates.")
  
  def precompileScalate(output: File, templateRoots: Seq[File], imports: File) = {
    pragmagica.scalate.Generator.precompile(templateRoots.map(_.toString).toList, output.toString, imports.toString)
    output
  }
  
  lazy val pluginSettings = inConfig(Scalate)(Seq(
    templateRoots <<= (resourceDirectory in Compile) { rd => Seq(rd / "templates") },
    importsFile <<= (resourceDirectory in Compile) { rd => Some(rd / "imports.txt") },
    generatedDirectory <<= (sourceManaged in Compile) { _ / "gen" },
    precompile <<= (sourceManaged, templateRoots, importsFile) map { (g, roots, imports) => precompileScalate(g, roots, imports.get) }
  ))
}
