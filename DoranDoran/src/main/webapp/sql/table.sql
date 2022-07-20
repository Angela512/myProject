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