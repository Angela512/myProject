<<<<<<< HEAD
create table notice(
	notice_num number not null, 
	mem_num number not null,
	notice_title varchar2(50) not null,
	notice_date date not null,
	notice_count number(5),
	notice_content clob not null,
	notice_file1 varchar2(150),
	notice_file2 varchar2(150),
	notice_file3 varchar2(150)
);
=======
create table trade(
    trade_num number not null,
	mem_num number not null,
	trade_head varchar2(30) not null,
	trade_category varchar2(20) not null,
	trade_title varchar2(50) not null,
	trade_date date default sysdate not null,
	trade_content clob not null,
	trade_price number(10) not null,
	trade_like varchar2(90) not null,
    trade_image1 varchar2(100),
    trade_image2 varchar2(100),
    trade_image3 varchar2(100),
    trade_count number(5),
	trade_phone varchar2(20),
	constraint doran_trade_pk primary key (trade_num)
);

create sequence trade_seq;

--notice
create table notice(
	notice_num number not null, 
	mem_num number not null,
	notice_title varchar2(50) not null,
	notice_date date default sysdate not null,
	notice_count number(5),
	notice_content clob not null,
	notice_file1 varchar2(150),
	notice_file2 varchar2(150),
	notice_file3 varchar2(150),
	constraint notice_pk primary key (notice_num),
	constraint notice_fk foreign key (mem_num) references member(mem_num)
	);
create sequence notice_seq;
