addSbtPlugin("com.github.sbt"    % "sbt-git"          % "2.0.0")
addSbtPlugin("org.scalameta"       % "sbt-scalafmt"     % "2.4.6")
addSbtPlugin("de.heikoseeberger"   % "sbt-header"       % "5.7.0")
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.6.7")
addSbtPlugin("com.timushev.sbt"    % "sbt-updates"      % "0.6.2")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.36" // Needed by sbt-git
