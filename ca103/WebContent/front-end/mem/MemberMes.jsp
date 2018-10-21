<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.mem.controller.*"%>
<%@ page import="com.message.model.*"%>
<%@ page import="com.message.controller.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>

<%--
	MemService memSvc = new MemService();
	MemJDBCDAO memJDBCDAO = new MemJDBCDAO();
	List<MemVO> list = new ArrayList<MemVO>();
	MemVO memVO = memJDBCDAO.findByPrimarKey("678mkhyi");
--%>

<%
	Map delMsg = (Map)request.getAttribute("delMsg");
	Map okMsgs = (Map)request.getAttribute("okMsgs");
	MemVO memVO = (MemVO) session.getAttribute("memVO");//使用者自己的物件
	MesService mesSvc = new MesService();
	String getId = memVO.getMem_id();
	List<MesVO> list = mesSvc.getAll(getId);
	pageContext.setAttribute("list", list);
	pageContext.setAttribute("delMsg", delMsg);
	pageContext.setAttribute("okMsgs", okMsgs);
%>
<!doctype html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" />

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<!--      漂亮icon的引用-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
		
<style>
div.dataTables_wrapper {
        margin-bottom: 3em;
    }

</style>

<title>自轉車-會員專區</title>
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
					<a href="<%=request.getContextPath()%>/front-end/mem/MemberMain.jsp" class="list-group-item list-group-item-action ">會員資料</a> 
					<a href="<%=request.getContextPath()%>/front-end/mem/MemberMes.jsp" class="list-group-item list-group-item-action active">站內信</a>
					<a href="<%=request.getContextPath()%>/front-end/mem/FriendsList.jsp" class="list-group-item list-group-item-action disabled">好友清單</a>
				</div>
			</div>
			<div class="col-md-9 mt-5" style="padding-top: 121px;margin-top: 16px;">
				<table style="border-style: solid" class="display">
					<tr>
						<td style="border-style: solid;">信件編號</td>
						<td style="border-style: solid">寄件者</td>
						<td style="border-style: solid;">標題</td>
						<td style="border-style: solid;">時間</td>
					</tr>
					<%@ include file="/sources/file/Member/page1.file" %>
					<c:forEach var="list" items="${list}" begin="<%=pageIndex%>"
						end="<%=pageIndex+rowsPerPage-1%>">
						<tr>
							<td style="border-style: solid">${list.chat_no}</td>
							<td style="border-style: solid">${list.login_nickname}(${list.login_ac})</a></td>
							<td style="border-style: solid"><a href="<%=request.getContextPath()%>/front-end/mem/mes.do?action=getMesText&chat_no=${list.chat_no}"/>${list.chat_title}</td>
							<td style="border-style: solid">${list.chat_date}</td>
						</tr>
					</c:forEach>
				</table>
				<%@ include file="/sources/file/Member/page2.file" %>
				<form action="<%=request.getContextPath()%>/front-end/mem/MemMesOut.jsp" method="post">
				<button type="submit" class="btn btn-success">寫信</button>
				<input type="hidden" name="login_id" value="<% memVO.getMem_id(); %>">
				</form>
				<span style="color:#E63F00;">${delMsg.string}${delMsg.chat_no}${delMsg.deleteOk}${okMsgs.sendOk}</span>
			</div>
		</div>
	</div>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>	
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script>
$(document).ready(function() {
    $('table.display').DataTable();
} );

</script>	

</body>

</html>