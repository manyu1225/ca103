<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gp.model.*"%>
<%@ page import="com.joined_gp.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="java.sql.*" %>
				
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<jsp:useBean id="jSrc" class="com.joined_gp.model.JoinedGPService"/>
	<% 
		JoinedGPService joined_gpSrc = new JoinedGPService();
		List<GPVO> list = joined_gpSrc.SearchJoinedGPByMember(((MemVO) (request.getSession().getAttribute("memVO"))).getMem_id());
		pageContext.setAttribute("joined_GPList",list);
	%>
	
	<body>

			<table class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col" id="c1"><div class="imgCloumn">#</div></th>
			      <th scope="col" id="c2">Date</th>
			      <th scope="col" id="c3">Title</th>
			      <th scope="col" id="c4">Description</th>
			      <th scope="col" id="c5">Number</th>
			      <th scope="col" id="c6">Status</th>
			      <th scope="col" id="c7">Operation</th>
			    </tr>
			  </thead>
			  <tbody>
			  
			  	<c:forEach var="joined_GPVO" items="${joined_GPList}" begin="${param.currentPage==null?'0':10*param.currentPage-10}" end="${param.currentPage==null?'9':10*param.currentPage-1}">
				    <tr>
				      <th scope="row"><img src="<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${joined_GPVO.gp_id}"></th>
				      <td>${joined_GPVO.gp_date}</td>
				      <td>${joined_GPVO.gp_title}</td>
				      <td>${joined_GPVO.gp_desc}</td>
				      <td>${jSrc.findNumberOfGP(joined_GPVO)+1}/${joined_GPVO.max_num}</td>
				      <td>	
				      		${"0" == joined_GPVO.gp_status ? '開放揪團中':''}
				      		${"1" == joined_GPVO.gp_status ? '報名截止':''}
				      		${"2" == joined_GPVO.gp_status ? '已結束':''}
				      		${"3" == joined_GPVO.gp_status ? '已流團':''}
				      		${"4" == joined_GPVO.gp_status ? '已流團':''}
				      </td>
				      <td>
					      <a target="_blank" href="<%=request.getContextPath() %>/gp.do?gp_id=${joined_GPVO.gp_id}&action=GP_SEARCH" class="btn btn-info">查看</a>
					      <a href="<%=request.getContextPath() %>/joined_gp.do?gp_id=${joined_GPVO.gp_id}&action=GP_JOINED_DEL" class="btn btn-info">${(joined_GPVO.gp_status=="0" or joined_GPVO.gp_status=="1")?'退出揪團':'刪除揪團'}</a>
				      </td>
				      
				    </tr>
			    </c:forEach>
			  </tbody>
			</table>
	</body>
	
	
	<link rel="stylesheet" href="<%=request.getContextPath() %>/sources/css/gp/GP_Managing.css">
	
</html>
					
					
					
					
					
					
					