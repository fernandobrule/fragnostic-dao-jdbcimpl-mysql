import sbt._
import Keys._

object Dependencies {
    
  lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime"
  lazy val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.26"
  lazy val scalatest = "org.scalatest" %% "scalatest" % "3.0.8" % "test"
  lazy val hikariCP = "com.zaxxer" % "HikariCP" % "3.2.0"
  lazy val mariadbJavaClient = "org.mariadb.jdbc" % "mariadb-java-client" % "2.3.0"
  lazy val mysql8JavaClient = "mysql" % "mysql-connector-java" % "8.0.18"

  //
  // fragnostic
  //
  lazy val fragnosticDaoApi = "com.fragnostic" % "fragnostic-dao-api_2.13" % "0.1.19-SNAPSHOT"
  lazy val fragnosticConf = "com.fragnostic" % "fragnostic-conf_2.13" % "0.1.10"
  lazy val fragnosticSupport = "com.fragnostic" % "fragnostic-support_2.13" % "0.1.12-SNAPSHOT"

}
