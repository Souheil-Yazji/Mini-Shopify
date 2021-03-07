alter table migrations.product drop column image;
alter table migrations.product add column image text not null; -- Store Base64 instead
alter table migrations.product add column description text not null;