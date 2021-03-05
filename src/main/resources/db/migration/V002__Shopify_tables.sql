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
  id BIGINT generated always as identity primary key,
  owner_id BIGINT REFERENCES migrations.user(id),
  name text NOT NULL,
  description TEXT
);

create table migrations.product(
    id bigint generated always as identity primary key,
    shop_id BIGINT REFERENCES migrations.shop(id),
    name text not null,
    price decimal not null,
    quantity integer not null,
    image bytea not null
);

create table migrations.shop_category(
    shop_id BIGINT REFERENCES migrations.shop (id),
    category_id BIGINT REFERENCES migrations.category (id),
    CONSTRAINT shop_category_id PRIMARY KEY (shop_id, category_id)
);

create table migrations.shop_tag (
    shop_id BIGINT REFERENCES migrations.shop (id),
    tag_id BIGINT REFERENCES migrations.tag (id),
    CONSTRAINT shop_tag_id PRIMARY KEY (shop_id,tag_id)
);