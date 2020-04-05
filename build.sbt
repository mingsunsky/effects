name := "effects"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.0.0",
  "org.typelevel" %% "cats-effect" % "2.0.0",
  "io.monix" %% "monix" % "3.0.0",
  "dev.zio" %% "zio" % "1.0.0-RC16",
  "org.scalactic" %% "scalactic" % "3.0.8",
  "org.typelevel" %% "cats-effect-laws" % "2.0.0" % "test",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)