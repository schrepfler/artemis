// *****************************************************************************
// Projects
// *****************************************************************************

lazy val artemis = (project in file("."))
  .aggregate(`artemis-protocol`)
  .aggregate(`artemis-client`)
  .aggregate(`artemis-server`, `artemis-protocol`)
  .aggregate(`integration-tests`)
  .enablePlugins(AutomateHeaderPlugin, GitVersioning)
  .settings(settings)

lazy val `artemis-protocol` = (project in file("./artemis-protocol"))
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
  .settings(javaCompileSettings: _*)

lazy val `artemis-client` = (project in file("./artemis-client"))
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"   % "10.1.9",
      "com.typesafe.akka" %% "akka-stream" % "2.5.23"
    )
  )
  .dependsOn(`artemis-protocol`)
  .settings(javaCompileSettings: _*)

lazy val `artemis-server` = (project in file("./artemis-server"))
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"   % "10.1.9",
      "com.typesafe.akka" %% "akka-stream" % "2.5.23"
    )
  )
  .dependsOn(`artemis-protocol`)
  .settings(javaCompileSettings: _*)

lazy val `integration-tests` = (project in file("./integration-tests"))
  .settings()


// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val library =
  new {
    object Version {
      val scalaCheck = "1.14.0"
      val scalaTest  = "3.0.7"
      val circe = "0.11.1"
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
    scalaVersion := "2.12.8",
    organization := "net.sigmalab.artemis",
    organizationName := "Sigmalab",
    homepage := Some(url("https://github.com/schrepfler/artemis/")),
    startYear := Some(2019),
    licenses += "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-1.8",
      "-encoding", "UTF-8"
    ),
    unmanagedSourceDirectories.in(Compile) := Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) := Seq(scalaSource.in(Test).value),
    version in ThisBuild := "1.0-SNAPSHOT"
)

lazy val gitSettings =
  Seq(
    git.useGitDescribe := true
  )

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true,
    scalafmtOnCompile.in(Sbt) := false,
    scalafmtVersion := "2.0.0-RC1"
  )

lazy val javaCompileSettings = Seq(
  javacOptions in Compile ++= Seq(
    "-encoding", "UTF-8",
    "-source", "1.8",
    "-target", "1.8",
    "-Xlint:all",
    "-parameters" // See https://github.com/FasterXML/jackson-module-parameter-names
  )
)
