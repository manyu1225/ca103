drop table add_row;
drop table add_area;
drop table add_city;
drop SEQUENCE seq_add_row;
drop SEQUENCE seq_add_area;
drop SEQUENCE seq_add_city;

CREATE TABLE add_city(
city_id VARCHAR2(4) PRIMARY KEY,
city_name VARCHAR2(15),
city_eng_name VARCHAR2(50)
);

CREATE TABLE add_area(
area_id VARCHAR2(6) PRIMARY KEY,
city_id VARCHAR2(4),
Zip_ID	VARCHAR2(4),
area_name VARCHAR2(15),
area_eng_name VARCHAR2(50),
CONSTRAINT FK_city_id FOREIGN KEY (city_id) REFERENCES add_city (city_id)
);

CREATE TABLE add_row(
row_id VARCHAR2(10) PRIMARY KEY,
area_id VARCHAR2(6),
row_name VARCHAR2(1000),
area_eng_name VARCHAR2(50),
CONSTRAINT FK_area_id FOREIGN KEY (area_id) REFERENCES add_area (area_id)
);




CREATE SEQUENCE seq_add_city
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE seq_add_area
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE seq_add_row
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;
  
commit;
-- INSERT INTO add_city VALUES('1', '2','3');
--insert INTO currency VALUES('M000001','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),'1000',current_TIMESTAMP,1,null);
