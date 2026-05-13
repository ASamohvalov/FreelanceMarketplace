CREATE TABLE feedback_conversations
(
    id              UUID NOT NULL,
    conversation_id UUID NOT NULL,
    feedback_id     UUID NOT NULL,
    CONSTRAINT pk_feedback_conversations PRIMARY KEY (id)
);

ALTER TABLE feedback_conversations
    ADD CONSTRAINT FK_FEEDBACK_CONVERSATIONS_ON_CONVERSATION FOREIGN KEY (conversation_id) REFERENCES conversations (id);

ALTER TABLE feedback_conversations
    ADD CONSTRAINT FK_FEEDBACK_CONVERSATIONS_ON_FEEDBACK FOREIGN KEY (feedback_id) REFERENCES feedbacks (id);