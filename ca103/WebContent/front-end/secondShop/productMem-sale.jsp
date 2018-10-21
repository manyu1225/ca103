<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.List"%>
<%@ page import="com.secondShop.product.model.*"%>
<%@ page import="com.secondShop.productPhoto.model.*"%>
<%@ page import="com.secondShop.product.controller.*"%>
<%@ page import="com.secondShop.currency.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import ="java.sql.Timestamp"%>
<%@ page import="java.util.Date"%>

<%		
	System.out.println("productMem-sale.jsp");
	MemVO memVO = (MemVO)session.getAttribute("memVO");
	String memId= memVO.getMem_id();  

	ProductService productSrv = new ProductService();
	ProductPhotoService productPhotoSrv = new ProductPhotoService();

	CurrencyService currencSve = new CurrencyService();

	Integer memCurrecyTotal = currencSve.memCurrecyTotal(memId);
	pageContext.setAttribute("memCurrecyTotal",memCurrecyTotal); 
	List<ProductVO>	listProduct1 = productSrv.findSalesListByMemId(memId,1);
    pageContext.setAttribute("listProduct1",listProduct1);
	List<ProductVO>	listProduct2 = productSrv.findSalesListByMemId(memId,2);
      pageContext.setAttribute("listProduct2",listProduct2);
	List<ProductVO>	listProduct3 = productSrv.findSalesListByMemId(memId,4);
     pageContext.setAttribute("listProduct3",listProduct3);
	List<ProductVO>	listProduct4 = productSrv.findSalesListByMemId(memId,5);
     pageContext.setAttribute("listProduct4",listProduct4);
     
   

// 	List<ProductVO> productVO  = (List) request.getAttribute("productBuyListByMemId");
   
%>

