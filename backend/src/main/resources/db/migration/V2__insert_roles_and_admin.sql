INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'), ('ROLE_USER'), ('ROLE_FREELANCER'), ('ROLE_MODERATOR');

INSERT INTO users (email, password, first_name, last_name)
VALUES ('admin@ad.min', '$2a$12$YmzRx.1DnXRWTBqoj45V1es82wCv22RLIlqGf49gaO2Ac0VCITx7q', 'Oleg', 'Popov');

INSERT INTO user_roles (user_id, role_id)
VALUES (
    (SELECT id FROM users WHERE email = 'admin@ad.min'),
    (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')
);