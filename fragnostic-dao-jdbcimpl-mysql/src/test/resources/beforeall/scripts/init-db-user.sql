
use mysql;

delete from user where user ='mysqltestusr';
flush privileges;

create user 'mysqltestusr'@'%' identified by 'mysqltestpsw';

-- https://dev.mysql.com/doc/refman/8.0/en/creating-accounts.html
CREATE USER 'mysqltestusr'@'${env.IP_HOST}'
  IDENTIFIED BY 'mysqltestpsw';
GRANT ALL
  ON *.*
  TO 'mysqltestusr'@'localhost'
  WITH GRANT OPTION;

--grant all on *.* to 'mysqltestusr'@'${env.IP_HOST}';
--identified by "mysqltestpsw" with grant option;

flush privileges;


