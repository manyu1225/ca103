<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.activity.model.*"%>
<%@ page import="com.emp.model.*"%>


					<!--///////////////// 此處為活動單一頁面////////////// -->



<%-- <%String errorMsgs = (String)request.getAttribute("errorMsgs"); %> --%>

<%
//員工真實資料






%>
	




<%

Activity_VO activityVO = (Activity_VO)request.getAttribute("activityVO");



%>
<!-- Activity_VO activity_VO = req.get  -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="UTF-8">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/Forum/jquery.datetimepicker.css" />
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.js"></script> -->


<style>

.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}

span {
	color: red;
	text-size: 11px;
	font-family: "微軟正黑體"
}

</style>

<title>活動新增後台</title>
</head>


<body>

<%@ include file="/sources/file/Home/backNav.file"%>
   
   <nav aria-label="breadcrumb">
  	  <ol class="breadcrumb">
  	    <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/BackHomePage.jsp">Home</a></li>
		<li class="breadcrumb-item active" aria-current="page">活動上架</li>
  	  </ol>
  	</nav>
	<div class="container">
	
	
		<form class="needs-validation" method="post" action="<%=request.getContextPath()%>/activity/activity.do" enctype="multipart/form-data">
			<div class="row">
				<div class="col-8 border border-white ryan">
				

					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="inpuCusName">客戶聯絡人姓名</label> <input type="text"
								class="form-control" name="cusName" id="inpuCusName"
								value="${param.cusName}"> <span>${errorMsgs.cusName}</span>
						</div>



						<div class="form-group col-md-6">
							<label for="emailAddressInpu">客戶信箱</label> <input type="email"
								class="form-control" id="emailAddressInpu" name="cusMail"
								value="${param.cusMail}"> <span>${errorMsgs.cusMail}</span>
						</div>
					</div>


					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="act_name">活動名稱</label> <input type="text"
								class="form-control" name="act_name" id="act_name"
								value="${param.act_name}"> <span>
								${errorMsgs.act_name}</span>
						</div>
						<div class="form-group col-md-6">
							<label for="act_holder">主辦單位</label> <input type="text"
								class="form-control" name="act_holder" id="act_holder"
								value="${param.act_holder}"> <span>${errorMsgs.act_holder}</span>
						</div>
					</div>



					<div class="form-row">
						<div class="col-6 ">
							<div class="form-group">
								<label for="act_href">插入連結1(相關連結)</label> <input type="text"
									class="form-control" name="act_href" id="act_href"
									value="${param.act_href}"> <span>${errorMsgs.act_href}</span>
							</div>
						</div>
						<div class="col-6">
							<div class="form-group">
								<label for="act_href2">插入連結2(報名簡章)</label> <input type="text"
									class="form-control" id="inputAddress" name="act_href2"
									value="${param.act_href2}"> <span>${errorMsgs.act_href2}</span>
							</div>
						</div>
					</div>






					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="f_date1">活動開始日期</label> <input type="text"
								class="form-control" name="act_sDate" id="f_date1"
								value="${param.act_sDate}"> <span>${errorMsgs.act_sDate}</span>
						</div>
						<div class="form-group col-md-6">
							<label for="f_date2">活動結束日期</label> <input type="text"
								class="form-control" name="act_eDate" id="f_date2"
								value="${param.act_eDate}"> <span>${errorMsgs.act_eDate}</span>
						</div>
					</div>







					<div class="form-row">
						<div class="form-group col-md-6">
							<!-- 報名日期 -->
							<label for="f_date3">報名日期</label> <input type="text"
								class="form-control" name="act_regis_date" id="f_date3"
								value="${param.act_regis_date}"> <span>${errorMsgs.act_regis_date}</span>
						</div>
					</div>



					<!-- 					待跟小毛整合     用controlelr驗證 -->
					
				<jsp:useBean id="routeSvc" scope="page" class="com.route.model.RouteService" />
					<div class="form-group">
						<label for="inputRot">匯入路線</label> 
						<select id="inputRot" name="rot_id" class="form-control">
						<c:forEach var="routeVO" items="${routeSvc.getRouteList('order by rot_name desc')}">							
						<option value="${routeVO.rot_id}" ${(activityVO.rot_id == routeVO.rot_id)? 'selected' :''}>${routeVO.rot_name}
						</c:forEach>
						</select> <span>${errorMsgs.rot_id}</span>
					</div>
					
						<input type="hidden" name="rot_id" value="${routeVO.rot_id}">
					
					


					<div class="form-group">
						<label for="inpuInfo">活動簡介</label>
						<textarea class="form-control" rows="4" cols="50" name="act_info"
							id="inpuInfo">${param.act_info}</textarea>
						<span>${errorMsgs.act_info}</span>
					</div>

				</div>









				<div class="col-4 border border-white ryan">

					<!--  用controlelr驗證 -->
					<div class="row">
						<label for="inputCity">活動圖片</label>
					</div>

					<div class="row">

						<br> <input type="file"  onchange="PreviewImage(this)" name="activityPhoto">
					</div>


					<div class="row">
						<div id="uploadimg">
							<div name="act_pic" id="imgPreview"
								style="width: 133px; height: 100px; overflow: hidden;"></div>
						</div>
					</div>





		<jsp:useBean id="empVO" scope="request" class="com.emp.model.EmpService" />
					<div class="row">
						<div class="form-group mt-5">
							<select id="inputState" class="form-control" name="emp_id">
								<option>---請選擇維護的員工---
						<c:forEach var="empVO" items="${empVO.all}">							
								<option value="${empVO.emp_id}" ${(activityVO.emp_id == empVO.emp_id)? 'selected' :''}>
								${empVO.emp_id}－${empVO.emp_lastname}${empVO.emp_firstname}
						</c:forEach>							
							</select>
							<div class="invalid-feedback">${errorMsgs.emp_id}</div>
						</div>
					</div>
					
					
					

					<div class="row">
						<div class="form-check form-check-inline">
							<input class="form-check-input" name="act_state" type="radio"
								name="inlineRadioOptions" id="inlineRadio1" value="1"> <label
								class="form-check-label" for="inlineRadio1">公開</label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" name="act_state" type="radio"
								name="inlineRadioOptions" id="inlineRadio2" value="2"> <label
								class="form-check-label" for="inlineRadio2">隱藏</label> <span>${errorMsgs.act_state}</span>
						</div>
					</div>


					<div class="row">
						<input type="hidden" name="action" value="addAct">
						<button type="submit" class="btn btn-primary mr-2 mt-3">預覽</button>
						<button type="submit" class="btn btn-primary mt-3">送出</button>
                         
                         <a id="texeDate" class="btn btn-sm btn-info active shodd ml-2 mt-3">神奇按鈕</a>
						
