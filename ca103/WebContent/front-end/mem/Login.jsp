<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.mem.controller.*"%>

<%-- <jsp:useBean id="memVO" scope="request" class="com.mem.model.MemVO" /> --%>

<!-- 用EL取值 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>自転車-會員登入</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="sources/css/css.css">
<!--      漂亮icon的引用-->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
	integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU"
	crossorigin="anonymous">

</head>
<body background="<%=request.getContextPath()%>/sources/img/Member/forLoginjpg.jpg">
<%@ include file="/sources/file/Home/NavBar.file"%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
<div class="container">
    <div class="row">
      <div class="col-sm-9 col-md-7 col-lg-5 mx-auto" style="margin-top: 40px;
">
        <div class="card card-signin my-5">
          <div class="card-body">
            <h5 class="card-title text-center">會員登入</h5>
            <form class="form-signin" action="<%=request.getContextPath()%>/front-end/mem/mem.do" method="post">
              <div class="form-label-group">
                <label for="inputEmail">帳號</label>
                <input type="text" class="form-control" name="mem_ac" id="mem_ac">
              </div>
              <div class="form-label-group">
                <label for="inputPassword">密碼</label>
               <input type="password" class="form-control" name="mem_password" id="mem_password">
              </div>

              <hr class="my-4">
              <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">登入</button>
              <input type="hidden" name="login" value="login">
            </form>
              <button class="btn btn-info" onclick="login1()">使用者1</button>
              <button class="btn btn-info" onclick="login2()">使用者2</button>
              <button class="btn btn-info" onclick="login3()">拍賣者</button>
              <button class="btn btn-info" onclick="login4()">討論區</button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <script>
  function login1(){
	  var mem_ac = document.getElementById("mem_ac").value="sinleman";
	  var mem_password = document.getElementById("mem_password").value="longgo12";
  }
  function login2(){
	  var mem_ac = document.getElementById("mem_ac").value="chen1989";
	  var mem_password = document.getElementById("mem_password").value="longgo12";
  }
  function login3(){
	  var mem_ac = document.getElementById("mem_ac").value="LILI";
	  var mem_password = document.getElementById("mem_password").value="123456";
  }
  function login4(){
	  var mem_ac = document.getElementById("mem_ac").value="LILI1";
	  var mem_password = document.getElementById("mem_password").value="123456";                                     
	  
  }
  
  
  
  </script>
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>