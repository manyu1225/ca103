<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.mem.model.*" %>
<%
 MemVO memVO = (MemVO) request.getAttribute("memVO");
 
%>
<!DOCTYPE html>
<html>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<title>自轉車-認證信</title>
<!------ Include the above in your HEAD tag ---------->
<!--用請求轉交，登入者名子呈現在上面-->
<div class="container">
	<div class="row text-center">
        <div class="col-sm-6 col-sm-offset-3">
        <br><br> <h2 style="color:#0fad00">創建完成</h2>
        <img src="<%=request.getContextPath()%>/Mem_photoReader?mem_ac=${param.mem_ac}" width="60">
        <h3>${param.mem_nickname}，歡迎你!</h3>
        <p style="font-size:20px;color:#5C5C5C;">歡迎您的加入，系統已送出認證信，請於您的信箱中確認，以便啟動網站完整功能</p>
        <a href="<%=request.getContextPath()%>/HomePage.jsp" class="btn btn-success">    回首頁      </a>
    <br><br>
        </div>
        
	</div>
</div>
</html>