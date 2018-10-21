<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.routemessage.model.*"%>
<%@ page import="com.routemessagereport.model.*"%>
<%@ page import="com.routereport.model.*"%>
<%@ page import="com.route.model.*"%>
<!DOCTYPE html>
<html>
<head>

<!-- Required meta tags -->
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	
    <title>路線管理</title>
    
    <style type="text/css">
      .hidden{
        display: none;
    }
    	.cardclor{
      background-color: #007bff;
    }
    //定義各欄位的寬度
	 $grid-gutter-width: 30px ;
	.custom-table-width {
		overflow-x: auto;
		margin-right: $grid-gutter-width/2; // 利用 margin 推回去原本 padding 的空間
		margin-left : $ grid-gutter-width/ 2;
		padding-right: 0;
		padding-left: 0;
	}
	
	.table {
		table-layout: fixed; //表格和欄寬將根據所給定的寬度來顯示（預設等寬） 
		min-width: 1440px;	//避免因寬度不足而字疊在一起
	}
	
	//
	流水號 # thead th:first-child, tbody td:first-child {
		width: 2em;
	}
	
	//
	Time
	  thead th:nth-child(10), tbody td:nth-child(10) {
		width: 13em;
	}
	
	//
	Remark
	  thead th:last-child, tbody td:last-child {
		width: 20%;
	}
  </style>
</head>
<body>
<% 
		//	第一次連結進此檢舉頁面
		//	一：先取出「路線留言檢舉」的清單出來		
		if (request.getAttribute("rotMessRepList") == null) {
			RouteMessageReportService rotMessRepSvc = new RouteMessageReportService();
			RouteMessageReportVO rotMessRepVO;
			List<RouteMessageReportVO> rotMessRepList = rotMessRepSvc.getAll();
			while((rotMessRepList.size()%10) > 0){
				rotMessRepVO = new RouteMessageReportVO();
				rotMessRepVO.setMem_id("");
				rotMessRepList.add(rotMessRepVO);
			}
			pageContext.setAttribute("rotMessRepList", rotMessRepList);
			
			//	二：取出被檢舉的「路線留言」的清單出來
			RouteMessageService rotMessSvc = new RouteMessageService();
			List<RouteMessageVO> rotMessList = rotMessSvc.getAll();
			pageContext.setAttribute("rotMessList", rotMessList);
		}
		
		if (request.getAttribute("rotRepList") == null) {	
			//	三：取出「路線檢舉」的清單出來
			RouteReportService rotRepSvc = new RouteReportService();
			RouteReportVO rotRepVO;
			List<RouteReportVO> rotRepList = rotRepSvc.getAll();
			while((rotRepList.size()%10) > 0){
				rotRepVO = new RouteReportVO();
				rotRepVO.setMem_id("");
				rotRepList.add(rotRepVO);
			}
			pageContext.setAttribute("rotRepList", rotRepList);
			
			//	四：再取出被檢舉的「路線資料」的連結出來
			RouteService rotSvc = new RouteService();
			List<RouteVO> rotList = rotSvc.getRouteList("");
			pageContext.setAttribute("rotList", rotList);
		}
		
		String goWhere = (String)request.getAttribute("goWhere");
	    pageContext.setAttribute("goWhere",goWhere);
