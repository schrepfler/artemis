addSbtPlugin("com.typesafe.sbt"    % "sbt-git"          % "1.0.0")
addSbtPlugin("org.scalameta"       % "sbt-scalafmt"     % "2.0.5")
addSbtPlugin("de.heikoseeberger"   % "sbt-header"       % "5.2.0")
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.5.3")
addSbtPlugin("com.timushev.sbt"    % "sbt-updates"      % "0.4.2")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.28" // Needed by sbt-git
