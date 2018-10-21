<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.secondShop.productFavorite.model.*"%>
<%@ page import="com.secondShop.product.model.*"%>
<%@ page import="com.mem.model.*"%>
<html lang="en">
<%  

	 /* 14-15模擬會員已登入,送回來的memVO */
    MemVO memVO = (MemVO)session.getAttribute("memVO");
    String memId= memVO.getMem_id();  
    ProductFavoriteService  productFavoriteSvc = new ProductFavoriteService();
    List<ProductFavoriteVO> productFavoriteList = productFavoriteSvc.allFavorite(memId);
    pageContext.setAttribute("productFavoriteList", productFavoriteList);
    
%>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">  
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/secondShop/productmem.css">
     <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">
     <link rel="icon" href="<%=request.getContextPath()%>/sources/icon/secondShop/cycling.png" type="image/x-icon" />
     <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet"  />

    <title>我的收藏商品</title>
</head>

<body class="bodycolor">
<%@ include file="/sources/file/Home/NavBar.file"%>
     <nav aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Home</a></li>
        <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/productMem-sale.jsp">買家中心</a></li>
        <li class="breadcrumb-item active" aria-current="page">我的收藏商品</li>
      </ol>
    </nav>
    <div class="bg-light">
        <div class="container-floid mt-4" id="productlistbody">
            <div class="row mx-auto">
          <%@ include file="/sources/file/secondShop/productSideBar.file" %> 
                <div class="col-12 col-sm-10 mt-4">
                       <h2 class="text-center bg-danger p-4 text-white">
                        <img src="<%=request.getContextPath()%>/sources/icon/secondShop/hearts.png"> 我的收藏商品</h2>
                            <table id="example" class="table table-striped table-hover" style="width:100%" >
                                <thead>
                                    <tr>
                                        <th>
                                        </th>
                                        <th>日期</th>
                                        <th>商品名稱</th>
                                     
                                        <th>售價</th>
                                    </tr>
                                </thead>
                                <tbody>
								 <% 
								 for(ProductFavoriteVO productFavoritelist : productFavoriteList){
									 ProductService productSvc =new ProductService();
									 String productId = productFavoritelist.getProductId();
									 ProductVO productVO = productSvc.findProductByPK(productId);	 
									  java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
			                          String productUpdateDate = df.format(productVO.getProductUpdateDate());
                                %>                      
                                    <tr name="<%=productVO.getProductId()%>">
                                        <td >
                                            <img class="favorite" data-like="<%=productVO.getProductId()%>"src="<%=request.getContextPath()%>/sources/icon/secondShop/heart.png">
                                        </td>
                                        <td><%=productUpdateDate%></td>
                                        <td><a href="<%=request.getContextPath()%>/ProductServlet?productId=<%=productVO.getProductId()%>&action=getOneForView"><%=productVO.getProductName()%></a></td>
                                      
                                        <td><%=productVO.getProductPrice()%></td>
                                    </tr>
                                     <% }%> 
                                </tbody>
                            </table>
                    </div>
                </div>
            </div>
          </div>
     </div>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
     <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
     <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
     <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js" crossorigin="anonymous"></script>
     <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>

     <script type="text/javascript">
    $(document).ready(function(){

    $('#example').DataTable({
         "columns":[
            {"orderable":false},
            null,
            {"orderable":false},
            {"orderable":false}
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

      $('.favorite').click(function(){
         var productId = $(this).attr('data-like')
         var memId = "${memVO.mem_id}";
        
          $.ajax({
            type:"POST",
            url:"<%=request.getContextPath()%>/ProductFavoriteServlet",
            data:{"productId":productId,"memId":memId,"action":"deleteFavorite"},
            dataType:"json",
            success:function(data){
            $('tr[name="'+data.productId+'"]').remove()
            // swal({
            //   title: "取消收藏",
            //   text: "商品已經取消收藏",
            //   icon: "success",
            //   button: "Aww yiss!",
            // });
            Command: toastr["info"]("取消收藏成功")

              toastr.options = {
                "closeButton": false,
                "debug": false,
                "newestOnTop": false,
                "progressBar": false,
                "positionClass": "toast-top-right",
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
            },
            error:function(){
              alert("取消收藏")
            }
          })
    })
    
</script>
</body>

</html>