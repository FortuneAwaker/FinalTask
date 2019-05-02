CREATE DATABASE sport_club DEFAULT CHARACTER SET utf8;

GRANT SELECT,INSERT,UPDATE,DELETE
  ON sport_club.*
  TO library_user@localhost
  IDENTIFIED BY 'library_password';

GRANT SELECT,INSERT,UPDATE,DELETE
  ON `sport_club`.*
  TO library_user@'%'
  IDENTIFIED BY 'library_password';