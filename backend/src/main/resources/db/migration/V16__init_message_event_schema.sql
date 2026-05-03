CREATE TYPE message_events_type AS ENUM ('NEW_MESSAGE', 'EDIT_MESSAGE', 'DELETE_MESSAGE', 'READ_MESSAGE');

CREATE TABLE message_events (
    id BIGSERIAL PRIMARY KEY,
    type message_events_type NOT NULL,
    message_id UUID NOT NULL REFERENCES messages(id),
    created_at TIMESTAMP DEFAULT NOW() NOT NULL
);