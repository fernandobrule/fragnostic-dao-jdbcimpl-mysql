package com.fragnostic.dao

import com.fragnostic.dao.crud.FindByIdAgnostic
import com.fragnostic.dao.glue.CodeName
import com.fragnostic.dao.support.DaoLifeCycleSupport

import java.sql.{ PreparedStatement, ResultSet }

class FindByIdTest extends DaoLifeCycleSupport with FindByIdAgnostic {

  val sqlFindById: String =
    """
      |
      | select test_code,
      |        test_name
      | from dbmysqlimpltest.test_table
      | where test_id = ?
      |
        """.stripMargin

  val fragnostic_dao_test_error_ps_fillout_exception = "fragnostic.dao.test.error.ps.fillout.exception"

  def filloutPsFindById = (ps: PreparedStatement, rq: Long) =>
    try {
      ps.setLong(1, rq)
      Right(ps)
    } catch {
      case e: Exception =>
        Left(fragnostic_dao_test_error_ps_fillout_exception)
      case e: Throwable =>
        Left("fragnostic.dao.test.error.ps.fillout.throwable")
    }

  def filloutPsFindByIdWrong = (ps: PreparedStatement, rq: Long) =>
    try {
      ps.setLong(2, rq)
      Right(ps)
    } catch {
      case e: Exception =>
        Left(fragnostic_dao_test_error_ps_fillout_exception)
      case e: Throwable =>
        Left("fragnostic.dao.test.error.ps.fillout.throwable")
    }

  def filloutPsFindByIdTotalyWrong = (ps: PreparedStatement, rq: Long) =>
    try {
      Right(ps)
    } catch {
      case e: Exception =>
        Left(fragnostic_dao_test_error_ps_fillout_exception)
      case e: Throwable =>
        Left("fragnostic.dao.test.error.ps.fillout.throwable")
    }

  def newCodeName = (rs: ResultSet, args: Map[String, String]) => try {
    Right(CodeName(
      rs.getString("test_code"),
      rs.getString("test_name")))
  } catch {
    case e: Exception => Left(e.getMessage)
  }

  describe("Find By Id Agnostic Test") {

    it("Can Find By Id") {

      val id: Long = 2

      val opt = findById(id, sqlFindById, filloutPsFindById, newCodeName) fold (
        error => throw new IllegalStateException(error),
        opt => opt)

      assertResult(opt.get.code)("beta")
      assertResult(opt.get.name)("BETA")

    }

    it("Cannot Find By Id") {

      val id: Long = 2000

      val opt = findById(id, sqlFindById, filloutPsFindById, newCodeName) fold (
        error => throw new IllegalStateException(error),
        opt => opt)

      assertResult(opt)(None)
    }

    it("Can Handle Error On Query") {

      val id: Long = 2

      val opt = findById(id, "sqlFindById", filloutPsFindById, newCodeName) fold (
        error => error,
        opt => opt)

      assertResult(opt)(fragnostic_dao_test_error_ps_fillout_exception)

    }

    it("Can Handle Error On PS Fill Out") {

      val id: Long = 2

      val opt = findById(id, sqlFindById, filloutPsFindByIdWrong, newCodeName) fold (
        error => error,
        opt => opt)

      assertResult(opt)(fragnostic_dao_test_error_ps_fillout_exception)

    }

    it("Can Handle Error On Empty PS Fill Out") {

      val id: Long = 2

      val opt = findById(id, sqlFindById, filloutPsFindByIdTotalyWrong, newCodeName) fold (
        error => error,
        opt => opt)

      assertResult(opt)("prepared.statement.support.error.on.execute.query.1")

    }

  }

}
