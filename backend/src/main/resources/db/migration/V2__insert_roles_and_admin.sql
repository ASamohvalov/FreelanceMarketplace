insert into roles (name)
values ('ROLE_ADMIN'), ('ROLE_USER'), ('ROLE_FREELANCER'), ('ROLE_MODERATOR');

insert into users (email, password, first_name, last_name)
values ('admin@ad.min', '$2a$12$YmzRx.1DnXRWTBqoj45V1es82wCv22RLIlqGf49gaO2Ac0VCITx7q', 'Oleg', 'Popov');

insert into user_roles (user_id, role_id)
values (
    (select id from users where email = 'admin@ad.min'),
    (select id from roles where name = 'ROLE_ADMIN')
);