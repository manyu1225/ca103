<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.reported_gp.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<!-- <link rel="stylesheet" -->
<!-- 	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" -->
<!-- 	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" -->
<!-- 	crossorigin="anonymous"> -->

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" -->
<!-- 	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" -->
<!-- 	crossorigin="anonymous"></script> -->
<!-- <script -->
<!-- 	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" -->
<!-- 	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" -->
<!-- 	crossorigin="anonymous"></script> -->
<!-- <script -->
<!-- 	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" -->
<!-- 	integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" -->
<!-- 	crossorigin="anonymous"></script> -->

<title>GP_Report</title>

<style>
//
定義各欄位的寬度
		 $grid-gutter-width: 30px ; .custom-table-width {
	overflow-x: auto;
	margin-right: $grid-gutter-width/2; // 利用 margin 推回去原本 padding 的空間
	margin-left : $ grid-gutter-width/ 2;
	padding-right: 0;
	padding-left: 0;
}

.table {
	table-layout: fixed; //
	表格和欄寬將根據所給定的寬度來顯示（預設等寬） min-width: 1440px;
	//
	避免因寬度不足而字疊在一起
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

	<jsp:useBean id="memSrc" class="com.mem.model.MemService"/>
	<jsp:useBean id="gpSrc" class="com.gp.model.GPService"/>

	<%
		Reported_GPService repSrc = new Reported_GPService();
		List<Reported_GPVO> repGPList0 = repSrc.getAllRepGP(false);
		List<Reported_GPVO> repGPList1 = repSrc.getAllRepGP(true);

		if ("tab1".equals(request.getParameter("tab_list"))) {
			pageContext.setAttribute("repGPList", repGPList0);
		} else {

			pageContext.setAttribute("repGPList", repGPList1);
		}
	%>







	<div id="carouselExampleControls" class="carousel slide"
		data-ride="carousel" data-interval="false">
		<div class="carousel-inner">
			<%-- 			<div class="carousel-item ${ (goWhere!='routeReport')?'active':'' }"> --%>
			<!------------------------------------------ 這邊下面是畫面輪播的第一個畫面 -------------------------------------->
			<div class="container d-block w-100">
				<div class="row">
					<div class="col-12">
						<div class="container" style="margin-top: 15px;">
							<div class="row">
								<!------------------------- 這邊下面是表單的上方的選項 -------------------------------------->
								<div class="col-12">
								
									<ul class="nav nav-tabs" id="myTab" role="tablist">
										<li class="nav-item"><a
											class="nav-link ${('tab1' == param.tab_list)?'active':'' }"
											id="home-tab"
											href="<%=request.getContextPath() %>/back-end/gp/GP_Report.jsp?tab_list=tab1&action=GP_Report"
											role="tab" aria-controls="home" aria-selected="true">
												待審核清單 </a></li>
										<li class="nav-item"><a
											class="nav-link ${('tab2' == param.tab_list)?'active':'' }"
											id="contact-tab"
											href="<%=request.getContextPath() %>/back-end/gp/GP_Report.jsp?tab_list=tab2&action=GP_Report"											
											role="tab" aria-controls="home" aria-selected="false">
												已審核清單 </a></li>
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
										<%
											int rotMesRepItems = 0;
										%>
										<c:forEach var="repGPVO" items="${repGPList}" begin="0"
											end="${repGPList.size()}">
											<%
												rotMesRepItems++;
											%>
											<tr>
												<th scope="row"><%=rotMesRepItems%></th>
												<td>${memSrc.findMemById(repGPVO.mem_id).mem_firstname}${memSrc.findMemById(repGPVO.mem_id).mem_lastname}</td>
												<td colspan="5" class="text-center">${ repGPVO.rep_detail }</td>
												<td><a data-toggle="collapse"
													href="#${repGPVO.mem_id}${repGPVO.gp_id}"
													aria-expanded="false" aria-controls="collapseExample">
														${ (repGPVO.rep_status == 0)?'待審核':'已結案' } 
												</a></td>
												<td><fmt:formatDate value="${ (repGPVO.rep_time) }" pattern="yyyy-MM-dd"/></td>
											</tr>
											<tr>
												<td colspan="9" style="padding: 0px;border=0px">
													<div class="collapse"
														id="${repGPVO.mem_id}${repGPVO.gp_id}">
														<div class="card card-body"
															style="padding: 0px; border-top: 0px">
															<table class="table table-hover"
																style="background-color: #eee; margin: 0px; border: solid">
																<thead>
																	<tr>
																		<th></th>
																		<th>被檢舉人</th>
																		<th></th>
																		<th></th>
																		<th class="text-center">被檢舉的揪團</th>
																		<th></th>
																		<th></th>
																		<th>審核結果</th>
																		<th>時間</th>
																	</tr>
																</thead>
																<tbody>
																	<tr>
																		<th scope="row"></th>
																		<td>${memSrc.findMemById(gpSrc.searchGPVOById(repGPVO.gp_id).mem_id).mem_firstname}${memSrc.findMemById(gpSrc.searchGPVOById(repGPVO.gp_id).mem_id).mem_lastname}</td>
																		
																		<td class="text-center" colspan=5><a
																			target="_blank" 
																			href="<%=request.getContextPath() %>/gp.do?gp_id=${repGPVO.gp_id}&action=GP_SEARCH&isEmp=true">${ repGPVO.gp_id }</a>
																		</td>
																		<td><c:if test="${ 'tab1' == param.tab_list }">
																				<a class="btn btn-primary btn-sm"
																					href="<%=request.getContextPath()%>/reported_gp.do?action=REPSUCCESS&mem_id=${repGPVO.mem_id}&gp_id=${repGPVO.gp_id}">核可</a>
																				<a class="btn btn-primary btn-sm"
																					href="<%=request.getContextPath()%>/reported_gp.do?action=REPFAIL&mem_id=${repGPVO.mem_id}&gp_id=${repGPVO.gp_id}">駁回</a>
																			</c:if> <c:if test="${ 'tab2' == param.tab_list }">
																				<input type="hidden" name="rotMes_id"
																					value="${ rotMessVO.rotMes_id }">
																				<b>${ (repGPVO.rep_status == 1)?'核可':'駁回' }</b>
																			</c:if></td>
																		<td><fmt:formatDate value="${ repGPVO.rep_time }" pattern="yyyy-MM-dd"/></td>
																	</tr>
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
			<!-- 			</div> -->

		</div>

	</div>





</body>
</html>