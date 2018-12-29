name := """helloworld"""
organization := "rilaser"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.7"

libraryDependencies += guice
libraryDependencies += javaJdbc
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.25.2"

EclipseKeys.preTasks := Seq(compile in Compile, compile in Test)

lazy val myProject = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

mainClass in assembly := Some("play.core.server.ProdServerStart")
fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)

assemblyMergeStrategy in assembly := {
  case manifest if manifest.contains("MANIFEST.MF") =>
    // We don't need manifest files since sbt-assembly will create
    // one with the given settings
    MergeStrategy.discard
  case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
    // Keep the content for all reference-overrides.conf files
    MergeStrategy.concat
  case x =>
    // For all the other files, use the default sbt-assembly merge strategy
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

javaOptions in Test ++= Seq(
  "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5555",
  "-Xms512M",
  "-Xmx1536M",
  "-Xss1M",
  "-XX:MaxPermSize=384M"
)