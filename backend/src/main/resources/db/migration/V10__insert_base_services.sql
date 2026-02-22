--------------------------------------------------
-- 1. USERS
--------------------------------------------------

INSERT INTO users (email, password, first_name, last_name) VALUES
('dev1@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Алексей', 'Иванов'),
('dev2@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Дмитрий', 'Смирнов'),
('dev3@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Максим', 'Кузнецов'),
('dev4@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Игорь', 'Соколов'),
('dev5@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Артем', 'Попов'),
('designer1@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Мария', 'Волкова'),
('designer2@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Анна', 'Лебедева'),
('seo1@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Олег', 'Морозов'),
('copy1@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Елена', 'Новикова'),
('translator1@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Наталья', 'Федорова'),
('video1@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Кирилл', 'Орлов'),
('biz1@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Владимир', 'Егоров'),
('data1@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Сергей', 'Павлов'),
('shop1@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Роман', 'Крылов'),
('auto1@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Тимур', 'Белов');


--------------------------------------------------
-- 2. ROLE_FREELANCER
--------------------------------------------------

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE r.name = 'ROLE_FREELANCER'
AND u.email IN (
'dev1@mail.com','dev2@mail.com','dev3@mail.com','dev4@mail.com','dev5@mail.com',
'designer1@mail.com','designer2@mail.com','seo1@mail.com','copy1@mail.com',
'translator1@mail.com','video1@mail.com','biz1@mail.com','data1@mail.com',
'shop1@mail.com','auto1@mail.com'
);


--------------------------------------------------
-- 3. FREELANCERS
--------------------------------------------------

INSERT INTO freelancers (user_id, job_title_id, about_yourself)
SELECT
    u.id,
    (SELECT id FROM job_titles WHERE name = 'Programmer'),
    'Опытный специалист с более чем 5-летним стажем работы в коммерческих проектах. Работал с клиентами из разных сфер бизнеса, соблюдаю сроки и поддерживаю постоянную коммуникацию. Гарантирую качественный результат и профессиональный подход к каждому заказу.'
FROM users u
WHERE u.email IN (
'dev1@mail.com','dev2@mail.com','dev3@mail.com','dev4@mail.com','dev5@mail.com',
'designer1@mail.com','designer2@mail.com','seo1@mail.com','copy1@mail.com',
'translator1@mail.com','video1@mail.com','biz1@mail.com','data1@mail.com',
'shop1@mail.com','auto1@mail.com'
);


--------------------------------------------------
-- 4. SERVICES (15 услуг)
--------------------------------------------------

INSERT INTO services VALUES
(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='dev1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Web Development'),
 'Разработка сайта под ключ',
 'Создам современный адаптивный сайт под ключ с учетом целей вашего бизнеса. Выполняю полный цикл разработки: проектирование структуры, верстку, backend-часть, подключение базы данных, интеграцию форм обратной связи и размещение на сервере. Оптимизирую скорость загрузки и обеспечу корректную работу на всех устройствах.',
 1000,12,3),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='dev2@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Frontend Development'),
 'Frontend на React',
 'Разработаю быстрый и современный интерфейс на React с использованием TypeScript. Реализую адаптивную верстку, анимации, подключение к API и оптимизацию производительности. Код будет структурированным, читаемым и готовым к дальнейшему масштабированию проекта.',
 750,8,2),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='dev3@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Backend Development'),
 'Backend разработка API',
 'Создам надежную серверную архитектуру и REST API для вашего проекта. Реализую авторизацию пользователей, работу с базой данных и интеграцию сторонних сервисов. Особое внимание уделяю безопасности данных и стабильной работе системы при высокой нагрузке.',
 850,9,2),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='dev4@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Full-Stack Development'),
 'Full-stack веб-приложение',
 'Разработаю полноценное веб-приложение от проектирования архитектуры до деплоя на сервер. Настрою frontend, backend, базу данных и интеграции с платежными системами. Обеспечу удобство использования, безопасность и оптимизацию скорости работы.',
 1600,15,4),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='dev5@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Mobile Development'),
 'Разработка мобильного приложения',
 'Создам мобильное приложение для Android или iOS с современным интерфейсом и удобной навигацией. Реализую подключение к серверу, авторизацию пользователей и публикацию в магазине приложений. Помогу протестировать и оптимизировать работу продукта.',
 1400,20,3),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='designer1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='UI/UX Design'),
 'UI/UX дизайн приложения',
 'Разработаю продуманный интерфейс с учетом пользовательских сценариев и современных тенденций дизайна. Подготовлю прототипы, интерактивные макеты и финальный визуальный стиль. Учитываю удобство использования и логику взаимодействия.',
 600,6,3),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='designer2@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Graphic Design'),
 'Разработка фирменного стиля',
 'Создам уникальный фирменный стиль: логотип, цветовую палитру, типографику и брендбук. Дизайн подчеркнет индивидуальность компании и выделит ее среди конкурентов. Подготовлю материалы для печати и цифровых платформ.',
 500,7,3),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='seo1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='SEO'),
 'SEO оптимизация сайта',
 'Проведу комплексный аудит сайта и выполню внутреннюю и внешнюю SEO-оптимизацию. Улучшу структуру страниц, метатеги и скорость загрузки. Помогу увеличить органический трафик и повысить позиции сайта в поисковых системах.',
 400,7,2),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='copy1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Copywriting'),
 'Написание продающих текстов',
 'Напишу убедительные тексты для лендингов, коммерческих предложений и рекламных кампаний. Учитываю целевую аудиторию и особенности продукта. Тексты будут структурированы и направлены на повышение конверсии.',
 300,5,2),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='translator1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Translation'),
 'Профессиональный перевод',
 'Выполню качественный перевод текстов любой сложности с сохранением смысла и стилистики оригинала. Работаю с техническими, маркетинговыми и бизнес-материалами. Гарантирую грамотность и соблюдение сроков.',
 200,4,1),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='video1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Video Production'),
 'Монтаж и создание видео',
 'Создам профессиональный видеоролик для рекламы или социальных сетей. Выполню монтаж, цветокоррекцию, добавлю титры и анимацию. Помогу сделать динамичный и привлекательный визуальный контент.',
 950,10,2),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='biz1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Business Consulting'),
 'Бизнес-консалтинг',
 'Проведу анализ бизнес-процессов и предложу стратегию развития компании. Помогу оптимизировать расходы, повысить прибыль и улучшить управление командой. Предоставлю подробный план внедрения изменений.',
 1200,10,2),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='data1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Data Analysis'),
 'Анализ данных',
 'Проведу анализ данных с использованием современных инструментов. Построю отчеты, визуализации и выявлю ключевые закономерности. Помогу принять управленческие решения на основе цифр и аналитики.',
 700,8,2),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='shop1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Online Stores'),
 'Создание интернет-магазина',
 'Разработаю интернет-магазин с каталогом товаров, корзиной и системой онлайн-оплаты. Настрою управление заказами, интеграцию с доставкой и админ-панель для удобного управления контентом.',
 1300,14,3),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id=(SELECT id FROM users WHERE email='auto1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name='Automation'),
 'Автоматизация процессов',
 'Настрою автоматизацию бизнес-процессов с помощью современных инструментов и интеграций. Это позволит сократить ручной труд, снизить количество ошибок и повысить эффективность работы сотрудников.',
 900,9,2);