<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.secondShop.product.model.*"%>
<%@ page import="com.secondShop.productBidding.model.*"%>
<%@ page import ="com.secondShop.product.controller.*"%>
<%@ page import="com.secondShop.productPhoto.model.*"%>
<%@ page import="com.secondShop.currency.controller.*"%>
<%@ page import="com.secondShop.currency.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.secondShop.productDeliveryAddrData.model.*"%>
<%@ page import="com.secondShop.productDelivery.model.*"%>
<%@ page import="com.secondShop.productFavorite.model.*"%>
<%@ page import ="java.sql.Timestamp"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE html>
<%
	System.out.println("viewProduct.jsp : 我來產品畫面囉");

	
	MemVO memVO = (MemVO)session.getAttribute("memVO");
	String memId= memVO.getMem_id(); 
 	

 	DeliveryService deliverySvc = new DeliveryService();
 	List<DeliveryVO> deliveryList = deliverySvc.allAddress(memId);
 	pageContext.setAttribute("deliveryList", deliveryList);
 	
 	
	ProductVO productVO = (ProductVO) request.getAttribute("productVO");
	MemVO saleMemVO = new MemService().findMemById(productVO.getMemIdSale());
	pageContext.setAttribute("saleMemVO", saleMemVO);

	AddressDAO addressDAO = new AddressDAO();
	List<CityVO> cityList = addressDAO.getCity();
	pageContext.setAttribute("cityList",cityList); 
	
	ProductPhotoService productPhotSrv = new ProductPhotoService(); 
	List<ProductPhotoVO> photoList = productPhotSrv.ProductPhotoList(productVO.getProductId());	

  	CurrencyService currencSve = new CurrencyService();
	Integer memCurrecyTotal = currencSve.memCurrecyTotal(memId);
  	pageContext.setAttribute("memCurrecyTotal",memCurrecyTotal); 
	
	BiddingVO biddingVO = new BiddingService().biddingBenefitMem(productVO.getProductId());
	pageContext.setAttribute("biddingVO",biddingVO);
  
	ProductFavoriteVO  productFavoriteVO = new ProductFavoriteService().findOneByPK(memId, productVO.getProductId());
    ProductService productforRating = new ProductService(); 
    Integer ratingAvg = productforRating.memRatingAvg(memId);
    Integer ratingCount = productforRating.memRatingCount(memId);
    ProductService ProductSvc = new ProductService();
    Integer memSaleProductTotal = ProductSvc.memSaleProductTotal(productVO.getMemIdSale());
   

%>
<html>

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${productVO.productName}</title>
  <link rel="icon" href="<%=request.getContextPath()%>/sources/icon/secondShop/cycling.png" type="image/x-icon" />
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" media="all" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/secondShop/css.css">
  <!--最後載入 圖片  -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet"  />
 
  <style type="text/css">
    .font-smll{
      font-size:1.2rem;
    }

    .font-smll3{
      font-size:1.3rem;
    }

    .font-product{
      font-size:1.4rem;
    }

    .font-productbing{
      font-size:1.6rem;
    }

    .font-productsecond{
      font-size:1.5rem;
    }

  	.hidden{
      display: none;
    }

    .carouselImg{
      max-width: 100%;
      max-height:100%;
    }

    .shodd{
        box-shadow: 0 2px 3px rgba(0,0,0,0.2);
    }

    .bg-grayy{
        background-color: #dee2e6;
    }

    .PLR1{
        padding-left: 1%;
        padding-right: 1%;
    }

    .TAC{
    	text-align: center;
    }

    .myShadowCard{
            border-radius: 1rem;
            box-shadow: 0 2px 3px rgba(0,0,0,.075);;
    }

    .myppfont{
      white-space: pre-wrap;
    }

    .no_pricestr{
      vertical-align: top;
      font-size: 1.2rem;
      margin-right: 5px;
      color: #ea0000;
      font-family: Arial;
      font-weight: bold;
      margin-top: 10px;
    }

    .no_pricenum {
      font-size: 2.9rem;
      line-height: 1;
      color: #ea0000;
      font-family: 'lativAbweb','Century Gothic','Tw Cen MT Condensed';
      font-weight: bold;
    }

    .smll_pricenum {
      font-size: 2rem;
      line-height: 1;
      color: #ea0000;
      font-family: 'lativAbweb','Century Gothic','Tw Cen MT Condensed';
      font-weight: bold;
    }

    div[name=myAminate]{
      position: fixed;
      bottom: 300px;
      right: -300px;
      width: 300px;
      border: 1px solid #ffc107;
      background-color: #ffc107;
      box-shadow: 0 2px 3px rgba(0,0,0,0.2);
      opacity: 0.8;
    }
    .myhouver:hover{
       opacity: 0.8;
    }
