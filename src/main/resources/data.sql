INSERT INTO users (username, password, enabled) VALUES ('user@epam.com', 'password', true);
INSERT INTO users (username, password, enabled) VALUES ('admin@epam.com', 'password', true);

INSERT INTO authorities (username, authority) VALUES ('user@epam.com', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin@epam.com', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin@epam.com', 'ROLE_ADMIN');