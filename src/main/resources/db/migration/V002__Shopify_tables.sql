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

create table migrations.product(
    id bigint generated always as identity primary key,
    shop_id BIGINT REFERENCES migrations.shop(id),
    name text not null,
    price BIGINT not null,
    quantity BIGINT not null,
    image bytea not null
);

create table migrations.shop_category(
    shop_id BIGINT REFERENCES migrations.shop (id),
    category_id BIGINT REFERENCES migrations.category (id),
    CONSTRAINT shop_category_id PRIMARY KEY (shop_id, category_id)
);