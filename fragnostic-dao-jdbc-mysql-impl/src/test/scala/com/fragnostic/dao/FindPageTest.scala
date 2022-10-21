package com.fragnostic.dao

import com.fragnostic.dao.crud.FindPageAgnostic
import com.fragnostic.dao.glue.{ CodeName, Page }
import com.fragnostic.dao.support.{ DaoLifeCycleSupport, SqlOrderBySupport }

import java.sql.ResultSet

class FindPageTest extends DaoLifeCycleSupport with FindPageAgnostic with SqlOrderBySupport {

  val numPage: Int = 11
  val nummaxBadgets: Short = 5
  val rowsPerPg: Int = 10
  val prmsCount: Map[Int, (String, String)] = Map[Int, (String, String)]()
  val prmsPage: Map[Int, (String, String)] = Map[Int, (String, String)]()

  val sqlCountTotalRows: String =
    s"""
       | select count(*) as total_rows
       | from dbmysqlimpltest.test_table
       |
     """.stripMargin

  val sqlFindPageStage: String =
    s"""
       | select test_id,
       |   test_code,
       |   test_name
       | from dbmysqlimpltest.test_table
       | {{where}}
       | {{orderBy}}
       | limit ?, ?
       |
     """.stripMargin

  val orderDesc: Boolean = true
  val orderReqEmpty = ""
  val orderReq = "name"
  val orderAvailable: Map[String, String] = Map(
    "code" -> "test_code",
    "name" -> "test_name" //
  )

  val whereReqEmpty: List[(String, String, String)] = List.empty
  val whereReq: List[(String, String, String)] = List(
    ("test_code", "=", "uno") //
  )
  val whereAvailable: List[(String, String, String)] = List(
    ("test_code", "=", "varchar"),
    ("test_code", "=", "varchar") //
  )

  def newCodeName(rs: ResultSet, args: Seq[String]): Either[String, CodeName] =
    try {
      Right(CodeName(
        rs.getString("test_code"),
        rs.getString("test_name") //
      )) //
    } catch {
      case e: Exception => Left(e.getMessage)
      case e: Throwable => Left(e.getMessage)
    }

  describe("Find Page Agnostic Test") {

    it("Can Retrieve Page") {

      val page: Page[CodeName] = findPage(
        numPage,
        nummaxBadgets,
        rowsPerPg,
        orderDesc,
        orderReq,
        orderAvailable,
        whereReq,
        whereAvailable,
        prmsCount,
        prmsPage,
        sqlCountTotalRows,
        sqlFindPageStage,
        newCodeName) fold (
        error => throw new IllegalStateException(error),
        page => page //
      )

      println(s"numPage: Long = ${page.numPage}")
      println(s"orderBy: String = ${page.orderBy}")
      println(s"linksLimitLeft: Long = ${page.linksLimitLeft}")
      println(s"linksLimitRight: Long = ${page.linksLimitRight}")
      println(s"linksAsList: List[Int] = ${page.links}")
      println(s"rowsPerPage: Long = ${page.rowsPerPage}")
      println(s"numRows: Long = ${page.numRows}")
      println(s"numPages: Long = ${page.numPages}")
      page.list foreach (cn => println(cn))
      println(s"isEmpty: Boolean = ${page.listIsEmpty}")

    }

  }

}
