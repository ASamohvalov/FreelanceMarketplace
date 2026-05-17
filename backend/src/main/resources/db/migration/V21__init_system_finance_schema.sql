CREATE TABLE system_finance
(
    id                     BIGINT NOT NULL,
    currency_rate          BIGINT NOT NULL,
    total_service_earnings BIGINT NOT NULL,
    version                BIGINT,
    CONSTRAINT pk_system_finance PRIMARY KEY (id)
);