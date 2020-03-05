package com.fragnostic.dao.support

import com.fragnostic.dao.CakeDaoMySql
import com.mysql.cj.jdbc.MysqlDataSource
import org.scalatest.{ BeforeAndAfterAll, FunSpec, Matchers }
import org.slf4j.{ Logger, LoggerFactory }

trait DaoLifeCycleSupport extends FunSpec with Matchers with BeforeAndAfterAll {

  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  val dataSource: MysqlDataSource = CakeDaoMySql.mysql8DataSource.getDataSource fold (
    error => throw new IllegalStateException(error),
    dataSource => dataSource)

  override def afterAll(): Unit = {
    //dataSource.close()
    if (logger.isInfoEnabled) logger.info(s"afterAll() - DataSource has been closed")
  }

}
