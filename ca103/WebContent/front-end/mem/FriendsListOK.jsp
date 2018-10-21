<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.mem.controller.*"%>
<%@ page import="com.friends.model.*" %>
<%@ page import="com.friends.controller.*" %>
<%@ page import="java.util.*"%>

<%--
	MemService memSvc = new MemService();
	MemJDBCDAO memJDBCDAO = new MemJDBCDAO();
	List<MemVO> list = new ArrayList<MemVO>();
	MemVO memVO = memJDBCDAO.findByPrimarKey("678mkhyi");
--%>

<%
	MemVO memVO = (MemVO) session.getAttribute("memVO");
	FriendsService frSvc = new FriendsService();
	String getId = memVO.getMem_id();
	List<FriendsVO> list = frSvc.getAll(getId);
	
	pageContext.setAttribute("list", list);	
	
%>
<!doctype html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
<title>自轉車-會員專區</title>

<style type="text/css">
/* div { */
/* 	border-style: solid; */
/* } */
input[type="text"]:disabled {
	background: #FFFFFF;
}
</style>

</head>
<body style="background-color: #CCEEFF;">

<%@ include file="/sources/file/Home/NavBar.file" %>


	<div class="container">
		<div class="row">
			<div class="col-md-3 mt-5" style="padding-top: 90px">
				<div class="list-group" style="border-radius: 50%">
					<div style="background-color:#F7F7F7;border-width: 1px;border-color:#F5F5F5;border-radius:10px;">
						<img class="img-thumbnail"
							src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${memVO.mem_id}" style="border-radius:50px;" width="100">
							<h5 style="border-style: none; display: inline;background-color:#F7F7F7;"><%=memVO.getMem_lastname()%><%=memVO.getMem_firstname()%></h5>
					</div>
					<a
						href="<%=request.getContextPath()%>/front-end/mem/MemberMain.jsp"
						class="list-group-item list-group-item-action">會員資料 </a> 
					<a
						href="<%=request.getContextPath()%>/front-end/mem/MemberMes.jsp"
						class="list-group-item list-group-item-action disabled">站內信</a> 
					<a
						href="<%=request.getContextPath()%>/front-end/mem/FriendsList.jsp"
						class="list-group-item list-group-item-action active">好友清單</a>
				</div>
			</div>

			<div class="col-md-9 mt-5" style="padding-top: 90px;margin-top: 16px;">
					<div class="btn-group" role="group" aria-label="Basic example">
  						<button type="button" class="btn btn-light" onclick="location.href='FriendsList.jsp'">待確認</button>
  						<button type="button" class="btn btn-primary" onclick="location.href='FriendsListOK.jsp'">好友</button>
  						</div>
  						<form action="<%=request.getContextPath()%>/front-end/mem/fr.do" method="post">
				<table>
					<%@ include file="/sources/file/Member/page1.file" %>
					<c:forEach var="list" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<c:if test="${list.fr_status==1}">
					<tr>
						<td><img class="img-thumbnail" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${list.self_id}" width="50" hight="50"></td>
						<td>${list.self_nickname}(${list.self_ac})</td>
						<td>狀態：好友</td>
						<td><button type="submit" class="btn btn-danger" name="self_id" value="${list.self_id}">封鎖</button></td>
					</tr>
					</c:if>
					</c:forEach>
				</table>
				<input type="hidden" name="action" value="getBlock_fr">
				<input type="hidden" name="other_id" value="${memVO.mem_id}">
				</form>
			</div>
		</div>
	</div>
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
	<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
	<script
		src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

</body>
</html>
