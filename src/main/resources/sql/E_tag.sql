drop table if exists e_tag;
create table e_tag(
  tid int auto_increment primary key,
  tag_type tinyint,
  tag_name tinytext
);