INSERT INTO users (email, password, first_name, last_name)
VALUES (
    'crisjames@google.com',
    '$2a$12$nD16/WWWO4XzGnNPNk4NouHSDqLLn.f9oZ.QU5YVsvQXS.ww3nyUy',
    'Cris',
    'James'
), (
    '123@123',
    '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW',
    'Steve',
    'Pol'
), (
    '1@1',
    '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW',
    'Albert',
    'Newgate'
), (
    'f@f',
    '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW',
    'Edward',
    'Lock'
), (
    'f1@f1',
    '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW',
    'Richard',
    'Toads'
);

INSERT INTO user_roles (user_id, role_id)
VALUES (
    (SELECT id FROM users WHERE email = 'crisjames@google.com'),
    (SELECT id FROM roles WHERE name = 'ROLE_MODERATOR')
), (
    (SELECT id FROM users WHERE email = '123@123'),
    (SELECT id FROM roles WHERE name = 'ROLE_USER')
), (
    (SELECT id FROM users WHERE email = '1@1'),
    (SELECT id FROM roles WHERE name = 'ROLE_USER')
), (
    (SELECT id FROM users WHERE email = 'f@f'),
    (SELECT id FROM roles WHERE name = 'ROLE_FREELANCER')
), (
    (SELECT id FROM users WHERE email = 'f1@f1'),
    (SELECT id FROM roles WHERE name = 'ROLE_FREELANCER')
);

INSERT INTO freelancers (user_id, job_title_id, phone_number)
VALUES (
    (SELECT id FROM users WHERE email = 'f1@f1'),
    (SELECT id FROM job_titles WHERE name = 'Programmer'),
    89321248543
), (
    (SELECT id FROM users WHERE email = 'f@f'),
    (SELECT id FROM job_titles WHERE name = 'Programmer'),
    89034132845
)
