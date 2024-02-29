package com.fragnostic.dao.jdbcimpl

import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.dao.api.DataSourceApi
import com.mysql.cj.jdbc.MysqlDataSource
import org.slf4j.{ Logger, LoggerFactory }

import scala.util.{ Failure, Success, Try }

trait MySql8DataSource extends DataSourceApi {

  private[this] val logger: Logger = LoggerFactory.getLogger("MySql8DataSource")

  def dataSource = new DefaultDataSource

  class DefaultDataSource extends DataSourceApi {

    //
    // https://www.programcreek.com/java-api-examples/index.php?api=com.mysql.cj.jdbc.MysqlDataSource
    // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-jdbc-url-format.html
    // https://www.journaldev.com/2509/java-datasource-jdbc-datasource-example
    // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html
    // https://tersesystems.com/blog/2012/12/27/error-handling-in-scala/
    //
    private def getConfig(
      host: String,
      port: String,
      db: String,
      usr: String,
      psw: String //
    ): Either[String, (String, Int, String, String, String)] = {
      for {
        host <- CakeConfEnvService.confEnvService.getString(host)
        port <- CakeConfEnvService.confEnvService.getInt(port)
        db <- CakeConfEnvService.confEnvService.getString(db)
        usr <- CakeConfEnvService.confEnvService.getString(usr)
        psw <- CakeConfEnvService.confEnvService.getString(psw)
      } yield {
        (host, port, db, usr, psw)
      }
    }

    override def getDataSource(
      host: String,
      port: String,
      db: String,
      usr: String,
      psw: String //
    ): Either[String, MysqlDataSource] = {
      //logger.info(s"getDataSource() - enter")
      getConfig(host, port, db, usr, psw) fold (
        error => {
          logger.error(s"getDataSource() - 1 - $error")
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
              Left(throwable.getMessage)
            }
          }
        } //
      )
    }

  }

}
