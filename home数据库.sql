drop database if exists home;
create database home;

use home;

create table User(
id int primary key auto_increment,
name varchar(20) unique,
password varchar(20) not null,
address varchar(50) not null,
phone char(15) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table Type(
tid varchar(5) primary key,
tname varchar(10) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table Device(
did int not null auto_increment,
dname varchar(10) not null,
tid varchar(5)not null,
location varchar(10) not null,  
param   varchar(100) not null,  
status   varchar(2) not null  , 
primary key(did,status),
constraint Device_tid foreign key (tid) references Type(tid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table Record(
did int not null,
dname varchar(10) not null,
status   varchar(2) not null,
risk varchar(10)  primary key,
CONSTRAINT did FOREIGN KEY (did) REFERENCES Device (did)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table Alarm(
aid int  primary key auto_increment,
did int not null ,
dname varchar(10) not null,
risk      varchar(10) not null ,
measure Varchar(50) not null,
constraint Alarm_id foreign key (did) references Device(did),
constraint Alarm_risk foreign key (risk) references Record(risk)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table Task(
taskid int primary key auto_increment,
dname varchar(10) not null,
instruction varchar(100) not null 
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into User values (1,"zs","123","广东省湛江市赤坎区","15688475624");
insert into User values (2,"lisi","123","北京市小胡同里隔壁","15678475424");
insert into User values (3,"ww","123","广东省清远市阳山县","15628475424");
insert into User values (4,"zl","123","广东省湛江市麻章区","15608475724");

insert into Type values("t01","空调");
insert into Type values("t02","电视");
insert into Type values("t03","灯");
insert into Type values("t04","热水器");
insert into Type values("t05","洗衣机");
insert into Type values("t06","饮水机");
insert into Type values("t07","冰箱");
insert into Type values("t08","窗帘");
insert into Type values("t09","抽风机");

insert into Device values(1,"电视机1","t02","客厅","海尔V66","0");
insert into Device values(2,"空调1","t01","客厅","海尔V66","1");
insert into Device values(3,"空调2","t01","卧室","海尔V66","1");
insert into Device values(4,"饮水机","t06","客厅","海尔V66","0");
insert into Device values(5,"灯1","t03","客厅","海尔V66","1");
insert into Device values(6,"热水器1","t04","洗手间","海尔V66","0");
insert into Device values(7,"洗衣机","t05","阳台","海尔V66","0");
insert into Device values(8,"电视机2","t02","卧室","海尔V66","0");
insert into Device values(9,"空调2","t01","卧室","海尔V66","0");
insert into Device values(10,"灯2","t03","客厅","海尔V66","0");
insert into Device values(11,"灯3","t03","卧室","海尔V66","0");
insert into Device values(12,"灯4","t03","洗手间","海尔V66","0");
insert into Device values(13,"灯5","t03","厨房","海尔V66","0");
insert into Device values(14,"灯2","t03","客厅","海尔V66","1");
insert into Device values(15,"冰箱","t07","厨房","美的","0");
insert into Device values(16,"窗帘","t08","客厅","海蓝之家","1");
insert into Device values(17,"抽风机","t09","洗手间","美的","0");

insert into Record values(2,'空调1','2','低');
insert into Record values(6,'热水器1','2','中');
insert into Record values(1,'电视机1','2','高');

insert into Alarm values(1,6,'热水器1','中','报修');
insert into Alarm values(2,1,'电视机1','高','更换');

insert into Task values(1,'空调2','开,26,中速');
insert into Task values(2,'灯1','开');