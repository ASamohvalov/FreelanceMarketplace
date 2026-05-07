CREATE TYPE order_status AS ENUM ('CANCELLED', 'IN_PROGRESS', 'REJECTED', 'COMPLETED', 'PENDING');

CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    customer_id UUID REFERENCES users(id) NOT NULL,
    freelancer_id UUID REFERENCES freelancers(id) NOT NULL,
    service_id UUID REFERENCES services(id) NOT NULL,
    status order_status NOT NULL DEFAULT 'IN_PROGRESS',
    order_date TIMESTAMP DEFAULT NOW() NOT NULL,
    completion_date TIMESTAMP,
    deadline_date TIMESTAMP
);

CREATE TYPE order_report_status AS ENUM ('ACCEPTED', 'REJECTED', 'PENDING');

CREATE TABLE order_reports (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id UUID REFERENCES orders(id) NOT NULL,
    freelancer_id UUID REFERENCES freelancers(id) NOT NULL,
    customer_id UUID REFERENCES users(id) NOT NULL,
    title VARCHAR(255) NOT NULL,
    report TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL,
    status order_report_status DEFAULT 'PENDING' NOT NULL,
    customer_comment TEXT
);
