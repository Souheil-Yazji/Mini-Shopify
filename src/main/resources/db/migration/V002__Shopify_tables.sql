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

create table migrations.shop (
  id BIGINT GENERATED ALWAYS AS IDENTITY,
  owner_id BIGINT,
  name text NOT NULL,
  description TEXT,
  PRIMARY KEY (id),
  CONSTRAINT fd_owner_id
    FOREIGN KEY (owner_id)
      REFERENCES migrations.user(id)
);