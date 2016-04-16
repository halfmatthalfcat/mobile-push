import com.malliina.sbtutils.SbtProjects
import com.malliina.sbtutils.SbtUtils.{developerName, gitUserName}
import sbt.Keys._
import sbt._

/**
  * A scala build file template.
  */
object PushBuild extends Build {
  lazy val mobileProject = SbtProjects.testableProject("mobile-push")
    .enablePlugins(bintray.BintrayPlugin)
    .settings(projectSettings: _*)

  lazy val projectSettings = Seq(
    version := "1.5.0",
    scalaVersion := "2.11.7",
    gitUserName := "malliina",
    organization := s"com.${gitUserName.value}",
    developerName := "Michael Skogberg",
    fork in Test := true,
    resolvers := Seq(
      Resolver.jcenterRepo,
      Resolver.bintrayRepo("malliina", "maven")) ++ resolvers.value,
    libraryDependencies ++= Seq(
      "com.malliina" %% "util" % "2.4.1",
      "com.notnoop.apns" % "apns" % "1.0.0.Beta6",
      "com.squareup.okhttp" % "okhttp" % "2.7.5"
    ),
    libraryDependencies += "org.mortbay.jetty.alpn" % "alpn-boot" % "8.1.6.v20151105" % "runtime",
    javaOptions <++= (managedClasspath in Runtime) map { attList =>
      for {
        file <- attList.map(_.data)
        path = file.getAbsolutePath
        if path.contains("jetty.alpn")
      } yield "-Xbootclasspath/p:" + path
    },
    licenses +=("MIT", url("http://opensource.org/licenses/MIT"))
  )
}
