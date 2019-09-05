addSbtPlugin("com.typesafe.sbt"  % "sbt-git"      % "1.0.0")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt" % "2.0.0")
addSbtPlugin("de.heikoseeberger" % "sbt-header"   % "5.2.0")
addSbtPlugin("com.timushev.sbt"  % "sbt-updates"  % "0.4.0")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.26" // Needed by sbt-git
