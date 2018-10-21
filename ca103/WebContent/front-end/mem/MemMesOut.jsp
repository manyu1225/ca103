<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.message.model.*"%>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*"%>
<%
	MemVO memVO = (MemVO) session.getAttribute("memVO");
	Map reMsg = (Map) request.getAttribute("reMsg");
	pageContext.setAttribute("reMsg", reMsg);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>自轉幣-寄送訊息</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
</head>
<body style="background-color: #CCEEFF;">
<%@ include file="/sources/file/Home/NavBar.file" %>
	<div class="container">
		<div class="row">
			<div class="col-md-3 mt-5" style="padding-top: 90px;">
				<div class="list-group" style="border-radius: 50%">
					<div
						style="background-color: #F7F7F7; border-width: 1px; border-color: #F5F5F5; border-radius: 10px;">
						<img class="img-thumbnail"
							src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${memVO.mem_id}"
							style="border-radius: 50px;" width="100">
							<h5 style="border-style: none; display: inline;background-color:#F7F7F7;"><%=memVO.getMem_lastname()%><%=memVO.getMem_firstname()%></h5>
							
					</div>
					<a
						href="<%=request.getContextPath()%>/front-end/mem/MemberMain.jsp"
						class="list-group-item list-group-item-action active">會員資料 </a> <a
						href="<%=request.getContextPath()%>/front-end/mem/MemberMes.jsp"
						class="list-group-item list-group-item-action disabled">站內信</a> <a
						href="<%=request.getContextPath()%>/front-end/mem/FriendsList.jsp"
						class="list-group-item list-group-item-action disabled">好友清單</a>
				</div>
			</div>
			<div class="col-md-6 mt-5" style="padding-top: 90px;margin-top: 16px;">
				<form action="mes.do" method="post">
					  <div class="form-group">
    					<label for="exampleInputEmail1">收件者帳號：</label>
					    <input type="text" class="form-control" name="receive_ac"  value="${reMsg.reMailAc}" id="mes_ac">
  			</div>
					  <div class="form-group">
    					<label for="exampleInputEmail1">主旨：</label>
					    <input type="text" class="form-control" name="chat_title" value="${reMsg.re}${reMsg.reMailTitle}" id="mes_title">
  			</div>
					<div class="form-group">
    <label for="exampleFormControlTextarea1">內容</label>
    <textarea class="form-control" name="chat_text" rows="3" id="mes_text"></textarea>
  					</div>
					<br> <button type="submit" class="btn btn-info">寄信</button> 
					<input type="hidden" name="login_ac" value="${sessionScope.memVO.mem_ac}">
					<input type="hidden" name="sendMes" value="sendMes">
				</form>
				<button type="button" class="btn btn-info" onclick="get_send1()">按鍵小精靈yu</button> 
				<button type="button" class="btn btn-info" onclick="get_send2()">按鍵小精靈nan</button> 
			</div>
		</div>
	</div>
<script>
	function get_send1(){
		var mes_ac = document.getElementById("mes_ac").value="chen1989";
		var mes_title = document.getElementById("mes_title").value="我覺得你的揪團很棒!";
		var mes_text = document.getElementById("mes_text").innerHTML = "小裕，謝謝你帶來這麼好的揪團，希望下次可以再參加你的團!!";
	}
	function get_send2(){
		var mes_ac = document.getElementById("mes_ac").value="sinleman";
		var mes_title = document.getElementById("mes_title").value="謝謝你，下次再一起揪團吧!";
		var mes_text = document.getElementById("mes_text").innerHTML = "TO小南：不會，很感謝你的鼓勵，下次再一起揪團吧!!!";
	}
</script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>