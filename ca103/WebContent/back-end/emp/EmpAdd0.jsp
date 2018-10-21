<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.controller.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>員工新增</title>
</head>
<body>
<h3>新增員工</h3>

<%-- <c:if test="${not empty errorMsgs}"> --%>
<!-- 	<font style="color:red">請修正以下錯誤:</font> -->
<!-- 	<ul> -->
<%-- 		<c:forEach var="message" items="${errorMsgs}"> --%>
<%-- 			<li style="color:red">${message.value}</li> --%>
<%-- 		</c:forEach> --%>
<!-- 	</ul> -->
<%-- </c:if> --%>

<div class="container">

<form action="<%=request.getContextPath()%>/back-end/emp.do" method="post">
  <div class="form-group">
    <label>員工姓&nbsp<span style="color:red">${errorMsgs.lastname}</span></label>
    <div></div><input type="text" class="form-control" name="emp_firstname" value="${param.emp_lastname}" id="emp_lastname">
  </div>
  <div class="form-group">
    <label>員工名&nbsp<span style="color:red">${errorMsgs.firstname}</span></label>
    <input type="text" class="form-control" name="emp_lastname" value="${param.emp_firstname}" id="emp_firstname">
  </div>
  <div class="form-group">
    <label>員工帳號&nbsp<span style="color:red">${errorMsgs.account}</span></label>
    <input type="text" class="form-control" name="emp_account" value="${param.emp_account}" id="emp_ac">
  </div>
  <div class="form-group">
    <label>員工密碼&nbsp<span style="color:red">${errorMsgs.password}</span></label>
    <input type="password" class="form-control" name="emp_password" value="${param.emp_password}" id="emp_pw">
  </div>
  <div class="form-group">
    <label>員工信箱&nbsp<span style="color:red">${errorMsgs.email}</span></label>
    <input type="email" class="form-control" name="emp_email" value="${param.emp_email}" id="emp_email">
  </div>
 
  <div class="form-group">
    <label>員工住址&nbsp<span style="color:red">${errorMsgs.emp_ad}</span></label>
    <input type="text" class="form-control" name="emp_ad" value="${param.emp_ad}" id="emp_ad">
  </div>
   <div class="form-group">
    <label>員工權限&nbsp<span style="color:red">${errorMsgs.emp_pr}</span></label>
    <select name="emp_pr">
    	<option>超級管理員</option>
    	<option>路線管理員</option>
    	<option>揪團管理員</option>
    	<option>拍賣管理員</option>
    	<option>文字管理員</option>
    </select>
  </div>
  <input type="hidden" name="action" value="insert">
  <button type="submit" class="btn btn-primary" >送出</button>
</form>
<br>
  <button type="button" class="btn btn-info" onclick="get_emp()">按鍵小精靈</button>
</div>
<script type="text/javascript">
	function get_emp(){
		var emp_lastname = document.getElementById("emp_lastname").value="譽";
		var emp_firstname = document.getElementById("emp_firstname").value="鄭淳";
		var emp_ac = document.getElementById("emp_ac").value="poi45678";
		var emp_pw = document.getElementById("emp_pw").value="longgo12";
		var emp_email = document.getElementById("emp_email").value="ntr4018@nitori.com.tw";
		var emp_ad = document.getElementById("emp_ad").value="新北市八里區龍米路一段";
	}


</script>
</body>
</html>