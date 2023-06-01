INSERT INTO role (id, name) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');
INSERT INTO users (id, username, email, password) VALUES (1, 'gavrilovegor519-admin', 'gavrilovegor519-admin@gmail.com',   '$2a$10$oudgUPFfV2b0H2k6yGEjjO/z.6VHwaQJ0uKAKOurHRWirIDHfU2km');
INSERT INTO user_role (user_id, role_id) VALUES (1, 2);