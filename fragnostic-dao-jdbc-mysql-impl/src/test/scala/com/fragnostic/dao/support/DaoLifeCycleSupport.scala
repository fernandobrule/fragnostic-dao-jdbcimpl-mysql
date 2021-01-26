package com.fragnostic.dao.support

import com.fragnostic.dao.CakeDaoMySql
import com.mysql.cj.jdbc.MysqlDataSource
import org.scalatest.{ BeforeAndAfterAll, FunSpec, Matchers }

import scala.language.postfixOps
import scala.sys.process._

trait DaoLifeCycleSupport extends FunSpec with Matchers with BeforeAndAfterAll {

  val dataSource: MysqlDataSource = CakeDaoMySql.mysql8DataSource.getDataSource fold (
    error => throw new IllegalStateException(error),
    dataSource => dataSource)

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
