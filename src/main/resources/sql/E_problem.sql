drop table if exists e_problem;
create table e_problem(
   pid int auto_increment primary key,
   uid int not null,
   problem_content text not null,
   created_at timestamp default current_timestamp not null
);