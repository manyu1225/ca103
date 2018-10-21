<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.secondShop.currency.controller.*"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.secondShop.currency.controller.*"%>
<%@ page import="com.secondShop.currencyCheackout.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% 
	CurrencyCheackoutService currencyCheackoutSvc = new CurrencyCheackoutService();
	List<CurrencyCheackoutVO> currencyckt = currencyCheackoutSvc.currencyCheackoutList();
	pageContext.setAttribute("currencyckt",currencyckt);
	List<CurrencyCheackoutVO> currencyUnconfirm = currencyCheackoutSvc.currencyCheackoutList(0);
	pageContext.setAttribute("currencyUnconfirm",currencyUnconfirm);
	List<CurrencyCheackoutVO> currencyConfirm = currencyCheackoutSvc.currencyCheackoutList(1);
	pageContext.setAttribute("currencyConfirm",currencyConfirm);
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

    <title>Hello, world!</title>
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
  	    <li class="breadcrumb-item active" aria-current="page">提領審核</li>
  	  </ol>
  	</nav>
    <div class="">
    	<div class="row">
    		<%@ include file="/sources/file/Home/backSidebar.file"%>

    	<div class="col-10">
          <h2 class="text-center bg-warning p-4 text-white">
           <img src="<%=request.getContextPath()%>/sources/icon/secondShop/check.png"> 自幣提領審核</h2>
            <ul class="nav nav-tabs" id="myTab" role="tablist">
              <li class="nav-item">
                <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">全部</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">未審核</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">已審核</a>
              </li>
            </ul>
            <div class="tab-content" id="myTabContent">
              <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">      <table id="example" class="table table-striped table-hover" style="width:100%" >
                    <thead>
                        <tr>
                            <th><img class="img-fluid"src="<%=request.getContextPath()%>/sources/icon/secondShop/receipt.png"/></th>
                            <th>審核提領</th>
                            <th>提領日期</th>
                            <th>會員編號</th>
                            <th>提領金額</th>
                            <th>狀態</th>
                        </tr>
                    </thead>
                    <tbody>
                   <c:forEach var="currencyCheackoutVO" items="${currencyckt}">
                        <tr>
                            <td><i class="fa fa-star"></i></td>
                            <td class="font-currency">
                        <form action="<%=request.getContextPath()%>/currencyCheackoutServlet" method="Post">
                             <c:if test="${currencyCheackoutVO.cheackoutStatus!=1}">
                                <button type="submit" class="btn btn-primary">確認</button>
                            </c:if>
                            <input class="hidden" type="text" name="currencyId" value="${currencyCheackoutVO.currencyId}">
                            <input class="hidden" type="text" name="action" value="currencyCheackoutDone">
                            <input class="hidden" type="text" name="requestURL" value="<%=request.getServletPath()%>">
                        </form>
                            </td>
                            <td><p class="font-currency">
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${currencyCheackoutVO.cheackoutDate}"/>
                                </p>
                            </td>
                            <td class="font-currency">
                                <p>${currencyCheackoutVO.memId}</p>
                            </td>
                            <td class="font-currency text-right">
                                <p>${currencyCheackoutVO.cheackoutBalance}</p>
                            </td>
                             <td class="font-currency text-right">
                                 <p>${(currencyCheackoutVO.cheackoutStatus=='0')? '未審核' :'已審核'}</p>
                            </td>
                       </tr>
                  </c:forEach>
                    </tbody>
                </table></div>
              <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                    <table id="example1" class="table table-striped table-hover" style="width:100%" >
                    <thead>
                        <tr>
                            <th><img class="img-fluid"src="<%=request.getContextPath()%>/sources/icon/secondShop/receipt.png"/></th>
                            <th>審核提領</th>
                            <th>提領日期</th>
                            <th>會員編號</th>
                            <th>提領金額</th>
                            <th>狀態</th>
                        </tr>
                    </thead>
                    <tbody>
                   <c:forEach var="currencyCheackoutVO" items="${currencyUnconfirm}">
                        <tr>
                            <td><i class="fa fa-star"></i></td>
                            <td class="font-currency">
                        <form action="<%=request.getContextPath()%>/currencyCheackoutServlet" method="Post">
                             <c:if test="${currencyCheackoutVO.cheackoutStatus!=1}">
                                <button type="submit" class="btn btn-primary">確認</button>
                            </c:if>
                            <input class="hidden" type="text" name="currencyId" value="${currencyCheackoutVO.currencyId}">
                            <input class="hidden" type="text" name="action" value="currencyCheackoutDone">
                            <input class="hidden" type="text" name="requestURL" value="<%=request.getServletPath()%>">
                        </form>
                            </td>
                            <td><p class="font-currency">
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${currencyCheackoutVO.cheackoutDate}"/>
                                </p>
                            </td>
                            <td class="font-currency">
                                <p>${currencyCheackoutVO.memId}</p>
                            </td>
                            <td class="font-currency text-right">
                                <p>${currencyCheackoutVO.cheackoutBalance}</p>
                            </td>
                             <td class="font-currency text-right">
                                 <p>${(currencyCheackoutVO.cheackoutStatus=='0')? '未審核' :'已審核'}</p>
                            </td>
                       </tr>
                  </c:forEach>
                    </tbody>
                </table></div>
              <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
                    <table id="example2" class="table table-striped table-hover" style="width:100%" >
                    <thead>
                        <tr>
                            <th><img class="img-fluid"src="<%=request.getContextPath()%>/sources/icon/secondShop/receipt.png"/></th>
                            <th>審核提領</th>
                            <th>提領日期</th>
                            <th>會員編號</th>
                            <th>提領金額</th>
                            <th>狀態</th>
                        </tr>
                    </thead>
                    <tbody>
                   <c:forEach var="currencyCheackoutVO" items="${currencyConfirm}">
                        <tr>
                            <td><i class="fa fa-star"></i></td>
                            <td class="font-currency">
                        <form action="<%=request.getContextPath()%>/currencyCheackoutServlet" method="Post">
                             <c:if test="${currencyCheackoutVO.cheackoutStatus!=1}">
                                <button type="submit" class="btn btn-primary">確認</button>
                            </c:if>
                            <input class="hidden" type="text" name="currencyId" value="${currencyCheackoutVO.currencyId}">
                            <input class="hidden" type="text" name="action" value="currencyCheackoutDone">
                            <input class="hidden" type="text" name="requestURL" value="<%=request.getServletPath()%>">
                        </form>
                            </td>
                            <td><p class="font-currency">
                                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${currencyCheackoutVO.cheackoutDate}"/>
                                </p>
                            </td>
                            <td class="font-currency">
                                <p>${currencyCheackoutVO.memId}</p>
                            </td>
                            <td class="font-currency text-right">
                                <p>${currencyCheackoutVO.cheackoutBalance}</p>
                            </td>
                             <td class="font-currency text-right">
                                 <p>${(currencyCheackoutVO.cheackoutStatus=='0')? '未審核' :'已審核'}</p>
                            </td>
                       </tr>
                  </c:forEach>
                    </tbody>
                </table></div>
            </div>

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
        $('#example1').DataTable({
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
        $('#example2').DataTable({
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