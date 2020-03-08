
-- ##############################################################
-- Tabla: TEST_TABLE
-- ##############################################################
create table if not exists dbmysqlimpltest.testable(
   testable_id              smallint unsigned not null auto_increment,
   testable_field           varchar(32) not null,
   constraint testable_pk   primary key(testable_id)
) engine = innodb default charset=latin1;
