import sbt._

object Dependencies {

  lazy val fragnosticConfEnv       = "com.fragnostic"        % "fragnostic-conf-env_2.13"       % "0.1.11"
  lazy val fragnosticDaoApi        = "com.fragnostic"        % "fragnostic-dao-api_2.13"        % "0.2.1"
  lazy val fragnosticSupport       = "com.fragnostic"        % "fragnostic-support_2.13"        % "0.1.19"
  lazy val logbackClassic          = "ch.qos.logback"        % "logback-classic"                % "1.5.0" % "runtime"
  lazy val mysql8JavaClient        = "com.mysql"             % "mysql-connector-j"              % "8.3.0"
  lazy val scalatestFunSpec        = "org.scalatest"        %% "scalatest-funspec"              % "3.2.18" % Test
  lazy val slf4jApi                = "org.slf4j"             % "slf4j-api"                      % "2.0.12"

}
