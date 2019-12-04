addSbtPlugin("com.typesafe.sbt"    % "sbt-git"          % "1.0.0")
addSbtPlugin("org.scalameta"       % "sbt-scalafmt"     % "2.2.1")
addSbtPlugin("de.heikoseeberger"   % "sbt-header"       % "5.3.0")
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.5.4")
addSbtPlugin("com.timushev.sbt"    % "sbt-updates"      % "0.5.0")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.29" // Needed by sbt-git
