create table migrations.tag (
  id bigint generated always as identity primary key,
  name text not null
);

create table migrations.category (
  id bigint generated always as identity primary key,
  name text not null
);