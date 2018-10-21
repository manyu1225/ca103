<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.secondShop.product.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import ="java.sql.Timestamp"%>
<%@ page import="java.util.Date"%>

<%	
	MemVO memVO = (MemVO)session.getAttribute("memVO");
	String memId= memVO.getMem_id();  

	ProductVO productVO = (ProductVO) request.getAttribute("productVO");
	String productEndBidding = (String)request.getAttribute("productEndBidding");
	String productAd = (String) request.getAttribute("productAd");	
	
	if(productVO==null){
		productVO = new ProductVO();
		productVO.setProductSaleType("直購");
		productEndBidding = "";
		productAd = "";
	}
	
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible " content="IE=edge ">
    <meta name="viewport " content="width=device-width, initial-scale=1.0, shrink-to-fit=no ">
    <title>自転車</title>  
    <link rel="stylesheet " href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css ">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/secondShop/fileInput/fileinput.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" media="all" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/secondShop/css/fileInput/theme.css">
    <link rel="stylesheet " type="text/css " href="<%=request.getContextPath()%>/sources/css/secondShop/css.css ">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pretty-checkbox@3.0/dist/pretty-checkbox.min.css"/>

    <style type="text/css">
        .hidden{
            display: none;
        }
        .shodd{
          box-shadow: 0 0.1rem 0.2rem 0 rgba(0,0,0,.1);
        }
</style>

