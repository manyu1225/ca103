drop table add_road;
drop table add_area;
drop table add_city;
drop SEQUENCE seq_road;
drop SEQUENCE seq_area;
drop SEQUENCE seq_city;


CREATE TABLE add_city(
city_id VARCHAR2(4) PRIMARY KEY,
city_name VARCHAR2(15),
city_eng_name VARCHAR2(50)
);

CREATE TABLE add_area(
area_id VARCHAR2(7) PRIMARY KEY,
zip_code VARCHAR2(5),
area_name VARCHAR2(15),
area_eng_name VARCHAR2(50),
city_id VARCHAR2(4),
CONSTRAINT FK_city_id FOREIGN KEY (city_id) REFERENCES add_city (city_id)
);

CREATE TABLE add_road(
road_id VARCHAR2(10) PRIMARY KEY,
road_name VARCHAR2(500),
road_eng_name VARCHAR2(500),
area_id VARCHAR2(7),
CONSTRAINT FK_area_id FOREIGN KEY (area_id) REFERENCES add_area (area_id)
);



CREATE SEQUENCE seq_city
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE seq_area
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE seq_road
INCREMENT BY 1
START WITH 1
NOCYCLE
NOCACHE;
  
commit;

-- INSERT INTO add_city VALUES('CT'||lpad(to_char(seq_city.nextval),2,'0'), ?, ?)
-- INSERT INTO add_city VALUES('AR'||lpad(to_char(seq_area.nextval),3,'0'), ?, ?)
-- INSERT INTO add_city VALUES('RD'||lpad(to_char(seq_road.nextval),4,'0'), ?, ?)
--


-- select COUNT(*) FROM add_road WHERE area_id = 'AR00032';