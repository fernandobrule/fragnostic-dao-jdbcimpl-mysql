import sbt._
import Keys._

object Dependencies {
    
  lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime"
  lazy val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.25"
  lazy val scalatest = "org.scalatest" %% "scalatest" % "3.0.8" % "test"
  lazy val mysql8JavaClient = "mysql" % "mysql-connector-java" % "8.0.18"

  //
  // fragnostic
  //
  lazy val fragnosticDaoApi = "com.fragnostic" % "fragnostic-dao-api_2.13" % "0.1.22-SNAPSHOT"
  lazy val fragnosticSupport = "com.fragnostic" % "fragnostic-support_2.13" % "0.1.14-SNAPSHOT"

}
