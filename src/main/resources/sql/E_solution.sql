drop table if exists e_solution;
create table e_solution(
    sid int auto_increment primary key,
    pid int not null,
    uid int not null,
    solution_content text not null,
    like_count int not null,
    created_at timestamp default current_timestamp not null
);