import sbt._

object Dependencies {

  lazy val fragnosticDaoApi           = "com.fragnostic"        % "fragnostic-dao-api_2.13"        % "0.2.0-SNAPSHOT"
  lazy val fragnosticSupport          = "com.fragnostic"        % "fragnostic-support_2.13"        % "0.1.19-SNAPSHOT"
  lazy val fragnosticConfEnv          = "com.fragnostic"        % "fragnostic-conf-env_2.13"       % "0.1.10-SNAPSHOT"

  lazy val mysql8JavaClient           = "mysql"                 % "mysql-connector-java"           % "8.0.27"
  lazy val logbackClassic             = "ch.qos.logback"        % "logback-classic"                % "1.3.0-alpha12" % "runtime"
  lazy val slf4jApi                   = "org.slf4j"             % "slf4j-api"                      % "2.0.0-alpha5"
  lazy val scalatestFunSpec           = "org.scalatest"        %% "scalatest-funspec"              % "3.3.0-SNAP3" % Test

}
