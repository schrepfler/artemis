// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val library =
  new {

    object Version {
      val scalaCheck = "1.15.1"
      val scalaTest = "3.2.2"
      val circe = "0.13.0"
      val testContainers = "1.11.3"
      val testContainersScala = "0.25.0"
      val cornichon = "0.17.2-SNAPSHOT"
    }

    val scalaCheck = "org.scalacheck"                   %% "scalacheck"           % Version.scalaCheck
    val scalaTest = "org.scalatest"                     %% "scalatest"            % Version.scalaTest
    val circeParser = "io.circe"                        %% "circe-parser"         % Version.circe
    val circeGeneric = "io.circe"                       %% "circe-generic"        % Version.circe
    val circeLiteral = "io.circe"                       %% "circe-literal"        % Version.circe
    val circeTesting = "io.circe"                       %% "circe-testing"        % Version.circe
    val circeGenericExtras = "io.circe"                 %% "circe-generic-extras" % Version.circe
    val testContainersScala = "com.dimafeng"            %% "testcontainers-scala" % Version.testContainersScala
    val testContainersKafka = "org.testcontainers"      % "kafka"                 % Version.testContainers
    val testContainersCassandra = "org.testcontainers"  % "cassandra"             % Version.testContainers
    val testContainersPostgreSQL = "org.testcontainers" % "postgresql"            % Version.testContainers
    val cornichonKafka = "com.github.agourlay"          %% "cornichon-kafka"      % Version.cornichon
    val cornichonCheck = "com.github.agourlay"          %% "cornichon-check"      % Version.cornichon
    val cornichonScalaTest = "com.github.agourlay"      %% "cornichon-scalatest"  % Version.cornichon
    val cornichonHttpMock = "com.github.agourlay"       %% "cornichon-http-mock"  % Version.cornichon
  }

// *****************************************************************************
// Settings
// *****************************************************************************

lazy val settings =
  commonSettings ++
  gitSettings ++
  scalafmtSettings ++
  publishSettings

lazy val commonSettings =
  Seq(
    scalaVersion := "2.13.1",
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
      "-encoding",
      "UTF-8"
    ),
    Compile / unmanagedSourceDirectories := Seq((Compile / scalaSource).value),
    Test / unmanagedSourceDirectories := Seq((Test / scalaSource).value),
    version in ThisBuild := "1.0-SNAPSHOT"
  )

lazy val gitSettings =
  Seq(
    git.useGitDescribe := true
  )

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true
  )

lazy val publishSettings =
  Seq(
    pomIncludeRepository := (_ => false)
  )

// *****************************************************************************
// Projects
// *****************************************************************************

lazy val `artemis` = (project in file("."))
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
      library.scalaCheck   % Test,
      library.scalaTest    % Test
    )
  )
  .settings(settings)

lazy val `artemis-client` = (project in file("./artemis-client"))
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"   % "10.2.1",
      "com.typesafe.akka" %% "akka-stream" % "2.6.10"
    )
  )
  .dependsOn(`artemis-protocol`)
  .settings(javaCompileSettings: _*)
  .settings(settings)

lazy val `artemis-server` = (project in file("./artemis-server"))
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"   % "10.2.1",
      "com.typesafe.akka" %% "akka-stream" % "2.6.10"
    )
  )
  .dependsOn(`artemis-protocol`)
  .settings(javaCompileSettings: _*)
  .settings(settings)

lazy val `integration-tests` = (project in file("./integration-tests"))
  .settings(settings)

lazy val javaCompileSettings =
  Seq(
    javacOptions in Compile ++= Seq(
      "-encoding", "UTF-8",
      "-source", "1.8",
      "-target", "1.8",
      "-Xlint:all",
      "-parameters" // See https://github.com/FasterXML/jackson-module-parameter-names
    )
  )
