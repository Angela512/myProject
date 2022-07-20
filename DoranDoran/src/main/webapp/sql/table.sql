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