DROP SEQUENCE product_seq;
DROP SEQUENCE photo_seq;
DROP SEQUENCE bidding_seq;
DROP SEQUENCE product_report_seq;
DROP SEQUENCE currency_seq;
DROP SEQUENCE cheackout_seq;
DROP SEQUENCE delivery_seq;

DROP TABLE bidding;
DROP TABLE product_report;
DROP TABLE product_favorite;
DROP TABLE product_photo;
DROP TABLE product;
DROP TABLE currency_cheackout;
DROP TABLE currency;
DROP TABLE product_Delivery;
DROP TABLE MEMBER;

CREATE SEQUENCE delivery_seq
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE currency_seq
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE product_seq
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE photo_seq
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE bidding_seq
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE product_report_seq
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE cheackout_seq
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;


--------------------------------------------------------
CREATE TABLE MEMBER
(
MEM_ID VARCHAR2(7 BYTE) NOT NULL
, MEM_AC VARCHAR2(20 BYTE) NOT NULL
, MEM_PASSWORD VARCHAR2(16 BYTE) NOT NULL
, MEM_FIRSTNAME VARCHAR2(20 BYTE) NOT NULL
, MEM_LASTNAME VARCHAR2(20 BYTE) NOT NULL
, MEM_TEL VARCHAR2(100 BYTE)
, MEM_PHONE VARCHAR2(100 BYTE) NOT NULL
, MEM_EMAIL VARCHAR2(100 BYTE) NOT NULL
, MEM_STATUS NUMBER
, MEM_PHOTO BLOB
, MEM_CART_PHOTO BLOB
, MEM_CART_TYPE VARCHAR2(20 BYTE)
, MEM_NICKNAME VARCHAR2(20 BYTE)
, MEM_ABOUTME CLOB
, MEM_BIRTHDAY DATE NOT NULL
, MEM_ROT_BADTIMES NUMBER
, MEM_ACT_BADTIMES NUMBER
, MEM_GRU_BADTIMES NUMBER
, MEM_POST_BADTIMES NUMBER
, MEM_SALE_BADTIMES NUMBER
, CONSTRAINT MEMBER_PK PRIMARY KEY (MEM_ID)
);

-- insert INTO MEMBER VALUES ('M000001','AC_lulu','lulu',0);
-- insert INTO MEMBER VALUES ('M000002','AC_candy','candy',0);
-- insert INTO MEMBER VALUES ('M000003','AC_tom','tom',0);
-- insert INTO MEMBER VALUES ('M000004','AC_kevin','kevin',0);
	
-- insert INTO MEMBER VALUES ('M000005','AC_mia','mia',0);
-- insert INTO MEMBER VALUES ('M000006','AC_lily','lily',0);
-- insert INTO MEMBER VALUES ('M000007','AC_allan','allan',0);
-- insert INTO MEMBER VALUES ('M000008','AC_apple','apple',0);

/*----------------------------------------------------------*/
CREATE TABLE PRODUCT_Delivery (
Delivery_ID VARCHAR2(6)NOT NULL,
mem_id VARCHAR2(7) NOT NULL,
Delivery_name VARCHAR2(100)NOT NULL,
Delivery_address VARCHAR2(1000)NOT NULL,
Delivery_phone VARCHAR2(20)NOT NULL,
CONSTRAINT PK_Delivery_ID PRIMARY KEY (Delivery_ID),
CONSTRAINT FK_address_mem_id FOREIGN KEY (mem_id) REFERENCES MEMBER (mem_id)
);

/*----------------------------------------------------------*/

CREATE TABLE currency (
	mem_id	VARCHAR2(7)NOT NULL,	/*會員編號*/
	currency_id	VARCHAR2(6)NOT NULL,/*自轉幣編號*/
	currency_balance NUMBER (9)NOT NULL,/*異動金額*/
	currency_chargedate DATE,/*異動時間*/
	currency_status NUMBER(1)NOT NULl,/*異動狀態*/
	currency_detail VARCHAR2(255),
	CONSTRAINT PK_vc_id PRIMARY KEY (currency_id),
	CONSTRAINT fk_currency_mem_id FOREIGN KEY (mem_id) REFERENCES MEMBER (mem_id)
);

/*----------------------------------------------------------*/
CREATE TABLE currency_cheackout(
	mem_id	VARCHAR2(7)NOT NULL,/*會員編號*/
	currency_id	VARCHAR2(6)NOT NULL,/*自轉幣編號*/
	cheackout_id VARCHAR2(6)NOT NULL,/*提領編號*/
	cheackout_balance NUMBER(9)NOT NULL,/*提領金額*/
	cheackout_date	DATE,/*提領時間*/
	cheackout_status NUMBER	(7),/*提領狀態*/
	CONSTRAINT PK_cheackout_id PRIMARY KEY (cheackout_id),
	CONSTRAINT fk_cheackout_mem_id FOREIGN KEY (mem_id) REFERENCES MEMBER (mem_id),
	CONSTRAINT fk_currency_id FOREIGN KEY (currency_id) REFERENCES currency (currency_id)
);

