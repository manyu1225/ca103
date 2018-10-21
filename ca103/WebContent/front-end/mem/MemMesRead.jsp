<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.message.model.*"%>
<%@ page import="com.message.controller.*"%>
<%@ page import="com.mem.model.*" %>
<%@ page import="com.mem.controller.*" %>
<%@ page import="java.util.*"%>
<%
MesVO mesVO = new MesVO();
MemVO memVO = new MemVO();
mesVO =(MesVO) request.getAttribute("mesVO");
memVO =(MemVO) session.getAttribute("memVO");

pageContext.setAttribute("mesVO", mesVO);
%>
<!DOCTYPE html>


<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" />

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
	

<title>自轉車-站內信</title>
</head>
<body style="background-color: #CCEEFF;">
<%@ include file="/sources/file/Home/NavBar.file" %>


<div class="container">
		<div class="row">
			<div class="col-md-3 col-mt-5" style="padding-top: 90px;margin-top:48px;">
				<div class="list-group" style="border-radius: 50%">
					<div style="background-color:#F7F7F7;border-width: 1px;border-color:#F5F5F5;border-radius:10px;">
						<img class="img-thumbnail"
							src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${memVO.mem_id}" style="border-radius:50px;" width="100">
							<h5 style="border-style: none; display: inline;background-color:#F7F7F7;"><%=memVO.getMem_lastname()%><%=memVO.getMem_firstname()%></h5>
					</div>
					<a href="<%=request.getContextPath()%>/front-end/mem/MemberMain.jsp" class="list-group-item list-group-item-action  disabled">會員資料</a> 
					<a href="<%=request.getContextPath()%>/front-end/mem/MemberMes.jsp" class="list-group-item list-group-item-action active">站內信</a>
					<a href="<%=request.getContextPath()%>/front-end/mem/FriendsList.jsp" class="list-group-item list-group-item-action disabled">好友清單</a>
				</div>
			</div>
			
		<div class="col-md-9 mt-5" style="padding-top: 90px;margin-top: 16px;">
		<div class="card">
		  <div class="card-header">
		    主旨：${mesVO.chat_title}
		  </div>
		  <div class="card-body">
		    <h5 class="card-title border-bottom-0">寄件者：${mesVO.login_nickname}(${mesVO.login_ac})</h5>
		    <p class="card-text">${mesVO.chat_text}</p>
		    
		   </div>
		   </div> 
		   
			<form method="post"  action="<%=request.getContextPath()%>/front-end/mem/mes.do" style="display:inline;">
		    <button type="submit" class="btn btn-primary" >回覆</button>
		    <input type="hidden" name="action" value="reMail">
		    <input type="hidden" name="reMailAc" value="${mesVO.login_ac}">
		    <input type="hidden" name="reMailTitle" value="${mesVO.chat_title}">
		    </form>
		    
		    <form method="post"  action="<%=request.getContextPath()%>/front-end/mem/mes.do" style="display:inline;">
		    <button type="submit" class="btn btn-danger">刪除</button>
		    <input type="hidden" name="action" value="mesDelete"> 
		    <input type="hidden" name="deleteNo" value="${mesVO.chat_no}">
		    </form>
		</div>
	</div>
	
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

<script src="https://code.jquery.com/jquery-3.3.1.js"></script>	
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
		var $jUI = $.noConflict(true);
</script>
<script>

$(document).ready(function() {
    $jUI('table.display').DataTable();
} );

</script>	
</body>
</html>