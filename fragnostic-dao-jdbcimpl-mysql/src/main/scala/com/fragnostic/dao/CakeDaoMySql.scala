package com.fragnostic.dao

import com.fragnostic.dao.jdbcimpl.MySql8DataSource

object CakeDaoMySql {

  lazy val mysql8DataSourcePiece = new MySql8DataSource {}

  val mysql8DataSource = mysql8DataSourcePiece.dataSource

}
