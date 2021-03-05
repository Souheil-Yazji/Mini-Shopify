create table migrations.tag (
  id bigint generated always as identity primary key,
  name text not null
);

create table migrations.category (
  id bigint generated always as identity primary key,
  name text not null
);

create table migrations.user (
  id bigint generated always as identity primary key,
  email text not null,
  name text not null
);