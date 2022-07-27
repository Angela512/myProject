--trade
create table trade(
    trade_num number not null,
	mem_num number not null,
	trade_head number(1) not null,
	trade_category varchar2(20) not null,
	trade_title varchar2(50) not null,
	trade_date date default sysdate not null,
	trade_content clob not null,
	trade_price number(10) not null,
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


--food
CREATE TABLE FOOD 
(
  FOOD_NUM NUMBER NOT NULL 
, MEM_NUM NUMBER NOT NULL  
, FOOD_NAME VARCHAR2(15) NOT NULL 
, FOOD_PHONE VARCHAR2(20) 
, FOOD_TIME VARCHAR2(20) 
, FOOD_MENU VARCHAR2(60) 
, FOOD_LINK VARCHAR2(30) 
, FOOD_ZIPCODE VARCHAR2(5) NOT NULL
, FOOD_ADDR1 VARCHAR2(150) NOT NULL 
, FOOD_ADDR2 VARCHAR2(150) NOT NULL 
, FOOD_MAP VARCHAR2(150) NOT NULL 
, FOOD_IMAGE1 VARCHAR2(150) 
, FOOD_IMAGE2 VARCHAR2(150) 
, FOOD_IMAGE3 VARCHAR2(150) 
, FOOD_COUNT NUMBER(5)
, constraint FOOD_PK primary key (FOOD_NUM)
, constraint FOOD_FK foreign key (MEM_NUM) 
                             references MEMBER (MEM_NUM)
); 

create sequence food_seq;


--job
CREATE TABLE JOB 
(
  JOB_NUM NUMBER NOT NULL 
, JOB_CONTENT CLOB NOT NULL 
, JOB_DATE DATE default sysdate not null
, JOB_COUNT NUMBER
, JOB_LOGO VARCHAR2(150) 
, JOB_ENDDATE VARCHAR2(50) 
, JOB_ADDR1 VARCHAR2(100) NOT NULL 
, JOB_ADDR2 VARCHAR2(100) NOT NULL 
, JOB_LOCATION VARCHAR2(20) 
, JOB_CATEGORY VARCHAR2(20) 
, JOB_LINK VARCHAR2(20) 
, JOB_ZIPCODE VARCHAR2(10) NOT NULL 
, MEM_NUM NUMBER NOT NULL 
, JOB_TITLE VARCHAR2(50) NOT NULL 
, CONSTRAINT JOB_PK PRIMARY KEY 
  (
    JOB_NUM 
  )
  ENABLE 
);


create sequence job_seq;

ALTER TABLE job ADD CONSTRAINT fk_mj foreign KEY(mem_num) references MEMBER (mem_num);

--member
CREATE TABLE MEMBER 
(
  MEM_NUM NUMBER NOT NULL 
, MEM_ID VARCHAR2(12 BYTE) NOT NULL 
, AUTH NUMBER(1, 0) NOT NULL 
, CONSTRAINT MEMBER_PK PRIMARY KEY 
  (
    MEM_NUM 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX MEMBER_PK ON MEMBER (MEM_NUM ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NO INMEMORY 
NOPARALLEL;


--member_detail

CREATE TABLE MEMBER_DETAIL 
(
  MEM_NUM NUMBER NOT NULL 
, MEM_EMAIL VARCHAR2(50 BYTE) NOT NULL 
, MAM_NAME VARCHAR2(15 BYTE) NOT NULL 
, MEM_PW VARCHAR2(15 BYTE) NOT NULL 
, MEM_PHONE VARCHAR2(15 BYTE) NOT NULL 
, MEM_ZIPCODE VARCHAR2(5 BYTE) NOT NULL 
, MEM_ADDR1 VARCHAR2(100 BYTE) NOT NULL 
, MEM_ADDR2 VARCHAR2(100 BYTE) NOT NULL 
, MEM_DATE DATE default sysdate not null
, MEM_MODIFY_DATE DATE 
, MEM_PHOTO VARCHAR2(150 BYTE) 
, CONSTRAINT MEMBER_DETAIL_PK PRIMARY KEY 
  (
    MEM_NUM 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX MEMBER_DETAIL_PK ON MEMBER_DETAIL (MEM_NUM ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NO INMEMORY 
NOPARALLEL;

create sequence MEMBER_DETAIL_seq;

ALTER TABLE MEMBER_DETAIL ADD CONSTRAINT fk_mm foreign KEY(mem_num) references MEMBER (mem_num);

--trade_like
create table trade_like(
    like_num number not null,
    mem_num number not null,
    trade_num number not null,
    constraint trade_like_pk primary key (like_num),
    constraint trade_like_fk1 foreign key (mem_num) references member (mem_num),
    constraint trade_like_fk2 foreign key (trade_num) references trade (trade_num)
);

create sequence trade_like_seq;

--자유게시판 
create table board(
 mem_num number not null,
 board_num number not null,
 board_head varchar2(15),
 board_title varchar2(50) not null,
 board_content clob not null,
 board_date date default sysdate not null,
 board_modifydate date,
 board_count number(5),
 board_image1 varchar2(150),
 board_image2 varchar2(150),
 board_image3 varchar2(150),
 constraint board_pk primary key (board_num),
 constraint board_fk foreign key (mem_num) references member (mem_num)
);

create sequence board_seq;

--자유게시판 댓글
create table board_reply(
 reply_num number not null,
 board_num number not null,
 mem_num number not null,
 reply_content varchar2(330) not null,
 reply_date date default sysdate not null,
 reply_modifydate date,
 --질문1)pk, fk 명칭은 sql디벨로퍼에 저장할 때 쓰는데 어디서 볼 수 있나요? 그리고 따로 fk를 추가했을 경우 디벨로퍼에서 설정하는 방법은?
 constraint board_reply_pk primary key (reply_num),
 constraint board_reply_fk foreign key (mem_num) references member (mem_num),
 constraint board_reply_fk2 foreign key (board_num) references board (board_num)
);
--질문2) pk는 해당 게시판의 고유값(번호 등)으로 지정하면 유니크한 값을 만들어줌. fk는 다른 테이블에서 가져오는 데이터를 검증.
--fk를 쓰지 않아도 가져올 수 있지만, 사용자가 fk값을 바꿀 경우 연동되도록 하려면 필요한것이 맞는지?
--자유게시판에 mem_name없이 mem_name을 작성자명으로 사용중인데 넣어주는것이 좋은지?
create sequence board_reply_seq;



