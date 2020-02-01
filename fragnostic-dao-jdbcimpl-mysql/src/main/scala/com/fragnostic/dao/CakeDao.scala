package com.fragnostic.dao

import com.fragnostic.dao.impl.MySql8DataSource

object CakeDao {

  lazy val mysql8DataSourcePiece = new MySql8DataSource {}

  val mysql8DataSource = mysql8DataSourcePiece.dataSource

}
