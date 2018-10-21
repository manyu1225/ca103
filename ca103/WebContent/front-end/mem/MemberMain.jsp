<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.mem.controller.*"%>
<%@ page import="java.util.*"%>

<%--
	MemService memSvc = new MemService();
	MemJDBCDAO memJDBCDAO = new MemJDBCDAO();
	List<MemVO> list = new ArrayList<MemVO>();
	MemVO memVO = memJDBCDAO.findByPrimarKey("678mkhyi");
--%>

<%
	MemVO memVO = (MemVO) session.getAttribute("memVO");
%>
<!doctype html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
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
<body style="background-color:#CCEEFF;">
<%@ include file="/sources/file/Home/NavBar.file" %>

	<div class="container">
		<div class="row">
			<div class="col-md-3 mt-5" style="padding-top: 90px;">
				<div class="list-group" style="border-radius: 50%">
					<div></div>
					<div style="background-color:#F7F7F7;border-width: 1px;border-color:#F5F5F5;border-radius:10px;">
						<img class="img-thumbnail"
							src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${memVO.mem_id}" style="border-radius:50px;" width="100">
							<h5 style="border-style: none; display: inline;background-color:#F7F7F7;"><%=memVO.getMem_lastname()%><%=memVO.getMem_firstname()%></h5>
						
					</div>
					<a	href="<%=request.getContextPath()%>/front-end/mem/MemberMain.jsp"
						class="list-group-item list-group-item-action active">會員資料 </a> 
					<a	href="<%=request.getContextPath()%>/front-end/mem/MemberMes.jsp"
						class="list-group-item list-group-item-action disabled">站內信</a> 
					<a	href="<%=request.getContextPath()%>/front-end/mem/FriendsList.jsp"
						class="list-group-item list-group-item-action disabled">好友清單</a>
				</div>
			</div>
			<div class="col-md-9" style="padding-top: 121px;margin-top: 16px;">
				<form action="<%=request.getContextPath()%>/back-end/mem/mem.do" method="post" enctype="multipart/form-data">
					<ul class="list-group list-group-flush">
					
						<li class="list-group-item">
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 11px;border-right-width: 100px;padding-right: 43px;padding-left: 22px;">個人資料</h6>
						
						</li>
					
						<li class="list-group-item ">
							<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 11px;border-right-width: 100px;padding-right: 90px;padding-left: 22px;">ID</h6>
							<div class="col-md-10 row" style="display: inline;"><%=memVO.getMem_id()%></div>
							<input type="hidden" name="mem_id" value="<%=memVO.getMem_id()%>"/>
						</li>

						<li class="list-group-item">
							<div style="display: inline; ">
								<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right: 75px;padding-left: 22px;">姓名</h6>
								
								<input type="text" style="border-style: none; display: inline;"
									id="lnUpdate" disabled name="mem_lastname" class="update"
									value="<%=memVO.getMem_lastname()%>" size="3" > 
									
								<input type="text" style="display: inline; border-style: none;"
									id="fsNameUpdate" disabled name="mem_firstname" class="update"
									value="<%=memVO.getMem_firstname()%>" size="5">
									</div> ${errorMsgs.mem_lastname}${errorMsgs.mem_firstname}
						</li>


						<li class="list-group-item">
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right:75px;padding-left: 22px;">暱稱</h6>
						<input type="text" style=" display: inline; border-style: none; width: 120px;"
							id="nickup" disabled name="mem_nickname" class="update" value="<%=memVO.getMem_nickname()%>"> 
							<span style="color: #FF3333;">${errorMsgs.mem_nickname}</span>
						</li>


						<li class="list-group-item">
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right: 43px;padding-left: 22px;">電子郵件</h6>
						
						<input type="text" style=" display: inline; border-style: none;"
							   name="mem_email" disabled id="email_update" class="update"
							   value="<%=memVO.getMem_email()%>"> ${errorMsgs.mem_email}
						</li>


						<li class="list-group-item">
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right: 43px;padding-left: 22px;">手機號碼</h6>
						<input type="text" style=" display: inline; border-style: none;"
							   name="mem_phone" disabled id="phone_update" class="update"
							   size="20" value="<%=memVO.getMem_phone()%>">
							   ${errorMsgs.mem_phone}
						</li>


						<li class="list-group-item">
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right: 43px;padding-left: 22px;">市話號碼 </h6>
						<input type="text" style=" display: inline; border-style: none;"
							   name="mem_tel" disabled id="tel_update" class="update"
							   value="<%=memVO.getMem_tel()%>"> ${errorMsgs.mem_tel}
						</li>


						<li class="list-group-item"> 
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right: 75px;padding-left: 22px;">生日</h6>
						<input type="text" style=" display: inline; border-style: none;"
							   name="mem_birthday" disabled id="birthday" class="update"
							   value="<%=memVO.getMem_birthday()%>">
							   ${errorMsgs.mem_birthday}
						</li>


						<li class="list-group-item">
						<h6 class="col-md-2" style="border-bottom-style:solid;border-top-width: 20px;top: 0px;right: 20px;border-color:#1E90FF;display: inline;padding-bottom: 12px;border-right-width: 100px;padding-right: 43px;padding-left: 22px;">自我介紹</h6>
						<input type="text" style="display: inline; border-style: none;"
							   name="mem_aboutme" disabled id="aboutme_update" class="update"
							   value="<%=memVO.getMem_aboutme()%>" size="60"> 
							   <span style="color: #FF3333;">${errorMsgs.mem_aboutme}</span>
						</li>

						<li class="list-group-item open" style="display: none">個人照片
							<input type="file" name="mem_photo" id="file_nm1"
							aria-describedby="file_upload"
							accept="image/gif, image/jpeg, image/png">
							
							<div>
								<img id="mem_photo"
									src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${sessionScope.memVO.mem_id}" />
							</div>
							
						</li>
						
						<li class="list-group-item open" style="display: none">車照 
						<input
							type="file" name="mem_cart_photo" id="file_nm2"
							aria-describedby="file_upload"
							accept="image/gif, image/jpeg, image/png">
							<div>
								<img id="mem_cart_photo"
									src="<%=request.getContextPath()%>/Mem_cart_photoReader?mem_id=${sessionScope.memVO.mem_id}" />
							</div>
						</li>
					</ul>
					<button type="button" style="margin-top:10px" class="btn btn-warning" id="up"
						onclick="memUpdate()">修改</button>
					<input type="hidden" name="action" value="updateProfile">
					<button type="submit" style="margin-top:9px;display:none;" id="upOk" class="btn btn-info">確認修改</button>
				</form>
			</div>
		</div>
	</div>
	<script>
		function memUpdate(){
		var getUp = document.getElementById("up");
		var getOk = document.getElementById("upOk");
		var getClassName = document.getElementsByClassName("update");
		var getOpenUpload = document.getElementsByClassName("open");
		getUp.style = "display:none";
		getOk.style="display:block";
			for(i=0;i<getClassName.length;i++){
				getClassName[i].disabled = false;
				getClassName[i].style="border-style:solid;border-color:#77DDFF";
			}
				getOpenUpload[0].style="display:block";
				getOpenUpload[1].style="display:block";
		}
		
	</script>

	<%
		java.sql.Date mem_birthday = null;
		try {
			mem_birthday = memVO.getMem_birthday();
		} catch (Exception e) {
			mem_birthday = new java.sql.Date(System.currentTimeMillis());
		}
	%>

	<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/sources/css/Member/jquery.datetimepicker.css" />
	<script src="<%=request.getContextPath()%>/sources/js/Member/jquery.js"></script>
	<script	src="<%=request.getContextPath()%>/sources/js/Member/jquery.datetimepicker.full.js"></script>

	<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

	<script>
        $.datetimepicker.setLocale('zh');
        $('#birthday').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%memVO.getMem_birthday();%>',
		 // value:   new Date(),
		//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
		//startDate:	            '2017/07/10',  // 起始日
		//minDate:               '-1970-01-01', // 去除今日(不含)之前
		//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
		});
		/**********上傳圖片************/
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
	</script>
	
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	
</body>
</html>
