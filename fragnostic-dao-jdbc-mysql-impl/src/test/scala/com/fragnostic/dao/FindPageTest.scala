package com.fragnostic.dao

import com.fragnostic.dao.crud.FindPageAgnostic
import com.fragnostic.dao.glue.{CodeName, Page}
import com.fragnostic.dao.support.{DaoLifeCycleSupport, SqlOrderBySupport}

import java.sql.ResultSet

class FindPageTest extends DaoLifeCycleSupport with FindPageAgnostic with SqlOrderBySupport {

  describe("Find Page Agnostic Test") {

    it("Can Retrieve Page") {

      val numPage: Int = 11

      val nummaxBadgets: Short = 5

      val rowsPerPg: Int = 10

      val optPrmsCount: Map[Int, (String, String)] = Map[Int, (String, String)]()

      val optPrmsPage: Map[Int, (String, String)] = Map[Int, (String, String)]()

      val sqlCountTotalRows: String =
        s"""
           | select count(*) as total_rows
           | from dbmysqlimpltest.test_table
           |
     """.stripMargin

      val sqlFindPageStage: String =
        s"""
           | select test_id,
           |        test_code,
           |        test_name
           | from dbmysqlimpltest.test_table
           | {{orderBy}}
           | limit ?, ?
           |
     """.stripMargin

      val orderByMap: Map[String, String] =
        Map(
          "code" -> "test_code",
          "name" -> "test_name")

      val orderBy = "name"
      val orderDesc = false
      val sqlFindPage: String = applyOrderBy(orderByMap, sqlFindPageStage, orderBy, orderDesc)

      def newCodeName = (rs: ResultSet, args: Seq[String]) => try {
        Right(CodeName(
          rs.getString("test_code"),
          rs.getString("test_name")))
      } catch {
        case e: Exception => Left(e.getMessage)
      }

      val page: Page[CodeName] = findPage(numPage, nummaxBadgets, orderBy, rowsPerPg, optPrmsCount, optPrmsPage, sqlCountTotalRows, sqlFindPage, newCodeName) fold (
        error => throw new IllegalStateException(error),
        page => page)

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