<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/sources/css/secondShop/productmem.css">
   <link rel="icon" href="<%=request.getContextPath()%>/sources/icon/secondShop/cycling.png" type="image/x-icon" />
    <title>自転車-賣家中心</title>
 	<style type="text/css">
  .no_pricestr{
    vertical-align: top;
    font-size: 1rem;
    margin-right: 5px;
    color: #ea0000;
    font-family: Arial;
    font-weight: bold;
    margin-top: 10px;
  }
  .no_pricenum {
      font-size: 2.5rem;
      line-height: 1;
      color: #ea0000;
      font-family: 'lativAbweb','Century Gothic','Tw Cen MT Condensed';
      font-weight: bold;
  </style>
</head>
<body>
<%@ include file="/sources/file/Home/NavBar.file" %>
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Home</a></li>
        <li class="breadcrumb-item"><a href="#">賣家中心</a></li>
        <li class="breadcrumb-item active" aria-current="page">商品管理</li>
      </ol>
    </nav>
    <div class="bg-light">
        <div class="container-floid mt-4" id="productlistbody">
            <div class="row mx-auto">    	
           		<%@ include file="/sources/file/secondShop/productSideBar.file" %> 
                <div class="col-12 col-sm-10" id="porductmen">
                    <section id="tabs">
                        <div class="">
                            <h6 class="section-title h1">商品管理</h6>
                            <div class="col-xs-12">
                                <nav>
                                    <div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">
                                        <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">銷售中</a>
                                        <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">己售出</a>
                                        <a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#nav-contact" role="tab" aria-controls="nav-contact" aria-selected="false">關閉賣場</a>
                                        <a class="nav-item nav-link" id="nav-about-tab" data-toggle="tab" href="#nav-about" role="tab" aria-controls="nav-about" aria-selected="false">檢舉商品</a>
                                    </div>
                                </nav>

                                <div class="tab-content py-3 px-3 px-sm-0" id="nav-tabContent">
                                	<%@ include file="/sources/file/secondShop/memSale_pag_1.file"%>
                                	<!-- 銷售中分頁 -->
                                    <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
                                        <c:forEach var="productVO" items="${listProduct1}"  begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
	                                        <div class="row mb-2 imgrow bg-warning rounded shodd cardHover">
	                                            <div class="col-sm-3 tacNpaddingTB">
	                                            	<a href="<%=request.getContextPath()%>/ProductServlet?productId=${productVO.productId}&action=getOneForView">
	                                                	<img class="imgmax shodd rounded" src="<%=request.getContextPath()%>/ProductPhotoServlet?productId=${productVO.productId}&action=photoForOne">
	                                                </a>
	                                            </div>
	                                            <div class="col-sm-6 align-self-center">
	                                                <p class="font-sale text-truncate">${productVO.productName}</p>
	                                                <div class="row">
	                                                	<label class=" mr-4 font-smll border rounded bg-danger pl-1 pr-1 align-self-center">${productVO.productSaleType}</label>
	                                                	<span class="font-weight-bold no_pricestr">NT</span>
                                                    <label class="no_pricenum">${productVO.productPrice}</label>
	                                                </div>
	                                                <p class="font-mm text-dark text-truncate">${productVO.productDetail}</p>
	                                               	<p class="font-mm text-right">
	                                               		<label class="text-dark">最後更新時間：</label>
	                                               		<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${productVO.productUpdateDate}"/>
	                                               	</p>
	                                            </div>
			                                    <div class="col-sm-3 text-center align-self-center">
			                                        <div class="row justify-content-center">
														<c:if test="${productVO.productAd==null}">
				                                            <button class="btn btn-secondary shodd mr-4" name="adBuy" type="button" data-toggle="modal" data-target="#exampleModalCenter" data-productId="${productVO.productId}"data-productName="${productVO.productName}" data-productPrice="${productVO.productPrice}"> 
			                                                	<i class="fas fa-sort-amount-up"></i>
			                                                </button>
		 												</c:if>  
			                                            <form action="<%=request.getContextPath()%>/front-end/secondShop/ProductUpdate" method="post">
				                                            <button class="btn btn-secondary shodd mr-4" type="submit" data-toggle="tooltip" data-placement="top" title="修改商品">
				                                                <i class="fas fa-pencil-alt"></i>
				                                            </button>
				                                            <input type="hidden" name ="productId" value="${productVO.productId}"> 
				                                            <input type="hidden" name ="action" value="getOneForUpdate">
				                                            <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->    
				                                        </form>
			                                            <form action="<%=request.getContextPath()%>/ProductServlet" method="post">
			                                            	<button class="btn btn-secondary shodd mr-4" type="submit" data-toggle="tooltip" data-placement="top" title="下架商品">
			                                                	<i class="fas fa-times-circle"></i>
			                                                </button>
			                                                <input type="hidden" name ="productId" value="${productVO.productId}"> 
			                                                <input type="hidden" name ="action" value="shutDownPorduct">
			                                                <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
			                                                	<!--送出本網頁的路徑給Controller-->    
			                                             </form>
			                                        </div>   
			                                    </div>
	                                      	</div>
       									</c:forEach>
       									<%@ include file="/sources/file/secondShop/productPag.file"%>
                                    </div>
                                	<!-- 已售出分頁 -->
                                    <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                                    	<%@ include file="/sources/file/secondShop/memSale_pag_2.file"%>
										<c:forEach var="productVO" items="${listProduct2}" begin="<%=pageIndexTwo%>" end="<%=pageIndexTwo+rowsPerPageTwo-1%>">
	                                        <div class="row mb-2  bg-warning">
	                                        	<div class="col-sm-3 tacNpaddingTB imgrow">
	                                        		<a href="<%=request.getContextPath()%>/ProductServlet?productId=${productVO.productId}&action=getOneForView">
	                                        	    <img class="imgmax shodd rounded" src="<%=request.getContextPath()%>/ProductPhotoServlet?productId=${productVO.productId}&action=photoForOne">
	                                        	    </a>
	                                        	</div>
	                                        	<div class="col-sm-6 align-self-center">
	                                        	    <p class="font-sale text-truncate">${productVO.productName}</p>
	                                        	    <div class="row">
                                                    <label class=" mr-4 font-smll border rounded bg-danger pl-1 pr-1 align-self-center">${productVO.productSaleType}</label>
                                                    <span class="font-weight-bold no_pricestr">NT</span>
                                                    <label class="no_pricenum">${productVO.productPrice}</label>
	                                        	    </div>
	                                        	    <p class="font-mm text-dark text-truncate">${productVO.productDetail}</p>
	                                        	   	<p class="font-mm text-right">
	                                        	   		<label class="text-dark">最後更新時間：</label>
	                                        	   		<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${productVO.productUpdateDate}"/>
	                                        	   	</p>
	   	    		                                <div class="collapse" id="${productVO.productId}">
	   	    		                                    <div class="container border rounded">
	   	    		                                        <div class="row mt-2">
	   	    		                                            <div class="col-3 align-self-center">
	   	    		                                                <p>收件人姓名</p>
	   	    		                                            </div>
	   			                                              	<div class="col-3 align-self-center">
	   																<P>${productVO.deliveryName}</P>
	   		 	                                               	</div>  
	   			                                              	<div class="col-3 align-self-center">
	   			                                                 	<p>收件人電話</p>
	   			                                               	</div>  
	   			                                              	<div class="col-3 align-self-end">
	   			                                               	 	<P>${productVO.deliveryPhone}</P>
	   			                                               	</div>    
	   			                                          	</div>
	   	    		                                        <div class="row">
	   	    		                                            <div class="col-3 align-self-center">
	   	    		                                                <p>收件人地址</p>
	   	    		                                            </div> 
	   	    		                                            <div class="col-7 align-self-center">
	   	    		                                                <P>${productVO.deliveryAddress}</P>
	   	    		                                             </div>         
	   	    		                                        </div>
	   	    		                                    </div>
	   	    		                                </div>
	                                        	</div>
	                                            <div class="col-3 text-center align-self-center">
	                                            	<div class="row justify-content-center">
	                                            		<img id="Snapshotbutton" name="${productVO.productId}" src="<%=request.getContextPath()%>/sources/icon/secondShop/photo-camera-48.png"  data-toggle="modal" data-target="#SnapShot" class="mr-2">
	                                            		<button class="btn btn-primary shodd" type="button" data-toggle="collapse" data-target="#${productVO.productId}" aria-expanded="false" aria-controls="collapseExample">出貨明細
	                                            		</button>
	                                            	</div>
	                                            </div>
	                                        </div>
										</c:forEach>
										<%@ include file="/sources/file/secondShop/memSale_pag_2-2.file"%>
									</div>
                                    <!-- 關閉分頁 -->
                                    <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
                                    	<%@ include file="/sources/file/secondShop/memSale_pag_3.file"%>
										<c:forEach var="productVO" items="${listProduct3}"  begin="<%=pageIndexThree%>" end="<%=pageIndexThree+rowsPerPageThree-1%>">
	                                        <div class="row mb-2 imgrow bg-warning">
	                                        	<div class="col-sm-3 tacNpaddingTB">
	                                        		<a href="<%=request.getContextPath()%>/ProductServlet?productId=${productVO.productId}&action=getOneForView">
	                                        	    <img class="imgmax shodd rounded" src="<%=request.getContextPath()%>/ProductPhotoServlet?productId=${productVO.productId}&action=photoForOne">
	                                        	    </a>
	                                        	</div>
	                                        	<div class="col-sm-6 align-self-center">
	                                        	    <p class="font-sale text-truncate">${productVO.productName}</p>
	                                        	    <div class="row">
                                                    <label class=" mr-4 font-smll border rounded bg-danger pl-1 pr-1 align-self-center">${productVO.productSaleType}</label>
                                                    <span class="font-weight-bold no_pricestr">NT</span>
                                                    <label class="no_pricenum">${productVO.productPrice}</label>
  	                                        	  </div>
	                                        	    <p class="font-mm text-dark text-truncate">${productVO.productDetail}</p>
	                                        	   	<p class="font-mm text-right">
	                                        	   		<label class="text-dark">最後更新時間：</label>
	                                        	   		<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${productVO.productUpdateDate}"/>
	                                        	   	</p>
	                                        	</div>
	                                        	<div class="col-sm-3 align-self-center text-center">
<!-- 	                                        		<form action="<%=request.getContextPath()%>/front-end/secondShop/ProductUpdate" method="post">
					                                   <button class="btn btn-secondary shodd mr-4" type="submit" data-toggle="tooltip" data-placement="top" title="上架商品"><i class="fas fa-pencil-alt"></i>
					                                    </button>
                                                		<input type="hidden" name ="productId" value="${productVO.productId}"> 
        			                                    <input type="hidden" name ="action" value="resumeProduct">
        			                                    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">	
        			                                </form>     -->                                    		
	                                        	</div>
	                                        </div>
                              			</c:forEach>
                              			<%@ include file="/sources/file/secondShop/memSale_pag_3-2.file"%>
                                    </div>
                                    <!-- 分頁 -->
                                    <div class="tab-pane fade" id="nav-about" role="tabpanel" aria-labelledby="nav-about-tab">
										<c:forEach var="productVO" items="${listProduct4}">
											<div class="row mb-2 imgrow bg-warning">
												<div class="col-sm-3 tacNpaddingTB">
													<a href="<%=request.getContextPath()%>/ProductServlet?productId=${productVO.productId}&action=getOneForView">
												    <img class="imgmax shodd rounded" src="<%=request.getContextPath()%>/ProductPhotoServlet?productId=${productVO.productId}&action=photoForOne">
												    </a>
												</div>
												<div class="col-sm-6 align-self-center">
												    <p class="font-sale text-truncate">${productVO.productName}</p>
												    <div class="row">
                                <label class=" mr-4 font-smll border rounded bg-danger pl-1 pr-1 align-self-center">${productVO.productSaleType}</label>
                                <span class="font-weight-bold no_pricestr">NT</span>
                                <label class="no_pricenum">${productVO.productPrice}</label>
												    </div>
												    <p class="font-mm text-dark text-truncate">${productVO.productDetail}</p>
												   	<p class="font-mm text-right">
												   		<label class="text-dark">最後更新時間：</label>
												   		<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${productVO.productUpdateDate}"/>
												   	</p>
												</div>
	                                            <div class="col-sm-3 text-center align-self-center">
	                                                <a class="btn btn-secondary mr-1" data-toggle="tooltip" data-placement="top" title="鎖定">
	                                                   <i class="fas fa-lock"></i>
	                                                </a>
	                                            </div>
	                                        </div>
                                 		</c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
	<!-- Modal -->
	<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" >
	  <div class="modal-dialog modal-dialog-centered modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle">購買廣告</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
            <div class="row border-bottom">
	            <div class="col-8">
	                <p class="text-center" id="productId" >商品名稱</p>
	            </div>
	            <div class="col-4">
	                <p class="text-center">目前可用餘額</p>
	            </div>
            </div>
            <div class="row">
                <div class="col-8">
                  <p id="productName" name="pName" class="text-center" xxx="ooo"></p> 
                  <!--       $('p[name=pName]').attr('xxx','uuu')                  -->
                  <!-- <input value="" id="inputId" name="inName" class="text-center" xxx="ooo">yuyu</input>  -->
                  <!--       $('input[name=inName]').attr('xxx','uuu')            -->
                </div>

                <div class="col-4">
                  <p class="text-center text-danger font-sale"><%=memCurrecyTotal%></p>
                </div>
            </div>
            <div class="row justify-content-end">
				<p class="text-center mr-2">購買廣告會花費100元，獲得為期三天的置頂廣告</p>
            </div>
	      </div>
	      <%
	      Date date = new Date();                        // util.Date 物件拿到當前時間
	      Long milSecFromd19700101 = date.getTime();      // date當前時間.getTime() =>  拿到當前時間的 從1970/1/1 起的毫秒數
	      Long DayTime = milSecFromd19700101 + 3*32*60*60*1000; //增加3天的時間 1d * 24h * 60m * 60s * 
	      Timestamp sqlTimeStamp_2 = new Timestamp(DayTime); 
	      
          java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String formatDate = df.format(sqlTimeStamp_2);
	      %>
	      <div class="modal-footer d-flex justify-content-end">
			<div class="col-2 ">廣告結束時間</div>
			<div class="col-4 font-sale text-danger"><%=formatDate%></div>
			<div class="col-6 d-flex justify-content-end">
			     <button id="buyAD" type="button" class="btn btn-info" data-dismiss="modal">購買廣告</button>
			</div>
	      </div>
	    </div>
	  </div>
	</div>
	<div class="modal fade  bd-example-modal-lg" id="SnapShot" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-body">
	      	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
	      	  <span aria-hidden="true">&times;</span>
	      	</button>
	      	<img class="img-fluid" id="SnapshotModal">
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<script type="text/javascript">
	$('#Snapshotbutton').click(function(){
		var productIdfor = $(this).attr('name')
		var st ='<%=request.getContextPath()%>/ProductServlet?action=lokSnapshot&productId='+productIdfor
		$("#SnapshotModal").attr('src',st)
	})


	$('button[name=adBuy]').click(function(){
		var productId = $(this).attr('data-productId')
		var productName = $(this).attr('data-productName')  //    < data-productName="">
		$('#productId').attr('data-productId',productId) //找到標籤的屬性
		$('#productName').text(productName)      
		$("#exampleModalCenter").modal();
	})

	$('#buyAD').click(function(){
		var productId = $('#productId').attr('data-productId') //拿到該屬性的值
		var productName = $('#productName').text()
		var memId ='<%=memId%>'
		console.log("#buyAD clicl~~~~")
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/front-end/secondShop/ProductforByAD",
			data:{"productId":productId,"productName":productName,"action":"porductBuyAD","memIdSale":memId},
			dataType:"json",
			success:function(data){
			$('button[data-productId='+data.productId+']').remove()
				 swal({
				  title: "購買廣告成功",
				  text: "購買廣告成功可到商品列表查看",
				  icon: "success",
				  button: "Aww yiss!",
				});

			},error:function(){
				 alert("AJAX-class發生錯誤囉!")
			}

		})
	})


	// function ajaxAdData(productId,productName){
	// 	return '{"productId":"'+ productId +'","productName":"'+ productName +'","action":"porductBuyAD","memIdSale":"M000003"}'
	// }

	

	$(function() {
	    $('[data-toggle="tooltip"]').tooltip()

	    $('[data-toggle="popover"]').popover({
	        html: true
	    })
	})


</script>
      
</body>

</html>