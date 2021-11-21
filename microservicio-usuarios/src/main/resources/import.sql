INSERT INTO `usuarios` (username, password, enabled, name, last_name, email) VALUES ('jakie','1234',1, 'Jakie', 'Alarcon','alarcon@gmail.com');
INSERT INTO `usuarios` (username, password, enabled, name, last_name, email) VALUES ('admin','3434',1, 'John', 'Doe','jhon.doe@bolsadeideas.com');

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);