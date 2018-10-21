<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gp.model.*"%>
<%@ page import="com.favorite_gp.model.*"%>
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
		Favorite_GPService favSrc = new Favorite_GPService();
		List<GPVO> fav_GPList = favSrc.searchFavGP(((MemVO) (request.getSession().getAttribute("memVO"))).getMem_id());
		pageContext.setAttribute("fav_GPList",fav_GPList);
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
			  
			  	<c:forEach var="fav_GPVO" items="${fav_GPList}" begin="${param.currentPage==null?'0':10*param.currentPage-10}" end="${param.currentPage==null?'9':10*param.currentPage-1}">
				    <tr>
				      <th scope="row"><img src="<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${fav_GPVO.gp_id}"></th>
				      <td>${fav_GPVO.gp_date}</td>
				      <td>${fav_GPVO.gp_title}</td>
				      <td>${fav_GPVO.gp_desc}</td>
				      <td>${jSrc.findNumberOfGP(fav_GPVO)+1}/${fav_GPVO.max_num}</td>
				      <td>
				      		${"0" == fav_GPVO.gp_status ? '開放揪團中':''}
				      		${"1" == fav_GPVO.gp_status ? '報名截止':''}
				      		${"2" == fav_GPVO.gp_status ? '已結束':''}
				      		${"3" == fav_GPVO.gp_status ? '已流團':''}
				      		${"4" == fav_GPVO.gp_status ? '已流團':''}
				      </td>
				      <td>
				      	<a target="_blank" href="<%=request.getContextPath() %>/gp.do?gp_id=${fav_GPVO.gp_id}&action=GP_SEARCH" class="btn btn-info">查看</a>
				      	<a href="<%=request.getContextPath() %>/favorite_gp.do?gp_id=${fav_GPVO.gp_id}&action=GP_FAV_DEL" class="btn btn-info">取消收藏</a>
				      </td>
				    </tr>
			    </c:forEach>
			  </tbody>
			</table>
	</body>
	
	
	<link rel="stylesheet" href="<%=request.getContextPath() %>/sources/css/gp/GP_Managing.css">
	
</html>
					
					
					
					
					
					
					