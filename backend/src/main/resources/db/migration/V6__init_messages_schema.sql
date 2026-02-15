CREATE TABLE proposals (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    author_id UUID REFERENCES users(id),
    service_id UUID REFERENCES services(id),
    description VARCHAR(155),
    is_accepted BOOLEAN DEFAULT false
);

CREATE TABLE conversations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4()
);

CREATE TABLE conversation_members (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    member_id UUID REFERENCES users(id),
    conversation_id UUID REFERENCES conversations(id)
);

CREATE TABLE messages (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    author_id UUID REFERENCES users(id),
    conversation_id UUID REFERENCES conversations(id),
    send_at TIMESTAMP,
    message TEXT
);

CREATE TABLE service_reviews (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    author_id UUID REFERENCES users(id),
    service_id UUID REFERENCES services(id),
    review TEXT
);
