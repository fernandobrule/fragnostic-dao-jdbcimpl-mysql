package com.fragnostic.dao.jdbcimpl

import com.fragnostic.conf.env.service.CakeConfEnvService.confEnvService.{ getInt, getString }
import com.fragnostic.dao.api.DataSourceApi
import com.mysql.cj.jdbc.MysqlDataSource
import org.slf4j.{ Logger, LoggerFactory }

import scala.util.{ Failure, Success, Try }

trait MySql8DataSource extends DataSourceApi {

  private[this] val logger: Logger = LoggerFactory.getLogger("MySql8DataSource")

  private val DATASOURCE_HOST = "DATASOURCE_HOST"
  private val DATASOURCE_PORT = "DATASOURCE_PORT"
  private val DATASOURCE_DB = "DATASOURCE_DB"
  private val DATASOURCE_USR = "DATASOURCE_USR"
  private val DATASOURCE_PSW = "DATASOURCE_PSW"
  private def getEnv(key: String): String = {
    System.getenv(key)
  }

  def dataSource = new DefaultDataSource

  class DefaultDataSource extends DataSourceApi {

    //
    // https://www.programcreek.com/java-api-examples/index.php?api=com.mysql.cj.jdbc.MysqlDataSource
    // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-jdbc-url-format.html
    // https://www.journaldev.com/2509/java-datasource-jdbc-datasource-example
    // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html
    // https://tersesystems.com/blog/2012/12/27/error-handling-in-scala/
    //
    private val getConfig: Either[String, (String, Int, String, String, String)] = {
      for {
        host <- getString(DATASOURCE_HOST)
        port <- getInt(DATASOURCE_PORT)
        db <- getString(DATASOURCE_DB)
        usr <- getString(DATASOURCE_USR)
        psw <- getString(DATASOURCE_PSW)
      } yield {
        (host, port, db, usr, psw)
      }
    }

    override def getDataSource: Either[String, MysqlDataSource] = {
      //logger.info(s"getDataSource() - enter")
      getConfig fold (
        error => {
          logger.error(s"getDataSource() - 1 - $error")
          logger.error(s"getDataSource() - 1 - DATASOURCE_HOST[${getEnv(DATASOURCE_HOST)}], DATASOURCE_PORT[${getEnv(DATASOURCE_PORT)}], DATASOURCE_DB[${getEnv(DATASOURCE_DB)}], DATASOURCE_USR[${getEnv(DATASOURCE_USR)}], DATASOURCE_PSW[******]")
          Left(error)
        },
        config => {
          //logger.info(s"getDataSource() - config[$config]")
          Try({
            val mysqlDataSource: MysqlDataSource = new MysqlDataSource()
            mysqlDataSource.setServerName(config._1)
            mysqlDataSource.setPort(config._2)
            mysqlDataSource.setDatabaseName(config._3)
            mysqlDataSource.setUser(config._4)
            mysqlDataSource.setPassword(config._5)
            mysqlDataSource
          }) match {
            case Success(mysqlDataSource) => Right(mysqlDataSource)
            case Failure(throwable) => {
              logger.error(s"getDataSource() - 2 - ${throwable.getMessage}")
              logger.error(s"getDataSource() - 2 - DATASOURCE_HOST[${getEnv(DATASOURCE_HOST)}], DATASOURCE_PORT[${getEnv(DATASOURCE_PORT)}], DATASOURCE_DB[${getEnv(DATASOURCE_DB)}], DATASOURCE_USR[${getEnv(DATASOURCE_USR)}], DATASOURCE_PSW[******]")
              Left(throwable.getMessage)
            }
          }
        } //
      )
    }

  }

}
