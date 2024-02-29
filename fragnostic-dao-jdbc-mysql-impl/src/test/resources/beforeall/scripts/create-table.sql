
-- ##############################################################
-- Tabla: TEST_TABLE
-- ##############################################################
create table if not exists dbmysqlimpltest.test_table(
   test_id                   bigint unsigned not null auto_increment,
   test_code                 varchar(32) not null,
   test_name                 varchar(32) not null,
   test_date                 datetime not null,
   constraint test_table_pk  primary key(test_id)
) engine = innodb default charset=latin1;
