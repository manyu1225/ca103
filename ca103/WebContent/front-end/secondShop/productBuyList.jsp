<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.List"%>
<%@ page import="com.secondShop.product.model.*"%>
<%@ page import ="com.secondShop.product.controller.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import ="com.secondShop.product.controller.*"%>
<%@ page import="com.secondShop.currency.model.*"%>
<%@ page import="com.secondShop.productDeliveryAddrData.model.*"%>
<%@ page import="com.secondShop.productDelivery.model.*"%>
<%	
	System.out.println("productBuyList.jsp");	

	MemVO memVO = (MemVO)session.getAttribute("memVO");
	String memId= memVO.getMem_id();  

	ProductService productSrv = new ProductService();
	List<ProductVO>	buyListByMemId = productSrv.findBuyListByMemId(memId);
    pageContext.setAttribute("buyListByMemId",buyListByMemId);

    ProductVO productVO  = (ProductVO) request.getAttribute("productVO"); 
    CurrencyService currencSve = new CurrencyService();
	Integer memCurrecyTotal = currencSve.memCurrecyTotal(memId);
  	pageContext.setAttribute("memCurrecyTotal",memCurrecyTotal); 

  	AddressDAO addressDAO = new AddressDAO();
	List<CityVO> cityList = addressDAO.getCity();
	pageContext.setAttribute("cityList",cityList); 

	DeliveryService deliverySvc = new DeliveryService();
 	List<DeliveryVO> deliveryList = deliverySvc.allAddress(memId);
 	pageContext.setAttribute("deliveryList", deliveryList);
%>

<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/sources/css/secondShop/productmem.css">
    <link rel="icon" href="<%=request.getContextPath()%>/sources/icon/secondShop/cycling.png" type="image/x-icon" />
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/sources/css/secondShop/fontawesome-stars.css">
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">


    <title>自転車-買家中心</title>
</head>
	<style type="text/css">
		.hidden{
			display: none;
		}
		.shodd{
		    box-shadow: 0 2px 3px rgba(0,0,0,0.2);
		}
	</style>
<body class="bodycolor">
<%@ include file="/sources/file/Home/NavBar.file"%>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="#">Home</a></li>
            <li class="breadcrumb-item"><a href="#">買家中心</a></li>
            <li class="breadcrumb-item active" aria-current="page">我的收藏商品</li>
        </ol>
    </nav>
    <div class="bg-light">
        <div class="container-floid mt-4" id="productlistbody">
            <div class="row mx-auto">
            		<%@ include file="/sources/file/secondShop/productSideBar.file" %> 
               	<div class="col-12 col-sm-10 mt-4" id="porduct-color">
               		<h2 id="buylist" class="text-center p-4 text-white bg-danger">
                        <img src="<%=request.getContextPath()%>/sources/icon/secondShop/girl.png">  我的敗家記錄
					</h2>
                <!--     <table class="table table-inbox table-hover" id="tabs"> -->
                    <table id="example2" class="table table-striped table-hover" style="width:100%" >
                    	<thead>
							<tr class="unread">
	                         	<td class="text-center">日期</td>
	                            <td class="text-center">確認收貨</td>
	                            <td class="text-center" >評價</td>
	                            <td class="text-center">商品名稱</td>
	                            <td class="text-center" >明細</td>
	                            <td class="text-right" >狀態</td>
                       		</tr>
                    	</thead>
                        <tbody>

                           
  							<c:forEach var="productVO" items="${buyListByMemId}">
                            <tr class="unread" data-productPrice="${(productVO.productBiddingWinPrice==null)? productVO.productBiddingWinPrice:productVO.productPrice}"
                            data-memIdSale="${productVO.memIdSale}">
								<td class="text-center d-inline-block text-truncate" style="width:8rem">
	  							   <fmt:formatDate pattern="yyyy-MM-dd" value="${productVO.productUpdateDate}"/>
                                </td>
                                <td class="text-center">
                 					<!--   狀態為售出時可以點選收貨和取消訂單 -->
 								<c:if test="${productVO.productDataStatus ==2}">
 									<img data-productId="${productVO.productId}"
 									name="acceptProduct" src="<%=request.getContextPath()%>/sources/icon/secondShop/delivery-man.png">
                               	</c:if>
                                </td>
                                <td class="text-center">
             					<!-- 狀態為收貨才可以顯示評價 -->
                                <c:if test="${(productVO.productDataStatus==3 && productVO.productRating==null)}">
                                	<img data-productId="${productVO.productId}" name="ratingProduct" src="<%=request.getContextPath()%>/sources/icon/secondShop/thumbs-32.png">
                                </c:if>        
                                </td>
                                <td class="text-center">
                                	<div class="row d-flex align-items-center">
			                            <img class="Snapshotbutton" name="${productVO.productId}" 
			                            src="<%=request.getContextPath()%>/sources/icon/secondShop/${(productVO.productDataStatus==6) ? 'hand.png':'photo-camera.png'}"  data-toggle="modal" data-target="#SnapShot">
		                                <a href="<%=request.getContextPath()%>/ProductServlet?productId=${productVO.productId}&action=getOneForView" target="_blank">
		                                	<label class="d-inline-block text-truncate" style="width:22em">${productVO.productName}</label>
		                                </a>
	                                </div>
	                                <div class="collapse" id="${productVO.productId}">
                                        <div class="container border rounded">
                                            <div class="row mt-2">
                                                <div class="col-6 align-self-center">
                                                    <p>收件人姓名</p>
                                                </div>
                                                <div class="col-6 align-self-center">
                                                	<p>收件人電話</p>
	                                        	</div>
	                                        </div>
                                            <div class="row mt-2">  
                                                <div class="col-6 align-self-center ">
													<P>${productVO.deliveryName}</P>
                                                </div>  
                                                <div class="col-6 align-self-end">
                                               	 	<P>${productVO.deliveryPhone}</P>
                                                </div>    
                                            </div>
                                            <div class="row">
                                                <div class="col-3 align-self-center ">
                                                   <p>收件人地址</p>
                                                </div> 
                                                <div class="col-9 align-self-center"">
                                                   <P>${productVO.deliveryAddress}</P>
                                                </div>         
                                            </div>
                                        </div>
	                                </div>
                                </td>
                                <td class="text-center">
                                	<button class="btn btn-primary shodd" type="button" data-toggle="collapse" data-target="#${productVO.productId}" aria-expanded="false" aria-controls="collapseExample">明細
                                	</button>
                                </td>                     
                                <td class="text-right">
								<c:if test="${productVO.productDataStatus=='2'}">已購買</c:if>
								<c:if test="${productVO.productDataStatus=='3'}">已收貨</c:if>
								<c:if test="${productVO.productDataStatus=='6'}">已得標</c:if>							
								</td>
                            </tr>
 						</c:forEach>
                        </tbody>
                    </table>
                </div>
			</div>
		</div>
	</div>

