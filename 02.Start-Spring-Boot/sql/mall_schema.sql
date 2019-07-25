drop database if exists mall;
drop user if exists 'hello'@'localhost';
create database mall default character set utf8mb4 collate utf8mb4_unicode_ci;
use mall;
create user 'hello'@'localhost' identified by 'mall123456!';
grant all privileges on mall.* to 'hello'@'localhost';
flush privileges;