<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.secondShop.currency.controller.*"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.secondShop.currency.controller.*"%>
<%@ page import="com.secondShop.productReport.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% 
	ProductReportService productReportSvc = new ProductReportService();
	List<ProductReportVO> productReportlist = productReportSvc.allProductReport();
	pageContext.setAttribute("productReportlist",productReportlist);
%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">

    <title>自轉車管理後台</title>
  </head>
  <style type="text/css">
      .hidden{
        display: none;
    }
    	.cardclor{
      background-color: #007bff;
    }
  </style>
<body>
    <%@ include file="/sources/file/Home/backNav.file"%>

  	<nav aria-label="breadcrumb">
  	  <ol class="breadcrumb">
  	    <li class="breadcrumb-item"><a href="#">Home</a></li>
  	    <li class="breadcrumb-item active" aria-current="page">商品檢舉審核</li>
  	  </ol>
  	</nav>
    <div class="">
    	<div class="row">
    		<%@ include file="/sources/file/Home/backSidebar.file"%>
    		<div class="col-10">
          <h2 class="text-center bg-warning p-4 text-white">
           <img src="<%=request.getContextPath()%>/sources/icon/secondShop/check.png">商品檢舉審核</h2>
    			<table id="example" class="table table-striped table-hover" style="width:100%" >
    			    <thead>
    			        <tr>
    			            <th><img class="img-fluid"src="<%=request.getContextPath()%>/sources/icon/secondShop/receipt.png"/></th>
    			            <th>審核檢舉</th>
    			            <th>檢舉日期</th>
    			            <th>會員編號</th>
    			            <th>檢舉理由</th>
                            <th>商品編號</th>
    			            <th>狀態</th>
    			        </tr>
    			    </thead>
    			    <tbody>
    			   <c:forEach var="productReportVO" items="${productReportlist}">
    			        <tr>
    			            <td><i class="fa fa-star"></i></td>
    			            <td class="font-currency">
                                <div class="row">
                               		 <c:if test="${productReportVO.reportStatus==0}">
                                     <form action="<%=request.getContextPath()%>/front-end/secondShop/ProductReport" method="post">
                                        <input class="hidden" type="text" name="action" value="reverseReport">
                                        <input class="hidden" type="text" name="reportId" value="${productReportVO.reportId}">
                                        <input class="hidden" type="text" name="requestURL" value="<%=request.getServletPath()%>">
                                		<button type="submit" class="btn btn-primary mr-2">駁回</button>
                                    </form>
                                     <form action="<%=request.getContextPath()%>/front-end/secondShop/ProductReport" method="post">
                                        <input class="hidden" type="text" name="action" value="getReport">
                                        <input class="hidden" type="text" name="reportId" value="${productReportVO.reportId}">
                                        <input class="hidden" type="text" name="productId" value="${productReportVO.productId}">
                                        <input class="hidden" type="text" name="memId" value="${productReportVO.memId}">
                                        <input class="hidden" type="text" name="requestURL" value="<%=request.getServletPath()%>">
                                        <button type="submit" class="btn btn-danger">下架</button>
                                    </form>
                                	</c:if>
                                </div>
    			            </td>
    			            <td><p class="font-currency">
    			                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${productReportVO.reportDate}"/>
    			                </p>
    			            </td>
    			            <td class="font-currency">
                        		<p>${productReportVO.memId}</p>
    			            </td>
    			            <td class="font-currency text-left">
    			                <p>${productReportVO.reportDetailed}</p>
    			            </td>
                            <td class="font-currency text-left">
                               <a href="<%=request.getContextPath()%>/ProductServlet?productId=${productReportVO.productId}&action=getOneForView" target="_blank"> 
                               <p>${productReportVO.productId}</p></a>
                            </td>
    			             <td class="font-currency text-right">
       							 <p class="text-primary">${(productReportVO.reportStatus=='0')? '未審核' :''}</p>
                                 <p class="text-danger">${(productReportVO.reportStatus=='1')? '有效檢舉' :''}</p>
                                 <p class="text-secondary">${(productReportVO.reportStatus=='2')? '無效檢舉' :''}</p>

    			            </td>
    			       </tr>
    			  </c:forEach>
    			    </tbody>
    			</table>
    		</div>
    	</div>
    </div>



    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
	<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js " crossorigin="anonymous"></script>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">
    <script type="text/javascript">
	    $(document).ready(function(){

	    $('#example').DataTable({
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