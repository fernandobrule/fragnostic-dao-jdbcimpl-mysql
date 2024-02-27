package com.fragnostic.dao.support

import com.fragnostic.dao.CakeDaoMySql
import com.mysql.cj.jdbc.MysqlDataSource
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funspec.AnyFunSpec

import scala.language.postfixOps
import scala.sys.process._

 trait DaoLifeCycleSupport extends AnyFunSpec with BeforeAndAfterAll {

  val FRG_DAO_JDBC_IMPL_DATASOURCE_HOST: String = "FRG_DAO_JDBC_IMPL_DATASOURCE_HOST"
  val FRG_DAO_JDBC_IMPL_DATASOURCE_PORT: String = "FRG_DAO_JDBC_IMPL_DATASOURCE_PORT"
  val FRG_DAO_JDBC_IMPL_DATASOURCE_DB: String = "FRG_DAO_JDBC_IMPL_DATASOURCE_DB"
  val FRG_DAO_JDBC_IMPL_DATASOURCE_USR: String = "FRG_DAO_JDBC_IMPL_DATASOURCE_USR"
  val FRG_DAO_JDBC_IMPL_DATASOURCE_PSW: String = "FRG_DAO_JDBC_IMPL_DATASOURCE_PSW"

  val dataSource: MysqlDataSource = CakeDaoMySql.mysql8DataSource.getDataSource(FRG_DAO_JDBC_IMPL_DATASOURCE_HOST, FRG_DAO_JDBC_IMPL_DATASOURCE_PORT, FRG_DAO_JDBC_IMPL_DATASOURCE_DB, FRG_DAO_JDBC_IMPL_DATASOURCE_USR, FRG_DAO_JDBC_IMPL_DATASOURCE_PSW) fold (
    error => throw new IllegalStateException(error),
    dataSource => dataSource //
  )

  override def beforeAll(): Unit = {
    val ans = "./fragnostic-dao-jdbc-mysql-impl/src/test/resources/beforeall/antbeforeall" !

    //
    println(s"ans:\n$ans\n")
    //
  }

  override def afterAll(): Unit = {
    //dataSource.close()
    val ans = "./fragnostic-dao-jdbc-mysql-impl/src/test/resources/afterall/antafterall" !

    //
    println(s"ans:\n$ans\n")
    //
  }

}