<%-- 					<input type="hidden" value="<%%>"> --%>
					</div>
				</div>
			</div>
		</form>
	</div>





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


	<script src="<%=request.getContextPath()%>/sources/js/Forum/datetimepicker/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/sources/js/Forum/datetimepicker/jquery.datetimepicker.full.js"></script>


	<script>
	$('#texeDate').click(function(){
		 $('input[name=cusName]').val('吳冠宏');
		 $('input[name=cusMail]').val('davidWu@gmail.com');
		 $('input[name=act_name]').val('2018陽明山自行車登山王挑戰');
		 $('input[name=act_holder]').val('中華民國自行車協會');
		 $('input[name=act_href]').val('http://bao-ming.com/eb/www/activity_content.php?activitysn=3318');
		 $('input[name=act_sDate]').val('2018-10-19');
		 $('input[name=act_eDate]').val('2018-10-20');
		 $('input[name=act_regis_date]').val('2018-10-5');
//		 $('textarea[name=act_info]').val('')
		 $('input[name=regis_date]').val('2018-10-5');
	     $('textarea[name=act_info]').html('「中華民國自行車騎士協會」舉辦「臺灣自行車登山王挑戰」的初衷是：以吸引喜愛高山騎車的國際車友前來挑戰海拔3275的合歡山武嶺路線，進而推廣臺灣在國際的能見度！' + 
	    		' 「2018陽明山自行車登山王挑戰」活動，規劃於10/26KOM主場活動前之前導暖身活動，得以吸引外籍車友提前抵台、並讓國際觀光騎士們，在騎乘KOM活動路線之前，先行暖身體驗臺灣最北端北海岸公路，再由陽金公路上到陽明山小觀音路線，讓國內外遊客得以更廣、更深地體驗來臺灣進行自行車旅遊的價值。')
		 
       });
		</script>
		



	<script>
		$.datetimepicker.setLocale('zh');
		$('#f_date1').datetimepicker({
			theme : '', //theme: 'dark',
			timepicker : false, //timepicker:true,
			step : 1, //step: 60 (這是timepicker的預設間隔60分鐘)
			format : 'Y-m-d', //format:'Y-m-d H:i:s',
			value : '', // value:   new Date(),
		//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
		//startDate:	            '2017/07/10',  // 起始日
		//minDate:               '-1970-01-01', // 去除今日(不含)之前
		//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
		});

		$('#f_date2').datetimepicker({
			theme : '', //theme: 'dark',
			timepicker : false, //timepicker:true,
			step : 1, //step: 60 (這是timepicker的預設間隔60分鐘)
			format : 'Y-m-d', //format:'Y-m-d H:i:s',
			value : '', // value:   new Date(),
		//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
		//startDate:	            '2017/07/10',  // 起始日
		//minDate:               '-1970-01-01', // 去除今日(不含)之前
		//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
		});

		$('#f_date3').datetimepicker({
			theme : '', //theme: 'dark',
			timepicker : false, //timepicker:true,
			step : 1, //step: 60 (這是timepicker的預設間隔60分鐘)
			format : 'Y-m-d', //format:'Y-m-d H:i:s',
			value : '', // value:   new Date(),
		//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
		//startDate:	            '2017/07/10',  // 起始日
		//minDate:               '-1970-01-01', // 去除今日(不含)之前
		//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
		});
	</script>


	<script type="text/javascript">
		function PreviewImage(imgFile) {
			var pattern = /(\.*.jpg$)|(\.*.png$)|(\.*.jpeg$)|(\.*.gif$)|(\.*.bmp$)/;
			if (!pattern.test(imgFile.value)) {
				alert("只支援jpg/jpeg/png/gif/bmp之格式檔案");
				imgFile.focus();
			} else {
				var path;
				if (document.all) { // IE
					imgFile.select();
					imgFile.blur();
					path = document.selection.createRange().text;
					document.getElementById("imgPreview").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\""
							+ path + "\")";// 濾鏡
				} else { // FF 或 Chrome 等
					path = URL.createObjectURL(imgFile.files[0]);
					document.getElementById("imgPreview").innerHTML = "<img src='"+ path +"'  width='143' height='100'/>";
				}
			}
		}
	</script>
	
	



</body>
</html>