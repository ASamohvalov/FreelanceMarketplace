CREATE TABLE order_report_files (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    file_path TEXT NOT NULL,
    order_report_id UUID NOT NULL REFERENCES order_reports(id)
)