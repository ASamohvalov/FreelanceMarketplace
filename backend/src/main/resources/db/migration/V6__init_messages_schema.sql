create extension if not exists "uuid-ossp";

create table proposals (
    id UUID primary key default uuid_generate_v4(),
    author_id UUID references users(id),
    service_id UUID references services(id),
    description varchar(155),
    is_accepted boolean default false
);

create table conversations (
    id UUID primary key default uuid_generate_v4()
);

create table conversation_members (
    id UUID primary key default uuid_generate_v4(),
    member_id UUID references users(id),
    conversation_id UUID references conversations(id)
);

create table messages (
    id UUID primary key default uuid_generate_v4(),
    author_id UUID references users(id),
    conversation_id UUID references conversations(id),
    message text
);

create table service_reviews (
    id UUID primary key default uuid_generate_v4(),
    author_id UUID references users(id),
    service_id UUID references services(id),
    review text
);
