drop table if exists e_user;
create table e_user(
    uid int auto_increment primary key,
    user_name tinytext not null,
    user_email tinytext not null,
    user_password tinytext not null,
    user_status tinyint not null,
    created_at timestamp(6) default current_timestamp(6) not null
);