drop table if exists e_problem;
create table e_problem(
   pid int auto_increment primary key,
   problem_title tinytext not null,
   problem_content text not null
);