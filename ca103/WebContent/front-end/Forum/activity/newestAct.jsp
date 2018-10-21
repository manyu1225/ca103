<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.activity.model.*"%>

	

<%
	Activity_Service activitySvc = new Activity_Service();
	List<Activity_VO> list = activitySvc.getAllAct();
	pageContext.setAttribute("list", list);
%>
	
	
	<jsp:useBean id="forClass_Svc" scope="page"
	class="com.forPos_class.model.Forum_class_Service" />
	
	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">


	
	<style>
	.word{
		font-size:13px;
	}
	
	a > small{
			font-size:4px;
	}
	
	
		.list-group-item {
		  position: relative;
		  display: block;
		  padding: 0.75rem 1.25rem;
		  margin-bottom: -1px;
		  background-color: #fff;
		  border: none;
		}
		
		
		#newestActDate{
		font-size:0.5rem;
		
		
		}

	
	</style>





<title>Lately Post</title>
</head>
<body>


	<h3>
		<i class="fa fa-newspaper-o"></i>最近活動
	</h3>



	<c:forEach  var="activityVO" items="${list}" begin="0" end="4" step="1">
	
		<div class="list-group">
			
			<a href="activity.do?act_ID=${activityVO.act_ID}&action=getOneAct_onPage&act_click=${activityVO.act_click}" class="box-padding list-group-item list-group-item-action flex-column align-items-start  list-group-item-light">
				<div class="d-flex w-100 justify-content-between">
					<h5 class="mb-1 word"><b>${activityVO.act_name}</b></h5>
				</div>
				<small>報名日期</small>
				<small id="newestActDate"><fmt:formatDate  value="${activityVO.act_regis_date}" pattern="yyyy-MM-dd "/></small>
				
			</a>
		</div>
		
	</c:forEach>



</body>
</html>