addSbtPlugin("com.github.sbt"    % "sbt-git"          % "2.0.0")
addSbtPlugin("org.scalameta"       % "sbt-scalafmt"     % "2.4.6")
addSbtPlugin("de.heikoseeberger"   % "sbt-header"       % "5.8.0")
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.6.7")
addSbtPlugin("com.timushev.sbt"    % "sbt-updates"      % "0.6.4")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "2.0.3" // Needed by sbt-git