/*---------------------------------------------------------*/
CREATE TABLE product(
product_id      VARCHAR2(6) NOT NULL, /*商品編號*/
mem_id_sale     VARCHAR2(7) NOT NULL,/*會員編號賣*/
mem_id_buy      VARCHAR2(7),/*會員編號買*/
product_name   VARCHAR2(255) NOT NULL,/*商品名稱*/
product_type    VARCHAR2(20) NOT NULL,/*商品類別*/
product_detail  VARCHAR2(2000),/*商品描述*/
product_price    NUMBER(6) NOT NULL,/*商品價格*/
product_status	VARCHAR2(20) NOT NULL,/*商品使用狀態*/
product_score	NUMBER(1),/*商品評分*/
product_rating	VARCHAR2(500),/*商品評價*/
product_bidding_price	NUMBER(6),/*起標價格*/
product_update_date	DATE,/*商品更新時間*/
product_sale_type	VARCHAR2(20),/*商品銷售模式*/
product_end_bidding	DATE,/*商品競標時間*/
product_ad	DATE,/*商品廣告*/
product_data_status NUMBER(20),/*商品狀態*/
Delivery_address VARCHAR2(500), 
Delivery_Phone VARCHAR2(20),
Delivery_Name VARCHAR2(100),
product_bidding_win_price NUMBER(6),/*競標得標價格*/
product_Snapshot BLOB,
CONSTRAINT pk_product_id PRIMARY KEY (product_id),
CONSTRAINT fk_mem_id_sale FOREIGN KEY (mem_id_sale) REFERENCES MEMBER (mem_id),
CONSTRAINT fk_mem_id_buy FOREIGN KEY (mem_id_buy) REFERENCES MEMBER (mem_id)
);
 


/*----------------------------------------------------------*/
CREATE TABLE product_photo(
photo_id VARCHAR2(6) NOT NULL, /*圖片編號*/
product_id VARCHAR2(6) NOT NULL, /*商品編號*/
product_photo BLOB,/*商品圖片*/
CONSTRAINT pk_photo_id PRIMARY KEY (photo_id),
CONSTRAINT fk_product_photo_product_id FOREIGN KEY (product_id) REFERENCES product (product_id)
);


/*----------------------------------------------------------*/
CREATE TABLE bidding (
bidding_id VARCHAR2(6) NOT NULL,/*競標編號*/
product_id VARCHAR2(6) NOT NULL,/*商品編號*/
mem_id     VARCHAR2(7) NOT NULL,/*會員編號*/
bidding_price NUMBER(6) NOT NULL,/*競標出價*/
bidding_date DATE,/*出價時間*/
CONSTRAINT pk_bidding_id PRIMARY KEY (bidding_id),
CONSTRAINT fk_bidding_product_id FOREIGN KEY (product_id) REFERENCES product (product_id),
CONSTRAINT fk_bidding_mem_id FOREIGN KEY (mem_id) REFERENCES MEMBER (mem_id)
);


/*----------------------------------------------------------*/
CREATE TABLE product_report(
report_id  VARCHAR2(6) NOT NULL,/*REP*/
mem_id     VARCHAR2(7) NOT NULL,/*會員編號*/
product_id VARCHAR2(7) NOT NULL,/*商品編號*/
report_date DATE,/*檢舉時間*/
report_status NUMBER(1),/*審核狀態*/
report_detail VARCHAR2(255),
CONSTRAINT pk_report_id PRIMARY KEY (report_id),
CONSTRAINT fk_product_report_product_id FOREIGN KEY (product_id) REFERENCES product (product_id),
CONSTRAINT fk_product_report_mem_id FOREIGN KEY (mem_id) REFERENCES MEMBER (mem_id)
);

/*----------------------------------------------------------*/
CREATE TABLE product_favorite(
mem_id     VARCHAR2(7) NOT NULL,/*會員編號 */ 
product_id VARCHAR2(6) NOT NULL,/*商品編號*/	
CONSTRAINT fk_product_favorite_mem_id FOREIGN KEY (mem_id) REFERENCES MEMBER (mem_id),
CONSTRAINT fk_product_favorite_product_id FOREIGN KEY (product_id) REFERENCES product (product_id),
CONSTRAINT pk_product_favorite PRIMARY KEY (mem_id,product_id)
);

commit;
