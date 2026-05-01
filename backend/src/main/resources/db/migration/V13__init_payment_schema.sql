CREATE TABLE accounts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID REFERENCES users(id) NOT NULL,
    balance BIGINT DEFAULT 0 NOT NULL
);

CREATE TYPE transfer_status AS ENUM ('HELD', 'RELEASED', 'CANCELLED');

CREATE TABLE transfers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id UUID REFERENCES orders(id) NOT NULL,
    recipient_account_id UUID REFERENCES accounts(id) NOT NULL,
    status transfer_status DEFAULT 'HELD' NOT NULL,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL,
    amount BIGINT NOT NULL
);