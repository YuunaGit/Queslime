drop table if exists e_study;
create table e_study(
    uid int not null,
    pid int not null,
    pass tinyint not null,
    difficulty tinyint not null,
    created_at timestamp default current_timestamp not null
);