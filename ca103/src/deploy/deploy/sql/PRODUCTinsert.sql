
/*---------------------會員------------------------------------*/

INSERT INTO MEMBER(MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME,
MEM_LASTNAME,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_PHOTO,MEM_CART_PHOTO,MEM_NICKNAME,MEM_ABOUTME,MEM_BIRTHDAY) 
values ('M000901','IISCA103','123456','林','培南','02-28052756'
,'0981884277','k99611244@gcloud.csu.edu.tw',null,null,'JAVA','大家好，希望可以多認識各位',TO_DATE('1988/08/08', 'yyyy/mm/dd'));
INSERT INTO MEMBER(MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME,
MEM_LASTNAME,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_PHOTO,MEM_CART_PHOTO,MEM_NICKNAME,MEM_ABOUTME,MEM_BIRTHDAY) 
values ('M000902','IISCA104','123456','陳','韋伶'
,'02-29661103','0911701415','loxa5210@gmail.com',null,null,'IIS-Spring','今天天氣不錯',TO_DATE('1989/12/31', 'yyyy/mm/dd'));
INSERT INTO MEMBER(MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME,
MEM_LASTNAME,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_PHOTO,MEM_CART_PHOTO,MEM_NICKNAME,MEM_ABOUTME,MEM_BIRTHDAY) 
values ('M000903','LILI','123456','黃','LILI'
,'05-77778888','0918599528','k99611244@gcloud.csu.edu.tw',null,null,'黃LILI','你好',TO_DATE('1973/11/13','yyyy/mm/dd'));
INSERT INTO MEMBER(MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME,MEM_LASTNAME
,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_PHOTO,MEM_CART_PHOTO,MEM_NICKNAME,MEM_ABOUTME,MEM_BIRTHDAY) 
values ('M000904','LILI1','123456','柯','文哲'
,'04-55665656','0982445678','k99611244@gcloud.csu.edu.tw',null,null,'IIS-JAVA','我想是這樣啦',TO_DATE('1999/08/31', 'yyyy/mm/dd'));
INSERT INTO MEMBER(MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME
,MEM_LASTNAME,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_PHOTO,MEM_CART_PHOTO,MEM_NICKNAME,MEM_ABOUTME,MEM_BIRTHDAY) 
values ('M000905','LILI2','123456','分','解茶'
,'02-25468741','0981557441','k99611244@gcloud.csu.edu.tw',null,null,'IIS-HTML','我還沒喝保',TO_DATE('1991/04/21', 'yyyy/mm/dd'));
INSERT INTO MEMBER(MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME,
MEM_LASTNAME,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_PHOTO,MEM_CART_PHOTO,MEM_NICKNAME,MEM_ABOUTME,MEM_BIRTHDAY) 
values ('M000906','JAVACA103','123456','林','偉翔','02-28052756'
,'0981884277','JAVACA103@hotmail.com',NULL,NULL,'JAVA','大家好，希望可以多認識各位',TO_DATE('1988/08/08', 'yyyy/mm/dd'));
INSERT INTO MEMBER(MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME,
MEM_LASTNAME,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_PHOTO,MEM_CART_PHOTO,MEM_NICKNAME,MEM_ABOUTME,MEM_BIRTHDAY) 
values ('M000907','SPRING103','123456','林','韋伶'
,'02-29661103','0911701415','k99611244@gcloud.csu.edu.tw',NULL,NULL,'IIS-小吳','今天天氣不錯',TO_DATE('1989/12/31', 'yyyy/mm/dd'));
INSERT INTO MEMBER(MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME,
MEM_LASTNAME,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_PHOTO,MEM_CART_PHOTO,MEM_NICKNAME,MEM_ABOUTME,MEM_BIRTHDAY) 
values ('M000908','HTML103','123456','喜','SPRING'
,'05-77778888','0987555222','HTML103@gmail.com',NULL,NULL,'IIS-大吳','你好',TO_DATE('1973/11/13', 'yyyy/mm/dd'));
INSERT INTO MEMBER(MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME,MEM_LASTNAME
,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_PHOTO,MEM_CART_PHOTO,MEM_NICKNAME,MEM_ABOUTME,MEM_BIRTHDAY) 
values ('M000909','CSS103','123456','愛','JAVA'
,'04-55665656','0982445678','loeiwhd12@gmail.com',NULL,NULL,'IIS-Struts','我想是這樣啦',TO_DATE('1999/08/31', 'yyyy/mm/dd'));
INSERT INTO MEMBER(MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME
,MEM_LASTNAME,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_PHOTO,MEM_CART_PHOTO,MEM_NICKNAME,MEM_ABOUTME,MEM_BIRTHDAY) 
values ('M000910','AJAXC103','123456','林','配茶',
'02-25468741','0981557441','bber1112@gmail.com',NULL,NULL,'IIS-AJAX','我還沒喝保',TO_DATE('1991/04/21', 'yyyy/mm/dd'));
commit;




