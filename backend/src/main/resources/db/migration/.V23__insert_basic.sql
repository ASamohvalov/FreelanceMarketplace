-- Orders + Reviews + Transfers filler
-- 3-5 orders per service
-- COMPLETED orders always have successful transfers
-- system_finance is untouched

WITH service_data AS (
    SELECT
        s.id AS service_id,
        s.price,
        s.freelancer_id,
        f.user_id AS freelancer_user_id
    FROM services s
             JOIN freelancers f ON f.id = s.freelancer_id
),

     generated_orders AS (
         SELECT
             gen_random_uuid() AS id,
             picked.customer_id,
             sd.freelancer_id,
             sd.service_id,
             sd.price,
             picked.status,
             now() - ((20 + random() * 120)::int || ' days')::interval AS order_date,
             CASE
                 WHEN picked.status = 'COMPLETED'::order_status
                     THEN now() - ((1 + random() * 15)::int || ' days')::interval
                 ELSE NULL
                 END AS completion_date,
             now() + ((3 + random() * 14)::int || ' days')::interval AS deadline_date,
             false AS reject_by_customer,
             false AS reject_by_freelancer
         FROM service_data sd
                  CROSS JOIN LATERAL (
             SELECT
                 u.id AS customer_id,
                 (ARRAY[
                     'COMPLETED',
                     'COMPLETED',
                     'COMPLETED',
                     'IN_PROGRESS',
                     'PENDING'
                     ])[1 + floor(random() * 5)::int]::order_status AS status
             FROM users u
             WHERE u.id <> sd.freelancer_user_id
             ORDER BY random()
             LIMIT (3 + floor(random() * 3)::int)
             ) picked
     ),

     inserted_orders AS (
         INSERT INTO orders (
                             id,
                             customer_id,
                             freelancer_id,
                             service_id,
                             status,
                             order_date,
                             completion_date,
                             deadline_date,
                             reject_by_customer,
                             reject_by_freelancer
             )
             SELECT
                 id,
                 customer_id,
                 freelancer_id,
                 service_id,
                 status,
                 order_date,
                 completion_date,
                 deadline_date,
                 reject_by_customer,
                 reject_by_freelancer
             FROM generated_orders
             RETURNING *
     )

-- Reviews only for completed orders
INSERT INTO reviews (
    order_id,
    review,
    rating,
    created_at,
    updated_at
)
SELECT
    o.id,
    (ARRAY[
        'Отличная работа, рекомендую!',
        'Все выполнено качественно и в срок.',
        'Хорошая коммуникация и быстрый результат.',
        'Исполнитель учел все пожелания.',
        'Буду обращаться еще.'
        ])[1 + floor(random() * 5)::int],
    4 + floor(random() * 2)::int,
    now() - ((1 + random() * 20)::int || ' days')::interval,
    now()
FROM inserted_orders o
WHERE o.status = 'COMPLETED';

-- Transfers for completed orders
INSERT INTO transfers (
    order_id,
    recipient_account_id,
    status,
    created_at,
    amount
)
SELECT
    o.id,
    a.id,
    'RELEASED'::transfer_status,
    o.completion_date,
    s.price
FROM orders o
         JOIN services s ON s.id = o.service_id
         JOIN freelancers f ON f.id = o.freelancer_id
         JOIN accounts a ON a.user_id = f.user_id
WHERE o.status = 'COMPLETED';
