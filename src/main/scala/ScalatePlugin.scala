import sbt._
import Keys._

import java.io.File

/**
 ++ inConfig(ScalatePlugin.scalate.Config)(
    // clean this crap up
    sourceGenerators in Compile <+= (sourceManaged, ScalatePlugin.scalate.templateRoots, ScalatePlugin.scalate.importsFile) map { (sourceManaged, templateRoots, importsFile) => {
      ScalatePlugin.precompileScalate(sourceManaged, templateRoots, importsFile.get)
    }}
    )
*/

object ScalatePlugin extends Plugin {
  
  object scalate {
    val Config              = config("scalate") extend(Compile)
    val importsFile         = SettingKey[Option[File]]  ("imports-file", "Imports inserted into compiled template.") in Config
    val generatedDirectory  = SettingKey[File]          ("generated-directory", "Output directory for precompiled templates.") in Config
    val templateRoots       = SettingKey[PathFinder]     ("template-roots", "Directorires containing Scalate templates.") in Config
    val precompile          = TaskKey[PathFinder]        ("precompile", "Precompile Scalate templates.") in Config
  
    lazy val settings = Seq(
      templateRoots       <<= (resourceDirectory in Compile) { _  / "templates" :: Nil },
      importsFile         <<= (resourceDirectory in Compile) { rd => Some(rd / "imports.txt") },
      generatedDirectory  <<= (sourceManaged in Compile) { x => x },
      precompile          <<= (sourceManaged, templateRoots, importsFile) map {
        (g, roots, imports) => precompileScalate(g, roots, imports.get)
      }
    )
  }
  
  def precompileScalate(output: File, templateRoots: PathFinder, imports: File) = {
    pragmagica.scalate.Generator.precompile(templateRoots.getPaths.toList, output.toString, imports.toString)
  }
}
