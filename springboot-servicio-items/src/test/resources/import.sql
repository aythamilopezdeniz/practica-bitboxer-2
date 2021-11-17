INSERT INTO roles (ROL) VALUES ('USER');
INSERT INTO roles (ROL) VALUES ('ADMIN');

INSERT INTO users (NAME, LASTNAME, USERNAME, PASSWORD) VALUES('Francisco', 'Carrasco', 'Fran', '1234');
INSERT INTO users (NAME, LASTNAME, USERNAME, PASSWORD) VALUES('Antonia', 'Fernández', 'Antonia', '1234');
INSERT INTO users (NAME, LASTNAME, USERNAME, PASSWORD) VALUES('Antonia Del Carmen', 'Fernandez', 'Antonita', '1234');

INSERT INTO users_roles (USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO users_roles (USER_ID, ROLE_ID) VALUES (1, 2);
INSERT INTO users_roles (USER_ID, ROLE_ID) VALUES (2, 1);
INSERT INTO users_roles (USER_ID, ROLE_ID) VALUES (3, 1);

INSERT INTO suppliers (NAME, COUNTRY) VALUES ('supplier1', 'German');
INSERT INTO suppliers (NAME, COUNTRY) VALUES ('supplier2', 'Spain');
INSERT INTO suppliers (NAME, COUNTRY) VALUES ('supplier3', 'Spain');
INSERT INTO suppliers (NAME, COUNTRY) VALUES ('supplier4', 'Spain');
INSERT INTO suppliers (NAME, COUNTRY) VALUES ('ferreteria S.L', 'France');

INSERT INTO price_reduction (REDUCTION_PRICE, START_DATE, END_DATE, STATE) VALUES (15.95, '2021-10-15', '2021-10-21', 'INACTIVE');
INSERT INTO price_reduction (REDUCTION_PRICE, START_DATE, END_DATE, STATE) VALUES (10.95, '2021-10-16', '2021-10-20', 'INACTIVE');
INSERT INTO price_reduction (REDUCTION_PRICE, START_DATE, END_DATE, STATE) VALUES (5.95, '2021-11-15', '2021-10-18', 'ACTIVE');

INSERT INTO items (ITEM_CODE, DESCRIPTION, PRICE, STATE, CREATION_DATE, USER_ID) VALUES ('asd213', 'Alta', 59.95, 'ACTIVE', '2021-10-22', 1);
INSERT INTO items (ITEM_CODE, DESCRIPTION, PRICE, STATE, CREATION_DATE, USER_ID) VALUES ('qwr456', 'Alta', 79.95, 'ACTIVE', '2021-10-22', 2);
INSERT INTO items (ITEM_CODE, DESCRIPTION, PRICE, STATE, CREATION_DATE, USER_ID) VALUES ('wxz750', 'Alta', 69.95, 'ACTIVE', '2021-10-22', 1);
INSERT INTO items (ITEM_CODE, DESCRIPTION, PRICE, STATE, CREATION_DATE, USER_ID) VALUES ('bnm987', 'Alta', 49.95, 'ACTIVE', '2021-10-22', 1);

INSERT INTO item_suppliers (ITEM_ID, SUPPLIER_ID) VALUES (1, 1);
INSERT INTO item_suppliers (ITEM_ID, SUPPLIER_ID) VALUES (1, 2);
INSERT INTO item_suppliers (ITEM_ID, SUPPLIER_ID) VALUES (1, 4);
INSERT INTO item_suppliers (ITEM_ID, SUPPLIER_ID) VALUES (2, 1);
INSERT INTO item_suppliers (ITEM_ID, SUPPLIER_ID) VALUES (2, 2);
INSERT INTO item_suppliers (ITEM_ID, SUPPLIER_ID) VALUES (3, 5);
INSERT INTO item_suppliers (ITEM_ID, SUPPLIER_ID) VALUES (4, 3);

INSERT INTO item_price_reductions (ITEM_ID, PRICE_REDUCTION_ID) VALUES (1, 1);
INSERT INTO item_price_reductions (ITEM_ID, PRICE_REDUCTION_ID) VALUES (1, 3);