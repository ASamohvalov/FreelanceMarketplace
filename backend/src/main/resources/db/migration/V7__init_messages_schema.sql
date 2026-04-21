CREATE TABLE proposals (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    author_id UUID REFERENCES users(id) NOT NULL,
    service_id UUID REFERENCES services(id) NOT NULL,
    description VARCHAR(155) NOT NULL,
    is_accepted BOOLEAN DEFAULT false NOT NULL
);

CREATE TYPE conversation_type AS ENUM ('ORDER', 'DISCUSSION');

CREATE TABLE conversations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id UUID REFERENCES orders(id),
    service_id UUID REFERENCES services(id) NOT NULL,
    type conversation_type DEFAULT 'DISCUSSION' NOT NULL
);

CREATE TABLE conversation_members (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    member_id UUID REFERENCES users(id) NOT NULL,
    conversation_id UUID REFERENCES conversations(id) NOT NULL
);

CREATE TABLE messages (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    author_id UUID REFERENCES users(id) NOT NULL,
    conversation_id UUID REFERENCES conversations(id) NOT NULL,
    send_at TIMESTAMP DEFAULT NOW() NOT NULL,
    update_at TIMESTAMP DEFAULT NOW() NOT NULL,
    is_read BOOLEAN DEFAULT FALSE NOT NULL,
    message TEXT NOT NULL
);