<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.mem.model.*" %>
<%@ page import="com.mem.controller.*" %>

<%
MemVO memVO = (MemVO) request.getAttribute("memVO");
MemService memSvc = new MemService();

pageContext.setAttribute("memVO", memVO);
%>

<!-- 這一支不能用session接，因為使用者可能會關掉server -->
<!-- 所以我從controller將資料傳過來這邊讓JSP接 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>自転車-認證完成</title>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"> -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/sources/css/Member/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/Member/css.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
	integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ"
	crossorigin="anonymous">
</head>
<body>
<div class="container">
	<div class="row text-center">
        <div class="col-sm-6 col-sm-offset-3">
        <br><br> <h2 style="color:#0fad00">創建完成</h2>
        <img src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=<% out.print(request.getParameter("mem_id"));%>" width="50">
        <h3>認證成功!</h3>
        <p style="font-size:20px;color:#5C5C5C;">${memVO.mem_nickname}，您已經可以使用網站的完整功能，請遵守網站使用規範</p>
        <a href="<%=request.getContextPath()%>/HomePage.jsp" class="btn btn-success">    回首頁      </a>
    <br><br>
        </div>
	</div>
</div>
<script type="text/javascript">
var url = location.href;
if(url.indexOf('?')!=-1){
	var ary1 = url.split('?');
	var ary2 =ary1[1].split('&');
	var ary3 =ary2[0].split('=');
	var id=ary3[1];
	
}
</script>
</html>