drop table if exists e_problem;
create table e_problem(
   pid int auto_increment primary key,
   problem_name tinytext not null,
   problem_content tinytext not null
);