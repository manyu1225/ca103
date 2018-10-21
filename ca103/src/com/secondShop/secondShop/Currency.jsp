<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.secondShop.currency.controller.*"%>
<%@ page import="com.secondShop.currency.model.*"%>
<%@ page import="com.secondShop.product.model.*"%>
<%@ page import="com.mem.model.*"%>
<!doctype html>
<%	
	System.out.println("Currency.jsp");		
	
	MemVO memVO = (MemVO)session.getAttribute("memVO");
	String memId= memVO.getMem_id();  
	System.out.print(memId);
	CurrencyService currencSve = new CurrencyService();
	Integer memCurrecyALLTotal = currencSve.memCurrecyALLTotal(memId);
	List<CurrencyVO> currencylist= currencSve.curencyListMem(memId);
	Integer memCurrecyTotal = currencSve.memCurrecyTotal(memId);

	pageContext.setAttribute("memCurrecyTotal",memCurrecyTotal); 
	pageContext.setAttribute("memCurrecyALLTotal",memCurrecyALLTotal); 
	pageContext.setAttribute("currencylist",currencylist); 
	
%>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/secondShop/currency.css">
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/sources/css/secondShop/productmem.css">

    <style type="text/css">
    </style>
  	<link rel="icon" href="<%=request.getContextPath()%>/sources/icon/secondShop/cycling.png" type="image/x-icon" />
    <title>自転車-自轉幣</title>
    <style type="text/css">
    .bgg{
        background-color: #74a5da;
    }
    </style>
</head>

<body style="background-color: #FFF!important">
<%--  <jsp:include page="/sources/file/nav.file"></jsp:include> --%>
<%@ include file="/sources/file/Home/NavBar.file" %>
       <nav aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Home</a></li>
        <li class="breadcrumb-item active" aria-current="page">自轉幣儲值</li>
      </ol>
    </nav>
    <div class="bg-light bgg">
        <div class="container-floid mt-4 ">
            <div class="row mx-auto">
					<%@ include file="/sources/file/secondShop/productSideBar.file"%>
                <div class="col-12 col-sm-10 bgg">
                    <div class="alert alert-primary mt-3">
                        <div class="row align-items-center">
                           <div class="col-4">
                        		<form action="<%=request.getContextPath()%>/front-end/secondShop/addCurrency" method="post">
                                    <div class="input-group ">
                                        <input type="number" class="form-control" placeholder="請輸入儲值金額" name="currencyBalance"
                                        value="" min="0" max="999999">
                                        <span class="input-group-btn">
                                            <button class="btn btn-warning shodd" type="submit">確認儲值</button>
                                        </span>
                                        <input class="hidden" type="text" name="memId" value="${memVO.mem_id}">
                                        <input class="hidden" type="text" name="action" value="addCurrency">
                                        <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
                                    </div>
                                </form>
                                <div class="mt-3">
                                  <form action="<%=request.getContextPath()%>/front-end/secondShop/outCurrency" method="post">
                                        <div class="input-group ">
                                            <input type="number" class="form-control" placeholder="請輸入提領金額" name="currencyBalance"
                                            value="" min="0" max="999999">
                                            <span class="input-group-btn">
                                                <button class="btn btn-dangers shodd" type="submit">確認提領</button>
                                            </span>
                                            <input class="hidden" type="text" name="memId" value="${memVO.mem_id}">
                                            <input class="hidden" type="text" name="action" value="outCurrency">
                                            <input class="hidden" type="text" name="memCurrecyALLTotal" value="<%=memCurrecyALLTotal%>">
                                            <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
                                        </div>
                                  </form>
                                </div>
                            </div>
                            <div class="col-2">
								<c:if test="${not empty errorMsgs}">
							      <c:forEach var="message" items="${errorMsgs}">
							          <h6>${message}</h6>
							      </c:forEach>
							 	</c:if>
							</div>
                            <div class="col-6 mt-7">
                                <div class="row mx-auto">
                                    <table class="mr-5" cellpadding="5" style="text-align:center;">
        									<tr>
        										<td rowspan="2"> 
                                                    <img class="img-fluid"src="<%=request.getContextPath()%>/sources/icon/secondShop/money-1.png"/>
                                                </td>
        										<td> 
                                                    <p class="font-currencyTop font-weight-bold">目前可用餘額</p>
                                                </td>
        									</tr>
        									<tr>
        										<td> <p class="font-currencyCash text-danger   font-weight-bold">
                                                    <%=memCurrecyALLTotal%></p>
                                                </td>
        									</tr>
        							</table>
        							<table  class="mr-5" cellpadding="5" style="text-align:center;">
        								<tr>
        									<td rowspan="2">
                                            <img class="img-fluid"src="<%=request.getContextPath()%>/sources/icon/secondShop/money-2.png"/>
                                            </td>
        									<td> 
                                                <p class="font-currencyCash font-weight-bold">目前餘額</p>
                                            </td>
        								</tr>
        								<tr>
        									<td>
                                                <p class="font-currencyTop text-danger font-weight-bold">
                                                <%=memCurrecyTotal%></p>
                                            </td>
        								</tr>
        							</table>
                                </div>
                            </div>
                        </div>
                    </div>
                        <table id="example" class="table table-striped table-hover" style="width:100%" >
                            <thead>
                                <tr>
                                    <th><img class="img-fluid"src="<%=request.getContextPath()%>/sources/icon/secondShop/receipt.png"/></th>
                                    <th>日期</th>
                                    <th>描述</th>
                                    <th>金額</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="currencyVO" items="${currencylist}">
                                <tr>
                                    <td><i class="fa fa-star"></i></td>
                                    <td><p class="font-currency">
                                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${currencyVO.currencyChangedate}"/>
                                        </p>
                                    </td>
                                    <td class="font-currency"><p>${currencyVO.currencyDetail}
                                    </p>
                                    </td>
                                    <td class="font-currency text-right">
                                        <p>${currencyVO.currencyBalance}</p>
                                    </td>
                               </tr>
                            </c:forEach>  

                            </tbody>
                        </table>
                </div>

            </div>
        </div>      
    </div>
    
 
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js " crossorigin="anonymous"></script>

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