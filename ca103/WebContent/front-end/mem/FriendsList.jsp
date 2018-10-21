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
	Map okMsgs = new LinkedHashMap();
	okMsgs = (Map) request.getAttribute("okMsgs");
	pageContext.setAttribute("list", list);
	pageContext.setAttribute("getId", getId);
	pageContext.setAttribute("okMsgs", okMsgs);
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
<!--      漂亮icon的引用-->
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
			<div class="col-md-3 mt-5" style="padding-top: 90px;">
				<div class="list-group" style="border-radius: 50%">
					<div style="background-color:#F7F7F7;border-width: 1px;border-color:#F5F5F5;border-radius:10px;">
						<img class="img-thumbnail"
							src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${memVO.mem_id}" style="border-radius:50px;" width="100">
							<h5 style="border-style: none; display: inline;background-color:#F7F7F7;"><%=memVO.getMem_lastname()%><%=memVO.getMem_firstname()%></h5>
						
					</div>
					<a  href="<%=request.getContextPath()%>/front-end/mem/MemberMain.jsp"
						class="list-group-item list-group-item-action">會員資料 </a> 
						
					<a	href="<%=request.getContextPath()%>/front-end/mem/MemberMes.jsp"
						class="list-group-item list-group-item-action disabled">站內信</a> 
						
					<a	href="<%=request.getContextPath()%>/front-end/mem/FriendsList.jsp"
						class="list-group-item list-group-item-action active">好友清單</a>
				</div>
			</div>

			<div class="col-md-9 mt-5" style="padding-top: 90px;margin-top: 16px;">
						<div class="btn-group" role="group" aria-label="Basic example">
  						<button type="button" class="btn btn-primary" onclick="location.href='FriendsList.jsp'">待確認</button>
  						<button type="button" class="btn btn-light" onclick="location.href='FriendsListOK.jsp'">好友</button>
  						</div>
				<table>
					<%@ include file="/sources/file/Member/page1.file" %>
					<c:forEach var="list" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<c:if test="${list.fr_status==0}">
					<tr>
						<td><img class="img-thumbnail" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${list.self_id}" width="50" height="50"></td>
						<td class="border border-primary rounded">${list.self_nickname}(${list.self_ac})</td>
						<td class="border border-primary rounded">狀態：待確認</td>
						
						<td>
						<form action="<%=request.getContextPath()%>/front-end/mem/fr.do" name="${list.self_ac}" method="post">
						<button type="submit" class="btn btn-success" name="self_id" value="${list.self_id}">確認</button>
						<input type="hidden" name="other_id" value="${getId}"/>
						<input type="hidden" name="action" value="getConfrim_id"/>
						</form>
						</td>
						
						<td>
						<form action="<%=request.getContextPath()%>/front-end/mem/fr.do" name="${list.self_id}" method="post">
						<button type="submit" class="btn btn-danger" name="self_id" value="${list.self_id}">清除</button>
						<input type="hidden" name="other_id" value="${getId}"/>
						<input type="hidden" name="action" value="cleanInvite"/>
						</form>
						</td>
					</tr>
					</c:if>
					</c:forEach>
					</table>
					<span style="color:red;">${okMsg.cleanOk}</span>

			<%@ include file="/sources/file/Member/page2.file" %>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
	<script
		src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>	
</body>
</html>
