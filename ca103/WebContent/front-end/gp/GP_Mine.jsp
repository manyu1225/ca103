<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gp.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="java.sql.*" %>
				
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
<!--     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous"> -->
	



	<!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
<!--     <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script> -->
<!--     <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script> -->
				
	<jsp:useBean id="jSrc" class="com.joined_gp.model.JoinedGPService"/>
	<% 
		GPService gpSrc = new GPService();
		List<GPVO> list = gpSrc.searchCreGP(((MemVO) (request.getSession().getAttribute("memVO"))).getMem_id());
		pageContext.setAttribute("creGPList",list);
	%>
				
				
	<body>

			<table class="table table-striped" style="overflow:hidden">
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
			  
			  	<c:forEach var="creGPVO" items="${creGPList}" begin="${param.currentPage==null?'0':10*param.currentPage-10}" end="${param.currentPage==null?'9':10*param.currentPage-1}">
				    <tr>
				      <th scope="row"><img src="<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${creGPVO.gp_id}"></th>
				      <td>${creGPVO.gp_date}</td>
				      <td>${creGPVO.gp_title}</td>
				      <td>${creGPVO.gp_desc}</td>
				      <td>${jSrc.findNumberOfGP(creGPVO)+1}/${creGPVO.max_num}</td>
				      <td>
							${"0" == creGPVO.gp_status ? '開放揪團中':''}
				      		${"1" == creGPVO.gp_status ? '報名截止':''}
				      		${"2" == creGPVO.gp_status ? '已結束':''}
				      		${"3" == creGPVO.gp_status ? '已流團':''}
							${"1" == creGPVO.pub_set ?'(未公開)':''}
					  </td>
				      <td>
				      	<a target="_blank" href="<%=request.getContextPath() %>/gp.do?gp_id=${creGPVO.gp_id}&action=GP_SEARCH" class="btn btn-info">查看</a>
				      	<% if(((GPVO)pageContext.getAttribute("creGPVO")).getGp_status()<=1){%> 
					      	<a href="<%=request.getContextPath() %>/gp.do?gp_id=${creGPVO.gp_id}&action=GP_CANCEL" class="btn btn-info">取消揪團</a>
				      		
				      	<%}else{%>
					      	<a href="<%=request.getContextPath() %>/gp.do?gp_id=${creGPVO.gp_id}&action=GP_DELETE" class="btn btn-info">刪除紀錄</a>
						<%} %>
				      </td>
				    </tr>
			    </c:forEach>
			  </tbody>
			</table>
	</body>
	
	
	<link rel="stylesheet" href="<%=request.getContextPath() %>/sources/css/gp/GP_Managing.css">
	
</html>
					
					
					
					
					
					
					