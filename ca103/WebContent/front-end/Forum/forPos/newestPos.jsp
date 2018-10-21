<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.forPos.model.*"%>

	
					<!--///////////////// 此處為最新文章側邊欄列表  給單一頁面include用////////////// -->

<%
	Forum_post_Service forPosSvc = new Forum_post_Service();
	List<Forum_post_VO> list = forPosSvc.getAllForPos();
	pageContext.setAttribute("list", list);
%>
	
	
	<jsp:useBean id="forClass_Svc" scope="page"
	class="com.forPos_class.model.Forum_class_Service" />
	
	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">


	
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
		
		
		#newestPosDate{
		font-size:0.5rem;
		
		
		}

	
	</style>





<title>Lately Post</title>
</head>
<body>


	<h3>
		<i class="fa fa-newspaper-o"></i>最新文章
	</h3>



	<c:forEach  var="forPosVO" items="${list}" begin="0" end="4" step="1">
	
		<div class="list-group">
			
			<a href="<%=request.getContextPath()%>/forPos/forPos.do?forPost_ID=${forPosVO.forPost_ID}&mem_ID=${forPosVO.mem_ID}&action=getOnePos_onPage&forPost_view=${forPosVO.forPost_view}&forPost_state=${forPosVO.forPost_state}" class="box-padding list-group-item list-group-item-action flex-column align-items-start  list-group-item-light">
				<div class="d-flex w-100 justify-content-between">
					<h5 class="mb-1 word"><b>${forPosVO.forPost_theme}</b></h5>
				</div>
				<small>${forPosVO.mem_ID} 張貼於</small>
				<small id="newestPosDate"><fmt:formatDate  value="${forPosVO.forPost_time}" pattern="yyyy-MM-dd "/></small>
				
			</a>
		</div>
		
	</c:forEach>



</body>
</html>