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
<!--     <meta charset="utf-8"> -->
<!--     <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> -->
    
        <!-- Bootstrap CSS -->
<!--     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous"> -->
	



	<!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
<!--     <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script> -->
<!--     <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script> -->
    
    
<title>Insert title here</title>
</head>
<body>
	<jsp:useBean id="jSrc" class="com.joined_gp.model.JoinedGPService"/>
	<jsp:useBean id="gpSrc" class="com.gp.model.GPService"/>
		
		
		<div class="contatiner-fluid">
			
			<div class="col-md-12 col-sm-12">
				<nav>
				  <div class="nav nav-tabs" id="nav-tab" role="tablist">
					    <a class="nav-item nav-link active" id="nav-1-tab" data-toggle="tab" href="#nav-1" role="tab" aria-controls="nav-1" aria-selected="true">全部揪團</a>
					    <a class="nav-item nav-link" id="nav-2-tab" data-toggle="tab" href="#nav-2" role="tab" aria-controls="nav-2" aria-selected="false">已截止</a>
					    <a class="nav-item nav-link" id="nav-3-tab" data-toggle="tab" href="#nav-3" role="tab" aria-controls="nav-3" aria-selected="false">已結束</a>
					    <a class="nav-item nav-link" id="nav-4-tab" data-toggle="tab" href="#nav-4" role="tab" aria-controls="nav-4" aria-selected="false">已流團</a>
					    <a class="nav-item nav-link" id="nav-5-tab" data-toggle="tab" href="#nav-5" role="tab" aria-controls="nav-5" aria-selected="false">已封存</a>
					    <a class="nav-item nav-link" id="nav-6-tab" data-toggle="tab" href="#nav-6" role="tab" aria-controls="nav-6" aria-selected="false">正在揪團</a>
				  </div>
				</nav>
				<div class="tab-content" id="nav-tabContent">
