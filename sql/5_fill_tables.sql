use sport_club;
INSERT INTO `users` (`id`,
                     `login`,
                     `password`,
                     `role`)
VALUES (2, "Castiel", "qwerty", 1),
       (3, "Crowly", "tyty", 1),
       (4, "Ravena", "ahalah", 1),
       (5, "Jack", "angel", 1),
       (6, "Sam", "asdfgh", 2),
       (7, "Dean", "hgfdsa", 2),
       (8, "Waiter", "wait", 1),
  (9, "BestCoach", "train", 2),
  (10, "Lizzzka", "cool", 2);

INSERT INTO `user_info` (`user_id`,
                         `surname`,
                         `name`,
                         `patronymic`,
                         `phone`
                         #`avatar`
)
VALUES (2, "Иванов", "Иван", "Иванович", 375441234567),
       (3, "Кравцов", "Николай", "Дмитриевич", 78945612),
       (4, "Кулешов", "Дмитрий", "Николаевич", 5791384),
       (5, "Бондарев", "Никита", "Вастльевич", 7788994),
       (6, "Жук", "Илья", "Степанович", 1122338),
       (7, "Веселуха", "Любовь", "Михайовна", 8844669),
       (8, "Будько", "Евгения", "Степановна", 7699458),
  (9, "Степаненко", "Юлия", "Эдуардовна", 7788456),
  (10, "Комарова", "Елизавета", "Юрьевна", 2283224);

INSERT INTO `groups` (`group_id`,
                      `coach_id` ,
                      `exercises_type`,
                      `max_clients`,
                      `current_clients`
)
VALUES (1, 6, 1, 10, 3),
  (2, 7, 2, 5, 2),
  (3, 9, 3, 5, 2),
  (4, 10, 4, 3, 3);

INSERT INTO `subscription` (
  `id`,
  `id_of_group`,
  `left_visits`,
  `last_day`
)
VALUES #(2, 1, 3, "2019-05-20")
  (2, 4, 4, "2019-05-25"),
  (3, 1, 8, "2019-06-10"),
  (3, 4, 2, "2019-06-20"),
  (4, 1, 6, "2019-06-20"),
  (4, 4, 3, "2019-06-20"),
  (5, 2, 3,"2019-06-20"),
  (5, 3, 3, "2019-06-20"),
  (8, 1, 1 ,"2019-06-07"),
  (8, 2, 4, "2019-06-30"),
  (8, 3, 4, "2019-06-30");

INSERT INTO `prices` (`id`,
                      `exercises_type`,
                      `number_of_visits`,
                      `number_of_days`,
                      `price`
)
VALUES (1, 1, 8 , 30, 40),
       (2, 1, 4, 30, 25),
       (3, 1, 30, 30, 60),
       (4, 2, 4, 30, 20),
       (5, 2, 8, 30, 35),
       (6, 3, 4, 30, 20),
       (7, 4, 4, 30 , 20);

INSERT INTO `exercises` (`exercises_id`, `exercises_type`)
VALUES (1, "Тренажёрный зал"),
       (2, "Фитнес"),
       (3, "Аэробика"),
       (4, "Йога");