</head>
<body style="background-color:#f0f0f5">
<%@ include file="/sources/file/Home/NavBar.file"%>
    <div class="container">
        <div class="col-sm-12 formContent ">
            <form action="<%=request.getContextPath()%>/ProductServlet" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <h4 class="mt-3" align="center">新增商品</h4>
                </div>
                <div class="form-group">
                    <input id="input-id" type="file" class="file shodd" data-preview-file-type="text" name="productPhoto" multiple>
                </div>
                <label class="text-danger">${errorMsgs.produtPhotoList}</label>
                <div class="form-group">
                    <div class="row mt-5">
                        <div class="col-sm-4">
                            <label>商品銷售模式</label>
                            <div>
                               <div class="form-check form-check-inline">
                                 <input class="form-check-input" type="radio" name="productSaleType" id="saleTypeR1" value="直購">
                                 <label class="form-check-label" for="saleTypeR1">直購商品</label>
                               </div>
                               <div class="form-check form-check-inline">
                                 <input class="form-check-input" type="radio" name="productSaleType" id="saleTypeR2" value="競標">
                                 <label class="form-check-label" for="saleTypeR2">競標商品</label>
                               </div>
                            </div>
                            <label class="text-danger">${errorMsgs.productSaleType}</label>
                        </div>
                        <div class="col-sm-4 BIDDING ">
                            <label class="mr-2">起標價格</label>
                            <label class="text-danger">${errorMsgs.productBiddingPrice}</label>
                            <input type="number" class="form-control" placeholder="0元" name="productBiddingPrice" min="0" max="999999" value="<%=(productVO.getProductBiddingPrice()==null)? "1" : productVO.getProductBiddingPrice() %>">
                        </div>
                        <div class="col-sm-4 BIDDING ">
                            <label class="mr-2">競標時間</label> 
                            <label class="text-danger">${errorMsgs.productEndBidding}</label>
                            <select class="form-control" name="productEndBidding">
                                <option value="0">請選擇</option>
                                <option value="7"  ${(productEndBidding==7)? "selected" : "" }>7日</option>
                                <option value="10" ${(productEndBidding==10)? "selected" : "" }>10日</option>
                                <option value="15" ${(productEndBidding==15)? "selected" : "" }>15日</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-sm-3">
                            <label class="mr-2">商品類別</label>
                            <select class="form-control" name="productType">
                                <option value="0">請選擇</option>
                                <option value="單車"<%=("單車".equals(productVO.getProductType()) ? "selected" : "") %>>單車</option>
                                <option value="配件"<%=("配件".equals(productVO.getProductType()) ? "selected" : "") %>>配件</option>
                                <option value="其他"<%=("其他".equals(productVO.getProductType()) ? "selected" : "") %>>其他</option>
                            </select>
                        </div>
                        <div class="col-sm-3 text-danger">${errorMsgs.productType}</div>
                        <div class="col-sm-3">
                            <label class="mr-2">商品使用狀況</label>
                            <select class="form-control" name="productStatus">
                                <option value="0">請選擇</option>
                                <option value="全新" <%= ("全新".equals(productVO.getProductStatus()))? "selected" : ""%>>全新</option>
                                <option value="二手" <%= ("二手".equals(productVO.getProductStatus()))? "selected" : ""%>>二手</option>
                            </select>
                        </div>
                        <div class="col-sm-3 text-danger" >${errorMsgs.productStatus}</div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="mr-2">商品名稱</label><label class="text-danger">${errorMsgs.productName}</label>
                    <input required type="text" class="form-control" placeholder="商品名稱" name="productName" value="<%=(productVO.getProductName()==null)? "" : productVO.getProductName() %>">
                </div>
                <div class="form-group">
                    <label class="mr-2">商品價格</label><label class="text-danger">${errorMsgs.productPrice}</label>
                    <input required type="number" class="form-control" placeholder="0元" name="productPrice" min="0" max="999999" value="<%=(productVO.getProductPrice()==null)? "0" : productVO.getProductPrice() %>">
                </div>
                <div class="form-group">
                    <label class="mr-2">商品描述</label><label class="text-danger">${errorMsgs.productDetail}</label>
                    <textarea class="form-control" name="productDetail" placeholder="商品描述" style="height: 300px" > <%=(productVO.getProductDetail()==null)? "" : productVO.getProductDetail() %></textarea>
                </div>
                <div class="form-group">
                    <div class="row align-self-center">
                        <div class="col-sm-4  mt-1">
                             <a class="btn" data-container="body" data-toggle="popover" data-placement="bottom" title="置頂廣告說明
                             " data-content="<p>提醒您購買廣告將會扣除您自転幣100元，請先確認自転幣餘額是否足夠，否則廣告會購買失敗。謝謝<br>這是系統優先幫您在商品列中置頂的廣告，若未購買則是系統亂序排列。</p>">
                             <img src="<%=request.getContextPath()%>/sources/icon/secondShop/idea.png"/></a>  
                           <label >購買置頂廣告</label>
                             <label class="switch mt-1">
                                <input type="checkbox" class="success" name="productAd" value="1">
                                <span class="slider round"></span>
                            </label>
                                      <%
                                      Date date = new Date();                        // util.Date 物件拿到當前時間
                                      Long milSecFromd19700101 = date.getTime();      // date當前時間.getTime() =>  拿到當前時間的 從1970/1/1 起的毫秒數
                                      Long DayTime = milSecFromd19700101 + 3*32*60*60*1000; //增加3天的時間 1d * 24h * 60m * 60s * 
                                      Timestamp sqlTimeStamp_2 = new Timestamp(DayTime); 
                                      
                                      java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                      String formatDate = df.format(sqlTimeStamp_2);
                                      String nowDate = df.format(date);
                                      %>
                               
                        </div>
                        <div class="col-sm-5 align-self-center mt-1">
                             <label>廣告時期間：<%=nowDate%>至<%=formatDate%></label>
                        </div>
                        <div class="text-right col-sm-3 ">
                            <button type="submit" class="btn btn-primary active shodd">確認送出</button>
                            <a id="texeDate" class="btn btn-info active shodd">神奇按鈕</a>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
                <input class="hidden" type="text" name="setMemIdSale" value="${memVO.mem_id}">
                <input class="hidden" type="text" name="action" value="createProduct">
            </form>
    	</div>
	</div>
   
    <footer class="bd-footer text-center">
	<div class="footer__copyright"> © 2018 自転車. 版權所有。</div>
    </footer>
    
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/sources/js/secondShop/fileInput/fileinput.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/sources/js/secondShop/fileInput/theme.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/sources/js/secondShop/fileInput/zh-TW.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/sources/js/secondShop/product.js"></script>
        
	<script type="text/javascript">
	var saleType = "<%=productVO.getProductSaleType()%>"; /* 檢查從controller傳回來的vo的ProductSaleType */      
	if(saleType == "直購"){
		$('input[name=productSaleType]:eq(0)').attr('checked','check')
		$('.BIDDING').css('display','none')
	}else{
		$('input[name=productSaleType]:eq(1)').attr('checked','check')
	}

    $('#texeDate').click(function(){
        $('input[name=productName]').val('JAVA 名師御用 6段變速 二手公路車 ')
        $('input[name=productPrice]').val(999)
        $('textarea[name=productDetail]').html('JAVA 名師御小吳濕敷 因為來資策會教ＪＡＶＡ 太認真教ＪＡＶＡ 課太多 沒日沒夜都沒時間睡覺 都沒用到此車 只好售出 跪求大大帶走 原價13800  特價9999 車架Ｍ號 從“蛙不停” GIANT專賣店購入 全部原廠 無更動 無倒車 2014/08/14購入 總里程近400(碼表一買即裝）附上 溜溜球鎖、簡易工具組、打氣頭車庫車 車況極新 不議價有興趣者台北市 內湖區可約看車')
        $('select[name=productType]').val('單車');
        $('select[name=productEndBidding').val(7);
        $('input[name=productSaleType]:eq(1)').attr('checked','checked');
        $('select[name=productStatus]').val('二手');

        });
	</script>

</body>
</html>