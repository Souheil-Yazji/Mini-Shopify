alter table migrations.product drop column image;
alter table migrations.product add column image text not null;