CREATE DATABASE sport_club DEFAULT CHARACTER SET utf8;

GRANT SELECT,INSERT,UPDATE,DELETE
  ON sport_club.*
  TO club_user@localhost
  IDENTIFIED BY 'club_password';

GRANT SELECT,INSERT,UPDATE,DELETE
  ON `sport_club`.*
  TO club_user@'%'
  IDENTIFIED BY 'club_password';