%>
	<%@ include file="/sources/file/Home/backNav.file"%>

	<nav aria-label="breadcrumb">
		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/BackHomePage.jsp">Home</a></li>
			<li class="breadcrumb-item active" aria-current="page">商品檢舉審核</li>
		</ol>
	</nav>
	<div class="container-fluid">
		<div class="row">
			
			<%@ include file="/sources/file/Home/backSidebar.file"%>
			
			<div class="col-10">
				<h2 class="text-center bg-warning p-4 text-white">
           			路線管理-審核相關檢舉
           		</h2>
       
    

	<div id="carouselExampleControls" class="carousel slide" data-ride="carousel" data-interval="false">
		<div class="carousel-inner">
			<div class="carousel-item ${ (goWhere!='routeReport')?'active':'' }">
			<!------------------------------------------ 這邊下面是畫面輪播的第一個畫面 -------------------------------------->
				<div class="container d-block w-100">
					<div class="row">
						<div class="col-12">
							<div class="container" style="margin-top: 15px;">
								<div class="row">
								<!------------------------- 這邊下面是表單的上方的選項 -------------------------------------->
								<button type="button" class="btn btn-outline-info" disabled="disabled">路線留言檢舉</button>
									<div class="col-10">
										<ul class="nav nav-tabs" id="myTab" role="tablist">
											<li class="nav-item">
												<form action="<%=request.getContextPath()%>/RouteMessageReport/RouteMessageReportServlet.do" Method="POST" name="routeMessageReportBefore">
													<input type="hidden" value="getRotMessRepList" name="action">
													<input type="hidden" value="0" name="rotMesRep_status">
													<input type="hidden" value="routeMessageReport" name="goWhere">
													<a class="nav-link ${('!=0' != rotMesRep_status)?'active':'' }"
														id="home-tab" href="javascript:document.routeMessageReportBefore.submit();"
														role="tab" aria-controls="home" aria-selected="true">
														待審核清單
													</a>
												</form>
											</li>
											<li class="nav-item">
												<form
													action="<%=request.getContextPath()%>/RouteMessageReport/RouteMessageReportServlet.do" Method="POST" name="routeMessageReportAfter">
													<input type="hidden" value="getRotMessRepList" name="action">
													<input type="hidden" value="!=0" name="rotMesRep_status">
													<input type="hidden" value="routeMessageReport" name="goWhere">
													<a	class="nav-link ${('!=0' == rotMesRep_status)?'active':'' }"
														id="contact-tab" href="javascript:document.routeMessageReportAfter.submit();" role="tab"
														aria-controls="home" aria-selected="false">
														已審核清單
													</a>
												</form>
											</li>
										</ul>
									</div>
								</div>
							</div>
							<br>
							<!------------------------------ 這邊下面是表單內容 ------------------------------------------->
							<div class="container shadow-lg">
								<div class="row">
									<table class="table table-hover">
										<thead>
											<tr>
												<th scope="col">項次</th>
												<th scope="col">檢舉人</th>
												<th scope="col"></th>
												<th scope="col"></th>
												<th scope="col" class="text-center">檢舉理由</th>
												<th scope="col"></th>
												<th scope="col"></th>
												<th scope="col">狀態</th>
												<th scope="col">檢舉時間</th>
											</tr>
										</thead>
										<tbody>
											<% int rotMesRepItems = 0; %>
											<c:forEach var="rotMessRepVO" items="${rotMessRepList}">
												<% rotMesRepItems++; %>
												<tr>
													<th scope="row"><%=rotMesRepItems%></th>
													<td>${ rotMessRepVO.mem_id }</td>
													<td colspan="5" class="text-center">${ rotMessRepVO.rotMesRep_cont }</td>
													<td>
														<a data-toggle="collapse" href="#${rotMessRepVO.mem_id}${ rotMessRepVO.rotMes_id }" aria-expanded="false" aria-controls="collapseExample">
															${ (rotMessRepVO.rotMesRep_status == 0)?'待審核':(rotMessRepVO.rotMesRep_status == 1 || rotMessRepVO.rotMesRep_status == 2)?'已結案':'' }
														</a>
													</td>
													<td>${ (rotMessRepVO.rotMesRep_time) }</td>
												</tr>
												<tr>
													<td colspan="9" style="padding: 0px;border=0px">
														<div class="collapse" id="${rotMessRepVO.mem_id}${ rotMessRepVO.rotMes_id }">
															<div class="card card-body" style="padding: 0px; border-top: 0px">
																<table class="table table-hover" style="background-color: #eee; margin: 0px; border: solid">
																	<thead>
																		<tr>
																			<th></th>
																			<th>被檢舉人</th>
																			<th></th>
																			<th></th>
																			<th class="text-center">被檢舉的留言內容</th>
																			<th></th>
																			<th></th>
																			<th>審核結果</th>
																			<th>時間</th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach var="rotMessVO" items="${rotMessList}">
																			<c:if test="${ rotMessVO.rotMes_id == rotMessRepVO.rotMes_id}">
																				<tr>
																					<th scope="row"></th>
																					<td>${ rotMessVO.mem_id }</td>
																					<td class="text-center" colspan=5>${ rotMessVO.rotMes_cont }</td>
																					<td><c:if test="${ rotMessRepVO.rotMesRep_status == 0 }">
																							<form action="<%=request.getContextPath()%>/RouteMessageReport/RouteMessageReportServlet.do" style="float: left">
																								<input type="hidden" name="action" value="confirm">
																								<input type="hidden" name="rotMesRep_status" value="1">
																								<input type="hidden" name="mem_id"	value="${ rotMessVO.mem_id }">
																								<input type="hidden" name="rotMes_id" value="${ rotMessVO.rotMes_id }">
																								<button type="submit" class="btn btn-primary btn-sm">核可</button>
																							</form>
																							<form action="<%=request.getContextPath()%>/RouteMessageReport/RouteMessageReportServlet.do" style="float: right">
																								<input type="hidden" name="action" value="confirm">
																								<input type="hidden" name="rotMesRep_status" value="2">
																								<input type="hidden" name="mem_id" value="${ rotMessVO.mem_id }">
																								<input type="hidden" name="rotMes_id" value="${ rotMessVO.rotMes_id }">
																								<button type="submit" class="btn btn-primary btn-sm">駁回</button>
																							</form>
																						</c:if>
																						<c:if test="${ rotMessRepVO.rotMesRep_status != 0 }">
																							<input type="hidden" name="rotMes_id" value="${ rotMessVO.rotMes_id }">
																							<b>${ (rotMessVO.rotMes_status == 0)?'核可':'駁回' }</b>
																						</c:if>
																					</td>
																					<td>${ rotMessVO.rotMes_time }</td>
																				</tr>
																			</c:if>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
														</div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="carousel-item ${ (goWhere=='routeReport')?'active':'' }">
				<!------------------------------------------ 這邊下面是畫面輪播的第二個畫面 -------------------------------------->
				<div class="container d-block w-100">
					<div class="row">
						<div class="col-12">
							<div class="container" style="margin-top: 15px;">
								<div class="row">
								<!------------------------- 這邊下面是表單的上方的選項 -------------------------------------->
								<button type="button" class="btn btn-outline-info" disabled="disabled">路線檢舉</button>
									<div class="col-10">
										<ul class="nav nav-tabs" id="myTab" role="tablist">
											<li class="nav-item" id="rotRepNotYet">
												<form action="<%=request.getContextPath()%>/RouteReport/RouteReportServlet.do" Method="POST" name="routeReportBefore">
													<input type="hidden" value="getRotRepList" name="action">
													<input type="hidden" value="0" name="rotRep_status">
													<input type="hidden" value="routeReport" name="goWhere">
													<a class="nav-link ${('!=0' != rotRep_status)?'active':'' }"
														id="home-tab" href="javascript:document.routeReportBefore.submit();"
														role="tab" aria-controls="home" aria-selected="true">
														待審核清單
													</a>
												</form>
											</li>
											<div id="rotRepFinish"></div>
											<li class="nav-item">
												<form
													action="<%=request.getContextPath()%>/RouteReport/RouteReportServlet.do" Method="POST" name="routeReportAfter">
													<input type="hidden" value="getRotRepList" name="action">
													<input type="hidden" value="!=0" name="rotRep_status">
													<input type="hidden" value="routeReport" name="goWhere">
													<a	class="nav-link ${('!=0' == rotRep_status)?'active':'' }"
														id="contact-tab" href="javascript:document.routeReportAfter.submit();" role="tab"
														aria-controls="home" aria-selected="false">
														已審核清單
													</a>
												</form>
											</li>
										</ul>
									</div>
								</div>
							</div>
							<br>
							<!------------------------------ 這邊下面是表單內容 ------------------------------------------->
							<div class="container shadow-lg">
								<div class="row">
									<table class="table table-hover">
										<thead>
											<tr>
												<th scope="col">項次</th>
												<th scope="col">檢舉人</th>
												<th scope="col"></th>
												<th scope="col"></th>
												<th scope="col" class="text-center">檢舉理由</th>
												<th scope="col"></th>
												<th scope="col"></th>
												<th scope="col">狀態</th>
												<th scope="col">檢舉時間</th>
											</tr>
										</thead>
										<tbody>
											<% int rotRepItems = 0; %>
											<c:forEach var="rotRepVO" items="${rotRepList}">
												<% rotRepItems++; %>
												<tr>
													<th scope="row"><%=rotRepItems%></th>
													<td>${ rotRepVO.mem_id }</td>
													<td colspan="5" class="text-center">${ rotRepVO.rotRep_cont }</td>
													<td>
														<a data-toggle="collapse" href="#${rotRepVO.mem_id}${ rotRepVO.rot_id }" aria-expanded="false" aria-controls="collapseExample">
															${ (rotRepVO.rotRep_status == 0)?'待審核':(rotRepVO.rotRep_status == 1 || rotRepVO.rotRep_status == 2)?'已結案':'' }
														</a>
													</td>
													<td>${ (rotRepVO.rotRep_time) }</td>
												</tr>
												<tr>
													<td colspan="9" style="padding: 0px;border=0px">
														<div class="collapse" id="${rotRepVO.mem_id}${ rotRepVO.rot_id }">
															<div class="card card-body" style="padding: 0px; border-top: 0px">
																<table class="table table-hover" style="background-color: #eee; margin: 0px; border: solid">
																	<thead>
																		<tr>
																			<th></th>
																			<th>被檢舉人</th>
																			<th></th>
																			<th></th>
																			<th class="text-center">被檢舉的路線名稱</th>
																			<th></th>
																			<th></th>
																			<th>審核結果</th>
																			<th></th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach var="rotVO" items="${rotList}">
																			<c:if test="${ rotVO.rot_id == rotRepVO.rot_id}">
																				<tr>
																					<th scope="row"></th>
																					<td>${ rotVO.mem_id }</td>
																					<td class="text-center" colspan=5>
																						<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route${rotRepVO.mem_id}${ rotRepVO.rot_id }">
																							<input type="hidden" value="getRouteDetailed" name="action">
																							<input type="hidden" value="${ rotVO.rot_id }" name="rot_id">
																							<input type="hidden" value="updataRoute" name="back_manager">
																							<a href="javascript:document.route${rotRepVO.mem_id}${ rotRepVO.rot_id }.submit();">
																								${ rotVO.rot_name }
																							</a>
																						</form>
																					</td>
																					<td><c:if test="${ rotRepVO.rotRep_status == 0 }">
																							<form action="<%=request.getContextPath()%>/RouteReport/RouteReportServlet.do" Method="POST" style="float: left">
																								<input type="hidden" name="action" value="confirm">
																								<input type="hidden" name="rotRep_status" value="1">
																								<input type="hidden" name="mem_id"	value="${ rotVO.mem_id }">
																								<input type="hidden" name="rot_id" value="${ rotVO.rot_id }">
																								<input type="hidden" value="routeReport" name="goWhere">
																								<button type="submit" class="btn btn-primary btn-sm">核可</button>
																							</form>
																							<form action="<%=request.getContextPath()%>/RouteReport/RouteReportServlet.do" Method="POST" style="float: right">
																								<input type="hidden" name="action" value="confirm">
																								<input type="hidden" name="rotRep_status" value="2">
																								<input type="hidden" name="mem_id" value="${ rotVO.mem_id }">
																								<input type="hidden" name="rot_id" value="${ rotVO.rot_id }">
																								<input type="hidden" value="routeReport" name="goWhere">
																								<button type="submit" class="btn btn-primary btn-sm">駁回</button>
																							</form>
																						</c:if>
																						<c:if test="${ rotRepVO.rotRep_status != 0 }">
																							<input type="hidden" name="rot_id" value="${ rotVO.rot_id }">
																							<b>${ (rotRepVO.rotRep_status == 1)?'核可':(rotRepVO.rotRep_status == 2)?'駁回':'' }</b>
																						</c:if>
																					</td>
																					<td></td>
																				</tr>
																			</c:if>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
														</div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev" style="background-color: #eee; width:10%">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span>
			<span class="sr-only">Previous</span>
		</a>
		<a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next" style="background-color: #eee; width:10%">
		<span class="carousel-control-next-icon" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
		</div>
           		</div>
           		</div>
           		</div>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"	crossorigin="anonymous"></script>
<script	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  
</body>
</html>