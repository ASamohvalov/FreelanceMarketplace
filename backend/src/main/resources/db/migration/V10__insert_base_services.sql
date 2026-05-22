--------------------------------------------------
-- 1. USERS
--------------------------------------------------

INSERT INTO users (email, password, first_name, last_name)
VALUES ('dev1@mail.com', '$2a$12$MU.vTIV6avXTcNLa2gO66.PNS9rd4TPXDQyDlYcEER53d3CXW/ufW', 'Алексей', 'Иванов'),
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
FROM users u,
     roles r
WHERE r.name = 'ROLE_FREELANCER'
  AND u.email IN (
                  'dev1@mail.com', 'dev2@mail.com', 'dev3@mail.com', 'dev4@mail.com', 'dev5@mail.com',
                  'designer1@mail.com', 'designer2@mail.com', 'seo1@mail.com', 'copy1@mail.com',
                  'translator1@mail.com', 'video1@mail.com', 'biz1@mail.com', 'data1@mail.com',
                  'shop1@mail.com', 'auto1@mail.com'
    );


--------------------------------------------------
-- 3. FREELANCERS
--------------------------------------------------

INSERT INTO freelancers (user_id, job_title_id, about_yourself)
SELECT u.id,
       (SELECT id FROM job_titles WHERE name = 'Programmer'),
       'Опытный специалист с более чем 5-летним стажем работы в коммерческих проектах. Работал с клиентами из разных сфер бизнеса, соблюдаю сроки и поддерживаю постоянную коммуникацию. Гарантирую качественный результат и профессиональный подход к каждому заказу.'
FROM users u
WHERE u.email IN (
                  'dev1@mail.com', 'dev2@mail.com', 'dev3@mail.com', 'dev4@mail.com', 'dev5@mail.com',
                  'designer1@mail.com', 'designer2@mail.com', 'seo1@mail.com', 'copy1@mail.com',
                  'translator1@mail.com', 'video1@mail.com', 'biz1@mail.com', 'data1@mail.com',
                  'shop1@mail.com', 'auto1@mail.com'
    );


--------------------------------------------------
-- 4. SERVICES (15 услуг)
--------------------------------------------------

INSERT INTO services
VALUES (uuid_generate_v4(),
        (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'dev1@mail.com')),
        (SELECT id FROM service_subcategories WHERE name = 'Web Development'),
        'Разработка сайта под ключ',
        'Нужен современный сайт для бизнеса, услуг или онлайн-продаж? Сделаю проект под ключ — от структуры до запуска на сервере.

Что входит в работу:

- адаптивная верстка
- backend и база данных
- формы заявок и интеграции
- оптимизация скорости
- SEO-friendly структура
- размещение на хостинге

Почему стоит выбрать меня?

- чистый и понятный код
- современный стек технологий
- помощь после сдачи проекта
- всегда на связи

Для старта отправьте:
- пример сайтов
- описание проекта
- желаемый функционал',
 100000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'dev2@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'Frontend Development'),
 'Frontend на React',
 'Сделаю frontend на React / Next.js с быстрой загрузкой и аккуратным интерфейсом.

Могу реализовать:

- личный кабинет
- dashboard
- лендинг
- SPA приложение
- интеграцию API
- анимации и адаптив

Использую:
React, TypeScript, Redux, Tailwind, Next.js.

Что получите:
- структурированный проект
- качественную верстку pixel perfect
- готовность к масштабированию
- поддержку по запуску

Важно:
Код пишется вручную без конструкторов.',
 75000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'dev3@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'Backend Development'),
 'Backend разработка API',
 'Разработаю backend и REST API для вашего сервиса или мобильного приложения.

Что будет сделано:

- авторизация JWT
- работа с PostgreSQL / MongoDB
- интеграция сторонних сервисов
- защита API
- админ-панель
- документация endpoints

Особое внимание уделяю:
- безопасности
- производительности
- чистой архитектуре

Подойдет для:
SaaS, CRM, маркетплейсов, мобильных приложений и стартапов.',
 85000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'dev4@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'Full-Stack Development'),
 'Full-stack веб-приложение',
 'Полностью разработаю веб-приложение любой сложности.

От вас нужна только идея :)

Что входит:
- frontend
- backend
- база данных
- деплой
- интеграции
- авторизация пользователей

Работаю быстро и без лишней бюрократии.
Помогу подобрать технологии и оптимальную архитектуру.

В результате вы получите готовый продукт для запуска бизнеса.',
 160000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'dev5@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'Mobile Development'),
 'Разработка мобильного приложения',
 'Создам мобильное приложение для Android или iOS с современным дизайном и стабильной работой.

Что могу сделать:
- приложение для бизнеса
- сервис доставки
- онлайн-магазин
- приложение для бронирования
- интеграцию API

