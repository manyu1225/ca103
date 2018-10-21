<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.secondShop.product.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import ="com.secondShop.product.controller.*"%>
<%@ page import="com.secondShop.productPhoto.model.*"%>
<%@ page import="com.mem.model.*"%>
<%
  	System.out.print("updateProduct.jsp~~");	
	ProductVO productVO = (ProductVO) request.getAttribute("productVO");
	MemVO memVO = (MemVO)session.getAttribute("memVO");
  	String memId= memVO.getMem_id(); 
	
	ProductPhotoService productPhotSrv = new ProductPhotoService();
	List<ProductPhotoVO> photoList = productPhotSrv.ProductPhotoList(productVO.getProductId());	
	
	
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible " content="IE=edge ">
    <meta name="viewport " content="width=device-width, initial-scale=1.0, shrink-to-fit=no ">
    <title>修改商品>${productVO.productName}</title>
    <link rel="icon" href="<%=request.getContextPath()%>/sources/icon/secondShop/cycling.png" type="image/x-icon"/> 
    <link rel="stylesheet " href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css ">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/secondShop/fileInput/fileinput.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" media="all" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/secondShop/fileInput/theme.css">
    <link rel="stylesheet " type="text/css " href="<%=request.getContextPath()%>/sources/css/secondShop/css.css ">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet"  />
    
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
<%@ include file="/sources/file/Home/NavBar.file" %>
    <div class="container">
        <div class="col-sm-12 formContent ">
            <form action="<%=request.getContextPath()%>/ProductServlet" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <h4 class="mt-3" align="center">修改商品</h4>
                </div>
                
                <div class="form-group">
                   <div class="row" style="height:40vh">
                       <div class="col-sm-4" style="overflow:auto">
                           <table class="table table-hover">
                             <tbody>
								<%for(ProductPhotoVO productPhotoVO:photoList){%>
                               <tr name="<%=productPhotoVO.getPhotoId()%>">
                                 <td>
                                 <img  name="deletePhotoAjax" data-photoId="<%=productPhotoVO.getPhotoId()%>" src="<%=request.getContextPath()%>/sources/icon/secondShop/delete-64.png">
                                 	
                                 </td>
                                 <td>
                                 	<img name="deletePhoto" style="height: 80px" src="<%=request.getContextPath()%>/ProductPhotoServlet?action=findOnePhotoByPhotoId&photoId=<%=productPhotoVO.getPhotoId()%>">
                                 </td>
                               </tr>
                  <%}%>
                               
                             </tbody>
                           </table>
                       </div>
                       <div class="col-sm-8">
                           <div class="form-group">
                                <input id="input-id" type="file" class="file shodd" data-preview-file-type="text" name="productPhoto" multiple>
                           </div>    
                       </div>  
                   </div>
                </div>              
				<div class="form-group">
                    <div class="row mt-5">
                        <div class="col-sm-4">
                            <label>商品銷售模式</label>
                            <div>
                            <c:if test="${productVO.productSaleType == '競標'}">
                             <img src="<%=request.getContextPath()%>/sources/icon/secondShop/auction-48.png">
                            </c:if>
                                 <label>${productVO.productSaleType}</label>
                            </div> 
                        </div>
                         <c:if test="${productVO.productSaleType == '競標'}">
	                        <div class="col-sm-4 BIDDING">
	                            <label>起標價格</label>
	                            <H5>${productVO.productBiddingPrice}</H5>
	                        </div>
	                        <div class="col-sm-4 BIDDING">
	                            <label>競標時間</label>
	                            <H5><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${productVO.productEndBidding}"/></H5>
                      	   </div>
                       </c:if>    
                    </div>
                </div>
                <div class="form-group">
                    <div class="row mt-5">
                        <div class="col-sm-6">
                            <label class="mr-2">商品類別</label>
                            <label class="text-danger">${errorMsgs[productType]}</label> <!-- errorMsgs.productType -->
                            <select class="form-control" name="productType">
                                <option>請選擇</option>
                                <option value="單車"<%=("單車".equals(productVO.getProductType()) ? "selected" : "") %>>單車</option>
                                <option value="配件" <%=("配車".equals(productVO.getProductType()) ? "selected" : "") %>>配件</option>
                                <option value="其他" <%=("其他".equals(productVO.getProductType()) ? "selected" : "") %>>其他</option>
                            </select>
                        </div>
                        <div class="col-sm-6">
                            <label class="mr-2">商品使用狀況</label>
                            <label class="text-danger">${errorMsgs[productStatus]}</label>
                            <select class="form-control" name="productStatus">
                                <option >請選擇</option>
                                <option value="全新" <%= ("全新".equals(productVO.getProductStatus()))? "selected" : ""%>>全新</option>
                                <option value="二手" <%= ("二手".equals(productVO.getProductStatus()))? "selected" : ""%>>二手</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label>商品名稱</label>
                    <label class="text-danger">${errorMsgs[productName]}</label>
                    <input type="text" class="form-control" placeholder="商品名稱" name="productName" value="<%=(productVO.getProductName()==null)? "" : productVO.getProductName() %>">
                </div>
                <div class="form-group">
                    <label class="mr-2">商品價格</label><label class="text-danger">${errorMsgs[productPrice]}</label>
                    <input type="number" class="form-control" placeholder="0" name="productPrice"  min="0" max="999999" value="<%=(productVO.getProductPrice()==null)? "0" : productVO.getProductPrice() %>">
                </div>
                <div class="form-group">
                    <label>商品描述</label>
                    <label class="text-danger">${errorMsgs[productDetail]}</label>
                    <textarea class="form-control" name="productDetail" placeholder="商品描述" style="height: 300px" > <%=(productVO.getProductDetail()==null)? "" : productVO.getProductDetail() %></textarea>
                </div>
                <div class="form-group">
                    <div class="col-12 text-right">
						<button type="submit" class="btn btn-primary active">確認送出</button>
						<input class="hidden" type="text" name="productId" value="${productVO.productId}">
                    </div>
                </div>
                <input class="hidden" type="text" name=productSaleType value="${productVO.productSaleType}">
                <input class="hidden" type="text" name="productBiddingPrice" value="${productVO.productBiddingPrice}">
                <input class="hidden" type="text" name="productEndBidding" value="${productVO.productEndBidding}">
                <input class="hidden" type="text" name="action" value="updateProduct">
                <input class="hidden" type="text" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
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
  <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
  <script src="<%=request.getContextPath()%>/sources/js/secondShop/product.js"></script>
        
  <script type="text/javascript">
    $('#input-id').fileinput({
      theme:"fa",
      language:"zh-TW",
      showUpload:false,
      allowedFileExtensions:["jpg","png","jpeg"],
      maxFileCount:5,
    })

    $(function(){
      $('[data-toggle="tooltip"]').tooltip()
      $('[data-toggle="popover"]').popover({ 
        html: true
      })
    })


    $('img[name="deletePhotoAjax"]').click(function(){
      var photoId = $(this).attr('data-photoId')
          console.log(photoId)
      $.ajax({
        type:"POST",
        url:"<%=request.getContextPath()%>/ProductPhotoServlet",
        data:{"action":"deletePhoto","photoId":photoId},
        dataType:"json",
        success:function(data){
          if(data.AjaxReturn =="OK"){
            Command: toastr["info"]("刪除商品照片成功")
              toastr.options = {
                "closeButton": false,
                "debug": false,
                "newestOnTop": false,
                "progressBar": false,
                "positionClass": "toast-top-left",
                "preventDuplicates": false,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "5000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
              }
             $('tr[name="'+ data.photoId +'"]').remove()
          }
        
        },
        error:function(){
        alert("AJAX-class發生錯誤囉!")
        } //ajax則最後面了 不用加,
      })
    })
  </script>

</body>
</html>