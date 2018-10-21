<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.forPos.model.*"%>

													<!-- 此處為會員個人管理的頁面 -->



<%-- <%Forum_post_VO forPosVO = (Forum_post_VO) request.getAttribute("forPosVO");%> --%>






<%
//真實會員的創建
	MemVO memVO = (MemVO)session.getAttribute("memVO");


if(memVO == null){
	String url = "/front-end/mem/Login.jsp";

	response.sendRedirect(request.getContextPath() + url);
}

%>



<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
	Forum_post_Service forPosSvc = new Forum_post_Service();
	List<Forum_post_VO> list = forPosSvc.getAllForPosByMem_id(memVO.getMem_id());
	pageContext.setAttribute("list", list);
	
	
	System.out.println("list-------"+ list);
%>

<jsp:useBean id="forClass_Svc" scope="page" class="com.forPos_class.model.Forum_class_Service" />
	
	


<%

Forum_post_Service forPosSvc2 = new Forum_post_Service();




List<Forum_post_VO> view1013 = forPosSvc2.getViewByDate("2018-10-16");

List<Forum_post_VO> view1014 = forPosSvc2.getViewByDate("2018-10-17");  
System.out.println("view1013---" + view1013);

List<Forum_post_VO> view1015 = forPosSvc2.getViewByDate("2018-10-18");
List<Forum_post_VO> view1016 = forPosSvc2.getViewByDate("2018-10-19");



Integer viewNum1013 = 0;
for(Forum_post_VO forPOsVO:view1013){
	 viewNum1013=viewNum1013 + forPOsVO.getForPost_view();

}

Integer viewNum1014 = 0;
for(Forum_post_VO forPOsVO:view1014){
	 viewNum1014=viewNum1014 + forPOsVO.getForPost_view();

}


Integer viewNum1015 = 0;
for(Forum_post_VO forPOsVO:view1015){
	 viewNum1015=viewNum1015 + forPOsVO.getForPost_view();

}

Integer viewNum1016 = 0;
for(Forum_post_VO forPOsVO:view1016){
	 viewNum1016=viewNum1016 + forPOsVO.getForPost_view();

}


System.out.println("viewNummmmmmmmmmmmmmmmm=" + viewNum1013);
System.out.println("viewNummmmmmmmmmmmmmmmm=" + viewNum1014);
System.out.println("viewNummmmmmmmmmmmmmmmm=" + viewNum1015);
System.out.println("viewNummmmmmmmmmmmmmmmm=" + viewNum1016);

%>



<!-- List<Forum_post_VO> date = forPost_Svc.getViewByDate(); -->



<!doctype html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
	
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />	
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">





<style type="text/css">
h1 {
	font-family: 微軟正黑體;
}

td{
	font-family: 微軟正黑體;
}

 th{
 
 	font-family: 微軟正黑體;
 
 }

/* .modal-title{ */

/* font-size:15px; */

/* } */

a {
    color: #000000;
    text-decoration: none;
    background-color: transparent;
}


.bg-circle-outline a, a:hover, .media a:focus {
    text-decoration: none !important;
    outline: none;
    color: #6495ED;
}

 





</style>

<title>我的文章管理</title>
</head>

<body style="background-color:#e6e6e6">

<%@ include file="/sources/file/Home/NavBar.file" %>

	<div class="container-fluid">
		<div class="row">
		
<!-- 		這是標籤  分類查詢的list group -->
		<div class="col-md-3 col-12 mt-5">
			<%@ include file="/sources/file/Forum/forPosSidebar.file" %>
			

   		
			<canvas id="myChart"  height="200px"></canvas>



		</div>
		
		
			<div class="col-md-9 col-12 mt-5 ">
				<h1>會員文章管理</h1>

			<table class="table table-hover  ">
				<thead class="table-fix">
						<tr class="table-fix2">
							<th>張貼時間</th>
							<th>文章名稱</th>
							<th>文章狀態</th>
