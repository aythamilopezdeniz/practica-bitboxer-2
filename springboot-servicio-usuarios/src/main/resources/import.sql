INSERT INTO roles (ROL) VALUES ('USER');
INSERT INTO roles (ROL) VALUES ('ADMIN');

INSERT INTO users (NAME, LASTNAME, USERNAME, PASSWORD) VALUES('Francisco', 'Carrasco', 'Fran', '1234');
INSERT INTO users (NAME, LASTNAME, USERNAME, PASSWORD) VALUES('Antonia', 'Fernández', 'Antonia', '1234');
INSERT INTO users (NAME, LASTNAME, USERNAME, PASSWORD) VALUES('Antonia Del Carmen', 'Fernandez', 'Antonita', '1234');

INSERT INTO users_roles (USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO users_roles (USER_ID, ROLE_ID) VALUES (1, 2);
INSERT INTO users_roles (USER_ID, ROLE_ID) VALUES (2, 1);
INSERT INTO users_roles (USER_ID, ROLE_ID) VALUES (3, 1);