import com.mle.sbtutils.SbtProjects
import com.mle.sbtutils.SbtUtils.{developerName, gitUserName}
import sbt.Keys._
import sbt._

/**
 * A scala build file template.
 */
object PushBuild extends Build {
  lazy val mobileProject = SbtProjects.testableProject("mobile-push")
    .enablePlugins(bintray.BintrayPlugin).settings(projectSettings: _*)

  lazy val projectSettings = Seq(
    version := "0.9.4",
    scalaVersion := "2.11.6",
    gitUserName := "malliina",
    organization := s"com.github.${gitUserName.value}",
    developerName := "Michael Skogberg",
    fork in Test := true,
    resolvers := Seq(
      "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases/",
      sbt.Resolver.jcenterRepo,
      "Bintray malliina" at "http://dl.bintray.com/malliina/maven") ++ resolvers.value,
    libraryDependencies ++= Seq(
      "com.github.malliina" %% "util" % "1.8.1",
      "com.typesafe.play" %% "play-json" % "2.3.9",
      "com.ning" % "async-http-client" % "1.8.13",
      "com.notnoop.apns" % "apns" % "1.0.0.Beta6"),
    libraryDependencies := {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, scalaMajor)) if scalaMajor >= 11 =>
          libraryDependencies.value :+ "org.scala-lang.modules" %% "scala-xml" % "1.0.1"
        case _ =>
          libraryDependencies.value
      }
    },
    licenses +=("MIT", url("http://opensource.org/licenses/MIT"))
  )
}
