CREATE TABLE order_requirements (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    description TEXT NOT NULL,
    deadline_days INT NOT NULL,
    order_id UUID NOT NULL REFERENCES orders(id),
    comment TEXT
);

CREATE TABLE order_requirement_files (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    file_path TEXT NOT NULL,
    order_requirement_id UUID NOT NULL REFERENCES order_requirements(id)
);