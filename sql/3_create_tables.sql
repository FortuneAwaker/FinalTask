USE sport_club;

CREATE TABLE `users`
(
  `id`       INTEGER      NOT NULL AUTO_INCREMENT,
  `login`    VARCHAR(255) NOT NULL UNIQUE,
  `password` CHAR(32)     NOT NULL,
  `role`     TINYINT      NOT NULL CHECK (`role` IN (0, 1, 2)),
  constraint ID PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE user_info
(
  `user_id`    INTEGER      NOT NULL,
  `surname`    VARCHAR(255) NOT NULL,
  `name`       VARCHAR(255) NOT NULL,
  `patronymic` VARCHAR(255) NOT NULL,
  `phone`      BIGINT      NOT NULL,
  `avatar`     BLOB,
  constraint ID_OF_USER FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  constraint PHONE PRIMARY KEY (`phone`)
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `coach`
(
  `id`              INTEGER NOT NULL,
  `max_clients`     int(11),
  `current_clients` int(11),
  `salary`          DOUBLE,
  constraint COACH_ID FOREIGN KEY (`id`)
    REFERENCES user_info (`user_id`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `groups`
(
  `group_id`       INTEGER NOT NULL AUTO_INCREMENT,
  `coach_id`       INTEGER NOT NULL,
  `exercises_type` INTEGER,
  constraint EXERCISE_TYPE FOREIGN KEY (`exercises_type`)
    REFERENCES `exercises` (`exercises_id`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  constraint COACH_OF_GROUP FOREIGN KEY (`coach_id`)
    REFERENCES `coach` (`id`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  constraint GROUP_ID PRIMARY KEY (`group_id`)
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `subscription`
(
  `id`          INTEGER NOT NULL,
  `id_of_group`    INTEGER NOT NULL,
  `left_visits` int(11),
  `last_day`   date,
  constraint CLIENT_ID FOREIGN KEY (`id`)
    REFERENCES user_info (`user_id`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  constraint GROUP_OF_CLIENT FOREIGN KEY (`id_of_group`)
    REFERENCES `groups` (`group_id`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `queue`
(
  `waiter_id`   INTEGER NOT NULL,
  `wanted_type` INTEGER NOT NULL,
  constraint ID_OF_WAITER FOREIGN KEY (`waiter_id`)
    REFERENCES `user_info` (`user_id`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  constraint WAITING_TYPE FOREIGN KEY (`wanted_type`)
    REFERENCES `exercises` (`exercises_id`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `prices`
(
  `id` INTEGER NOT NULL,
  `exercises_type`   INTEGER NOT NULL ,
  `number_of_visits` TINYINT NOT NULL,
  `number_of_days`   INT     NOT NULL,
  `price`            DOUBLE NOT NULL,
  constraint TYPE FOREIGN KEY (`exercises_type`)
    REFERENCES `exercises` (`exercises_id`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  constraint PRICE_OF_TYPE PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `exercises`
(
  `exercises_id`   INTEGER NOT NULL AUTO_INCREMENT,
  `exercises_type` VARCHAR(255),
  constraint EXERCISE_ID PRIMARY KEY (`exercises_id`)
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;