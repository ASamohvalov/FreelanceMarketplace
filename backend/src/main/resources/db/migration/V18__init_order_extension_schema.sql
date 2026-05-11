CREATE TYPE order_extension_status AS ENUM (
    'REJECTED',
    'PENDING',
    'ACCEPTED'
);

CREATE TABLE order_extensions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id UUID NOT NULL REFERENCES orders(id),
    days_added INT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL,
    status order_extension_status DEFAULT 'PENDING' NOT NULL
);