CREATE TABLE service_categories (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE service_subcategories (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE NOT NULL,
    category_id UUID REFERENCES service_categories(id) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE services (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    freelancer_id UUID NOT NULL REFERENCES freelancers(id),
    subcategory_id UUID NOT NULL REFERENCES service_subcategories(id),
    title VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    price BIGINT NOT NULL,
    deadline_days INT NOT NULL,
    revisions_count INT NOT NULL,
    is_hide BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMP DEFAULT NOW() NOT NULL,
    title_image_id UUID
);

CREATE TABLE service_images (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    image_path VARCHAR(255),
    service_id UUID REFERENCES services(id)
);

ALTER TABLE services
ADD CONSTRAINT fk_services_title_image
FOREIGN KEY (title_image_id) REFERENCES service_images(id) ON DELETE SET NULL;