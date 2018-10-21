<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.activity.model.*"%>

<%

Activity_VO activityVO = new Activity_VO();
Activity_Service actSvc = new Activity_Service();
List<Activity_VO> list = actSvc.getAllAct();

pageContext.setAttribute("list", list);

Activity_VO activityVO2 = (Activity_VO)request.getAttribute("activityVO"); 



%>


<jsp:useBean id="listEmps_ByCompositeQuery" scope="request" type="java.util.List<Activity_VO>" />


<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <title>Hello, world!</title>
  </head>
  
  
  <style>
  
  .act_name{
  
  font-family:"微軟正黑體";
  font-size:1.5rem;
  color:#07C;
  
  
  }
  

  
  </style>
  
  <body>


<br>
<br>
<br>
<br>
<div class="container">
	<form action="<%=request.getContextPath()%>/activity/activity.do" Method="POST">
		
		
		<div class="row">
			<div class="col-3">
				<input type="text" class="form-control form-control-sm" name="act_sDate" placeholder="活動開始日期"> 
			</div>
			<div class="col-3">
					<input type="text" class="form-control form-control-sm" name="act_eDate" placeholder="活動結束日期"> 
			</div>
			<div class="col-2">
				<select class="form-control form-control-sm" name="act_state">
					<option value=">-100">---活動狀態---</option>
					<option value="<5" >報名中</option>
					<option value="<10" >未開放報名</option>
				</select>
			</div>


			<div class="col-3">
				<input class="form-control form-control-sm" type="text" placeholder="請輸入關鍵字" name="text">
			</div>		
			<div class="col-1">
				<input type="hidden" value="getRouteList" name="action">
				<input type="submit" value="送出查詢" class="btn btn-outline-secondary btn-sm">
			</div>	
		</div>
			


		<div class="row d-flex justify-content-end mt-3">
			<div class="btn-group btn-group-toggle " data-toggle="buttons">
				<label class="btn btn-sm btn-outline-secondary active">
					<input type="radio" name="options" id="option1" autocomplete="off" checked> 最新活動
				</label>
				<label class="btn btn-sm btn-outline-secondary">
					<input type="radio" name="options" id="option2" autocomplete="off"> 熱門活動
				</label>
				<label class="btn btn-sm btn-outline-secondary">
					<input type="radio" name="options" id="option3" autocomplete="off"> 近五天活動
				</label>
			</div>

			</div>
	</form>
</div>





	<%@ include file="page/page1_ByCompositeQuery.file" %>

 <c:forEach var="activityVO" items="${listEmps_ByCompositeQuery}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>"  >
  <div class="container" style="background-color: #ecf5ff; margin-top:15px">    
    <div class="row row1 align-items-center">
      <!-- 放圖片 -->
      <div class="col-5 routeimg1 route " style="padding:0px;text-align:center">
		<img src="<%=request.getContextPath()%>/activity/activity.do?action=getListPic&act_ID=${activityVO.act_ID}" style="width: 350px; height: 200px;">									 	
	</div>
      <!-- 圖片右邊的說明 -->
      <div class="col-7 routeimg2 route" style="padding:0px;">
  
        <!-- 右邊最上面的說明 -->
        <div class="container">
          <div class="row row2 ">
            <!-- 活動名稱 -->
            
<%--             <%System.out.println(activityVO.getAct_holder()); %> --%>
--${activityVO.act_ID}
            
            <div class="col-6 act_name mb-5" style="padding:0px;">
				<a class="font-weight-bold" 
				href="activity.do?act_ID=${activityVO.act_ID}&action=getOneAct_onPage">${activityVO.act_name}</a>
			</div>
			
			<form action="<%=request.getContextPath()%>/activity.do" Method="POST">
            <!-- 主辦單位 -->
            <div class="col-6 routemem route" style="padding:0px;"><small>主辦單位：${activityVO.act_holder}</small></div>
            </form>
          </div>
        </div>
        
      <form action="<%=request.getContextPath()%>/activity/activity.do" Method="POST">
        <!-- 右邊中間的說明 -->
        <div class="container">
          <div class="row row3">
            <!-- 分類標籤 -->
            <div class="col-12 routedes route" style="padding:0px;">分類標籤：</div>
          </div>
        </div>
        
        <!-- 右邊下面的說明 -->
        <div class="container">
          <div class="row row4">
          
            <!-- 空格 -->
            <div class="col-6routeslope route" style="padding:0px;"></div>
            <!-- 熱門排名 -->
            <div class="col-6 routecount route" style="padding:0px;">瀏覽數：</div>
          </div>
        </div>
       </form>

      </div>
    </div>
  </div>
 </c:forEach>
  
<%@ include file="page/page2_ByCompositeQuery.file" %>

	


	<br>
	<br>
	<br>





    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>