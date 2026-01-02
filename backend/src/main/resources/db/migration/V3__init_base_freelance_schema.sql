create extension if not exists "uuid-ossp";

create table job_titles (
    id UUID primary key default uuid_generate_v4(),
    name varchar(255) unique
);

create table freelancers (
    id UUID primary key default uuid_generate_v4(),
    user_id UUID references users(id),
    job_title_id UUID references job_titles(id),
    phone_number varchar(11)
);

create table services (
    id UUID primary key default uuid_generate_v4(),
    freelancer_id UUID references freelancers(id),
    title text,
    description text,
    price int,
    image_path varchar(255)
);