В стоимость входит:
- UI интерфейс
- backend подключение
- push уведомления
- публикация в stores

Пишите — обсудим проект и сроки.',
 140000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'designer1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'UI/UX Design'),
 'UI/UX дизайн приложения',
 'Сделаю UI/UX дизайн, который будет не только красивым, но и удобным для пользователя.

Что вы получите:
- анализ пользовательского пути
- wireframes
- интерактивный прототип
- современный UI-kit
- дизайн для mobile/web

Работаю в Figma.

Мой подход:
Минимализм, логика интерфейса и внимание к деталям.',
 60000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'designer2@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'Graphic Design'),
 'Разработка фирменного стиля',
 'Создам фирменный стиль, который будет запоминаться.

Разработаю:
- логотип
- фирменные цвета
- типографику
- брендбук
- оформление соцсетей

Подходит для:
стартапов, магазинов, IT-компаний, брендов одежды и услуг.

Исходники передаю во всех нужных форматах.',
 50000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'seo1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'SEO'),
 'SEO оптимизация сайта',
 'Хотите больше трафика из Google? Проведу SEO оптимизацию сайта и помогу улучшить позиции.

Что входит:
- SEO аудит
- оптимизация метатегов
- анализ конкурентов
- улучшение скорости сайта
- рекомендации по структуре

White hat методы.
Без спама и рискованных техник.

Подходит для:
интернет-магазинов, блогов, корпоративных сайтов.',
 40000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'copy1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'Copywriting'),
 'Написание продающих текстов',
 'Напишу тексты, которые помогают продавать.

Могу подготовить:
- тексты для лендинга
- email-рассылки
- рекламные объявления
- описания услуг
- SEO статьи

Почему мои тексты работают?
- понятная структура
- акцент на выгоды
- адаптация под ЦА
- без воды и шаблонов

Для начала отправьте тему и задачу.',
 30000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'translator1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'Translation'),
 'Профессиональный перевод',
 'Выполню качественный перевод текстов любой сложности.

Работаю с:
- техническими документами
- бизнес-материалами
- статьями
- сайтами
- маркетинговыми текстами

Гарантирую:
- грамотность
- сохранение смысла
- естественную подачу текста
- соблюдение сроков

Возможен срочный перевод.',
 20000),
(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'video1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'Video Production'),
 'Монтаж и создание видео',
 'Смонтирую видео для YouTube, TikTok, Instagram или рекламы.

Что входит:
- монтаж ролика
- переходы и эффекты
- музыка и sound design
- титры и субтитры
- цветокоррекция
- motion graphics

Работаю с любыми форматами контента:
влоги, reels, shorts, реклама, интервью.

Результат:
динамичное видео, которое удерживает внимание зрителя.',
 95000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'biz1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'Business Consulting'),
 'Бизнес-консалтинг',
 'Помогу найти слабые места в бизнесе и увеличить эффективность процессов.

Что можем разобрать:
- продажи
- маркетинг
- расходы
- команду
- стратегию роста
- автоматизацию

Подходит для:
малого бизнеса, стартапов и онлайн-проектов.

После консультации вы получите:
- конкретный план действий
- рекомендации по улучшению
- разбор ошибок и точек роста',
 120000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'data1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'Data Analysis'),
 'Анализ данных',
 'Проведу анализ данных и подготовлю понятные отчеты для бизнеса.

Что могу сделать:
- обработка больших данных
- визуализация
- dashboards
- прогнозирование
- поиск закономерностей
- автоматические отчеты

Использую:
Python, SQL, Power BI, Excel.

Помогу превратить цифры в понятные решения.',
 70000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'shop1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'Online Stores'),
 'Создание интернет-магазина',
 'Разработаю интернет-магазин под ключ с современным дизайном и удобной админкой.

Функционал:
- каталог товаров
- корзина
- онлайн-оплата
- система заказов
- интеграция доставки
- личный кабинет

Преимущества:
- адаптивность
- высокая скорость загрузки
- SEO-ready структура
- удобное управление товарами

Подходит для запуска онлайн-продаж с нуля.',
 130000),

(uuid_generate_v4(),
 (SELECT id FROM freelancers WHERE user_id = (SELECT id FROM users WHERE email = 'auto1@mail.com')),
 (SELECT id FROM service_subcategories WHERE name = 'Automation'),
 'Автоматизация процессов',
 'Настрою автоматизацию рутинных задач и бизнес-процессов.

Что можно автоматизировать:
- CRM
- заявки
- email-рассылки
- отчеты
- интеграции сервисов
- работу менеджеров

Что это даст:
- меньше ручной работы
- снижение ошибок
- экономию времени
- рост эффективности команды

Подберу оптимальное решение под ваш бизнес.',
        90000);