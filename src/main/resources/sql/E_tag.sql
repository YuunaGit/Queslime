drop table if exists e_tag;
create table e_tag(
  tid int auto_increment primary key,
  tag_type int,
  tag_name tinytext
);