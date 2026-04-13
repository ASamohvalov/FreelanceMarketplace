CREATE TYPE order_status AS ENUM ('IN_PROGRESS', 'SUBMITTED', 'REVISION', 'COMPLETED');

CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    customer_id UUID REFERENCES users(id) NOT NULL,
    freelancer_id UUID REFERENCES freelancers(id) NOT NULL,
    service_id UUID REFERENCES services(id) NOT NULL,
    status order_status NOT NULL DEFAULT 'IN_PROGRESS',
    order_date TIMESTAMP DEFAULT NOW() NOT NULL,
    deadline_date TIMESTAMP NOT NULL
);

CREATE TABLE order_reports (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id UUID REFERENCES orders(id) NOT NULL,
    freelancer_id UUID REFERENCES freelancers(id) NOT NULL,
    report TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL,
    accepted BOOLEAN NOT NULL
);
