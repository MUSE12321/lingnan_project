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

insert into User values (1,"zs","123","�㶫ʡտ���г࿲��","15688475624");
insert into User values (2,"lisi","123","������С��ͬ�����","15678475424");
insert into User values (3,"ww","123","�㶫ʡ��Զ����ɽ��","15628475424");
insert into User values (4,"zl","123","�㶫ʡտ����������","15608475724");

insert into Type values("t01","�յ�");
insert into Type values("t02","����");
insert into Type values("t03","��");
insert into Type values("t04","��ˮ��");
insert into Type values("t05","ϴ�»�");
insert into Type values("t06","��ˮ��");
insert into Type values("t07","����");
insert into Type values("t08","����");
insert into Type values("t09","����");

insert into Device values(1,"���ӻ�1","t02","����","����V66","0");
insert into Device values(2,"�յ�1","t01","����","����V66","1");
insert into Device values(3,"�յ�2","t01","����","����V66","1");
insert into Device values(4,"��ˮ��","t06","����","����V66","0");
insert into Device values(5,"��1","t03","����","����V66","1");
insert into Device values(6,"��ˮ��1","t04","ϴ�ּ�","����V66","0");
insert into Device values(7,"ϴ�»�","t05","��̨","����V66","0");
insert into Device values(8,"���ӻ�2","t02","����","����V66","0");
insert into Device values(9,"�յ�2","t01","����","����V66","0");
insert into Device values(10,"��2","t03","����","����V66","0");
insert into Device values(11,"��3","t03","����","����V66","0");
insert into Device values(12,"��4","t03","ϴ�ּ�","����V66","0");
insert into Device values(13,"��5","t03","����","����V66","0");
insert into Device values(14,"��2","t03","����","����V66","1");
insert into Device values(15,"����","t07","����","����","0");
insert into Device values(16,"����","t08","����","����֮��","1");
insert into Device values(17,"����","t09","ϴ�ּ�","����","0");

insert into Record values(2,'�յ�1','2','��');
insert into Record values(6,'��ˮ��1','2','��');
insert into Record values(1,'���ӻ�1','2','��');

insert into Alarm values(1,6,'��ˮ��1','��','����');
insert into Alarm values(2,1,'���ӻ�1','��','����');

insert into Task values(1,'�յ�2','��,26,����');
insert into Task values(2,'��1','��');