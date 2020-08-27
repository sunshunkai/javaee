

DROP TABLE IF EXISTS user;
CREATE TABLE user (
`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 name varchar(35),
 PHONE varchar(35)
);
select * from user;
insert into user(id,name,PHONE) values(784,'在写作协作','123456');
insert into user(id,name,phone) values(90,'是的','123456');

select * from user;