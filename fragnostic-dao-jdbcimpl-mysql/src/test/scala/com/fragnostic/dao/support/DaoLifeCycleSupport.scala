package com.fragnostic.dao.support

import com.fragnostic.dao.CakeDaoMySql
import com.mysql.cj.jdbc.MysqlDataSource
import org.scalatest.{ BeforeAndAfterAll, FunSpec, Matchers }
import org.slf4j.{ Logger, LoggerFactory }
import scala.language.postfixOps
import sys.process._

trait DaoLifeCycleSupport extends FunSpec with Matchers with BeforeAndAfterAll {

  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  val dataSource: MysqlDataSource = CakeDaoMySql.mysql8DataSource.getDataSource fold (
    error => throw new IllegalStateException(error),
    dataSource => dataSource)

  override def beforeAll(): Unit = {
    val ans = "./fragnostic-dao-jdbcimpl-mysql/src/test/resources/beforeall/antbeforeall" !

    //
    println(s"ans:\n$ans\n")
    //
  }

  override def afterAll(): Unit = {
    //dataSource.close()
    val ans = "./fragnostic-dao-jdbcimpl-mysql/src/test/resources/afterall/antafterall" !

    //
    println(s"ans:\n$ans\n")
    //
  }

}
