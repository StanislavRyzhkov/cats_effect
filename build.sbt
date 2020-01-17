ThisBuild / scalaVersion := "2.12.10"

ThisBuild / version := "0.0.1"

ThisBuild / organization := "company.ryzhkov"

lazy val common = (project in file("."))
  .enablePlugins(ScalafmtPlugin)
  .enablePlugins(AssemblyPlugin)
  .settings(
    name := "cats_effect",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "2.0.0"
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-language:implicitConversions"
    ),
    mainClass in (Compile, run) := Some("company.ryzhkov.cats.Application"),
    mainClass in (assembly) := Some("company.ryzhkov.cats.Application"),
    assemblyJarName in assembly := "demo.jar"
  )
