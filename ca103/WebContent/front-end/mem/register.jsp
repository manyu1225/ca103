<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.mem.model.*" %>


<%
 MemVO memVO = (MemVO) request.getAttribute("memVO");
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>自転車-會員註冊</title>
<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous"> -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/sources/css/Member/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/Member/css.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
	integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ"
	crossorigin="anonymous">
</head>
<body>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>
<button class="btn btn-info" onclick="quickClick()">一鍵輸入</button>
	<div class="container">
		<div class="row">
			<div class="col-12">
				<section>
					<h1 class="entry-title">
						<span>基本資料</span>
					</h1>
					<hr>
					<form class="form-horizontal" action="mem.do" method="post" enctype="multipart/form-data">

						<div class="form-group">
							<label class="control-label col-sm-3">設定帳號 <span
								class="text-danger">*</span></label>
							<div class="col-md-8 col-sm-9">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="glyphicon glyphicon-envelope"></i></span> 
										<input type="text"	class="form-control" name="mem_ac" id="mem_ac">
								</div>
							</div>
						</div>



						<div class="form-group">
							<label class="control-label col-sm-3">設定密碼 <span
								class="text-danger">*</span></label>
							<div class="col-md-5 col-sm-8">
								<div class="input-group">
									<span class="input-group-addon"><i	class="glyphicon glyphicon-lock"></i></span> 
									<input type="password"	class="form-control" name="mem_password" id="mem_password">
								</div>
							</div>
						</div>


						<div class="form-group">
							<label class="control-label col-sm-3">確認密碼 <span
								class="text-danger">*</span></label>
							<div class="col-md-5 col-sm-8">
								<div class="input-group">
									<span class="input-group-addon"><i	class="glyphicon glyphicon-lock"></i></span> 
									<input type="password" class="form-control" name="pwConfirm" id="pwConfirm">
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-3">電子信箱 <span
								class="text-danger">*</span></label>
							<div class="col-md-5 col-sm-8">
								<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
										<input type="email" class="form-control" name="mem_email" id="mem_email" >
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-3">姓<span
								class="text-danger">*</span></label>
							<div class="col-md-8 col-sm-9">
								<input type="text" class="form-control" name="mem_lastname"	id="mem_lastname" >
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-3">名<span
								class="text-danger">*</span></label>
							<div class="col-md-8 col-sm-9">
								<input type="text" class="form-control" name="mem_firstname" id="mem_firstname">
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-sm-3">暱稱<span
								class="text-danger">*</span></label>
							<div class="col-md-8 col-sm-9">
								<input type="text" class="form-control" name="mem_nickname" id="mem_nickname">
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-3">生日 <span
								class="text-danger">*</span></label>
							<div class="col-xs-8">
								<div class="form-inline">
									<div class="input-group">
										<input type=text class="form-control" name="mem_birthday" id="birthday" style="margin-left: 15px;">
									</div>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-3">手機號碼 <span
								class="text-danger">*</span></label>
							<div class="col-md-5 col-sm-8" >
								<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span> 
										<input type="text" class="form-control" name="mem_phone" id="mem_phone">
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-3">家用電話</label>
							<div class="col-md-5 col-sm-8">
								<input type="text" class="form-control" name="mem_tel" id="mem_tel">
							</div>
						</div>
						<div class="form-group">

							<label class="control-label col-sm-3">你的照片 <br> <small>(optional)</small></label>
							<div class="col-md-5 col-sm-8">
								<div class="input-group">
									<span class="input-group-addon" id="file_upload"> <i class="glyphicon glyphicon-upload"></i></span> 
									<input type="file"	name="mem_photo" id="file_nm1" class="form-control upload"
										aria-describedby="file_upload"
										accept="image/gif, image/jpeg, image/png">
										<br>
										
								</div>
								<div><img id="mem_photo" src=""/></div>
							</div>
						</div>
						<div class="form-group">

							<label class="control-label col-sm-3">你的車照<br> <small>(optional)</small></label>
							<div class="col-md-5 col-sm-8">
								<div class="input-group">
									<span class="input-group-addon" id="file_upload"><i	class="glyphicon glyphicon-upload"></i></span> 
									<input type="file"	name="mem_cart_photo" id="file_nm2" class="form-control upload"
										aria-describedby="file_upload"
										accept="image/gif,image/jpeg,image/png">
										<br>
								</div>
								<div><img id="mem_cart_photo" src=""/></div>
							</div>
							
					</div>
					<div class="form-group">
							<label class="control-label col-sm-3">自我介紹</label>
							<div class="col-md-8 col-sm-9">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="glyphicon glyphicon-envelope"></i></span> 
										<textarea row="50" cols="50" name="mem_aboutme" id="mem_aboutme">請在這裡輸入你的簡介</textarea>
						</div>
			<div class="form-group">
				<div class="col-xs-offset-3 col-xs-10">
					<input name="Submit" type="submit" value="創建帳號"
						class="btn btn-primary">
					<input type="hidden" name="sign_up" value="sign_up">
				</div>
			</div>
			</form>
		</div>
	</div>
	</div>
	
	</script>
	<script
		src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"
		integrity="sha384-4oV5EgaV02iISL2ban6c/RmotsABqE4yZxZLcYMAdG7FAPsyHYAPpywE9PJo+Khy"
		crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$("#file_nm1").change(function() {
			// var id =$(event.srcElement).attr('id');
			var id = $(this).attr('id');
			readURL1(this, id);

		});

		$("#file_nm2").change(function() {
			// var id =$(event.srcElement).attr('id');
			var id = $(this).attr('id');
			readURL2(this, id);
			

		});

		function readURL1(input, id) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {	 
						$("#mem_photo").attr('src', e.target.result);		
				}
				reader.readAsDataURL(input.files[0]);
				
			}
		}
		
		function readURL2(input, id) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {		 
						$("#mem_cart_photo").attr('src', e.target.result);	
				}
				reader.readAsDataURL(input.files[0]);
			}
		}
		var mem_photo = $("mem_photo");
		console.log(mem_photo.width);
		
		
		function quickClick(){
			var mem_ac = document.getElementById("mem_ac").value="sinleman";
			var mem_password = document.getElementById("mem_password").value="longgo12";
			var pwConfirm = document.getElementById("pwConfirm").value="longgo12";
			var mem_email = document.getElementById("mem_email").value="ricksin1296@gmail.com";
			var mem_lastname = document.getElementById("mem_lastname").value="林";
			var mem_firstname = document.getElementById("mem_firstname").value="培南";
			var mem_nickname = document.getElementById("mem_nickname").value="小南";
			var birthday = document.getElementById("birthday").value="1987-01-23";
			var mem_phone = document.getElementById("mem_phone").value="0987654321";
			var mem_tel = document.getElementById("mem_tel").value="02-28052756";
			var mem_aboutme = document.getElementById("mem_aboutme").innerHTML="我是小南!! 一起騎車喔喔喔喔!!!!!";
		}
	</script>
</body>
<% 
  java.sql.Date mem_birthday = null;
  try {
	   mem_birthday = memVO.getMem_birthday();
   } catch (Exception e) {
	   mem_birthday = new java.sql.Date(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/js/Member/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/sources/js/Member/jquery.js"></script>
<script src="<%=request.getContextPath()%>/sources/js/Member/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>

        $.datetimepicker.setLocale('zh');
        $('#birthday').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
<%-- 		   value: '<%=mem_birthday%>', // value:   new Date(), --%>
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+2018-01-01'  // 去除今日(不含)之後
        });
</script>
</html>