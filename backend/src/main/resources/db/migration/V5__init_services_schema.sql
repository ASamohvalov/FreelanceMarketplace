CREATE TABLE service_categories (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE
);

CREATE TABLE service_subcategories (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE,
    category_id UUID REFERENCES service_categories(id)
);

CREATE TABLE services (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    freelancer_id UUID REFERENCES freelancers(id),
    subcategory_id UUID REFERENCES service_subcategories(id),
    title VARCHAR(50),
    description TEXT,
    price INT,
    deadline_days INT,
    revisions_count INT
);

CREATE TABLE service_images (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    image_path VARCHAR(255),
    service_id UUID REFERENCES services(id),
    is_title_image BOOLEAN
);