<!-- 					第一頁 -->
						  <div class="tab-pane fade show active" id="nav-1" role="tabpanel" aria-labelledby="nav-1-tab">
										  	<table class="table table-striped" style="overflow:hidden">
											  <thead>
											    <tr>
											      <th scope="col" id="c1"><div class="imgCloumn">#</div></th>
											      <th scope="col" id="c8">揪團ID</th>
											      <th scope="col" id="c2">Date</th>
											      <th scope="col" id="c3">Title</th>
											      <th scope="col" id="c5">Number</th>
											      <th scope="col" id="c6">Status</th>
											      <th scope="col" id="c7">Operation</th>
											    </tr>
											  </thead>
											  <tbody>
											  	
											  	<c:forEach var="allGPVO" items="${gpSrc.forGP_Manager('order by cre_time')}" begin="${param.currentPage==null?'0':10*param.currentPage-10}" end="${param.currentPage==null?9:10*param.currentPage-1}">
													<tr>
												      <th scope="row"><img src="<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${allGPVO.gp_id}"></th>
												      <td>${allGPVO.gp_id}</td>
												      <td>${allGPVO.gp_date}</td>
												      <td>${allGPVO.gp_title}</td>
												      <td>${jSrc.findNumberOfGP(allGPVO)+1}/${allGPVO.max_num}</td>
												      <td>
												      
															${"0" == allGPVO.gp_status ? '開放揪團中':''}
												      		${"1" == allGPVO.gp_status ? '報名截止':''}
												      		${"2" == allGPVO.gp_status ? '已結束':''}
												      		${"3" == allGPVO.gp_status ? '已流團':''}
												      		${"4" == allGPVO.gp_status ? '已流團':''}
												      		${"10" == allGPVO.gp_status ? '已封存':''}
															${"1" == allGPVO.pub_set ?'(未公開)':''}
															
													  </td>
												      <td>
												      
												      
												      	<a target="_blank" href="<%=request.getContextPath() %>/gp.do?gp_id=${allGPVO.gp_id}&action=GP_SEARCH&isEmp=true" class="btn btn-info">查看</a>
												      	<c:if test="${allGPVO.gp_status != '10'}">
													    	<a gp_id="${allGPVO.gp_id}" class="sealBtn btn btn-info">封存</a>
													    </c:if>
													    
												      </td>
												    </tr>
												    
											 	</c:forEach>
											 </tbody>
												  
												  
												  
											</table>
						  
		  									<div class="row">
												<select class="currentPage">
													<c:forEach var="myData" begin="1" end="${gpSrc.forGP_Manager('order by cre_time').size()/10+9/10}" >
							                    		<option value="${myData}" ${param.currentPage == myData?'selected':''}>第${myData}頁</option>
							                      	</c:forEach>
												</select>

											</div>
						  
						  
						  </div>
						  
						  
						  <div class="tab-pane fade" id="nav-2" role="tabpanel" aria-labelledby="nav-2-tab">
						  			<table class="table table-striped" style="overflow:hidden">
											  <thead>
											    <tr>
											      <th scope="col" id="c1"><div class="imgCloumn">#</div></th>
											      <th scope="col" id="c8">揪團ID</th>
											      <th scope="col" id="c2">Date</th>
											      <th scope="col" id="c3">Title</th>
											      <th scope="col" id="c5">Number</th>
											      <th scope="col" id="c6">Status</th>
											      <th scope="col" id="c7">Operation</th>
											    </tr>
											  </thead>
											  <tbody>
											  
											  	<c:forEach var="allGPVO" items="${gpSrc.forGP_Manager('where gp_status=1 order by cre_time')}" begin="${param.currentPage==null?'0':10*param.currentPage-10}" end="${param.currentPage==null?9:10*param.currentPage-1}">
													<tr>
												      <th scope="row"><img src="<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${allGPVO.gp_id}"></th>
												      <td>${allGPVO.gp_id}</td>
												      <td>${allGPVO.gp_date}</td>
												      <td>${allGPVO.gp_title}</td>
												      <td>${jSrc.findNumberOfGP(allGPVO)+1}/${allGPVO.max_num}</td>
												      <td>報名截止</td>
												      <td>
												      	<a target="_blank" href="<%=request.getContextPath() %>/gp.do?gp_id=${allGPVO.gp_id}&action=GP_SEARCH&isEmp=true" class="btn btn-info">查看</a>
												      	<c:if test="${allGPVO.gp_status != '10'}">
													    	<a gp_id="${allGPVO.gp_id}" class="sealBtn btn btn-info">封存</a>
													    </c:if>
													    
												      </td>
												    </tr>
											 	</c:forEach>
											 </tbody>
										</table>
										
										<div class="row">
											<select class="currentPage">
												<c:forEach var="myData" begin="1" end="${gpSrc.forGP_Manager('where gp_status=1 order by cre_time').size()/10+9/10}" >
						                    		<option value="${myData}" ${param.currentPage == myData?'selected':''}>第${myData}頁</option>
						                      	</c:forEach>
											</select>
										</div>
						  </div>
						
						
						  <div class="tab-pane fade" id="nav-3" role="tabpanel" aria-labelledby="nav-3-tab">
									<table class="table table-striped" style="overflow:hidden">
										  <thead>
										    <tr>
										      <th scope="col" id="c1"><div class="imgCloumn">#</div></th>
										      <th scope="col" id="c8">揪團ID</th>
										      <th scope="col" id="c2">Date</th>
										      <th scope="col" id="c3">Title</th>
										      <th scope="col" id="c5">Number</th>
										      <th scope="col" id="c6">Status</th>
										      <th scope="col" id="c7">Operation</th>
										    </tr>
										  </thead>
										  <tbody>
										  
										  	<c:forEach var="allGPVO" items="${gpSrc.forGP_Manager('where gp_status=2 order by cre_time')}" begin="${param.currentPage==null?'0':10*param.currentPage-10}" end="${param.currentPage==null?9:10*param.currentPage-1}">
												<tr>
											      <th scope="row"><img src="<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${allGPVO.gp_id}"></th>
											      <td>${allGPVO.gp_id}</td>
											      <td>${allGPVO.gp_date}</td>
											      <td>${allGPVO.gp_title}</td>
											      <td>${jSrc.findNumberOfGP(allGPVO)+1}/${allGPVO.max_num}</td>
											      <td>已結束</td>
											      <td>
											      	<a target="_blank" href="<%=request.getContextPath() %>/gp.do?gp_id=${allGPVO.gp_id}&action=GP_SEARCH&isEmp=true" class="btn btn-info">查看</a>
											      	<c:if test="${allGPVO.gp_status != '10'}">
												    	<a gp_id="${allGPVO.gp_id}" class="sealBtn btn btn-info">封存</a>
												    </c:if>
												    
											      </td>
											    </tr>
										 	</c:forEach>
										 </tbody>
									</table>
									<div class="row">
										<select class="currentPage">
											<c:forEach var="myData" begin="1" end="${gpSrc.forGP_Manager('where gp_status=2 order by cre_time').size()/10+9/10}" >
					                    		<option value="${myData}" ${param.currentPage == myData?'selected':''}>第${myData}頁</option>
					                      	</c:forEach>
										</select>
									</div>
			
						  </div>
						 
						 
						  <div class="tab-pane fade" id="nav-4" role="tabpanel" aria-labelledby="nav-4-tab">
									<table class="table table-striped" style="overflow:hidden">
										  <thead>
										    <tr>
										      <th scope="col" id="c1"><div class="imgCloumn">#</div></th>
										      <th scope="col" id="c8">揪團ID</th>
										      <th scope="col" id="c2">Date</th>
										      <th scope="col" id="c3">Title</th>
										      <th scope="col" id="c5">Number</th>
										      <th scope="col" id="c6">Status</th>
										      <th scope="col" id="c7">Operation</th>
										    </tr>
										  </thead>
										  <tbody>
										  	<c:forEach var="allGPVO" items="${gpSrc.forGP_Manager('where gp_status=3 OR gp_status=4 order by cre_time')}" begin="${param.currentPage==null?'0':10*param.currentPage-10}" end="${param.currentPage==null?9:10*param.currentPage-1}">
												<tr>
											      <th scope="row"><img src="<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${allGPVO.gp_id}"></th>
											      <td>${allGPVO.gp_id}</td>
											      <td>${allGPVO.gp_date}</td>
											      <td>${allGPVO.gp_title}</td>
											      <td>${jSrc.findNumberOfGP(allGPVO)+1}/${allGPVO.max_num}</td>
											      <td>已流團</td>
											      <td>
											      	<a target="_blank" href="<%=request.getContextPath() %>/gp.do?gp_id=${allGPVO.gp_id}&action=GP_SEARCH&isEmp=true" class="btn btn-info">查看</a>
											      	<c:if test="${allGPVO.gp_status != '10'}">
												    	<a gp_id="${allGPVO.gp_id}" class="sealBtn btn btn-info">封存</a>
												    </c:if>
												    
											      </td>
											    </tr>
										 	</c:forEach>
										 </tbody>
									</table>
									
									<div class="row">
										<select class="currentPage">
											<c:forEach var="myData" begin="1" end="${gpSrc.forGP_Manager('where gp_status=3 OR gp_status=4 order by cre_time').size()/10+9/10}" >
					                    		<option value="${myData}" ${param.currentPage == myData?'selected':''}>第${myData}頁</option>
					                      	</c:forEach>
										</select>
									</div>
						  </div>
						 
							
						  <div class="tab-pane fade" id="nav-5" role="tabpanel" aria-labelledby="nav-5-tab">
										<table class="table table-striped" style="overflow:hidden">
											  <thead>
											    <tr>
											      <th scope="col" id="c1"><div class="imgCloumn">#</div></th>
											      <th scope="col" id="c8">揪團ID</th>
											      <th scope="col" id="c2">Date</th>
											      <th scope="col" id="c3">Title</th>
											      <th scope="col" id="c5">Number</th>
											      <th scope="col" id="c6">Status</th>
											      <th scope="col" id="c7">Operation</th>
											    </tr>
											  </thead>
											  <tbody>
											  	<c:forEach var="allGPVO" items="${gpSrc.forGP_Manager('where gp_status=10 order by cre_time')}" begin="${param.currentPage==null?'0':10*param.currentPage-10}" end="${param.currentPage==null?9:10*param.currentPage-1}">
													<tr>
												      <th scope="row"><img src="<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${allGPVO.gp_id}"></th>
												      <td>${allGPVO.gp_id}</td>
												      <td>${allGPVO.gp_date}</td>
												      <td>${allGPVO.gp_title}</td>
												      <td>${jSrc.findNumberOfGP(allGPVO)+1}/${allGPVO.max_num}</td>
												      <td>已流團</td>
												      <td>
												      	<a target="_blank" href="<%=request.getContextPath() %>/gp.do?gp_id=${allGPVO.gp_id}&action=GP_SEARCH&isEmp=true" class="btn btn-info">查看</a>
												      	<c:if test="${allGPVO.gp_status != '10'}">
													    	<a gp_id="${allGPVO.gp_id}" class="sealBtn btn btn-info">封存</a>
													    </c:if>
													    
												      </td>
												    </tr>
											 	</c:forEach>
											 </tbody>
										</table>
										
																				
										<div class="row">
											<select class="currentPage">
												<c:forEach var="myData" begin="1" end="${gpSrc.forGP_Manager('where gp_status=10 order by cre_time').size()/10+9/10}" >
						                    		<option value="${myData}" ${param.currentPage == myData?'selected':''}>第${myData}頁</option>
						                      	</c:forEach>
											</select>
										</div>
						  </div>
				 
				 		  
				 		  <div class="tab-pane fade" id="nav-6" role="tabpanel" aria-labelledby="nav-6-tab">
										<table class="table table-striped" style="overflow:hidden">
											  <thead>
											    <tr>
											      <th scope="col" id="c1"><div class="imgCloumn">#</div></th>
											      <th scope="col" id="c8">揪團ID</th>
											      <th scope="col" id="c2">Date</th>
											      <th scope="col" id="c3">Title</th>
											      <th scope="col" id="c5">Number</th>
											      <th scope="col" id="c6">Status</th>
											      <th scope="col" id="c7">Operation</th>
											    </tr>
											  </thead>
											  <tbody>
											  	<c:forEach var="allGPVO" items="${gpSrc.forGP_Manager('where gp_status=0 order by cre_time')}" begin="${param.currentPage==null?'0':10*param.currentPage-10}" end="${param.currentPage==null?9:10*param.currentPage-1}">
													<tr>
												      <th scope="row"><img src="<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${allGPVO.gp_id}"></th>
												      <td>${allGPVO.gp_id}</td>
												      <td>${allGPVO.gp_date}</td>
												      <td>${allGPVO.gp_title}</td>
												      <td>${jSrc.findNumberOfGP(allGPVO)+1}/${allGPVO.max_num}</td>
												      <td>揪團中</td>
												      <td>
												      	<a target="_blank" href="<%=request.getContextPath() %>/gp.do?gp_id=${allGPVO.gp_id}&action=GP_SEARCH&isEmp=true" class="btn btn-info">查看</a>
												      	<c:if test="${allGPVO.gp_status != '10'}">
													    	<a gp_id="${allGPVO.gp_id}" class="sealBtn btn btn-info">封存</a>
													    </c:if>
													    
												      </td>
												    </tr>
											 	</c:forEach>
											 </tbody>
										</table>
										
										<div class="row">
											<select class="currentPage">
												<c:forEach var="myData" begin="1" end="${gpSrc.forGP_Manager('where gp_status=0 order by cre_time').size()/10+9/10}" >
						                    		<option value="${myData}" ${param.currentPage == myData?'selected':''}>第${myData}頁</option>
						                      	</c:forEach>
											</select>
										</div>
										
										
						  </div>
						  
				 
				 </div>
					

			
						
						
	 	</div>
		 	 
		 	 
		 	 
		</div>
		
		
		
		
		<script>
				$(function(){
					<c:if test="${param.tab==null}">
						var tab = 1;
					</c:if>
					<c:if test="${param.tab!=null}">
					var tab = ${param.tab};
					</c:if>
					
					for(let i=1;i<=6;i++){
						$('#nav-' + i +'-tab').click(function(){
							if(${param.currentPage!=null} && ${param.currentPage != 1}){
								window.location.href = "<%=request.getContextPath() %>/back-end/gp/GP_Manager.jsp?currentPage=1&tab=" + i;
							}
							tab=i;
// 							$('.currentPage').val(1);
						});
					}
					$('#nav-${param.tab}-tab').tab('show');
					$('.currentPage').change(function(){
						window.location.href = "<%=request.getContextPath() %>/back-end/gp/GP_Manager.jsp?currentPage=" + $(this).val() + "&tab=" + tab;
					})
					$('.sealBtn').click(function(){
						window.location.href = "<%=request.getContextPath() %>/gp.do?gp_id=" + $(this).attr('gp_id') + "&action=GP_SEAL&tab=" + tab + "&currentPage=" + $('.currentPage').val();
					});
					
				});
				
				
		</script>
		
		
</body>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/sources/css/gp/GP_Managing.css">

</html>




