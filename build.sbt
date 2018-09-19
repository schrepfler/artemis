// *****************************************************************************
// Projects
// *****************************************************************************

lazy val artemis =
  project
    .in(file("."))
    .enablePlugins(AutomateHeaderPlugin, GitVersioning)
    .settings(settings)
    .settings(
      libraryDependencies ++= Seq(
        library.circeParser,
        library.circeGeneric,
        library.circeGenericExtras,
        library.circeLiteral,
        library.circeTesting % Test,
        library.scalaCheck % Test,
        library.scalaTest  % Test
      )
    )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val library =
  new {
    object Version {
      val scalaCheck = "1.14.0"
      val scalaTest  = "3.0.5"
      val circe = "0.10.0-M2"
    }
    val scalaCheck = "org.scalacheck" %% "scalacheck" % Version.scalaCheck
    val scalaTest  = "org.scalatest"  %% "scalatest"  % Version.scalaTest
    val circeParser = "io.circe" %% "circe-parser" % Version.circe
    val circeGeneric = "io.circe" %% "circe-generic" % Version.circe
    val circeLiteral = "io.circe" %% "circe-literal" % Version.circe
    val circeTesting = "io.circe" %% "circe-testing" % Version.circe
    val circeGenericExtras = "io.circe" %% "circe-generic-extras" % Version.circe
  }

// *****************************************************************************
// Settings
// *****************************************************************************

lazy val settings =
  commonSettings ++
  gitSettings ++
  scalafmtSettings

lazy val commonSettings =
  Seq(
    scalaVersion := "2.12.6",
    organization := "default",
    organizationName := "Sigmalab",
    startYear := Some(2017),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-1.8",
      "-encoding", "UTF-8"
    ),
    unmanagedSourceDirectories.in(Compile) := Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) := Seq(scalaSource.in(Test).value)
)

lazy val gitSettings =
  Seq(
    git.useGitDescribe := true
  )

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true,
    scalafmtOnCompile.in(Sbt) := false,
    scalafmtVersion := "1.2.0"
  )