<!-- acceptProductModal -->
	<div class="modal fade bd-example-modal-lg" id="acceptProductModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	    <div class="modal-dialog modal-lg modal-dialog-centered " role="document">
	        <div class="modal-content">
	            <div class="modal-header text-light" id="modalbg">
	                <h5 class="modal-title text-center" id="exampleModalLongTitle">確認收貨</h5>
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                    <span aria-hidden="true">&times;</span>
	                </button>
	            </div>
	            <div class="modal-body">
	                <div class="container">
	                    <div class="row mx-auto">
	                        <div class="col-12">
	                            <div class="row  mx-auto">
	                                <img src="<%=request.getContextPath()%>/sources/icon/secondShop/agreement-1.png">
	                                <h5 class="mt-3 text-danger"> 提醒您，按下確認收貨，代表交易完成，交易完成後就無法取消訂單。</h5>
	                            </div>
	                            <div class="card text-center">
	                                <div class="card-header">
	                                    確認商品資訊
	                                </div>
	                                <div class="card-body">
	                                    <table class="table acceptProductModalTable">
	                                        <thead>
	                                            <tr>
	                                                <th scope="col">商品名稱</th>
	                                                <th scope="col">賣家帳號</th>
	                                                <th scope="col">售價</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                            <tr>
	                                                <td scope="row">productVO.productName</td>
	                                                <td>${currencyVO.currencyDetail}</td>
	                                                <td>productVO.productPrice</td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	                <div class="modal-footer">
	                    	<button id="doneDelivery" type="button" class="btn btn-primary" data-dismiss="modal">確認收貨</button>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>

<!-- cancelProductModal -->
	<div class="modal fade bd-example-modal-lg" id="cancelProductModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	    <div class="modal-dialog modal-lg modal-dialog-centered " role="document">
	        <div class="modal-content">
	            <div class="modal-header text-light" id="modalbg">
	                <h5 class="modal-title text-center" id="exampleModalLongTitle">確認取消訂單</h5>
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                    <span aria-hidden="true">&times;</span>
	                </button>
	            </div>
	            <div class="modal-body">
	                <div class="container">
	                    <div class="row mx-auto">
	                        <div class="col-12">
	                            <div class="row  mx-auto">
	                                <img src="<%=request.getContextPath()%>/sources/icon/secondShop/cancel-1.png">
	                                <h5 class="mt-3 text-danger">提醒您，按下取消訂單必須買家同意取消否則，無法取消訂單。</h5>
	                            </div>
	                            <div class="card text-center">
	                                <div class="card-header">確認商品資訊</div>
	                                <div class="card-body">
	                                    <table class="table cancalProductModalTable">
	                                        <thead>
	                                            <tr>
	                                                <td scope="row">商品名稱</td>
	                                                <td>賣家帳號</td>
	                                                <td>售價</td>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                            <tr>
	                                                <td scope="row">productVO.productName</td>
	                                                <td>productVO.memIdSale</td>
	                                                <td>productVO.productPrice</td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-primary" data-dismiss="modal">確認送出</button>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>