<!-- 							<th>留言數</th> -->
							<th>觀看次數</th>
							<th>全站分類</th>
							<th>編輯</th>
							<th>刪除</th>
						</tr>
					</thead>
				<tbody>
						<%@ include file="page1.file"%>
						
						<c:forEach var="forPosVO" items="${list}" begin="<%=pageIndex%>"
							end="<%=pageIndex+rowsPerPage-1%>">
						<tr>
<%--  						<jsp:useBean id="forPosVO" scope="page" class="java.util.Date" /> --%>
							
								<td><fmt:formatDate value="${forPosVO.forPost_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><a href="<%=request.getContextPath()%>/forPos/forPos.do?forPost_ID=${forPosVO.forPost_ID}&action=getOnePos_onPage&forPost_state=${forPosVO.forPost_state}&mem_ID=${forPosVO.mem_ID}&forPost_view=${forPosVO.forPost_view}&action=getHashTag">${forPosVO.forPost_theme}</a></td>
								<td>${forPosVO.forPost_state}</td>
								<td>${forPosVO.forPost_view}</td>
								<%-- 							<td>${ }</td> --%>
								<%-- 							<td>${ }</td> --%>
								<%-- 							<td>${ }</td> --%>
								<%-- 							<td>${ }</td> --%>
								<%-- 							<td>${ }</td> --%>
								<td>
									${forClass_Svc.getOneClass(forPosVO.forClass_ID).forClass_name}
								</td>
								
								<td>
									<form method="post" action="<%=request.getContextPath()%>/forPos/forPos.do;">
										<input class="btn btn-warning" type="submit" value="編輯">
										<input type="hidden" name="forPos_ID" value="${forPosVO.forPost_ID}">
										<input type="hidden" name="action"	value="getOne_For_Update">
										<input type="hidden" name="forPost_view" value="${forPosVO.forPost_view}">
										<!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
									</form>
								</td>
							
								<td> 
									<button data-target="#A${forPosVO.forPost_ID}" data-toggle="modal" class="btn btn-warning" type="button">刪除</button>	
									
								</td>
			 </tr>
						</c:forEach>
				</tbody>		
			</table>
	
		<c:forEach var="forPosVO" items="${list}">	
				<!-- Modal -->
				
				
			<form method="post" action="<%=request.getContextPath()%>/forPos/forPos.do" >
				<div class="modal fade" id="A${forPosVO.forPost_ID}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">您確定要刪除 【${forPosVO.forPost_theme}】</h5>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							
		
							</div>
							<div class="modal-body">
								一旦刪除便無法回復
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary" data-dismiss="modal">返回</button>
								<input type="hidden" name="forPos_ID" value="${forPosVO.forPost_ID}">
									<input type="hidden" name="action" value="delete">
								<button type="submit"  class="btn btn-danger">刪除</button>
								<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller--><!-- 目前尚未用到  -->
							</div>
						</div>
					</div>
				</div>
			</form>
			</c:forEach>		
			
	
				<%@ include file="page2.file"%>

			</div>
		</div>
	</div>
	
	


	
	
	
	
	


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.min.js"></script>
   	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.min.js"></script>


<!--     <script type="text/javascript"> -->
<!--      	$('#myList a').on('click', function (e) { -->
<!--    e.preventDefault() -->
<!--    $(this).tab('show') -->
<!--  }) -->
<!--     </script> -->
<script type="text/javascript">


var viewNum1013 =<%=viewNum1013%>
var viewNum1014 =<%=viewNum1014%>
var viewNum1015 =<%=viewNum1015%>
var viewNum1016 =<%=viewNum1016%>

 console.log(viewNum1016);
	
var ctx = document.getElementById('myChart');
var myChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: ['10/16','10/17','10/18','10/19'],
    datasets: [{
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)'
      ],
      borderColor: [
        'rgba(255,99,132,1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)'
      ],
      borderWidth: 1,
      label: '點擊次數',
      data: [viewNum1013, viewNum1014, viewNum1015, viewNum1016]
    }]
  }
});




</script>


</body>

</html>
