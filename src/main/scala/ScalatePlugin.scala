import sbt._
import Keys._

import java.io.File

/**
  inConfig(ScalatePlugin.Scalate)(
    (sourceGenerators in Compile) <+= ScalatePlugin.precompile map { x => x :: Nil }
  )
*/

object ScalatePlugin extends Plugin {
  
  object scalate {
    val Config              = config("scalate") extend(Compile)
    val importsFile         = SettingKey[Option[File]]  ("imports-file", "Imports inserted into compiled template.")
    val generatedDirectory  = SettingKey[File]          ("generated-directory", "Output directory for precompiled templates.")
    val templateRoots       = SettingKey[Seq[File]]     ("template-roots", "Directorires containing Scalate templates.")
    val precompile          = TaskKey[Seq[File]]        ("precompile", "Precompile Scalate templates.")
  
    lazy val settings = Seq(
      templateRoots       <<= (resourceDirectory in Compile) { _  / "templates" :: Nil },
      importsFile         <<= (resourceDirectory in Compile) { rd => Some(rd / "imports.txt") },
      generatedDirectory  <<= (sourceManaged in Compile) { x => x },
      precompile          <<= (sourceManaged, templateRoots, importsFile) map {
        (g, roots, imports) => precompileScalate(g, roots, imports.get)
      }
    )
  }
  
  def precompileScalate(output: File, templateRoots: Seq[File], imports: File) = {
    pragmagica.scalate.Generator.precompile(templateRoots.map(_.toString).toList, output.toString, imports.toString)
  }
}
