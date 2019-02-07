create table member(
  id bigint not null auto_increment primary key ,
  email varchar(30) not null ,
  name varchar(30) not null default "",
  password varchar(1000) not null ,
  phone varchar(13) not null default "",
  memberLevel int not null default 1,
  addresses varchar(300) not null default "",
  profile varchar (200) not null default "",
)