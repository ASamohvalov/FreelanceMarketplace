create extension if not exists "uuid-ossp";

create table service_categories (
    id UUID primary key default uuid_generate_v4(),
    name varchar(50) unique
);

create table service_subcategories (
    id UUID primary key default uuid_generate_v4(),
    name varchar(50) unique,
    category_id UUID references service_categories(id)
);

create table services (
    id UUID primary key default uuid_generate_v4(),
    freelancer_id UUID references freelancers(id),
    subcategory_id UUID references service_subcategories(id),
    title varchar(50),
    description text,
    price int,
    deadline_days int,
    revisions_count int
);

create table service_images (
    id UUID primary key default uuid_generate_v4(),
    image_path varchar(255),
    service_id UUID references services(id),
    is_title_image boolean
);