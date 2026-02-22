CREATE TYPE order_status AS ENUM ('IN_PROGRESS', 'COMPLETED');

CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID REFERENCES users(id) NOT NULL,
    service_id UUID REFERENCES services(id) NOT NULL,
    status order_status NOT NULL DEFAULT 'IN_PROGRESS'
);