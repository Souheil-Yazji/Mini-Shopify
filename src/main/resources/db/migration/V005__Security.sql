alter table migrations.user add column password text NOT NULL;      -- Table must be truncated before deployment

CREATE TABLE migrations.roles(
    id BIGINT primary key,
    role text NOT NULL
);

CREATE TABLE migrations.user_roles(
    user_id BIGINT REFERENCES migrations.user (id),
    role_id BIGINT REFERENCES migrations.roles (id),
    CONSTRAINT user_role_id PRIMARY KEY (user_id, role_id)
);