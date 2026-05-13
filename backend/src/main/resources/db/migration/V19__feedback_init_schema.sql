CREATE TABLE feedbacks
(
    id        UUID         NOT NULL,
    type      VARCHAR(255) NOT NULL,
    text      TEXT         NOT NULL,
    title     VARCHAR(255) NOT NULL,
    sender_id UUID         NOT NULL,
    accepted  BOOLEAN      NOT NULL,
    created_at  TIMESTAMP      NOT NULL,
    CONSTRAINT pk_feedbacks PRIMARY KEY (id)
);

ALTER TABLE feedbacks
    ADD CONSTRAINT FK_FEEDBACKS_ON_SENDER FOREIGN KEY (sender_id) REFERENCES users (id);
