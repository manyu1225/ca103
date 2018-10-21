<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.secondShop.productDelivery.model.*"%>
<%@ page import="com.secondShop.productDeliveryAddrData.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.secondShop.product.model.*"%>
<%@ page import="com.secondShop.productBidding.model.*"%>
<%@ page import ="com.secondShop.product.controller.*"%>

<html lang="en">
<%  

	MemVO memVO = (MemVO)session.getAttribute("memVO");
	String memId= memVO.getMem_id();  
    BiddingService BiddingSvc = new BiddingService();   
    List<BiddingVO> biddingVO= BiddingSvc.allBiddingprice(memId);
    pageContext.setAttribute("biddingVO", biddingVO);

%>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/secondShop/productmem.css">
     <link rel="icon" href="<%=request.getContextPath()%>/sources/icon/secondShop/cycling.png" type="image/x-icon" />
     <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">
    <title>自転車買家中心</title>
</head>

<body class="bodycolor">
<%@ include file="/sources/file/Home/NavBar.file" %>
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item"><a href="#">Home</a></li>
      <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/productMem-sale.jsp">買家中心</a></li>
      <li class="breadcrumb-item active" aria-current="page">我的競標紀錄</li>
    </ol>
  </nav>
  <div class="bg-light">
    <div class="container-floid mt-4" id="productlistbody">
      <div class="row mx-auto">
          <%@ include file="/sources/file/secondShop/productSideBar.file" %> 
        <div class="col-12 col-sm-10 mt-4">
          <h2 class="text-center bg-warning p-4 text-white">
            <img src="<%=request.getContextPath()%>/sources/icon/secondShop/auction-48.png"> 我的競標紀錄
          </h2>
          <table id="example" class="table table-striped table-hover" style="width:100% background:#FFF ">
            <thead>
              <tr>
                <th><img class="img-fluid"src="<%=request.getContextPath()%>/sources/icon/secondShop/auctionlist-32.png"/></th>
                <th>出價時間</th>
                <th>商品名稱</th>
                <th>我的出價</th>
                <th>目前最高價</th>
                <th>結束時間</th>
              </tr>
            </thead>
            <tbody>
				<% 
				java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
				String productAddBidding = null;
				String productEndBidding =null;
				BiddingVO biddingfotTop = new BiddingVO();
				for(BiddingVO biddingList: biddingVO){
					productAddBidding = df.format(biddingList.getBiddingDate());
					if(BiddingSvc.biddingBenefitMem(biddingList.getProductVO().getProductId()) !=null){
						biddingfotTop = BiddingSvc.biddingBenefitMem(biddingList.getProductVO().getProductId());
					}	
					if(biddingList.getProductVO().getProductEndBidding()!=null){
						productEndBidding = df.format(biddingList.getProductVO().getProductEndBidding()); 
					}
				%>
				<tr>
				  <td><i class="fa fa-star"></i></td>
                  <td><%=productAddBidding%></td>
                  <td><a href="<%=request.getContextPath()%>/ProductServlet?productId=<%=biddingList.getProductVO().getProductId()%>&action=getOneForView">
                  		<p class="word-wrap: break-word"><%=biddingList.getProductVO().getProductName()%></p></a></td>
                  <td><%=biddingList.getBiddingPrice()%></td>
                  <td><%=biddingfotTop.getBiddingPrice()%></td>
                  <td><%=(productEndBidding == null ? "競標結束" : productEndBidding)%> </td>
                </tr>
                <%}%>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
  
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js" crossorigin="anonymous"></script>
<script type="text/javascript">
  $(document).ready(function(){
	    $('#example').DataTable({
        "columns":[
           {"orderable":false},
           null,
           {"orderable":false},
           null,
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

</script>
</body>

</html>