<!-- ratingProductModal -->
	<div class="modal fade bd-example-modal-lg" id="ratingProductModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	    <div class="modal-dialog modal-lg modal-dialog-centered " role="document">
	        <div class="modal-content">
	            <div class="modal-header text-light" id="modalbg">
	                <h5 class="modal-title text-center" id="exampleModalLongTitle">確認評價</h5>
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                    <span aria-hidden="true">&times;</span>
	                </button>
	            </div>
	            <div>
	                    <div class="row mx-auto">
	                        <div class="col-12">
	                            <div class="row  mx-auto">
	                                <img src="<%=request.getContextPath()%>/sources/icon/secondShop/thumbs-up.png">
	                                <h5 class="mt-3 text-danger">給賣家一個評價</h5>
	                            </div>
	                            <div class="card text-center">
	                                <div class="card-header">確認商品資訊</div>
	                                    <table class="table ratingProductModalTable">
	                                        <thead>
	                                            <tr>
	                                                <th scope="col">商品名稱</th>
	                                                <th scope="col"><i class="fas fa-star"></i>給賣家評分</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                            <tr>
	                                                <td scope="row"></td>
	                                                <td>
	                                                	<div class="stars stars-example-fontawesome">
	                                                	  <select id="example-fontawesome" name="rating" autocomplete="off">
	                                                	    <option value="1">1</option>
	                                                	    <option value="2">2</option>
	                                                	    <option value="3">3</option>
	                                                	    <option value="4">4</option>
	                                                	    <option value="5">5</option>
	                                                	  </select>
	                                                	</div>
	                                                </td>
	                                            </tr>
	                                            <tr>
		                                            <td colspan="2">
		                                            	  <div class="form-group">
		                                            	    <p class="font-sale"><i class="far fa-comment-dots"></i> 給賣家評價</p>
		                                            	   <input type="text" class="form-control mt-2" aria-label="Small" aria-describedby="inputGroup-sizing-sm" id="discuss" value=" 超讚的商品品質 超讚的CP值 超讚的服務 超讚的出貨速度"/>
		                                            	  </div>	
		                                            </td>
	                                            </tr>
	                                        </tbody>
	                                    </table>
	                            </div>
	                        </div>
	                    </div>
	                <div class="modal-footer">
	              		<p id="ratingerror" class="text-danger text-center"></p>
	                    <button id="addRating" type="submit" class="btn btn-primary" >確認送出</button> <!-- data-dismiss="modal" -->
	                </div>
	            </div>
	        </div>
	    </div>
	</div>

