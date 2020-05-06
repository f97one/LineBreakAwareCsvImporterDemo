create table app_user (
    user_id serial primary key,
    username character varying (32) not null,
    description character varying (1024)
)