/*----------------------自転幣------------------------------------*/
-- 儲值
insert INTO currency VALUES('M000901','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),1000,current_TIMESTAMP,1,'儲值');
insert INTO currency VALUES('M000901','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),20000,current_TIMESTAMP,1,'儲值');
insert INTO currency VALUES('M000901','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),5000,current_TIMESTAMP,1,'儲值');
insert INTO currency VALUES('M000902','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),3000,current_TIMESTAMP,1,'儲值');
insert INTO currency VALUES('M000902','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),2000,current_TIMESTAMP,1,'儲值');
insert INTO currency VALUES('M000902','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),5000,current_TIMESTAMP,1,'儲值');
insert INTO currency VALUES('M000903','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),3000,current_TIMESTAMP,1,'儲值');
insert INTO currency VALUES('M000903','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),3000,current_TIMESTAMP,1,'儲值');
insert INTO currency VALUES('M000904','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),3000,current_TIMESTAMP,1,'儲值');
insert INTO currency VALUES('M000904','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),3000,current_TIMESTAMP,1,'儲值');
insert INTO currency VALUES('M000904','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),30000,current_TIMESTAMP,1,'儲值');
-- 提領審核 11
insert INTO currency VALUES('M000901','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),-500,current_TIMESTAMP,3,'提領成功');
insert INTO currency VALUES('M000901','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),-1000,current_TIMESTAMP,3,'審核提領');
insert INTO currency VALUES('M000902','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),-2000,current_TIMESTAMP,3,'審核提領');
insert INTO currency VALUES('M000902','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),-500,current_TIMESTAMP,3,'審核提領');
insert INTO currency VALUES('M000903','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),-500,current_TIMESTAMP,3,'審核提領');
insert INTO currency VALUES('M000903','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),-1000,current_TIMESTAMP,3,'審核提領');
insert INTO currency VALUES('M000904','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),-600,current_TIMESTAMP,3,'提領成功');
insert INTO currency VALUES('M000904','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),-200,current_TIMESTAMP,3,'審核提領');
--購買
insert INTO currency VALUES('M000903','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),-899,current_TIMESTAMP,'2','購買<(PRD019)二手摺車>商品');
insert INTO currency VALUES('M000901','CUY'||lpad(to_char(currency_seq.nextval),3,'0'),-3999,current_TIMESTAMP,'2','購買<(PRD016)SHI SRAM 11速棘輪系統,隨輪附 TUFO S33 PRO管胎>商品');
/*----------------------提領------------------------------------*/
insert INTO currency_cheackout VALUES ('M000901','CUY011','CHK'||lpad(to_char(cheackout_seq.nextval),3,'0'),-500,current_TIMESTAMP,1);
insert INTO currency_cheackout VALUES ('M000901','CUY012','CHK'||lpad(to_char(cheackout_seq.nextval),3,'0'),-1000,current_TIMESTAMP,0);
insert INTO currency_cheackout VALUES ('M000902','CUY013','CHK'||lpad(to_char(cheackout_seq.nextval),3,'0'),-2000,current_TIMESTAMP,0);
insert INTO currency_cheackout VALUES ('M000902','CUY013','CHK'||lpad(to_char(cheackout_seq.nextval),3,'0'),-500,current_TIMESTAMP,0);
insert INTO currency_cheackout VALUES ('M000903','CUY014','CHK'||lpad(to_char(cheackout_seq.nextval),3,'0'),-500,current_TIMESTAMP,0);
insert INTO currency_cheackout VALUES ('M000903','CUY015','CHK'||lpad(to_char(cheackout_seq.nextval),3,'0'),-1000,current_TIMESTAMP,0);
insert INTO currency_cheackout VALUES ('M000904','CUY016','CHK'||lpad(to_char(cheackout_seq.nextval),3,'0'),-600,current_TIMESTAMP,1);
insert INTO currency_cheackout VALUES ('M000904','CUY017','CHK'||lpad(to_char(cheackout_seq.nextval),3,'0'),-200,current_TIMESTAMP,0);
/*----------------------地址------------------------------------*/
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000904','汐止郵局','211新北市汐止區大同路三段168號',0912345678);
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000904','學士店','台中市北區學士路161號',0422077317);
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000904','學士店','台中市北區學士路161號',0422077317);
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000901','臺北北門郵局','臺北市中正區忠孝西路一段120號1樓',0223615752);
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000901','臺北東門郵局','臺北市中正區信義路二段163號',0223947576);
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000901','臺北漢中街郵局','臺北市萬華區漢中街173號',0223616809);
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000902','臺北西園郵局','臺北市萬華區長沙街二段156號',0223314524);
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000902','臺北龍山郵局','臺北市萬華區廣州街67號',0223064755);
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000902','臺北南海郵局','臺北市中正區重慶南路二段43號',0223963142);
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000902','臺北螢橋郵局','臺北市中正區廈門街76號',0223684714);
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000903','臺北青田郵局(臺北7支)','臺北市大安區和平東路一段155號',0223214288);
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000903','臺北復興橋郵局(臺北8支)','臺北市中正區忠孝西路一段1號',0223114421);
insert INTO PRODUCT_Delivery values(('ADD'||lpad(to_char(Delivery_seq.nextval),3,'0')),'M000903','臺北中山郵局(臺北9支)','臺北市中山區中山北路一段142號',0225426403);
/*----------------------商品------------------------------------*/
 -- 1銷售中 2賣出 3確認收貨 4關閉賣場 5檢舉有效
INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),
'M000901',null,'FUSIN-F101 新騎生活 20吋21速摺疊自行車-完整組裝','單車','高質感平價都市穿梭小折,汽車級亮粉烤漆,高級跑車型舒適座墊',
2399,'全新',null,null,null,current_TIMESTAMP,'直購',null,null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),
'M000902',null,'【Tern】Market Basket後貨架專用編織仿籐快拆式菜籃','配件','背包、雜物、甚至是毛小孩都能放入使與所有 TERN後貨架相容 他牌後貨架也能裝適合大部分後貨架KLICKFIX TOPKLIP安裝系統,確保物品穩固',
3000,'全新',null,null,null,current_TIMESTAMP,'直購',null,null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),
'M000902',null,'GARMIN Edge 520 Plus 自行車衛星導航','配件','導航功能 多人即時追蹤 連線功能 騎乘動態 STRAVA即時區段',
9000,'二手',null,null,null,current_TIMESTAMP,'直購',null,null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),
'M000906',null,'KUOTA KREDO經典公路車，高檔配備、品相佳、廉售','單車','超美配色，高檔配備，車架無傷，其它組件有正常騎乘的痕跡，購入時全車價值約16萬，車架：KUOTA KREDO全碳纖，尺寸：S~M，適合163~175cm組件：SHIMANO ULTEGRA大盤：FSA 50-34，大理石紋飛輪：12-27輪組：MAVIC SL一級輪組龍頭：FSA車把：FSA卡踏：DURA-ACE坐墊：sanmarco',
77777,'二手',null,null,29999,current_TIMESTAMP,'競標',to_date( to_char( last_day(sysdate), 'dd-mm-yyyy' )||'10:59:59', 'dd-mm-yy hh24:mi:ss'),null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000908',null,'超時空單車 出清破盤價KUOTA KOBALT碳纖維公路車、最新R7000（105變速）',
'單車','擁有高性能的車架，僅重1130克(S尺寸，含塗裝)高性能車款，性價比超乎想像這款確又擁有高性能的車架僅重1130克己（S尺寸，含塗裝）。是越級之作，搭載高階車款才有的隱藏式走線級BB386五通。
不僅於此，其他重要的特色例如可相容Di2電變，碳纖維前叉1-1/8" -1-1/5整合式頭碗組。最重要的是，這款車性能好、性價比超乎想像。圖片僅供參考，以實際成車零配件為主。',
39999,'二手',null,null,9999,current_TIMESTAMP,'競標',to_date( to_char( last_day(sysdate), 'dd-mm-yyyy' )||'16:59:59', 'dd-mm-yy hh24:mi:ss'),null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000904',null,'BAISKY自行車公路車登山車夏季排汗男款短車衣 逆轉 粉 百士奇 M','其他','● 海天網眼搭配滌綸網眼面料，透氣孔洞、滑順超柔軟
● YKK彈片式拉鍊，滑順好拉可輕鬆固定● 後側設置三個開放式收納口袋● 拉鍊側袋可防止水氣、確保貴重物品安全● 義大利墨水熱昇華轉印，色澤飽和亮麗布料主身：海天網眼領、後袋：牛奶絲側片：滌綸網眼',
999,'全新',null,null,null,current_TIMESTAMP,'直購',null,null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000906',null,'JAVA 終結者 全碳纖 空力車架 SUPREMA 105 公路 自行車','單車','詳細說明請參考圖片',
	99999,'二手',null,null,'9',current_TIMESTAMP,'競標',to_date( to_char( last_day(sysdate), 'dd-mm-yyyy' )||'　23:59:59', 'dd-mm-yy hh24:mi:ss' ),null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000909',null,'Hello Kitty 16寸兒童自行車輔助輪單車3-8歲童車男女孩兒童腳踏車','單車','買錯顏色兒子騎一兩次就不想騎了,
近乎全新,上來求售請有緣人帶走謝謝',2300,'二手',null,null,null,current_TIMESTAMP,'直購',null,null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000906',null,'2018 MAVIC AKSIUM 11速 s系統 公路車輪組 含原廠內外胎','配件','2018 MAVIC AKSIUM 含原廠內外胎Aksium的特點和技術通常為優質賽車車輪而設計，為日常道路行駛提供優質性能。其特點包括增加強度和剛度的直拉式輪輻，以及輕便的輪輞提高生動的駕乘質量。配件:原廠內外胎。快拆。說明書。',
	4000,'二手',null,null,null,current_TIMESTAMP,'直購',null,null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000906',null,'ACCELERO 40 PRO 40mm框高 公路車700C輪組 SRAM APEX1050 11-32t飛輪','單車','當初在大稻埕附近單車店購買
使用里程數約七八百，換輪組後，此輪組就一直放著沒使用商品有使用過的痕跡，不影響外觀，完美主義者麻煩您購買新品本人不是很了解規格，可能無法回答太專業的問題...',
70000,'二手',null,null,null,current_TIMESTAMP,'直購',null,null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000907',null,'SPORTONE U9 PLUS 700C彎把21速公路車搭載安全副煞把設計 專為入門練騎而生的男女學生公路跑車首選','單車','★建大胎700*25c + WINZIP+鋁合金夾器煞把煞車制動安全有效率 
★EVA高級輕量 柔軟觸感的跑車綁帶 +異型管車架 ★鋁合金龍頭 / 車手 / 座管 / 輪圈 / 夾器/前後快拆花鼓 ★NECO一體式密封中軸 NECO ★SHIMANO 21速變速系統 ★為初入手新手設計平握與彎把共存副煞車',
5000,'全新',null,null,null,current_TIMESTAMP,'直購',null,null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000903',null,'捷安特giant tcr ad2 碳纖維公路車',
'單車','捷安特 giant tcr ad2 2016輪組瞎貓c15車架尺寸M/L飛輪11-32T全套Shimano105龍頭TokenCema陶瓷B.B.Guee把帶碳纖水壺架X2高雄地區可看車誠可小小議',
70000,'二手',null,null,19999,current_TIMESTAMP,'競標',to_date( to_char( last_day(sysdate), 'dd-mm-yyyy' )||'　02:59:59', 'dd-mm-yy hh24:mi:ss' ),null,1,null,null,null,null,null);


INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000907',null,'CATEYE CC-PA100W Padrone 無線碼表 加大螢幕視窗 碼表器','配件','CATEYE CC-PA100W Padrone 無線碼表 加大螢幕視窗 碼表器  二手 配件 300
Youtube查詢皆有安裝教學可參考CATEYE CC-PA100W Padrone 無線碼表 加大螢幕視窗 輕薄款輕薄款重量 :56g   大顯示畫面  適合各種騎乘  
◆騎乘速度(現在.平均.最高)◆騎乘距離.累積距離◆騎乘時間.時刻◆超大顯示屏通過瘦身設計來實現輕巧外觀◆大螢幕大數字顯示◆簡單設計.確認和使用◆菜單畫面◆手動輸入累計距離◆自動計測◆弱電顯示◆按壓碼表表面即可切換模式的ClickTec功能
◆Flex-Tight支架◆超大螢幕面板 3.2CMx4.3CM',
300,'二手',null,null,null,current_TIMESTAMP,'直購',null,null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000903',null,'Giant 自行車 單車 鐵馬 後置物包 置物箱 環島包 黑白 merida nike  日本 ','配件','新舊程度:95%，買了用不到故售出。'												
,899,'二手',null,null,null,current_TIMESTAMP,'直購',null,null,1,null,null,null,null,null);							


INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000903',null,'USB前燈 充電式 300流明 自行車前燈 自行車燈 公路車燈 腳踏車燈','配件','二手商品多少有使用痕跡,不介意再購買 ~~'												
,399,'二手',null,null,null,current_TIMESTAMP,'直購',null,null,4,null,null,null,null,null);												
												
INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000903','M000901','SHI SRAM 11速棘輪系統,隨輪附 TUFO S33 PRO管胎','配件','功能外觀良好正常,代理公司貨,												
全碳纖管胎版本,SHI SRAM 11速棘輪系統,隨輪附 TUFO S33 PRO管胎,ROLF TDF4SL 框高40mm 前後一組重量約 1165g,商品包含輪組 快拆 專用煞車皮 輪袋等,二手商品多少有使用痕跡,不介意再購買 ~~'												
,39999,'二手',4,'00分的好賣家 值得推薦的好賣家 五星級的好賣家',null,current_TIMESTAMP,'直購',null,null,3,'臺北北門郵局',0223615752,'臺北市中正區忠孝西路一段120號1樓',null,null);	

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000903',null,'樂活單車_08期_About Brake','配件','有泛黃,不介意才請下標,謝謝! 低價特賣 書籍狀態請詳看圖示 *內容敘述為制式文字, 僅為針對書籍內容參考用,關於有無光碟與附件請以標題為準'												
,600,'二手',null,null,null,current_TIMESTAMP,'直購',null,null,4,null,null,null,null,null);
/*----------------------------------------------------------*/
INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000904',null,'Liv Sally 低跨點時尚單車',
'單車','低跨點時尚風格淑女車 大包覆鍊蓋及泥除 騎乘好安心 女性柔軟坐墊 騎久也不怕',
999,'二手',null,null,1,current_TIMESTAMP,'競標',to_date( to_char( last_day(sysdate), 'dd-mm-yyyy' )||'13:59:59', 'dd-mm-yy hh24:mi:ss'),null,4,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000906','M000903','二手摺車',
'單車','車架材質:高碳鋼
摺疊尺寸：長86 *寬37 *高65CM
車架：16寸鋁合金減震車架
前叉：鋁合金機械盤式制動器
座桿：鋁合金座桿
齒盤曲柄組：6速鋁合金曲柄公路齒板
可變速度手柄：Tsunrun變速度轉彎/後6速）
前傳輸：前傳輸
後變速器：6速後變速器
變速飛輪：6速飛輪（14-28齒）
輪組系統：鋼製快速釋放盤式制動輪組
輪胎：16英寸輪胎（輪胎寬度1.95英寸）
剎車手柄：鋁製剎車前後碟剎
制動器：鋁合金機械盤式制動器
制動管路內部鍍鋅/ 全注油制動器電纜外殼
重量:6.8 kg
注意
7成新
縲絲有生鏽以噴W40保護防鏽
車鏈及齒輪以噴滑油
車有正常花
所有縲絲以跟實安全無鬆
所有機件正常
轉波順利
呔以跟夠氣
極精神好力
少花小崩
公里少
以檢查過
頭尾牙、did鍊、迫力皮、頭尾呔、高腳踏
等機件正常
小出車泊有蓋屋企長開冷氣 長開抽氣扇 長開抽濕機及長開所有電器',
899,'二手',5,'超讚的賣家 超優質賣家 100分的好賣家 值得推薦的好賣家 五星級的好賣家',null,current_TIMESTAMP,'直購',null,null,3,'臺北市中山區中山北路一段142號',0225426403,'臺北中山郵局(臺北9支)',null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000901',null,'單車界臺灣名牌GIANT捷安特單車',
'單車','2.登山車種
3.前避震器
4.24段變速
5.日本大廠shimano變速器
6.輪胎類型：mix
（適合柏油路面及粗糙路面。）
7.車架尺寸：適合160cm-175cm身高的朋友

附贈：
1. 單車界零件大廠TOPEAK 堅固後貨架
2.手把牛角，舒緩手部壓力
3.雙水壺架
4.後車燈
5.後視鏡
6.附氣壓計打氣筒（法嘴，美嘴皆宜）',
9999,'二手',null,null,3000,current_TIMESTAMP,'競標',to_date( to_char( last_day(sysdate), 'dd-mm-yyyy' )||'　11:59:59', 'dd-mm-yy hh24:mi:ss' ),null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000904',null,'KREX CS-1800 拉風款自行車專用安全帽',
'配件','時尚流線造型，亮眼色彩搭配
可調式頭圍，依個人需求調整
21個通風孔設計，配戴舒適透氣
高密度EPS內殼減緩衝擊傷害
國家安全檢驗標章，安心有保障
台灣自行車專業品牌',
999,'二手',null,null,null,current_TIMESTAMP,'直購',null,null,4,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000910',null,'Java SILURO2 Shimano R3000 空力跑車 公路車',
'單車','車架 :輕量化鋁合金車架

前叉 : C5碳纖維前叉

輪組 : DECA 700C鋁合金輕量輪組 框高350mm

龍頭 : 31.8mm鋁合金輕量龍頭

把手 : DECA 空氣力學 鋁合金彎把 31.8mm

座管 : DECA鋁合金座管 隱藏式坐管束

座墊 : DECA

變把 : SHIMANO CLARIS ST-2400

前變 : SHIMANO CLARIS FD-2400

後變 : SHIMANO CLARIS RD-2400

飛輪 : SunShine 8S 11-28T 卡式飛輪

大盤 : DECA CNC鋁合金大盤 52/42T

重量 : 約9.7KG',
20000,'二手',null,null,5999,current_TIMESTAMP,'競標',to_date( to_char( last_day(sysdate), 'dd-mm-yyyy' )||'　18:59:59', 'dd-mm-yy hh24:mi:ss' ),null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000904',null,'Topeak Modula Java Cage 隨行杯水壺架 可調式水壺架',
'配件','Modula Java Cage是您早晨騎車想攜帶熱騰騰咖啡的最佳夥伴選擇，
工程級塑膠水壺架，可完全調整適合各種隨行杯容量。
不論是12oz或16oz的隨行杯Modula Java Cage都提供可調整橡膠固定帶
，保護咖啡在騎乘中不會溢出。四向調整可提供高度175~240mm 底部直徑60~78mm 頭部直徑75mm~90mm的隨行杯使用。',
999,'二手',null,null,1,current_TIMESTAMP,'競標',to_date( to_char( last_day(sysdate), 'dd-mm-yyyy' )||'　10:59:59', 'dd-mm-yy hh24:mi:ss' ),null,1,null,null,null,null,null);


INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000908',null,'捷安特R3700公路車',
'單車','【品　　名】：捷安特公路車R3700
【價　　格】：10000
【新舊程度】：存放於室內，八成新
【運送方式】：自取
SORA變速器，M號，重量9.75Kg，附贈停車架，前後燈，馬表，馬鞍袋，雙水壺架，假日可看車，喜歡可小議',
10000,'二手',null,null,1,current_TIMESTAMP,'競標',to_date( to_char( last_day(sysdate), 'dd-mm-yyyy' )||'　19:59:59', 'dd-mm-yy hh24:mi:ss' ),null,1,null,null,null,null,null);

INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000905',null,'Pinarello Dogma 60.1 Movistar TEAM',
'單車','【品 名】:Pinarello Dogma 60.1 Movistar TEAM公路車架
【新舊程度】:有使用上刮傷
【售 價】: 38000 原價168000
【物品地點】: 北市
【其他說明】:

Pinarello Dogma 60.1 Movistar TEAM

立管 46.5

上管 51.5

只售 車架前叉 坐管

三司達公司貨 有台北市三司達 經銷店家購買證明 收據',
38000,'二手',null,null,1,current_TIMESTAMP,'競標',to_date( to_char( last_day(sysdate), 'dd-mm-yyyy' )||'　10:11:59', 'dd-mm-yy hh24:mi:ss' ),null,1,null,null,null,null,null);


INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000910',null,'全新 MINGREN 風速 SHIMANO 14速 甩把 鋁合金 700c 公路車',
'單車','車架 : 鋁合金

變把 : Shimano ASTA070 2*7

中變 :Shimano TZ31
後變 : Shimano TY300
大盤 : 輪峰 52/42T*170MM 鋁腿
飛輪 : Shimano TZ21 7S 14-28T
中軸 : 一體式培林BB
煞車 : Shimano A070 / PROMAX可調鋁C夾+煞把
輪組 : 700c雙層鋁圈 / 久裕鋁花鼓
輪胎 : 建大 KENEA 700*23c F/V
坐墊 : MINGREN 人體工學
其他 : 鋁車手W420MM*31.8 / MINGREN鋁龍頭 / MINGREN鋁坐管
',
6800,'二手',null,null,5000,current_TIMESTAMP,'競標',to_date( to_char( last_day(sysdate), 'dd-mm-yyyy' )||'　17:59:59', 'dd-mm-yy hh24:mi:ss' ),null,1,null,null,null,null,null);


INSERT INTO product VALUES ('PRD'||lpad(to_char(product_seq.nextval),3,'0'),'M000909',null,'美利達Rb660換車割愛',
'單車','新港奉天宮
NT$4,500
出售陪我上山下海的公路車
美利達Rb660
可以給青少年當交通工具
或是假日休閒
贈送一個車尾燈
一個攜車袋
一個隨身打氣筒（聰明嘴）
想要什麼問一下，我看看有沒有多贈送給你
假日練騎爬山路順 下坡穩
我前幾個禮拜才洗過保養過歐
',
4800,'二手',null,null,500,current_TIMESTAMP,'競標',to_date( to_char( last_day(sysdate), 'dd-mm-yyyy' )||'　17:59:59', 'dd-mm-yy hh24:mi:ss' ),null,1,null,null,null,null,null);
/*----------------------競標時間------------------------------------*/
INSERT INTO bidding VALUES ('BID'||lpad(to_char(bidding_seq.nextval),3,'0'),'PRD004','M000907',42000,current_TIMESTAMP);
INSERT INTO bidding VALUES ('BID'||lpad(to_char(bidding_seq.nextval),3,'0'),'PRD004','M000908',45000,current_TIMESTAMP);
INSERT INTO bidding VALUES ('BID'||lpad(to_char(bidding_seq.nextval),3,'0'),'PRD005','M000901',30000,current_TIMESTAMP);
INSERT INTO bidding VALUES ('BID'||lpad(to_char(bidding_seq.nextval),3,'0'),'PRD005','M000902',29000,current_TIMESTAMP);
/*----------------------商品檢舉------------------------------------*/
INSERT INTO product_report VALUES ('REP'||lpad(to_char(product_report_seq.nextval),3,'0'),'M000901','PRD004',current_TIMESTAMP,0,'商品賣太貴');
INSERT INTO product_report VALUES ('REP'||lpad(to_char(product_report_seq.nextval),3,'0'),'M000902','PRD004',current_TIMESTAMP,0,'商品重覆販售');
/*----------------------商品收藏------------------------------------*/
INSERT INTO product_favorite VALUES ('M000901','PRD004');
INSERT INTO product_favorite VALUES ('M000903','PRD013');

commit;
