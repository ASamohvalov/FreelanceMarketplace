create extension if not exists "uuid-ossp";

create table users (
    id UUID primary key default uuid_generate_v4(),
    email varchar(255) unique not null,
    password varchar(255) not null,
    first_name varchar(155) not null,
    last_name varchar(155) not null
);

create table roles (
    id UUID primary key default uuid_generate_v4(),
    name varchar(55) not null
);

create table user_roles (
    id UUID primary key default uuid_generate_v4(),
    user_id UUID not null references users(id),
    role_id UUID not null references roles(id)
);

create table tokens (
    id UUID primary key default uuid_generate_v4(),
    user_id UUID not null references users(id),
    token text not null
);