/*==================Talk用==========*/
    .talkCard{
      position: fixed;
      bottom: 2px;
      right: -999px;
      width: 35vw;
      height: 40vh;
      background-color:#fff;
    }
    .putCenter{
      display:flex;
      align-items:center;
      justify-content:space-around;
    }
    .talkListTop{
      height:15%;
      border-radius:10px;
      border:1px solid #007bff;
      margin-top:1%;
      display:flex;
      align-items:center;
      justify-content:space-around;
    }
    .msgPlr1{
      padding-left:1%;
      padding-right:1%;
    }
    .talkListContent{
      overflow:auto;
      height:82%;
      margin-top:1%;
      margin-bottom:1%;
    }
    .talkListMem{
      height:15%;
      width:100%;
      display:flex;
      align-items:center;
      justify-content:space-around;
    }
    .msgTop{
      display: flex;
      height:15%;
      border-radius:10px;
      border:1px solid #f7c324;margin-top:1%;
    }
  
  .talkCardhide {
    position: fixed;
    bottom: 2px;
    right: -10px;
    width: 35vw;
    height: 40vh;
    background-color: #FFF;
  }
  #showMessage{
    position: fixed;
    bottom: 30px;
    right: 10px;
  }
  </style>
</head>

<body style="background-color: #74a5da!important" onload="connect();" onunload="disconnect();">
<%@ include file="/sources/file/Home/NavBar.file" %>
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb ${productVO.productScore}">
        <li class="breadcrumb-item"><a href="#">Home</a></li>
        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/secondShop/productList.jsp">商品列表</a></li>
        <li class="breadcrumb-item active" aria-current="page">${productVO.productName}</li>
    </ol>
  </nav>

  <div class="container myShadowCard" id="ta3">
    <c:if test="${productVO.productRating!=null}"> 
      <div class="border border rounded border border-warning mt-4">
        <div class="row mt-2">
          <div class="col-6 font-productsecond"><p class="text-center">商品評分</p></div>
          <div class="col-6 font-productsecond"><p>商品評價</p></div>
        </div>
        <div class="row">
          <div class="col-6 font-productbing">
            <p class="text-center">
              <i class="fas fa-star ${(productVO.productScore >=1)? 'text-warning':''}"></i>
              <i class="fas fa-star ${(productVO.productScore >=2)? 'text-warning':''}"></i>
              <i class="fas fa-star ${(productVO.productScore >=3)? 'text-warning':''}"></i>
              <i class="fas fa-star ${(productVO.productScore >=4)? 'text-warning':''}"></i>
              <i class="fas fa-star ${(productVO.productScore >=5)? 'text-warning':''}"></i>
            </p>
          </div>  
          <div class="col-6 font-smll">
            <p>${productVO.productRating}</p>
          </div>
        </div>
      </div>
    </c:if>    
    <div class="row" >
      <div class="col-md-6 mt-2 mb-2 " style="height: 60vh">
        <div id="carouselExampleIndicators" class="carousel slide h-100" data-ride="carousel">
          <ol class="carousel-indicators">
              <%  int i = 1;
              for(ProductPhotoVO productPhotoVO:photoList){
              %>
            <li data-target="#carouselExampleIndicators" data-slide-to="<%=i%>" ></li> <!-- class="active" -->
              <% i++; } %>
          </ol>
          <div class="carousel-inner h-100 ">
            <% for(ProductPhotoVO productPhotoVO:photoList){%>
            <div class="carousel-item h-100">
              <img class="d-block carouselImg shodd m-auto" alt="First slide" src="<%=request.getContextPath()%>/ProductPhotoServlet?action=findOnePhotoByPhotoId&photoId=<%=productPhotoVO.getPhotoId()%>">
            </div>
            <% } %>
          </div>
          <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
          </a>
          <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
          </a>
        </div>
      </div>
      <div class="col-md-6 mt-2 mb-2">
        <div class="row">
          <div class="col-12 TAC" >
            <label class="font-product text-white border rounded shodd PLR1 ${(productVO.productSaleType=='直購')? 'bg-danger':'bg-grayy'}">直購
            </label>
            <label class="font-product text-white border rounded shodd PLR1 ${(productVO.productSaleType=='競標')? 'bg-warning':'bg-grayy'}">競標
            </label>
            <label class="font-product text-white border rounded shodd PLR1 ${(productVO.productStatus=='全新')? 'bg-success':'bg-grayy'}">全新
            </label> 
            <label class="font-product text-white border rounded shodd PLR1 ${(productVO.productStatus=='二手')? 'bg-info':'bg-grayy'}">二手
            </label> 
            <label class="font-product text-white border rounded shodd PLR1 ${(productVO.productType=='單車')? 'bg-primary':'bg-grayy'}">單車
            </label> 
            <label class="font-product text-white border rounded shodd PLR1 ${(productVO.productType=='配件')? 'bg-primary':'bg-grayy'}">配件
            </label> 
            <label class="font-product text-white border rounded shodd PLR1 ${(productVO.productType=='其他')? 'bg-primary':'bg-grayy'}">其他
            </label> 
          </div> 
        </div>
        <div class="row"> <!--  d-flex justify-content-end -->
		  <div class="col-12">
		    <label class="font-productsecond">${productVO.productName}</label>
		  </div>
        </div>
        <div class="row">
          <div class="col-md-6 TAC">
             <label class="font-smll3">直購價</label>
              <span class="no_pricestr">NT$</span>
              <label class="no_pricenum">${productVO.productPrice}</label> 
          </div>
          <c:if test="${(productVO.productSaleType=='競標')}">
          <div class="col-md-6 TAC">
              <label class="font-smll3">競標底價</label>
              <span class="no_pricestr">NT$</span>
              <label class="no_pricenum">${productVO.productBiddingPrice}</label>  
          </div>
          </c:if>    
        </div>
        <div class="d-flex justify-content-end">
          <span class="font-smll">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${productVO.productUpdateDate}"/>
          </span>
        </div>
        
        
        <!-- 產品銷售類型必須是競標才顯示 -->
        <c:if test="${productVO.productSaleType == '競標'}">
          <div class="card">
            <div class="special-card card-header d-flex justify-content-center">
              <img class="img-fluid" src="<%=request.getContextPath()%>/sources/icon/secondShop/auction-1.png"> 
              <h4 class="mt-2 text-center">競標資訊</h4>
            </div>
            <div class="special-card card-body">
              <!-- 最高價格  起標價格 -->
              <div class="row mt-1">
                <div class="col-2">
                  <img class="img-fluid" src="<%=request.getContextPath()%>/sources/icon/secondShop/cup.png"> 
                </div>
                <div class="col-3 forTop">
                   <p class="font-smll">最高價格</p>
                </div> 
                <div class="col-4">
                  <p id="topPrice" class="text-center font-smll">${(biddingVO==null)? productVO.productBiddingPrice:biddingVO.biddingPrice}</p>
                </div>
              </div>

              <!-- 馬上出價 送出出價 如果這商品賣的人不是我 而且 商品銷售狀態是 銷售中 才顯示-->
              <c:if test="${(productVO.memIdSale!= memVO.mem_id && productVO.productDataStatus==1)}">
                <div class="row mt-1">
                  <div class="col-2">
                    <img class="img-fluid" src="<%=request.getContextPath()%>/sources/icon/secondShop/moneybidding.png"> 
                  </div>
                  <div class="col-3">
                    <p class="font-smll">馬上出價</p>
                  </div>
                  <div class="col-4">
                    <input id="callPrice" type="number" class="form-control"/>
                  </div>
                  <div class="col-3">
                    <button type="button" class="btn btn-primary btn-block" id="addBiddingPice">送出出價</button>
                  </div>
                </div>
              </c:if>

              <!--  競標倒數  -->
              <div class="row mt-1">
                <div class="col-2">
                  <img class="img-fluid" src="<%=request.getContextPath()%>/sources/icon/secondShop/clock.png"> 
                </div>
                <div class="col-3">
                  <p class="font-smll">競標倒數</p>
                </div>
                <div class="col-7 font-product">
                <c:if test="${(productDataStatus != 1)}">
                  <div id="countDownDiv">
                    <label class="font-productsecond text-white bg-dark border border-warning rounded" id="dd">00</label>
                    <label class="font-productsecond">天</label>
                    <label class="font-productsecond text-white bg-dark border border-warning rounded" id="hh">00</label>
                    <label class="font-productsecond">時</label>
                    <label class="font-productsecond text-white bg-dark border border-warning rounded" id="mm">00</label>
                    <label class="font-productsecond">分</label>
                    <label class="font-productsecond text-white bg-dark border border-warning rounded" id="ss">00</label>
                    <label class="font-productsecond">秒</label>
                  </div>
                 </c:if>
                </div>
              </div>
            </div>
          </div>
        </c:if>
        <div class="row d-flex justify-content-end mr-1">
          <p id="error" class="text-danger font-product"></p>
        </div> 
        <div class="row mt-2 p-3">
          <c:if test="${(productVO.memIdSale != memVO.mem_id && productVO.productDataStatus==1)}">
            <div class="col-3">
              <!-- Button trigger modal -->
              <button  type="button" class="btn btn-secondary shodd" data-toggle="modal" data-target="#Report"><i class="far fa-thumbs-down"></i> 檢舉
              </button>
            </div>
            <div class="col-3 text-center" >
              <img data-like="no" id="favorite" src="<%=request.getContextPath()%>/sources/icon/secondShop/like-1.png">
            </div>
            <div class="col-6"> 
              <button  class="btn btn-primary btn-block shodd" data-toggle="modal" data-target="#modalId">直接購買</button>
            </div>
          </c:if>
          <c:if test="${(productVO.productDataStatus==6)}">
          	<div class="col-6"> 
              <button  class="btn btn-primary btn-block shodd" data-toggle="modal" data-target="#modalId">得標結帳</button>
            </div>
          </c:if>
        </div>
      </div>
    </div>
  </div>

  <div class="container myShadowCard">
    <div class="row  mt-4 rounded myhouver" style="background-color: #e9ecef ">
        <div class="col-2 text-center">
          <a href="#">
            <img class="text-center mt-1" src="<%=request.getContextPath()%>/sources/icon/secondShop/girl-1.png">
          </a>
          <span class="mt-2 mr-2 text-center" id="sedMessage">
            <img src="<%=request.getContextPath()%>/sources/icon/secondShop/chat.png">與我聊聊
          </span>
        </div>
         <div class="col-10 d-flex align-items-center">
           <div class="row text-center">
              <div class="col-4">
                <p class="font-smll3 mr-3">會員帳號</p>
                <p class="font-product mr-3">${saleMemVO.mem_nickname}</p>
              </div>
              <div class="col-4">
                 <p class="font-smll3 mr-3">我的商品</p>
                 <p class="font-product mr-3"><%=memSaleProductTotal%></p>
              </div>
              <div class="col-4">
                <p class="font-smll3 mr-3">我的評分</p>
                <p class="font-product mr-3"><%=ratingAvg%>(共<%=ratingCount%>筆)</p>
              </div>    
           </div>
         </div>
       </div>
  </div>

  <div class="container">
    <div class="row d-flex justify-content-center border rounded mt-3" style="background-color: #fff">
       <p class="text-center font-smll3 myppfont">${productVO.productDetail}</p>
    </div>
  </div>

  <footer class="bd-footer text-center ">
    <div class="footer__copyright "> © 2018 自転車. 版權所有。</div>
  </footer>
    
  <div class="modal fade" id="modalId" tabindex="-1" role="dialog">
		<div class="modal-dialog modal-lg modal-dialog-centered " role="document">
			<div class="modal-content">
				<div class="modal-header" id="modalbg">
					<h5 class="modal-title text-center" id="exampleModalLongTitle">確認付款</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
          </button>
				</div>                              
        <form action="<%=request.getContextPath()%>/ProductServlet" method="post" id="mysubmit">
  				<div class="modal-body">
  					<div class="container">
  						<div class="row  mx-auto">
   							<img src="<%=request.getContextPath()%>/sources/icon/secondShop/cash.png">
                <h6 class="mt-3 text-danger"> 提醒您，交易完成後如要取消訂單需取得賣家同意。</h6>
              </div>
  						<div class="text-center">
                <div class="text-center card-header">商品資訊</div>
                <table class="table acceptProductModalTable">
                  <thead>
                    <tr>
                      <th scope="col">商品名稱</th>
                      <th scope="col">賣家帳號</th>
                      <th scope="col">${(productVO.productDataStatus==6)? "得標價格":售價}</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td scope="row">${productVO.productName}</td>
                      <td>${productVO.memIdSale}</td>
                      <td><p class="text-danger font-weight-bold">${(productVO.productDataStatus==6)? productVO.productBiddingWinPrice:productVO.productPrice}</p></td>
                    </tr>
                  </tbody>
                </table>
                <div class="container">
                  <div class="row">
                    <div class="col-3">
                      <p> 目前可以用餘額：</p>
                    </div>
                    <div class="col-2">
                      <p class="font-weight-bold text-danger"><%=memCurrecyTotal%></p>
                    </div>
                    <div class="col-5">
                      <p> 若餘額不足請先前往儲值自転幣</p> 
                    </div>
                    <div class="col-2">
                     <a href="<%=request.getContextPath()%>/front-end/secondShop/Currency.jsp">來去儲值</a>
                    </div>
                  </div>
                </div>
                <div class="text-center card-header">收件人資料</div>
                <div class="container">
                  <div class="row mt-2">
                    <div class="col-3 align-self-center">
                      <label class="label-control">引用地址清單</label>
                    </div>    
                    <div class="col-9">
                      <select class="form-control" id="beforAddr">
                        <option value="0">----引用我的歷史地址清單----</option> 
                        <c:forEach var="deliveryVO" items="${deliveryList}">
                          <option value="${deliveryVO.deliveryId}" data-address="${deliveryVO.deliveryAddress}" data-deliveryName="${deliveryVO.deliveryName}" data-deliveryPhone="${deliveryVO.deliveryPhone}">
                             ${deliveryVO.deliveryAddress} ${deliveryVO.deliveryName} ${deliveryVO.deliveryPhone}
                          </option> 
                        </c:forEach> 
                      </select> 
                    </div>    
                  </div>
                  <div class="row mt-2">
                      <div class="col-3 align-self-center">
                          <label>收件人姓名</label>
                      </div>
                      <div class="col-3 align-self-center">
                          <input required type="text" class="form-control" id="passName" name="deliveryName">
                      </div>  
                      <div class="col-3 align-self-center">
                          <label>收件人電話</label>
                      </div>  
                      <div class="col-3 align-self-end">
                          <input required type="number" class="form-control" id="passPhone" name="deliveryPhone">
                      </div>    
                  </div>
                  <div class="row mt-2">
                      <div class="col-3 align-self-center">
                          <label>收件人地址</label>
                      </div>
                      <div class="col-2 align-self-center">
                        <select class="form-control" name="city" id="selectCity">
                          <option value="0">請選擇縣市</option>
                          <c:forEach var="cityVO" items="${cityList}">
                            <option value="${cityVO.cityId}">${cityVO.cityName}</option>
                          </c:forEach>
                        </select>
                      </div>   
                      <div class="col-2 align-self-center">
                        <select class="form-control" name="area" id="selectArea">
                          <option value="0">請選擇區</option>
                        </select>
                      </div>   
                      <div class="col-5 align-self-center">
                        <select class="form-control" name="road" id="selectRoad">
                          <option value="0">請選擇路</option>
                        </select>
                      </div>           
                  </div>
                  <div class="row mt-2 justify-content-end">
                    <div class="col-9 align-self-center">
                      <input required type="text" class="form-control" id="buyAddress" name="deliveryAddress">
                    </div>
                  </div>  
                </div>
              </div>
            </div>
  				</div>
				  <div class="modal-footer">
  					<c:if test="${not empty errorMsgs}">
              <font color='red'>請修正以下錯誤:
                <c:forEach var="message" items="${errorMsgs}">
  			          <li>${message}</li>       
        				</c:forEach>
        			</font>
  					</c:if>	
  					  <!-- <form action="<%=request.getContextPath()%>/ProductServelt" method="post"> -->
            <button type="button" class="btn btn-primary" id="checkOut">確認購買</button>
            <input class="hidden" type="text" name="action" value="buyPorduct">
            <input class="hidden" type="text" name="productId" value="${productVO.productId}">
            <input class="hidden" type="text" name="productName" value="${productVO.productName}">
            <input class="hidden" type="text" name="memIdBuy" value="${memVO.mem_id}">
            <input class="hidden" type="text" name="productPrice" value="${(productVO.productDataStatus==6)? productVO.productBiddingWinPrice:productVO.productPrice}">
            <input class="hidden" type="text" name="memCurrecyTotal" value="<%=memCurrecyTotal%>">
            <input class="hidden" type="text" name="requestURL" value="<%=request.getServletPath()%>">
            <input class="hidden" type="text" name="imgBlob" id="imgBlob">
				  </div>
        </form>
      </div>
	  </div>
  </div>

  <!-- Modal -->
  <div class="modal fade" id="Report" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLongTitle">檢舉商品</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
         <div class="row">
           <div class="col-md-6"><p>商品名稱</p></div>
           <div class="col-md-6"><p>檢舉理由</p></div>
         </div>
         <div class="row">
            <div class="col-md-6"><p>${productVO.productName}</p></div>
            <div class="col-md-6"><input type="text" class="form-control" id="Reportdetailed" name="Reportdetailed">
            </div>  
         </div>
        </div>
        <div class="modal-footer">
          <p class="text-danger" id="errorReport"></p>
          <button type="button" class="btn btn-secondary" id="ReportSummit">確定檢舉</button>
        </div>
      </div>
    </div>
  </div>
  
  <div class="hidden" id="wsNotise"></div>

  <div class="row talkCard">
      <div class="col-4 border border-primary rounded-top msgPlr1">
          <div class="talkListTop" style="">
              <div class="" >會員帳號</div>
          </div>
          <div class="talkListContent" id="talkListContent">
              <div class="border-bottom talkListMem" data-MemName="" data-MenId="">
                ooxx
              </div>
          </div>
      </div>
      
      <div class="col-8 border border-warning rounded-top msgPlr1">
          <div class="msgTop">
              <div class="row" style="width:100%;margin: 0px">
                  <div style="width:14%;height:100%;margin-left:2%;margin-right:2%;display:flex;align-items:center;justify-content:">
                      <div style="width:90%;height:90%;border-radius:50%;border:1px solid #f7c324;text-align:center;">按</div>
                  </div>
                  <div style="width:80%;margin-right:2%;display:flex;align-items:center;justify-content:center;">
                      <button id="msgListenMem" class="btn btn-outline-warning" style="width:100%; padding-right:0px;padding-left:0px">
                          某會員的聊聊
                      </button>
                  </div>
              </div>
          </div>
          <div id="msgRow" style="height:66%;border-radius:10px;border:1px solid #f7c324;margin-top:1%;margin-bottom:1%">
            <ul class="list-unstyled">
              <li class="mr-1 text-left text-secondary ">收方: 訊息</li>
              <li class="mr-1 text-right text-primary" name="textmessage">發訊方: 訊息</li>
            <ul>
          </div>
          <div id="inputRow" style="height:14%;border-radius:10px;border:1px solid #f7c324;display:flex;align-items:center;justify-content:space-around;margin-bottom:1%">
              <div class="" style="width:80%;margin: 0 2% 0 2%;">
                  <input type="text" class="form-control" placeholder="跟賣家聊聊" id="sedMessageText">
              </div>
              <div class="" style="width:16%; margin-right: 2%">
                  <button class="btn btn-outline-warning" id="sedMessageButton" style="width:100%; padding-right:0px;padding-left:0px">
                  send
                  </button>
              </div>
          </div>
      </div>
    </div>
    <img id="showMessage" src="<%=request.getContextPath()%>/sources/icon/secondShop/chat-128.png">
  <script src="https://code.jquery.com/jquery-3.2.1.min.js "></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js "></script>
  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js "></script>
  <script src="<%=request.getContextPath()%>/sources/js/secondShop/html2canvas.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>

  <!-- /CA103G2/sources/css/secondShop/css.css -->
  <script type="text/javascript">

  /* ============================ 聊天推播 ========================== */
   $('#showMessage').click(function(){
     // $('.talkCardhide').removeClass('talkCardhide').addClass('talkCard')
        $('.talkCard').animate({'right':'10px'},1000)
        $('#showMessage').animate({'right':'-1000px'},500)
           connectTalk();
          // $('div[name=data-status=coles]').attr()
          // $('.talkCard').animate({'right':'10px'},1000)
          // $('#showMessage').animate({'right':'-1000px'},500)

     
   })


    $('#sedMessage').click(function(){
      $('.talkCard').animate({'right':'10px'},1000)
      $('#showMessage').animate({'right':'-1000px'},500)
      // $('.talkCard').animate({'right':'-1000px'},1000)
      // $('#showMessage').animate({'right':'10px'},500)
      var saleNickName = '${saleMemVO.mem_nickname}'
      var saleId = '${saleMemVO.mem_id}'
      var memInList = $('div[data-MemId='+saleId+']').attr('data-MemId')
      if(memInList == undefined){
        var addToListContent = '<div class="border-bottom talkListMem" data-MemNiclName="'+saleNickName+'" data-MemId="'+saleId+'">'+saleNickName+'</div>'
        $('#talkListContent').append(addToListContent);

        $('#msgListenMem').text($('div[data-MemId='+saleId+']').attr('data-MemNiclName'))
          connectTalk();
      }
    })

    function changeMsgListenMem(){
      $('.talkListMem').click(function(){
        $('#msgListenMem').text($(this).attr('data-MemNiclName'))
      })
    }



    var MyPoint = "/MessageTalk";/* 專案路徑 */
    var host = window.location.host;
    var path = window.location.pathname;
    var webCtx = path.substring(0, path.indexOf('/', 1));
    var endPointURL = "ws://" + window.location.host + webCtx + MyPoint; 
    var webSocket;

    function connectTalk() {
      // 建立 websocket 物件
      webSocket = new WebSocket(endPointURL);

      webSocket.onopen = function(event) {
        console.log("我建立聊天連線")
      };

      webSocket.onmessage = function(event) {  //{"productId":"PRD012","callPrice":20022}
        var jsonObj = JSON.parse(event.data);
        var webSaleId = jsonObj.TosaleId  
        var saleId = jsonObj.TosaleId 
        var webMemId = '<%=memId%>'
        var saleId = '${saleMemVO.mem_id}'
        var sedMessage = jsonObj.sedMessage
        // if(webSaleId==saleId && webMemId == saleId){
        var webMemIdsedMessage = '<li class="mr-1 text-right text-primary" name="textmessage">'+jsonObj.frommemId+'+: '+jsonObj.sedMessage+'</li>'

        console.log("HIHI"+webMemIdsedMessage)
          $('li[name=textmessage]').append(webMemIdsedMessage)
        // }
      };

      webSocket.onclose = function(event) {
        console.log("我離開連線")
      };
    }
    
    function disconnectTalk () {
      webSocket.close();
       console.log("斷線")
    }

    function sendMessageTalk() {
      var saleId = '${saleMemVO.mem_id}'
      var memId = '<%=memId%>'
      var sedMessageText = $('#sedMessageText').val()
      console.log(sedMessageText);
      var jsonObj = {"TosaleId":saleId,"frommemId":memId,"sedMessage":sedMessageText};
      webSocket.send(JSON.stringify(jsonObj));
    }

     $('#sedMessageButton').click(function(){
        sendMessageTalk();
     })
  /* ============================ 競標推播 ========================== */
    var MyPoint = "/BiddingEchoServer";/* 專案路徑 */
    var host = window.location.host;
    var path = window.location.pathname;
    var webCtx = path.substring(0, path.indexOf('/', 1));
    var endPointURL = "ws://" + window.location.host + webCtx + MyPoint; 
    var webSocket;
      
    function connect() {
      // 建立 websocket 物件
      webSocket = new WebSocket(endPointURL);

      webSocket.onopen = function(event) {
        console.log("我建立連線")
      };

      webSocket.onmessage = function(event) {  //{"productId":"PRD012","callPrice":20022}
        var jsonObj = JSON.parse(event.data);
        var webProductId = jsonObj.productId     //PRD012
        var productId ="${productVO.productId}"  //PRD012

        if(webProductId==productId){

          $("#topPrice").text(jsonObj.callPrice);
          moveDiv(jsonObj.callPrice);
        }
      };

      webSocket.onclose = function(event) {
        console.log("我離開連線")
      };
    }
    
    function disconnect () {
      webSocket.close();
       console.log("斷線")
    }

    function sendMessage(callPrice) {
      var productId ="${productVO.productId}"
      var jsonObj = {"productId":productId,"callPrice" : callPrice};
      webSocket.send(JSON.stringify(jsonObj));
    }
      
    var wsNotiseCount = 1;
    function moveDiv(data){
      var count = wsNotiseCount
      wsNotiseCount++
      var addString = '<div class="p-1" name="myAminate" id="myAminate'+count+'s">'
      addString = addString + '<p class="font-smll2 p-1 text-white">'
      addString = addString + '<i class="fas fa-bell mr-1"></i>競標價格更新通知</p>'
      addString = addString + '<label class="font-smll3">競標底價</label>'
      addString = addString + '<span class="no_pricestr">NT$</span>'
      addString = addString + '<label class="smll_pricenum" id="wsPrice">'+data+'</label>'

      $('#wsNotise').after(addString)
      // $('#wsPrice').text(data)
      $('#myAminate'+count+'s').animate({'right':'10px'},1000,function(){
        setTimeout(function(){
          // $('#myAminate').animate({'right':'-300px'},1000)
          $('#myAminate'+count+'s').animate({'bottom':'-300px','opacity':'0'},1000,function(){
            $('#myAminate'+count+'s').remove()
          })

          // $('#myAminate'+count+'s').fadeOut(1000,function(){
          //   $('#myAminate'+count+'s').animate({'right':'-300px','opacity':'1'},100)
          // });
        },2000)
      })
    }

  /* ============================ 檢舉 ========================== */
    $('#ReportSummit').click(function(){
      var memIdforReport = "${memVO.mem_id}";
      var porductId= "${productVO.productId}";
      var Reportdetailed = $('#Reportdetailed').val()
      console.log(Reportdetailed);
      $.ajax({
        type:"POST",
        url:"<%=request.getContextPath()%>/front-end/secondShop/ProductReport",
        data:{"memId":memIdforReport,"productId":productId,"action":"Report","Reportdetailed":Reportdetailed},
        dataType:"json",
        success:function(data){
          if(data.error!=null){
            $('#errorReport').text(data.error);
          }else{
              $('#Report').modal('hide')
            swal({
              title: "檢舉成功",
              text: "已經收到您的檢舉我們會盡快處理",
              icon: "success",
              button: "Aww yiss!",
            });
            $('#errorReport').empty()
            $('#Reportdetailed').val("");
          }
        
        },
        error:function(){       
          alert("檢舉發生錯誤");
        }
      })
    })

  /* ============================ 結帳時前,拍下當前網頁資訊 ========================== */
    $('#checkOut').click(function() {
        new Date()
        $('#ta3').prepend('<h3 id="SnapshotNotis" style="color: red">Snapshot on '+ new Date() +'</h3>')

        html2canvas($('#ta3'), {
          allowTaint: true, //允许污染
        taintTest: true, //在渲染前测试图片(没整明白有啥用)
        useCORS: true, //使用跨域(当allowTaint为true时这段代码没什么用,下面解释)
        onrendered: function(canvas) {
          $('body').append(canvas)
          var imgBlob = canvas.toDataURL('image/jpeg', 1.0); //将图片转为base64
          var imgBlob2 = imgBlob.toString().substring(imgBlob.indexOf(",") + 1); //截取base64以便上传
          doSubmit(imgBlob2);
        }
      });
    })

    function doSubmit(data){
      $('#imgBlob').val(data);
      $('#SnapshotNotis').remove();
      $('#mysubmit').submit();
    }

  /* ============================ 進入網頁時,競標倒數 ========================== */
    <%
      Timestamp reciprocal = productVO.getProductEndBidding();
      if(reciprocal!=null){
        Long longEndDate = reciprocal.getTime();
    %>
    function go(){
      // console.log(event.target)
      var nowDate = new Date();
      var endDate = new Date(<%=longEndDate%>);
      /* endDate = new Date( endDate.setDate(endDate.getDate()+1) ) */
      var secondLeft = (endDate-nowDate)/1000;

      var id = setInterval(work,1000)
      function work(){
          var dday = Math.floor( secondLeft / 86400 )      //24小時＊60分鐘*60秒  ->一天秒數 86400 
          var hhr = Math.floor(  (secondLeft % 86400) / 3600 )  //60分鐘*60秒   ->一小時秒數 3600
        var mmin = Math.floor( (secondLeft % 86400  % 3600)/ 60 )
        var ssec = Math.floor( secondLeft % 86400 % 3600 % 60 )
        if(dday<10)
          dday = "0"+dday
        if(hhr<10)
          hhr = "0"+hhr
        if(mmin<10)
          mmin = "0"+mmin
        if(ssec<10)
          ssec = "0"+ssec
        $('#dd').text(dday)
        $('#hh').text(hhr)
        $('#mm').text(mmin)
        $('#ss').text(ssec)
        secondLeft--;

        if(secondLeft==0){
          clearInterval(id)
        }
      }
    }
    go();
    <%}%>

  /* ============================ 進入網頁時,啟動商品圖 ========================== */
    $('.carousel-indicators').children(':first').addClass('active')
    $('.carousel-inner').children(':first').addClass('active')

  /* ============================ 出價競標的ajax ========================== */
    var productId ="${productVO.productId}"
    var memId = "${memVO.mem_id}" //瀏覽的會員
    var topBiddingPrice = $('#topPrice').text()
    var productPrice = "${productVO.productPrice}"
    var productBiddingPrice = ${productVO.productBiddingPrice}
    $('#addBiddingPice').click(function(){
      var callPrice = $('#callPrice').val()
      // if(callPrice<=topBiddingPrice){
      //     $('#error').text("出價金額不能低於目前最高出價");
      // }
      $.ajax({
        type:"POST",
        url:"<%=request.getContextPath()%>/BiddingSerlet",
        data:{"memIdBuy":memId,"productId":productId,"action":"addBiddingPice","callPrice":callPrice,"topBiddingPrice":topBiddingPrice,"productPrice":productPrice,"productBiddingPrice":productBiddingPrice},
        dataType:"json",
        success:function(callPricerror){
          if(callPricerror.return == "bingo"){
           $('#error').empty();
              $('#topPrice').text(callPricerror.BiddingPrice)
              $('#callPrice').val('')
              sendMessage(callPricerror.BiddingPrice);

          }else{
            $('#error').text(callPricerror.error)
            $('#callPrice').val('')
          }   
        },
        error:function(){       
          alert("競標出價發生錯誤");
        }
      })
    })

  /* ============================ 進入網頁時,如該商品已在收藏的清單,收藏圖案改為全愛心 ========================== */
    var favorite = '<%=(productFavoriteVO !=null)? "yes":"no"%>'; 
    if(favorite =="yes"){
      $('#favorite').attr('src','<%=request.getContextPath()%>/sources/icon/secondShop/heart.png').attr('data-like','yes');
    }
    
  /* ============================ 點擊愛心,執行ajax的加入收藏或是移除收藏 ========================== */
    $('#favorite').click(function(){
      if(favorite=="no"){
        $.ajax({
          type:"POST",
          url:"<%=request.getContextPath()%>/ProductFavoriteServlet",
          data:{"productId":productId,"memId":memId,"action":"addFavorite"},
          dataType:"json",
          success:function(data){
            favorite = "yes"
            $('#favorite').attr('src','<%=request.getContextPath()%>/sources/icon/secondShop/heart.png').attr('data-like','yes');
            swal({
              title: "加收藏",
              text: "商品已加入收藏",
              icon: "success",
              button: "Aww yiss!",
            });
          },
          error:function(){
            alert("加入收藏發生錯誤")
          }
        })
      }
      if(favorite=="yes"){
        $.ajax({
          type:"POST",
          url:"<%=request.getContextPath()%>/ProductFavoriteServlet",
          data:{"productId":productId,"memId":memId,"action":"deleteFavorite"},
          dataType:"json",
          success:function(){
            favorite = "no"
           $('#favorite').attr('src','<%=request.getContextPath()%>/sources/icon/secondShop/like-1.png').attr('data-like','no');
          swal({
            title: "取消收藏",
            text: "商品已經取消收藏可到列表查看",
            icon: "error",
            button: "Aww yiss!",
          });
          },
          error:function(){
            alert("取消收藏發生錯誤")
          }
        })
      }
    })

  /* ============================ 確認購買時,資料進入controller後如果有返回錯誤訊息回到這個畫面, 即開啟modal ========================== */
    <% List<String> errorList = (List<String>)(request.getAttribute("errorMsgs"));%>
    var checkError = '<%=(errorList.size() != 0 )? "yes":"no"%>'
    if(checkError == "yes"){
      $('#modalId').modal()
    }
  
    /* ============================ 購買商品時,選擇引入地址清單 把標籤裡面的值寫入真正的input位置 ========================== */
    $('#beforAddr').change(function(){
       var passAddress = $('#beforAddr option:selected').attr('data-address');
       var passName = $('#beforAddr option:selected').attr('data-deliveryName');
       var passPhone = $('#beforAddr option:selected').attr('data-deliveryPhone');
        $('#buyAddress').val(passAddress);
        $('#passName').val(passName);
        $('#passPhone').val(passPhone);
    })

    /* ============================ 購買商品時,收件地址的ajax動態取出 地區/路名 ========================== */
    $('#selectCity').change(function(){                                     //selectCity 值改變的時候
      $('#selectArea').empty().append('<option value="0">請選擇區</option>') //找到區域select,刪除全部,新增第一個空選項
      $('#selectRoad').empty().append('<option value="0">請選擇路</option>')
      $('#buyAddress').val('')

      var cityIdValue = $(this).val()   //取到selectCity的value(值)     //$(this) 用選擇器選擇 近來“這個”方法的物件
      
      if(cityIdValue != 0){  //value不是0的時候(有選到city)   才做肚子的事情
        $.ajax({
          type: "POST",                                        //http 的 method(方法)種類
          url: "<%=request.getContextPath()%>/AddServlet",     //要溝通的位置，相當於<form>裡面的 action
          data: {"cityId":cityIdValue,"addrType":"getArea"}, //要傳出去的資料 直接用Json Object格式送出 
          dataType: "json", // 基本上ajax都是用json送   ex: {"K":"V" <,"2K":"2V">}  // <,"2K":"2V"> 可傳送多於一組的ＫＶ
          success: function (datass){      //成功的話(回拿到http Status = 200) , 拿收到的data(資料)執行肚子的程式碼
            console.log(datass.length)     // ====== 方法一 ======= 
            for(i=0;i<datass.length;i++){     //用for迴圈把資料一個一個拿出來,因為data是json陣列(陣列的一種)
              // console.log(datass[i])          // jsonArray[索引] ,可以拿到json物件
              var areaId = datass[i].areaId    //json物件裡面是(key:value),所以可以 "物件.key"拿到value
              var areaName = datass[i].areaName
              var appendAreaOption = '<option value="'+ areaId +'">'+ areaName +'</option>'
              $('#selectArea').append(appendAreaOption)  //把html格式的字串,放在 #selectArea子元素的最後一個
            }
          },
          error: function(){
            alert("AJAX-class發生錯誤囉!")
          } //ajax則最後面了 不用加
        })
      }
    })

    $('#selectArea').change(function(){
      $('#selectRoad').empty().append('<option value="0">請選擇路</option>')
      
      var AreaIdValue = $(this).val();
      console.log(AreaIdValue)
      if(AreaIdValue !=0){
        $.ajax({
          type:"POST",
          url:"<%=request.getContextPath()%>/AddServlet", 
          data:{"AreaId":AreaIdValue,"addrType":"getRoad"},
          dataType:"json",
          success:function(road){
            for(i=0;i<road.length;i++){
              var roadId = road[i].roadId
              var roadName = road[i].roadName
              var roadEngName = road[i].roadEngName
              var appendRoadOption = '<option value="'+roadId+'">'+roadName+'</option>'
              $('#selectRoad').append(appendRoadOption)
            }         
          },
          error:function(){
            alert("AJAX-class發生錯誤囉!")
          }
        })
      }
    })

    $('#selectRoad').change(function(){
      if($('#selectRoad').val() !=0){
      var city = $('#selectCity option:selected').text() 
      var area = $('#selectArea option:selected').text() 
      var road = $('#selectRoad option:selected').text()
      console.log(city+area+road)
      $('#buyAddress').val(city+area+road);
      }
    })

  </script>
    
    
</body>
</html>