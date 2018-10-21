<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.activity.model.*"%>






<!doctype html>
<html lang="en">
<meta charset="UTF-8">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
	
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">




  <style>
  
  .act_name{
  font-family:"微軟正黑體";
  font-size:1.5rem;
  color:#07C;
  }
  
  
  
  .holder{
      margin-left: 6rem!important;
  }
  
/*   datetimepicker */
  
  .xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}



/* Circle */
.hover15 figure {
	position: relative;
}
.hover15 figure::before {
	position: absolute;
	top: 50%;
	left: 50%;
	z-index: 2;
	display: block;
	content: '';
	width: 0;
	height: 0;
	background: rgba(255,255,255,.2);
	border-radius: 100%;
	-webkit-transform: translate(-50%, -50%);
	transform: translate(-50%, -50%);
	opacity: 0;
}
.hover15 figure:hover::before {
	-webkit-animation: circle .75s;
	animation: circle .75s;
}
@-webkit-keyframes circle {
	0% {
		opacity: 1;
	}
	40% {
		opacity: 1;
	}
	100% {
		width: 200%;
		height: 200%;
		opacity: 0;
	}
}
@keyframes circle {
	0% {
		opacity: 1;
	}
	40% {
		opacity: 1;
	}
	100% {
		width: 200%;
		height: 200%;
		opacity: 0;
	}
	
	
/* 	flaoting buttton */
.button {
     border-radius: 50%; 
}

  
  </style>
      <title>活動列表</title>
    </head>
  
  
  <body style="background-color: #e6e6e6">
<%@ include file="/sources/file/Home/NavBar.file" %>
  
<br>
<br>
<br>
<br>
<div class="container">
	<form action="<%=request.getContextPath()%>/activity/activity.do" Method="POST">
		
										<%-- 萬用複合查詢  --%>
		<div class="row">
			<div class="col-3">
				<input  type="date" class="form-control form-control-sm" id="f_date1" name="act_sDate" placeholder="活動開始日期"> 
			</div>
			<div class="col-3">
					<input type="date" class="form-control form-control-sm" id="f_date2" name="act_eDate" placeholder="活動結束日期"> 
			</div>
			
<!-- 			<div class="col-2"> -->
<!-- 				<div class="form-check form-check-inline"> -->
<!-- 				  <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="1"> -->
<!-- 				  <label class="form-check-label" for="inlineRadio1">報名中</label> -->
<!-- 				</div> -->
<!-- 				<div class="form-check form-check-inline"> -->
<!-- 				  <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="2"> -->
<!-- 				  <label class="form-check-label" for="inlineRadio2">未開放報名</label> -->
<!-- 				</div> -->
<!-- 			</div> -->


<!-- 			<div class="col-3"> -->
<!-- 				<input class="form-control form-control-sm" type="text" placeholder="請輸入關鍵字" name="text"> -->
<!-- 			</div>		 -->
			<div class="col-1">
				<input type="submit" value="送出查詢" class="btn btn-outline-secondary btn-sm">
				<input type="hidden" name="action" value="listActivity_byCompositeQuery">
				
			</div>	
		</div>
	</form>
			
			
<!-- 	過濾查詢		 -->
		<div class="row justify-content-end">
			<form action="<%=request.getContextPath()%>/activity/activity.do" Method="POST">
		
				<div class="btn-group d-flex " role="group" aria-label="Basic example">
				  <button type="submit" name="action" value="getLatestAct" class="btn btn-secondary">最新活動</button>
				  <button type="submit" name="action" value="getPopAct" class="btn btn-secondary">熱門活動</button>
				</div>
			</form>
		</div>
	</div>
	
	
<!-- 	flaoting button -->
	
<a href="<%=request.getContextPath()%>/back-end/empAct/addActivity.jsp">
<button class="button buttonP" style="border-radius:50%"><i class="fa fa-plus my-float"></i></button>
</a>


 <c:forEach var="activityVO" items="${list}">
  <div class="container" style="background-color: #ecf5ff; margin-top:15px">    
    <div class="row row1 align-items-center shadow p-3 mb-5  rounded bg-white>">
      <!-- 放圖片 -->
      <div class="col-5 routeimg1 route hover15 column " style="padding:0px;text-align:center">
      <figure>
     <a href="<%=request.getContextPath()%>/activity/activity.do?act_ID=${activityVO.act_ID}&action=getOneAct_onPage">
		<img src="<%=request.getContextPath()%>/activity/activity.do?action=getListPic&act_ID=${activityVO.act_ID}" style="width: 350px; height: 200px;">									 	
	</a>
	</figure>
	</div>
      <!-- 圖片右邊的說明 -->
      <div class="col-7 routeimg2 route" style="padding:0px;">
  
        <!-- 右邊最上面的說明 -->
        <div class="container">
          <div class="row row2 ">
            <!-- 活動名稱 -->        
            <div class="col-6 act_name mb-5" style="padding:0px;">
				<a class="font-weight-bold" 
				href="<%=request.getContextPath()%>/activity/activity.do?act_ID=${activityVO.act_ID}&action=getOneAct_onPage&act_click=${activityVO.act_click}">${activityVO.act_name}</a>
			</div>
			
		 </div>
			
			<form action="<%=request.getContextPath()%>/activity.do" Method="POST">
				<div class="row d-flex mx-auto  justify-content-start ">
			
            <!-- 主辦單位 -->
           			 <i class="fas fa-user-alt"></i>主辦單位：${activityVO.act_holder}
               </div>
            
            </form>
        
      <form action="<%=request.getContextPath()%>/activity/activity.do" Method="POST">
        <!-- 右邊中間的說明 -->
        <div class="container">
        
        <div class="row">
        
          <div class="col-12 sdate edate" style="padding:0px;"><i class="far fa-calendar-alt"></i> 
          	活動期間： ${activityVO.act_sdate} <i class="fas fa-chevron-right"></i> ${activityVO.act_edate}</div>
        
        </div>
        
          <div class="row row3">
            <!-- 分類標籤 -->
            <div class="col-12 hashtag" style="padding:0px;">分類標籤：</div>
          </div>
        </div>
        
        <!-- 右邊下面的說明 -->
        <div class="container">
          <div class="row row4">
          
            <!-- 空格 -->
            <div class="col-6routeslope route" style="padding:0px;"></div>
            <!-- 瀏覽人數 -->
            <div class="col-6 routecount route" style="padding:0px;"><i class="fas fa-eye"></i>  ${activityVO.act_click}</div>
          </div>
        </div>
       </form>

      </div>
    </div>
  </div>
  </div>
 </c:forEach>
  

	


	<br>
	<br>
	<br>





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

<script>

//  var listSize ="${list.size()}";
//  var i;
 
 
// for(i = 1 ; 0 < listSize ; i++) {
// 	if(i%2==1){
// 		$(".row1").eq(i).addClass("bg-info");

// 	}
	
	
// }

	




</script>
        
        
  
  </body>
</html>