addSbtPlugin("com.typesafe.sbt"    % "sbt-git"          % "1.0.2")
addSbtPlugin("org.scalameta"       % "sbt-scalafmt"     % "2.4.6")
addSbtPlugin("de.heikoseeberger"   % "sbt-header"       % "5.6.0")
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.6.7")
addSbtPlugin("com.timushev.sbt"    % "sbt-updates"      % "0.6.1")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.33" // Needed by sbt-git
