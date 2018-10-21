<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.List"%>
<%@ page import="com.secondShop.product.model.*"%>
<%@ page import="com.secondShop.productPhoto.model.*"%>
<%@ page import="com.secondShop.productFavorite.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.secondShop.product.browserRec.*"%>

<%  
	System.out.println("productList.jsp");

	/* 14-15模擬會員已登入,送回來的memVO */

	MemVO memVO = (MemVO)session.getAttribute("memVO");
	String memId= memVO.getMem_id();  

    ProductService productSrv = new ProductService();
    List<ProductVO> productListbyDataStatus = productSrv.findProductListbyDataStatus(1);
    
    List<ProductVO> selectProductList = (List<ProductVO>)request.getAttribute("selectProductList");
    if(selectProductList!=null){
    	productListbyDataStatus = selectProductList;
    }
    
    pageContext.setAttribute("productListbyDataStatus",productListbyDataStatus);
    BrowserRec browserRec = new BrowserRec();
    List<ProductVO> browserRecList = browserRec.browserRecList(memId);
    
    pageContext.setAttribute("browserRecList", browserRecList);
	
    
%>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
    <link rel="icon" href="<%=request.getContextPath()%>/sources/icon/secondShop/cycling.png" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href=" https://cdn.jsdelivr.net/npm/pretty-checkbox@3.0/dist/pretty-checkbox.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet"  />
    

    <title>二手拍賣</title>

    <style type="text/css">
    .col-vh50{  /* 卡片外面的col高度 設定為螢幕50%的可視範圍  HTML5用法 */
         height: 36vh;
     }

    .hiih{      /* 卡片本身高度 設定為跟父元素同高 */
        height: inherit;
    }
        
    .ofAuto{
         overflow: auto;
    }
    .font-sale{
      font-size:1.4rem;
    }
    .font-smll{
      font-size:1.2rem;
    }
    .font-mm{
      font-size:1rem;
    }
    .productNameDiv{
     height: 2.4rem;
     font-size: 1rem;
     line-height:1.2rem;
     overflow: hidden;
    }
    

    .boxTop{
       border-color:red;
    }
    .hidden{
        display: none;
    }
    .shodd{
        box-shadow: 0 0.1rem 0.2rem 0 rgba(0,0,0,.1);;
    }
    .tttess:hover{
        font-text-shadow:3px 3px #cccccc;
        background-color: #cccc;
    }
    .list:hover{
        -webkit-transform: translateY(-.2rem);
        transform: translateY(-.2rem);
        z-index: 2;
    }
    .menubar{
        background-color: #cce6ff;
    }
    .bg-grayy{
        background-color: #dee2e6;
    }
    .no_pricestr{
      vertical-align: top;
      font-size: 0.5rem;
      margin-right: 5px;
      color: #ea0000;
      font-family: Arial;
      font-weight: bold;
      margin-top: 10px;
    }
    .no_pricenum {
        font-size: 1.5rem;
        line-height: 1;
        color: #ea0000;
        font-family: 'lativAbweb','Century Gothic','Tw Cen MT Condensed';
        font-weight: bold;
    </style>
</head>

<body style="background-color:  #f8f9fa!important;">

<%@ include file="/sources/file/Home/NavBar.file"%>
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Home</a></li>
        <li class="breadcrumb-item active" aria-current="page">商品列表</li>
      </ol>
    </nav>
    <div class="bg-light">
        <div class="container-floid">
            <div class="row mx-auto">
                <div class="col-12 col-sm-2">
                    <form action="<%=request.getContextPath()%>/front-end/secondShop/ProductforSelect" method="post"">
                        <div class="input-group p-1">
                            <input type="text" class="form-control" placeholder="請輸入關鍵字" name="PRODUCT_NAME">
                            <span class="input-group-btn">
                                <button class="btn btn-danger" type="submit">
                                    <i class="fas fa-search"></i>
                                </button>
                            </span>
                        </div>
                        <input type="text" class="hidden" name="action" value="selectProductName">
                        <input type="hidden" name="requestURL"  value="<%=request.getServletPath()%>">
                        <div class="card">
                            <div class="card-body menubar">
                                <h5 class="card-title">條件篩選</h5>
                                <h6 class="card-subtitle mb-2 text-muted">商品銷售類型</h6>
                                <div class="custom-control custom-checkbox">
                                    <div class="pretty p-icon p-curve p-tada">
                                        <input type="radio" name="PRODUCT_SALE_TYPE" value="直購">
                                        <div class="state p-primary-o">
                                          <i class="icon fas fa-check"></i>
                                            <label>直購</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="custom-control custom-checkbox">
                                    <div class="pretty p-icon p-curve p-tada">
                                        <input type="radio" name="PRODUCT_SALE_TYPE" value="競標">
                                        <div class="state p-primary-o">
                                          <i class="icon fas fa-check"></i>
                                            <label>競標</label>
                                        </div>
                                    </div>
                                </div>
                                <h6 class="card-subtitle mb-2 mt-2 text-muted">商品類別</h6>
                                <div class="custom-control custom-checkbox">
                                    <div class="pretty p-icon p-curve p-tada">
                                        <input type="radio" name="PRODUCT_TYPE" value="單車">
                                        <div class="state p-primary-o">
                                          <i class="icon fas fa-check"></i>
                                            <label>單車</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="custom-control custom-checkbox">
                                    <div class="pretty p-icon p-curve p-tada">
                                        <input type="radio" name="PRODUCT_TYPE" value="配件">
                                        <div class="state p-primary-o">
                                          <i class="icon fas fa-check"></i>
                                            <label>配件</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="custom-control custom-checkbox">
                                    <div class="pretty p-icon p-curve p-tada">
                                        <input type="radio" name="PRODUCT_TYPE" value="其他">
                                        <div class="state p-primary-o">
                                          <i class="icon fas fa-check"></i>
                                            <label>其他</label>
                                        </div>
                                    </div>
                                </div>
                                <h6 class="card-subtitle mb-2 mt-2 text-muted">商品使用狀態</h6>
                                <div class="custom-control custom-checkbox">
                                    <div class="pretty p-icon p-curve p-tada">
                                        <input type="radio" name="PRODUCT_Status" value="全新">
                                        <div class="state p-primary-o">
                                          <i class="icon fas fa-check"></i>
                                            <label>全新</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="custom-control custom-checkbox">
                                    <div class="pretty p-icon p-curve p-tada">
                                        <input type="radio" name="PRODUCT_Status" value="二手">
                                        <div class="state p-primary-o">
                                          <i class="icon fas fa-check"></i>
                                            <label>二手</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="card mt-2">
                      <div class="card-body menubar">
                        <h5 class="card-title">瀏覽紀錄</h5>
                        <% 
                        if(browserRecList.size() != 0){
                        	for(ProductVO productVO : browserRecList){
                        %>	
                       		<p>
	                       		<a class="text-dark" 
	                       			href="<%=request.getContextPath()%>/ProductServlet?productId=<%=productVO.getProductId()%>&action=getOneForView&memId=${memVO.mem_id}">
	                       			<%=productVO.getProductName()%>
	                       		</a>
                       		</p>
                        <%}}%>  
                        
                      </div>
                    </div>
                </div>
                <div class="col-12 col-sm-10 mt-1">
                    <div class="card-deck  ">
                        <%@ include file="/sources/file/secondShop/list.file"%>    
                    <c:forEach var="productVO" items="${productListbyDataStatus}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
                        <div class="col-sm-3 col-vh50 mt-3 pl-0 pr-0 list">
                            <div class="card hiih  ${(productVO.productAd == null)? '' :'boxTop'}">
                                    <a href="<%=request.getContextPath()%>/ProductServlet?productId=${productVO.productId}&action=getOneForView&memId=${memVO.mem_id}" class="text-center" style="height: 35%">
                                    <img class="mt-2 shodd" alt="Card image cap" src="<%=request.getContextPath()%>/ProductPhotoServlet?productId=${productVO.productId}&action=photoForOne" style="height:100%">
                                </a>
                                <div class="card-body " style="height: 45%">
                                    <div class="productNameDiv">      
                                        <p class="font-smll">${productVO.productName}</p>
                                 	</div>
                                    <p class="card-text">
                                        <div class="row">
                                            <div class="col-6">
                                                <label class="text-white border rounded shodd pl-1 pr-1 ${(productVO.productSaleType=='直購')? 'bg-danger':'bg-grayy'}">直購</label>
                                                <label class="text-white border rounded shodd pl-1 pr-1 ${(productVO.productSaleType=='競標')? 'bg-warning':'bg-grayy'}">競標</label>
                                            </div>
                                            <div class="col-6 text-right">
                                            <span class="no_pricestr">NT$</span>
                                            <label class="no_pricenum">${productVO.productPrice}</label>  
                                            </div>
                                        </div>
                                    </p>  
                                </div>
                                <div class="card-footer">
                                    <div class="row">
                                        <div class="col-6 text-left">
                                        <% 
                                        ProductVO productVO =  (ProductVO)pageContext.getAttribute("productVO");
                                        MemVO MemvofoNick =new MemService().findMemById(productVO.getMemIdSale()); %>
                                        <p class="font-mm"><%=MemvofoNick.getMem_nickname()%>賣場</p>

                                        </div>
                                        <div class="col-sm-2" name="forMessage"  data-Nick="<%=MemvofoNick.getMem_nickname()%>" data-memId="<%=MemvofoNick.getMem_id()%>" data-toggle="modal" data-target="#forMessage">
                                            <i class="fas fa-envelope"></i>
                                        </div>
                                        <div class="col-4 text-right">
                                            <%
                                           
                                            ProductFavoriteVO  pfVO = new ProductFavoriteService().findOneByPK(memVO.getMem_id(), productVO.getProductId()); 
                                            //ProductFavoriteVO  pfVO = new ProductFavoriteVO();
                                            //for(ProductVO productforId :productListbyDataStatus){
                                            //	pfVO = new ProductFavoriteService().findOneByPK(memVO.getMem_id(), productforId.getProductId()); 
                                            //	System.out.println("到底有沒有變心啊改成用JSP取直囉"+pfVO);
                                            //}
                                            %>
                                            <img class="favorite list" data-like="<%= (pfVO==null)? "no":"yes" %>"
                                            src="<%=request.getContextPath()%>/sources/icon/secondShop/<%= (pfVO==null)? "like-1.png":"heart.png" %>" 
                                            data-productId="${productVO.productId}">
                                            <% pfVO = null; %>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                       </c:forEach>
                    </div>
                     <%@ include file="/sources/file/secondShop/productPag.file"%>
                </div>
            </div>
        </div>
    </div>

    <!-- Button trigger modal -->
<!--     <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#forMessage">
      Launch demo modal
    </button> -->

    <!-- Modal -->
<form method="post"  action="<%=request.getContextPath()%>/front-end/mem/mes.do">
    <div class="modal fade" id="forMessage" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalCenterTitle"></h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>

          <div class="modal-body">
            <div class="form-group row">
              <label for="staticEmail" class="col-sm-4 col-form-label">信件主旨</label>
              <div class="col-sm-8">
                <input type="text"  class="form-control" id="titleMessage" name="Chat_title">
              </div>
            </div>

           <div class="row">
               <div class="col-sm-4">信件內容</div>
               <div class="col-sm-8">
                    <textarea class="form-control" style="height: 100px" name="Chat_text"></textarea>
                </div>
           </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-secondary" id="messageforClick">送出</button>
            <input type="hidden" name="sendMes" value="sendMes">
            <input type="hidden" name="receive_ac" value="" id="receive_ac">
            <input type="hidden" name="login_ac" value="" id="login_ac">
          </div>
        </div>
      </div>
    </div>
</form>
<script src="https://code.jquery.com/jquery-3.2.1.min.js "></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
 <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>

<script type="text/javascript">
    $('div[name=forMessage]').click(function(){
       var toMessageMemId= $(this).attr('data-memId');
       var formMessageNick ='${memVO.mem_nickname}'
       var toMessageNick=$(this).attr('data-Nick');
       $('input[name=receive_ac]').val(toMessageNick)
       $('input[name=login_ac]').val(formMessageNick)
       $('#exampleModalCenterTitle').html('').append('To：'+toMessageNick)


       
    })

    $('.favorite').click(function(){
        var productId = $(this).attr('data-productId');
        var favoriteAjax =$(this).attr('data-like');
        var memId = "${memVO.mem_id}"

        if(favoriteAjax=="no"){
          $.ajax({
            type:"POST",
            url:"<%=request.getContextPath()%>/ProductFavoriteServlet",
            data:{"productId":productId,"memId":memId,"action":"addFavorite"},
            dataType:"json",
            success:function(data){
                $('img[data-productId='+data.productId+']').attr('src','<%=request.getContextPath()%>/sources/icon/secondShop/heart.png').attr('data-like','yes');

                Command: toastr["success"]("加入收藏成功")
                toastr.options = {
                  "closeButton": false,
                  "debug": false,
                  "newestOnTop": true,
                  "progressBar": false,
                  "positionClass": "toast-top-right",
                  "preventDuplicates": false,
                  "onclick": null,
                  "showDuration": "300",
                  "hideDuration": "1000",
                  "timeOut": "2000",
                  "extendedTimeOut": "1000",
                  "showEasing": "swing",
                  "hideEasing": "linear",
                  "showMethod": "fadeIn",
                  "hideMethod": "fadeOut"
                }
            },
            error:function(){
              alert("加入收藏發生錯誤")
            }
          })
        }


        if(favoriteAjax=="yes"){
          $.ajax({
            type:"POST",
            url:"<%=request.getContextPath()%>/ProductFavoriteServlet",
            data:{"productId":productId,"memId":memId,"action":"deleteFavorite"},
            dataType:"json",
            success:function(data){
                $('img[data-productId='+data.productId+']').attr('src','<%=request.getContextPath()%>/sources/icon/secondShop/like-1.png').attr('data-like','no');

                Command: toastr["error"]("取消收藏成功")
                toastr.options = {
                  "closeButton": false,
                  "debug": false,
                  "newestOnTop": true,
                  "progressBar": false,
                  "positionClass": "toast-top-right",
                  "preventDuplicates": false,
                  "onclick": null,
                  "showDuration": "300",
                  "hideDuration": "1000",
                  "timeOut": "2000",
                  "extendedTimeOut": "1000",
                  "showEasing": "swing",
                  "hideEasing": "linear",
                  "showMethod": "fadeIn",
                  "hideMethod": "fadeOut"
                }
            },
            error:function(){
              alert("取消收藏發生錯誤")
            }
          })
        }
    })
</script>
</body>

</html>