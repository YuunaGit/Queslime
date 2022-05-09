drop table if exists e_user;
create table e_user(
    uid int auto_increment primary key,
    user_name varchar(100) unique not null,
    user_email varchar(100) unique not null,
    user_password tinytext not null,
    user_state tinyint not null,
    created_at timestamp default current_timestamp not null
);