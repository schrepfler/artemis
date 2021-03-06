addSbtPlugin("com.typesafe.sbt"    % "sbt-git"          % "1.0.0")
addSbtPlugin("org.scalameta"       % "sbt-scalafmt"     % "2.4.2")
addSbtPlugin("de.heikoseeberger"   % "sbt-header"       % "5.6.0")
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.6.4")
addSbtPlugin("com.timushev.sbt"    % "sbt-updates"      % "0.5.2")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.30" // Needed by sbt-git
