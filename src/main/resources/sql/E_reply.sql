drop table if exists e_reply;
create table e_reply(
   rid int auto_increment primary key,
   sid int not null,
   post_uid int not null,
   to_uid int,
   reply_content text not null,
   created_at timestamp default current_timestamp not null
);