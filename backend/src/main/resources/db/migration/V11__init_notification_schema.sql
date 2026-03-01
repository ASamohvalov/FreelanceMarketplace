CREATE TYPE notification_type AS ENUM ('NEW_ORDER', 'NEW_PROPOSAL', 'NEW_REVIEW');

CREATE TABLE notifications (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    recipient_id UUID REFERENCES users(id) NOT NULL,
    title VARCHAR(55) NOT NULL,
    message TEXT NOT NULL,
    type notification_type NOT NULL,
    entity_type VARCHAR(55) NOT NULL,
    entity_id UUID NOT NULL,
    send_at TIMESTAMP DEFAULT NOW() NOT NULL,
    read_at TIMESTAMP
);