<!-- 快照SnapshotModal -->
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
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<script src="<%=request.getContextPath()%>/sources/js/secondShop/examples.js"></script>
	<script>window.jQuery || document.write('<script src="js/vendor/jquery-1.11.2.min.js"><\/script>')</script>
	<script src="<%=request.getContextPath()%>/sources/js/secondShop/jquery.barrating.js"></script>
	<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
	<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js" crossorigin="anonymous"></script>

    <script type="text/javascript">
		$('.Snapshotbutton').click(function(){
			$("#SnapshotModal").remove('src')
			var productIdforShot = $(this).attr('name')
			console.log(productIdforShot)
			var st ='<%=request.getContextPath()%>/ProductServlet?action=lokSnapshot&productId='+productIdforShot
			$("#SnapshotModal").attr('src',st)

		})

    	    $(document).ready(function(){

    	    $('#example2').DataTable({
    	    	"columns":[
    	    	null,
    	    	{"orderable":false},
    	    	{"orderable":false},
    	    	{"orderable":false},
    	    	{"orderable":false},
    	  		null
    	    	],
    	        "lengthMenu": [[5, 25, 50, -1], [5, 25, 50, "All"]],
    	        "language" : {
    	            "search" : "關鍵字搜尋",
    	            "lengthMenu" : "每頁 _MENU_ 筆記錄",
    	            "zeroRecords" : "查無記錄",
    	            "info" : "第 _PAGE_ 頁 ( 總共 _PAGES_ 頁 )",
    	            "infoEmpty" : "查無資料",
    	            "infoFiltered" : "(從 _MAX_ 條記錄過濾)",
    	            "paginate" : {"previous" : "上一頁","next" : "下一頁"}
    	        }
    	    });
    	})
    	   

		$('#example-fontawesome').change(function(){
			console.log("Rating value : "+$(this).val())
		})

       $(function() {
          $('#example').barrating({
            theme: 'fontawesome-stars'
          });
       });

	</script>
	<script type="text/javascript">

		$('img[name=biddingProduct]').click(function(){
			var biddingprice = $(this).closest('tr').attr('data-productPrice')
			var biddingName = $(this).closest('tr').find('td:eq(3) span').text()
			var memIdSale = $(this).closest('tr').attr('data-memIdSale')
			$('.Biddingmodal tbody ').find('td:eq(0)').text(biddingName)
			$('.Biddingmodal tbody').find('td:eq(1)').text(memIdSale)
			$('.Biddingmodal tbody tr td:eq(2)').text(biddingprice)
			$("#Biddingmodal").modal();
		})

		var productId
		$('img[name=acceptProduct]').click(function(){
			var price = $(this).closest('tr').attr('data-productPrice')
			var memIdSale = $(this).closest('tr').attr('data-memIdSale')
			var productName = $(this).closest('tr').find('td:eq(3) label').text()
			productId = $(this).attr('data-productId')
			console.log(productId)
			console.log('memIdSale:'+memIdSale)
			console.log('productName:'+productName)

			$('.acceptProductModalTable tbody ').find('td:eq(0)').text(productName)
			console.log(productName);
			$('.acceptProductModalTable tbody').find('td:eq(1)').text(memIdSale)
			$('.acceptProductModalTable tbody tr td:eq(2)').text(price)

			$("#acceptProductModal").modal();
		})

		$('#doneDelivery').click(function(){
			var productName =$('.acceptProductModalTable tbody ').find('td:eq(0)').text()
			var memIdSale =$('.acceptProductModalTable tbody').find('td:eq(1)').text()
			var price = $('.acceptProductModalTable tbody tr td:eq(2)').text()
			$.ajax({
				type:"POST",
				url:"<%=request.getContextPath()%>/ProductServlet",
				data:{"productId":productId,"action":"doneDelivery","productPrice":price,"memIdSale":memIdSale,"productName":productName},
				dataType:"json",
				success:function(data){
					$('img[data-productId='+data.productId+']').remove()
					 swal({
					  title: "確認收貨成功",
					  icon: "success",
					});
					console.log("ajax 成功～～")
				},error:function(){
				 alert("AJAX-class發生錯誤囉!")
				}
			})
		})


		$('img[name=cancelProduct]').click(function(){
			var price = $(this).closest('tr').attr('data-productPrice')
			var memIdSale = $(this).closest('tr').attr('data-memIdSale')
			var productName = $(this).closest('tr').find('td:eq(4) span').text()
			
			console.log('price:'+price)
			console.log('memIdSale:'+memIdSale)
			console.log('productName:'+productName)

			$('.cancleProductModalTable tbody').find('td:eq(0)').text(productName)
			$('.cancleProductModalTable tbody').find('td:eq(1)').text(memIdSale)
			$('.cancleProductModalTable tbody').find('td:eq(2)').text(price)

			$("#cancelProductModal").modal();
		})

		$('img[name=ratingProduct]').click(function(){
			var productRating = $('#discuss').val("超讚的商品品質 超讚的CP值 超讚的服務 超讚的出貨速度");
			var price = $(this).closest('tr').attr('data-productPrice')
			var memIdSale = $(this).closest('tr').attr('data-memIdSale')
			var productName = $(this).closest('tr').find('td:eq(3) label').text()
				productId = $(this).attr('data-productId')
			$('.ratingProductModalTable tbody').find('td:eq(0)').text(productName)
			$("#ratingProductModal").modal();
		})

		$('#addRating').click(function(){

			$('#ratingerror').empty()
			var productScore = $('#example-fontawesome').change().val()
			var productRating = $('#discuss').val()
			$.ajax({
				Type:"POST",
				url:"<%=request.getContextPath()%>/front-end/secondShop/ProductforRiating",
				data:{"productId":productId,"action":"addRating","productScore":productScore,"productRating":productRating},
				dataType:"json",
				success:function(data){
					if(data.Error !=null){
						$('#ratingerror').append(data.Error)
					}else{
						$('#ratingProductModal').modal('hide')
						$('img[data-productId='+data.productId+']').remove()
						 swal({
						  title: data.Done,
						  icon: "success",
						});
					}
				},error:function(){
					alert('失敗囉');
				}
			})
		})

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
				// $.each(datass,function(i,item){   // i是data裡面第幾個索引(0~n)的資料  ,item就是資料本身
				//   $('#selectArea').append('<option value="'+ item.areaId +'">'+ item.areaName +'</option>')
				// })
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