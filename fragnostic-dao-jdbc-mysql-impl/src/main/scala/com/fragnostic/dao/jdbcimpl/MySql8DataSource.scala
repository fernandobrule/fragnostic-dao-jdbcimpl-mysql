package com.fragnostic.dao.jdbcimpl

import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.env.service.CakeConfEnvService.confEnvService.{ getInt, getString }
import com.fragnostic.dao.api.DataSourceApi
import com.mysql.cj.jdbc.MysqlDataSource

trait MySql8DataSource extends DataSourceApi {

  def dataSource = new DefaultDataSource

  class DefaultDataSource extends DataSourceApi {

    //
    // https://www.programcreek.com/java-api-examples/index.php?api=com.mysql.cj.jdbc.MysqlDataSource
    // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-jdbc-url-format.html
    // https://www.journaldev.com/2509/java-datasource-jdbc-datasource-example
    // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html
    // https://tersesystems.com/blog/2012/12/27/error-handling-in-scala/
    //
    private val getConfig: Either[String, (String, Int, String, String, String)] =
      getString("DATASOURCE_HOST") fold (
        error => Left(error),
        opt => opt map (
          host => {
            getInt("DATASOURCE_PORT") fold (
              error => Left(error),
              opt => opt map (
                port => {
                  getString("DATASOURCE_DB") fold (
                    error => Left(error),
                    opt => opt map (
                      db => {
                        getString("DATASOURCE_USR") fold (
                          error => Left(error),
                          opt => opt map (
                            usr => {
                              getString("DATASOURCE_PSW") fold (
                                error => Left(error),
                                opt => opt map (
                                  psw => {
                                    Right(host, port, db, usr, psw)
                                  } //
                                ) getOrElse Left("mysql.8.ds.get.config.error.DATASOURCE_PSW.env.var.no.exists") //
                              )
                            } //
                          ) getOrElse Left("mysql.8.ds.get.config.error.DATASOURCE_USR.env.var.no.exists") //
                        )
                      } //
                    ) getOrElse Left("mysql.8.ds.get.config.error.DATASOURCE_DB.env.var.no.exists") //
                  )
                } //
              ) getOrElse Left("mysql.8.ds.get.config.error.DATASOURCE_PORT.env.var.no.exists") //
            )
          } //
        ) getOrElse Left("mysql.8.ds.get.config.error.DATASOURCE_HOST.env.var.no.exists") //
      )

    override def getDataSource: Either[String, MysqlDataSource] =
      getConfig fold (
        error => Left(error),
        config => {
          val mysqlDataSource: MysqlDataSource = new MysqlDataSource()
          mysqlDataSource.setServerName(config._1)
          mysqlDataSource.setPort(config._2)
          mysqlDataSource.setDatabaseName(config._3)
          mysqlDataSource.setUser(config._4)
          mysqlDataSource.setPassword(config._5)
          Right(mysqlDataSource)
        })

  }

}
