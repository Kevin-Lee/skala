import java.io.{File => JFile, FileFilter => JFileFilter}

import org.apache.commons.io.filefilter.WildcardFileFilter
import sbt._

import scala.annotation.tailrec

object CommonUtils {

  def versionWriter(paramsResolver: () => Seq[String])(projectVersion: String, basePath: String = "target"): Unit = {
    println("\n== Writing Version File ==")
    val args: Seq[String] = paramsResolver()
    println(s"The project version is $projectVersion.")

    import IO._

    val filename = args.headOption.map(Prefix(basePath) + _).getOrElse("target/version.tmp")
    val versionFile = new sbt.File(filename)
    println(s"write $projectVersion into the file: $versionFile")

    write(versionFile, projectVersion, utf8, append = false)
    println("Done: Writing Version File\n")
  }

  def wildcardFilter(names: Seq[String]): JFileFilter =
    new WildcardFileFilter(names.toArray).asInstanceOf[JFileFilter]

  def wildcardFilter(name: String, names: String*): JFileFilter =
    wildcardFilter(name +: names.toSeq)

  //  def getAllSubDirs(dir: File): Array[File] = dir.listFiles(DirectoryFilter).flatMap(x => x +: getAllSubDirs(x))
  def getAllSubDirs(dir: File): Seq[File] = {
    @tailrec
    def getAllSubDirs(dirs: Array[File],
                      acc: Vector[File]): Vector[File] = dirs match {
      case Array() =>
        acc
      case Array(x) =>
        getAllSubDirs(x.listFiles(DirectoryFilter), acc :+ x)
      case array @ Array(x, _*) =>
        getAllSubDirs(array.drop(1) ++ x.listFiles(DirectoryFilter), acc :+ x)
    }
    getAllSubDirs(dir.listFiles(DirectoryFilter), Vector.empty)
    //    dir.listFiles(DirectoryFilter).flatMap(x => x +: getAllSubDirs(x))
  }

  object BasePath {
    def apply(base: File, dir: String): BasePath = BasePath(base, Option(dir))
    def apply(base: File): BasePath = BasePath(base, None)
  }

  case class BasePath(base: File, dir: Option[String] = None) {
    lazy val pathLength: Int = {
      val thePath = base / dir.getOrElse("")
      val basePath = thePath.getPath
      basePath.length + (if (basePath.endsWith(JFile.separator)) 0 else 1)
    }
  }

  object Prefix {
    def apply(value: String): Prefix = Option(value).fold[Prefix](NoPrefix)(PrefixVal)
    def apply(): Prefix = NoPrefix
  }

  sealed trait Prefix {
    def value: String
    def isEmpty: Boolean
    def fold(defaultVal: => String)(f: String => String): String = if (isEmpty) defaultVal else f(value)
    def +(path: => String): String = fold(path)(prefix => s"$prefix/$path")
  }

  case object NoPrefix extends Prefix { val isEmpty = true; val value = "" }
  private case class PrefixVal(value: String) extends Prefix { val isEmpty = false }

  def listFiles(dir: File, name: String, names: String*): Seq[File] =
    listFiles(dir, (name +: names).toList)

  def listFiles(dir: File, names: Seq[String]): Seq[File] = {
    def listFiles0(dir: File, names: Seq[String]): Seq[File] =
      dir.listFiles(wildcardFilter(names)).toList

    (dir +: getAllSubDirs(dir)).flatMap(listFiles0(_, names)).distinct
  }

  def fileAndPathNameList(basePath: BasePath,
                          prefix: Prefix,
                          name: String,
                          names: String*): Seq[(File, String)] =
    fileAndPathNameList(basePath, prefix, (name +: names).toList)

  def fileAndPathNameList(basePath: BasePath,
                          prefix: Prefix,
                          names: Seq[String]): Seq[(File, String)] = {

    val basePathLength = basePath.pathLength
    println(
      s"""
         |      basePath: ${basePath.base}
         |           dir: ${basePath.dir}
         |        prefix: ${prefix.value}
         |basePathLength: $basePathLength
       """.stripMargin)
    listFiles(basePath.base / basePath.dir.getOrElse(""), names)
      .map(f => (f, f.getPath))
      .map { case (file, parent) =>
        (
          file,
          basePath.dir.fold(prefix + parent.drop(basePathLength))(_ + s"/${prefix + parent.drop(basePathLength)}")
        )
      }
  }

  /* GitHub */
  case class Origin(organization: String, name: String)

  def githubOrigin(base: File): Option[Origin] = {
    import scala.util.Try
    val repoExtractPattern = """.+[:/]([^/]*)/([^/]*)""".r

    val maybeOriginUrl = Try {
      sys.process.Process(Seq("git", "ls-remote", "--get-url", "origin"), base).!!
    }.toOption

    maybeOriginUrl.flatMap { out =>
      val originUrl = out.trim.stripSuffix(".git")
      originUrl match {
        case repoExtractPattern(organization, name) =>
          Some(Origin(organization, name))
        case _ => None
      }
    }
  }

}