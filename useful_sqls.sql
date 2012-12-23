drop table urllist;
create table urllist( id int auto_increment primary key, url varchar(500) null, title varchar(500) null, category varchar(50) null);
drop table wordlocation;
create table wordlocation( wordid int primary key, location varchar(1000) null);
drop table wordlist;
create table wordlist( id int auto_increment primary key, word varchar(500) null);

show databases;
use search_engine_data;
show tables;

select * from urllist;
select * from wordlist;
select * from wordlocation;

show columns from urllist;
show columns from wordlist;
show columns from wordlocation;

show databases;
use search_engine_data_novels;
show tables;