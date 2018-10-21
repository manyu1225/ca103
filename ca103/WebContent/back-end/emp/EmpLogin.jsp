<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.emp.model.*" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>員工登入</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="/sources/file/Home/backNav.file"%>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-6">
				<img src="Login1.jpg" style="width: 100%">
			</div>
			<div class="col-md-6">
				<form action="<%=request.getContextPath()%>/back-end/emp/emp.do" method="post">
					<div style="padding-top:35%">
						<div class="form-group">
						<div class="col-md-6">
							<label>帳號&nbsp<span style="color:red">${errorMsgs.account}</span></label> 
							<input type="text" class="form-control" name="emp_account"  id="emp_ac">${param.emp_account}
						</div>
						</div>
						<div class="form-group">
							<div class="col-md-6"> 
							<label>密碼&nbsp<span style="color:red">${errorMsgs.password}</span> <a href="/sessions/forgot_password">(忘記密碼?)</a></label>
							<input type="password" class="form-control" name="emp_password"  id="emp_pw">${param.emp_password}
							</div>
						</div>
						<button type="submit" class="btn btn-md btn-default" id="empLogin">登入</button>
						<input type="hidden" name="action" value="login" >
						</div>
				</form>
						<button type="button" class="btn btn-md btn-info" onclick="get_emp()">按鍵小精靈</button>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	function get_emp(){
		var emp_ac = document.getElementById("emp_ac").value="gy775678";
		var emp_pw = document.getElementById("emp_pw").value="longgo12";
	}
	
	</script>
	  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js" crossorigin="anonymous"></script>
	<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js " crossorigin="anonymous"></script>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">
</body>
</html>