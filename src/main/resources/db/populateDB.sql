DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO users (name, email, password)
VALUES ('User', 'user@gmail.ru', 'password');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_ADMIN', 100000),
  ('ROLE_USER', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('2017-08-17 10:00:00', 'Админ завтрак', 500, 100000),
  ('2017-08-28 13:00:00', 'Админ обед', 1000, 100000),
  ('2017-08-28 18:00:00', 'Админ ужин', 1100, 100000),
  ('2017-08-30 10:00:00', 'Завтрак', 500, 100001),
  ('2017-08-30 13:00:00', 'Обед', 1000, 100001),
  ('2017-08-30 20:00:00', 'Ужин', 500, 100001),
  ('2017-08-31 10:00:00', 'Завтрак', 500, 100001),
  ('2017-08-31 13:00:00', 'Обед', 1000, 100001),
  ('2017-08-31 20:00:00', 'Ужин', 510, 100001);