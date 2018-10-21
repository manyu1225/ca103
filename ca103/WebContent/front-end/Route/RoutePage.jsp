<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.route.model.*"%>
<%@ page import="com.routemessage.model.*"%>
<%@ page import="com.routecollection.model.*"%>
<%@ page import="com.routereport.model.*"%>
<%@ page import="com.routemessagereport.model.*"%>
<%@ page import="com.mem.model.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<!--      google-api,不可省略-->
	<script src="https://www.google.com/jsapi"></script>
	<!--      漂亮icon的引用-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<!--      漂亮跳窗的引用-->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.28.1/sweetalert2.all.min.js" type="text/javascript"></script>
	
	<style>
        #map {
            height: 700px;
            width: 100%;
        }
        
        html {
  			height: 100%; 
	  	}
	  	
		body{
		 	font-family: Comic Sans MS,arial,helvetica,sans-serif;
		    background-position: center;
		    background-size: cover;
		    background-repeat: no-repeat;
	   		background-attachment: fixed;
		    background-image: url(<%=request.getContextPath()%>/sources/img/Route/bg13.jpg);
	    }
        #MyBlog{
		    position: fixed;  /*固定在網頁上不隨卷軸移動，若要隨卷軸移動用absolute*/
		    top: 85%;  /*設置垂直位置*/
		    right: -120px;  /*設置水平位置，依所放的內容多寡需要自行手動調整*/
		    background: #ffffff;  /*背景顏色*/
		    padding: 10px 20px;
		    border-radius: 10px;  /*圓角*/
		    -moz-border-radius: 10px;
		    -webkit-border-radius: 10px;
		    opacity:0.9;
		}

		#MyBlog:hover{  /*當滑鼠移至此區塊時，伸縮區塊*/
		    right: -20px;
		}
		
		#MyBlog #title{
		    padding-right: 5px;  /*讓標題與連結中間有空隙*/
		}
    </style>
	<title>Insert title here</title>
</head>
<body>
<a id="pageTop"></a>
<%@ include file="/sources/file/Home/NavBar.file" %>
<% 
	//	取得此路線的留言List,以便於在路線下方顯示留言內容
	List<RouteMessageVO> listRouteMessage = (List)session.getAttribute("listRouteMessage");
	pageContext.setAttribute("listRouteMessage",listRouteMessage);
	
	//	顯示此路線是否有被登入的該會員收藏,以便於在路線下方的收藏按鈕的顯示與隱藏
	Integer collOrNot = (Integer)session.getAttribute("collOrNot");
    pageContext.setAttribute("collOrNot",collOrNot);
    
	//	顯示此路線是否有被登入的該會員檢舉,以便於在路線下方的檢舉按鈕的顯示與隱藏
	Integer rotRepOrNot = (Integer)session.getAttribute("rotRepOrNot");
    pageContext.setAttribute("rotRepOrNot",rotRepOrNot);
    
    //	顯示此則留言是否有被登入的該會員檢舉,以便於在留言內部的檢舉按鈕的顯示與隱藏
	List listRouteMessageReport = (List)session.getAttribute("listRouteMessageReport");
	pageContext.setAttribute("listRouteMessageReport",listRouteMessageReport);
	
	//	找出會員資料
	List<MemVO> memList = (List<MemVO>)session.getAttribute("memList");
	pageContext.setAttribute("memList",memList);
	
	//	定位用
	String goWhere = (String)request.getAttribute("goWhere");
	pageContext.setAttribute("goWhere",goWhere);
	
	//	換頁定位用
	Integer insertMessagePageIndex = (Integer)request.getAttribute("pageIndex");
	pageContext.setAttribute("insertMessagePageIndex",insertMessagePageIndex);
	
	//	取得送出命令的tokens
	String uuid = UUID.randomUUID().toString();
	session.setAttribute("uuid", uuid);
%>
<%@ include file="/sources/file/Route/RouteMessagePageTop.file" %>
	<br>
	<br>
	
	<div class="container">
		<div class="row">
			<div class="col-12">
	<div id="map"></div>
    <!--      地圖出現的地方,style設定float是為了稍微排版,讓下面的經緯度顯示在地圖的右方-->
    </div>
	    </div>
    </div>
	<br>
<a id="collectionLocation" href="#collectionLocation"></a>
<a id="routeReport" href="#routeReport"></a>
    <div class="container">
		<div class="row">
			<div class="col-12">
			    <div id="chart_div" style="width: 100%; height: 250px;"></div>
			    <!--  面積高度圖長出來的位置    -->
		    </div>
	    </div>
    </div>

<br>
	<div class="container" style="opacity:0.9">
		<div class="row">
			<div class="col-12">
			    <div class="card" >
					<div class="card-header">
						<span><b>路線名稱：</b>${routeVO.rot_name}</span>
						<span style="float:right ">
							<div class="custom-control custom-radio custom-control-inline">
								<input class="custom-control-input" type="radio" name="rot_status" id="rot_status1" value="1" disabled ${('1'==routeVO.rot_status)?'checked':'' }>
								<label class="custom-control-label" for="rot_status1">公開路線</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input class="custom-control-input" type="radio" name="rot_status" id="rot_status2" value="0" disabled ${('0'==routeVO.rot_status)?'checked':'' }>
								<label class="custom-control-label" for="rot_status2">私人路線</label>
								<span class="d-inline-block" tabindex="0" data-toggle="tooltip" 
							title="公開路線：在公開的瀏覽路線中可以查詢得到此次建立的路線，&#10;　　　　　並且此路線會被加入到您的收藏列表中。&#10;　　　　　但是您將無法對路線做出修改與刪除&#10;
私人路線：在公開的瀏覽路線中無法查詢得到此次建立的路線，&#10;　　　　　必須由您的收藏路線列表中詢得。&#10;　　　　　您可以修改此路線的相關資訊。">
								　<i class="fa fa-cog fa-spin" aria-hidden="true" style="color:red;"></i>
								</span> 
							</div>
						</span>
					</div>
					<div class="card-body" >
						<blockquote class="blockquote mb-0">
							<div style="margin-bottom:10px">
								<span style="width:200px;margin-right:10px"><b>路線起點：</b>	${ routeVO.rot_loc_start }</span>
								<span><b>地點描述：</b>${ routeVO.rot_loc_start_des }</span>
							</div>
							<div style="margin-bottom:10px">
								<span style="width:200px;margin-right:10px"><b>路線終點：</b>	${ routeVO.rot_loc_end }</span>
								<span><b>地點描述：</b>${ routeVO.rot_loc_end_des }</span>
							</div>	
							<div style="">
								<b>路線描述：</b>${routeVO.rot_describe}
							</div>
						</blockquote>
						<div style="height:10px"></div>
						<footer class="blockquote-footer text-right ">
							建立者： 
							<cite title="Source Title">
				            	<c:forEach var="memVO" items="${memList}">
					            	<c:if test="${routeVO.mem_id == memVO.mem_id}">
					            		<a href="javascript:document.go${ routeVO.mem_id }.submit();">${ memVO.mem_nickname }</a>
					            		<form type="POST" action="<%=request.getContextPath()%>/front-end/mem/mem.do" name="go${ routeVO.mem_id }">
					            		<input type="hidden" name="action" value="goToMemberView" >
					            		<input type="hidden" name="mem_id" value="${ routeVO.mem_id }" >
					            		</form>
					          		</c:if>
				          		</c:forEach>
				          		<c:if test="${ routeVO.mem_id == null}">
					          			官方建立
					          	</c:if>
							</cite>
						</footer>
					</div>
				</div>
		    </div>
	    </div>
    </div>
	<br>
	<div class="container" style="opacity:0.9">
		<div class="row">
			<div class="col-12">
				<div class="card">
					<div class="card-header">
						<b>路線詳細資訊：</b>
					</div>
					<div class="card-body">
						<div class="container">
							<div class="row">
								<div class="col-12">
									<div class="card-deck">
										<div class="card">
											<div class="card-body">
												<h5><b>路線難度：</b>${routeVO.rot_hard}</h5>
												<h5><b>路線距離：</b>${routeVO.rot_dis} 公里</h5>
												
											</div>
										</div>
										<div class="card">
											<div class="card-body">
												<h5></h5>
												<h5><b>爬升高度：</b>${routeVO.rot_height_up} 公尺</h5>
												<h5><b>下降高度：</b>${routeVO.rot_height_down} 公尺</h5>
												<h5><b>最高海拔：</b>${routeVO.rot_height_ave} 公尺</h5>
											</div>
										</div>
										<div class="card">
											<div class="card-body">
												<h5><b>爬升累計坡度：</b>${routeVO.rot_slope_up} %</h5>
												<h5><b>爬升平均坡度：</b>${routeVO.rot_slope_down} %</h5>
												<h5><b>高度平均坡度：</b>${routeVO.rot_slope_ave} %</h5>
												<h5></h5>
											</div>
										</div>
									</div><br>
									<div class="container">
										<div class="row">
											<div class="col-6 align-self-start">
 												<a id="routeMessagePage" href="#routeMessagePage"></a><!-- 換頁的錨點 -->
												<c:if test="${ routeVO.rot_status == 1 }">
													<b>熱門度：</b>${routeVO.rot_popu}　
<!-- 													<b>路線評分：</b>尚未實裝 -->
												</c:if>
											</div>
											<c:if test="${ memVO != null || memVO.mem_id != null }">
											<c:if test="${ routeVO.rot_status == 0 }">
												<div class="col-3 align-self-start">
												</div>
												<div class="col-auto ml-auto">
												<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="deleteRouteForm">
													<input type="hidden" value="closeRoute" name="action">
													<input type="hidden" value="front" name="front">
													<input type="hidden" value=${routeVO.rot_id} name="rot_id">
													<button type="button" class="btn btn-primary" value="刪除路線" onclick="deleteRoute(this)">刪除路線</button>
												</form>
												</div>
												<div class="col-auto ml-auto">
													<!-- 路線修改按鈕!! -->
													<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST">	
														<input type="hidden" value="getRouteDetailed" name="action">
														<input type="hidden" value="${routeVO.rot_id}" name="rot_id">
														<input type="hidden" value="updataRoute" name="front_manager">
														<input type="submit" class="btn btn-primary" value="修改路線">
													</form>
												</div>
											</c:if>
											<c:if test="${ routeVO.rot_status == 1 }">
												<c:if test="${ collOrNot == 0 }">
												<div class="col-auto ml-auto">
													<!-- 路線收藏按鈕!! -->
													<form action="<%=request.getContextPath()%>/RouteCollection/RouteCollectionServlet.do" Method="POST">
														<input type="hidden" value="insertRoute" name="action">
														<input type="hidden" value=${routeVO.rot_id} name="rot_id">
														<input type="hidden" value="collectionLocation" name="goWhere">
														<input type="hidden" name="uuid" value="<%=uuid %>">
														<input type="submit" class="btn btn-primary" value="收藏路線">
													</form>
												</div>
												</c:if>
												<c:if test="${ collOrNot != 0 }">
												<div class="col-auto ml-auto">
													<!-- 刪除路線收藏按鈕!! -->
													<form action="<%=request.getContextPath()%>/RouteCollection/RouteCollectionServlet.do" Method="POST">
														<input type="hidden" value="deleteCollectionRoute" name="action">
														<input type="hidden" value=${routeVO.rot_id} name="rot_id">
														<input type="hidden" value="collectionLocation" name="goWhere">
														<input type="submit" class="btn btn-primary" value="刪除收藏">
													</form>
												</div>
												</c:if>
												<div class="col-auto">
													<!-- 留言按鈕!! -->
													<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#RouteMessage">
													  路線留言
													</button>
												</div>
												<c:if test="${ rotRepOrNot == 0 }">
													<div class="col-auto">
														<!-- 路線檢舉按鈕!! -->
														<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#RouteReport">
														  路線檢舉
														</button>
													</div>
												</c:if>
											</c:if>
											</c:if>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 留言跳窗的內容 -->
	<form action="<%=request.getContextPath()%>/RouteMessage/RouteMessageServlet.do" Method="POST" name="routeMessageInsertForm">
	<input type="hidden" value="insertMessage" name="action">
	<div class="modal fade" id="RouteMessage" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalCenterTitle">路線留言</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	<div class="form-group">
			    <textarea class="form-control" rows="5" name="rotMes_cont" id="rotMes_cont" maxlength="100"></textarea>
			</div>
			<div class="form-group" style="text-align:right;">
				<div> - 上限100個字</div>
			</div>
	      </div>
	      <div class="modal-footer">
	      	<input type="hidden" name="rot_id" value=${routeVO.rot_id}>
	      	<input type="hidden" value="routeMessageInsert" name="goWhere">
	      	<!-- 無留言時才不會出現錯誤訊息 -->
	      	<c:if test="<%= pageIndexArray.length > 0 %>">
		      	<input type="hidden" value="<%= pageIndexArray[pageIndexArray.length-1] %>" name="pageIndex">
	      	</c:if>
	        <button type="button" class="btn btn-secondary" id="cancelRouteMessageInsert" data-dismiss="modal" onclick="cancelRouteMessage()">放棄</button>
	        <button type="button" class="btn btn-primary" onclick="RouteMessageInsert()">留言</button>
	      </div>
	    </div>
	  </div>
	</div>
	</form>

	<!-- 檢舉路線跳窗的內容 -->
	<form action="<%=request.getContextPath()%>/RouteReport/RouteReportServlet.do" Method="POST" name="RouteReportForm">
	<input type="hidden" value="insertRouteReport" name="action">
	<div class="modal fade" id="RouteReport" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <div><h5 class="modal-title" id="exampleModalCenterTitle">檢舉路線 </h5></div>
	        <div style="float:right"><h7 class="modal-title" id="routeReportWindowTitle"></h7>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="margin:-16px">
	          <span aria-hidden="true">&times;</span>
	        </button>
	        </div>
	      </div>
	      <div class="modal-body">
	      	<div class="form-group">
			    <textarea class="form-control" rows="5" name="rotRep_cont" id="rotRep_cont" maxlength="100"></textarea>
			</div>
			<div class="form-group" style="text-align:right;">
				<div> - 上限100個字</div>
			</div>
	      </div>
	      <div class="modal-footer">
	      	<input type="hidden" name="rot_id" value=${routeVO.rot_id}>
	      	<input type="hidden" value="routeReport" name="goWhere">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="cancelRouteReport" onclick="CancelRouteReport()">放棄</button>
	        <button type="button" class="btn btn-primary" onclick="RouteReport()">檢舉</button>
	      </div>
	    </div>
	  </div>
	</div>
	</form>
	
	<br>
	<a id="routeMessageInsert" href="#routeMessageInsert"></a>
	<c:if test="${ routeVO.rot_status == 1 && listRouteMessage.size() > 0 }">
	<c:forEach var="routeMessageVO" items="${listRouteMessage}"  begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										<!-- 檢舉路線留言跳窗的內容 -->
										<form action="<%=request.getContextPath()%>/RouteMessageReport/RouteMessageReportServlet.do" Method="POST" name="repMesForm${routeMessageVO.rotMes_id}">
											<input type="hidden" value="insertMessageReport" name="action">
											<div class="modal fade" id="RouteMessageReport${routeMessageVO.rotMes_id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
												<div class="modal-dialog modal-dialog-centered" role="document">
													<div class="modal-content">
														<div class="modal-header">
															<h5 class="modal-title" id="exampleModalCenterTitle">檢舉路線留言</h5>
															<button type="button" class="close" data-dismiss="modal" aria-label="Close">
																<span aria-hidden="true">&times;</span>
															</button>
														</div>
														<div class="modal-body">
															<div class="form-group">
																<textarea class="form-control" rows="5" name="rotMesRep_cont" id="rotMesRep_cont${routeMessageVO.rotMes_id}" maxlength="100"></textarea>
															</div>
															<div class="form-group" style="text-align:right;">
																<div> - 上限100個字</div>
															</div>
														</div>
														<div class="modal-footer">
															<input type="hidden" name="rot_id" value=${routeVO.rot_id}>
															<input type="hidden" name="rotMes_id" value=${routeMessageVO.rotMes_id}>
															<input type="hidden" value="<%= pageIndex %>" name="pageIndex">
															<input type="hidden" value="routeMessageInsert" name="goWhere">
															<button type="button" class="btn btn-secondary" data-dismiss="modal" id="noReport${routeMessageVO.rotMes_id}">放棄</button>
															<button type="button" class="btn btn-primary" id="report${routeMessageVO.rotMes_id}">檢舉</button>
														</div>
													</div>
												</div>
											</div>
										</form>
										<!-- 路線留言修改跳窗的內容 -->
										<form action="<%=request.getContextPath()%>/RouteMessage/RouteMessageServlet.do" Method="POST" name="updateRouteMessage${routeMessageVO.rotMes_id}">
											<input type="hidden" value="updateRouteMessage" name="action">
											<div class="modal fade" id="RouteMessageUpdate${routeMessageVO.rotMes_id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
												<div class="modal-dialog modal-dialog-centered" role="document">
													<div class="modal-content">
														<div class="modal-header">
															<h5 class="modal-title" id="exampleModalCenterTitle">修改留言</h5>
															<button type="button" class="close" data-dismiss="modal" aria-label="Close">
																<span aria-hidden="true">&times;</span>
															</button>
														</div>
														<div class="modal-body">
															<div class="form-group">
																<textarea class="form-control" rows="5" name="rotMes_cont" id="cont${routeMessageVO.rotMes_id}" maxlength="100">${routeMessageVO.rotMes_cont}</textarea>
															</div>
															<div class="form-group" style="text-align:right;">
																<div> - 上限100個字</div>
															</div>
														</div>
														<div class="modal-footer">
															<input type="hidden" name="rot_id" value=${routeVO.rot_id}>
															<input type="hidden" name="rotMes_id" value=${routeMessageVO.rotMes_id}>
															<input type="hidden" name="whichPage" value="<%= whichPage %>">
															<input type="hidden" name="pageIndex" value="<%= pageIndex %>">
															<input type="hidden" value="routeMessageInsert" name="goWhere">
															<button type="button" class="btn btn-secondary" data-dismiss="modal" id="c${routeMessageVO.rotMes_id}">放棄</button>
															<button type="button" class="btn btn-primary" id="u${routeMessageVO.rotMes_id}">確定</button>
														</div>
													</div>
												</div>
											</div>
										</form>
															<script type="text/javascript">
															
															<!-- 以下為留言修改的跳窗事件 -->
															document.getElementById("c${routeMessageVO.rotMes_id}").onclick = function canc${routeMessageVO.rotMes_id}(){
																document.getElementById("cont${routeMessageVO.rotMes_id}").value = "${routeMessageVO.rotMes_cont}";
																console.log("放棄");
																return;
															}
															
															document.getElementById("u${routeMessageVO.rotMes_id}").onclick = function u${routeMessageVO.rotMes_id}(obj){
																	if(document.getElementById("cont${routeMessageVO.rotMes_id}").value.trim() == ""){
																		console.log("空白不能修改文");
																		document.getElementById("cont${routeMessageVO.rotMes_id}").value = "${routeMessageVO.rotMes_cont}";
																		document.getElementById("c${routeMessageVO.rotMes_id}").click();
																		return;
																	}
																	
																	swal({
															            title: "確定修改？",
															            html: "確定要修改留言內容嗎?",
															            type: "warning",
															            showCancelButton: true//顯示取消按鈕
															        }).then(
															            function (result) {
															                if (result.value) {
															                	//使用者按下「確定」要做的事
															                    document.updateRouteMessage${routeMessageVO.rotMes_id}.submit();
															                } else if (result.dismiss === "cancel"){
															                     //使用者按下「取消」要做的事
															                     document.getElementById("c${routeMessageVO.rotMes_id}").click();
															                }//end else  
															            });//end then 
																	
																}
																
																<!-- 以下為留言檢舉的跳窗事件 -->
																document.getElementById("noReport${routeMessageVO.rotMes_id}").onclick = function noReport${routeMessageVO.rotMes_id}(){
																	document.getElementById("rotMesRep_cont${routeMessageVO.rotMes_id}").value = "";
																	console.log("放棄檢舉");
																	return;
																}
																
																
																document.getElementById("report${routeMessageVO.rotMes_id}").onclick = function report${routeMessageVO.rotMes_id}(obj){
																	if(document.getElementById("rotMesRep_cont${routeMessageVO.rotMes_id}").value.trim() == ""){
																		console.log("空白不能檢舉文");
																		document.getElementById("rotMesRep_cont${routeMessageVO.rotMes_id}").value = "";
																		document.getElementById("noReport${routeMessageVO.rotMes_id}").click();
																		return;
																	}
																	
																	swal({
															            title: "確定檢舉？",
															            html: "確定要檢舉此留言內容嗎?",
															            type: "warning",
															            showCancelButton: true//顯示取消按鈕
															        }).then(
															            function (result) {
															                if (result.value) {
															                	//使用者按下「確定」要做的事
															                    document.repMesForm${routeMessageVO.rotMes_id}.submit();
															                } else if (result.dismiss === "cancel"){
															                     //使用者按下「取消」要做的事
															                     document.getElementById("noReport${routeMessageVO.rotMes_id}").click();
															                }//end else  
															            });//end then 
																	
																}
															</script>
								    </c:forEach>
    <div class="container" style="opacity:0.9">
		<div class="row">
			<div class="col-12">
				<div class="card">
					<div class="card-header">
						<b>路線留言：</b>
					</div>
					<div class="card-body">
						<blockquote class="blockquote mb-0">
								
								<% int messageCount = 0;%>
									<!-- 下面判斷新增留言後要跳到哪一個分頁,取得controller的值,覆蓋掉RouteMessagePageTop.file中的值 -->
									<c:if test="${ (goWhere == 'routeMessageInsert') && (pageNumber != 0) }">
										<% pageIndex = insertMessagePageIndex; %>
										<% whichPage = pageNumber; %>
									</c:if>
									<c:forEach var="routeMessageVO" items="${listRouteMessage}"  begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
									<% messageCount++ ; %>
										<!-- 路線留言的內容!! -->
										<div class="container">
											<div class="row">
												<div class="col-12">
													<div class="alert bg-light text-dark border border-primary" role="alert" style="height:160px";>
														<div class="container" style="height:60px;">
															<div class="row">
																<div class="col-2" style="text-align:center;">
																	<c:forEach var="memVO" items="${memList}">
														            	<c:if test="${routeMessageVO.mem_id == memVO.mem_id}">
																			<img style="height:90px" src="<%=request.getContextPath()%>/Mem_photoReader?mem_ac=${ memVO.mem_ac }">
														          		</c:if>
													          		</c:forEach>
																</div>
																	<div class="col-10" style="height:70px" >
																		<div class="container" style=" border-width:1px;  border-bottom-style:solid; border-color:#555; height:100px;">
																			<div class="row">
																				<div class="col-11">
																					<c:if test="${ routeMessageVO.rotMes_status == 1 }">
																						${routeMessageVO.rotMes_cont}
																					</c:if>
																					<c:if test="${ routeMessageVO.rotMes_status == 0 }">
																						<b style="font-style:italic;opacity:0.5">--此留言已被刪除--</b>
																					</c:if>
																				</div>
																				<div class="col-1 text-right" style="font-family:cursive;font-size:17px">
																					${listRouteMessage.indexOf(routeMessageVO) + 1}樓
																				</div>
																			</div>
																		</div>
																	
																	<div class="container" style="height:60px;">
																		<div class="row">
																			<div class="col-6">
																				<c:forEach var="memVO" items="${memList}">
																	            	<c:if test="${routeMessageVO.mem_id == memVO.mem_id}">
																	            		<a href="javascript:document.go${ routeMessageVO.rotMes_id }.submit();">${ memVO.mem_nickname }</a>
																	            		<form type="POST" action="<%=request.getContextPath()%>/front-end/mem/mem.do" name="go${ routeMessageVO.rotMes_id }">
																	            		<input type="hidden" name="action" value="goToMemberView" >
																	            		<input type="hidden" name="mem_id" value="${ memVO.mem_id }" >
																	            		</form>
																	          		</c:if>
																          		</c:forEach>
																			</div>
																			<div class="col-4 text-right">
																				${ TimeTool.timeTool(routeMessageVO.rotMes_time)}
																			</div>
																				<!-- 有登入過才會有下列三個按鈕 -->
																				<c:if test="${ memVO != null || memVO.mem_id != null }">
																				<!-- 此則留言狀態為「顯示」,則列出下列三個按鈕 -->
																				<c:if test="${ routeMessageVO.rotMes_status == 1 }">
																				<!-- 判斷此則留言是否有被登入的會員檢舉過 -->
																				<c:if test="${ (routeMessageVO.mem_id != memVO.mem_id) && !listRouteMessageReport.contains(routeMessageVO.rotMes_id) }">
																					<div class="col-1">
																					</div>
																					<div class="col-1">
																						<!-- 路線留言檢舉按鈕!! -->
																						<button type="button" class="btn btn-sm btn-outline-danger"  data-toggle="modal" data-target="#RouteMessageReport${routeMessageVO.rotMes_id}">
																						  檢舉
																					</div>
																				</c:if>
																				<c:if test="${ routeMessageVO.mem_id == memVO.mem_id }">
																					<div class="col-1">
																						<!-- 路線留言修改按鈕!! -->
																						<button type="button" class="btn btn-outline-primary btn-sm" data-toggle="modal" data-target="#RouteMessageUpdate${routeMessageVO.rotMes_id}">
																						  修改
																					</div>
																					<div class="col-1">
																						<!-- 路線留言刪除按鈕!! -->
																						<button type="button" class="btn btn-outline-primary btn-sm" data-toggle="modal" onclick="deleteRouteMessage${routeMessageVO.rotMes_id}()">
																						  刪除
																					</div>
																					<form action="<%=request.getContextPath()%>/RouteMessage/RouteMessageServlet.do" Method="POST" name="deleteRouteMessageForm${routeMessageVO.rotMes_id}">
																						<input type="hidden" name="action" value="closeRouteMessage"></input>
																						<input type="hidden" name="front" value="front"></input>
																						<input type="hidden" name="rotMes_id" value="${routeMessageVO.rotMes_id}"></input>
																						<input type="hidden" value="routeMessageInsert" name="goWhere">
																						<input type="hidden" value="<%= pageIndexArray[pageIndexArray.length-1] %>" name="pageIndex">
																						<input type="hidden" name="rot_id" value="${routeVO.rot_id}"></input>
																					</form>
																					
																					<script type="text/javascript">
																						function deleteRouteMessage${routeMessageVO.rotMes_id}(obj){
																							swal({
																					            title: "確定刪除？",
																					            html: "按下確定後留言會永久刪除",
																					            type: "warning",
																					            showCancelButton: true//顯示取消按鈕
																					        }).then(
																					            function (result) {
																					                if (result.value) {
																					                    //使用者按下「確定」要做的事
																					                    swal({
																					                    	title: "完成!",
																					                        html: "留言已經刪除",
																					                        type: "success",
																					                    }).then(
																					                    		function (result) {
																					                                if (result.value) {
																					                                	document.deleteRouteMessageForm${routeMessageVO.rotMes_id}.submit();
																					                                }
																					                    })
																					                } else if (result.dismiss === "cancel"){
																					                     //使用者按下「取消」要做的事
																					                    swal("取消", "留言未被刪除", "error");
																					                }//end else  
																					            });//end then 
																						}
																					</script>
																					
																					
																				</c:if>
																			</c:if>
																			</c:if>
																		</div>
																	</div>
																	</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
										</c:forEach>
										
    							<%@ include file="/sources/file/Route/RouteListPageBottom.file" %> 
						</blockquote>
					</div>
				</div>
    		</div>
	    </div>
    </div> 
    </c:if>
    <br>
    <br>
  <div id="MyBlog">
		<span id="title">回頁首</span>
		<a href="#pageTop">View the Map</a>
	</div>
	<br>
	<br>
	
	<br>
	<br>


	<%! String[] gps; %>
	<% 	String rot_start = ((RouteVO)session.getAttribute("routeVO")).getRot_start();
		String rot_end = ((RouteVO)session.getAttribute("routeVO")).getRot_end();
		String rot_gps = ((RouteVO)session.getAttribute("routeVO")).getRot_gps();
		
		//	處理資料庫中的GPS原始資料,去除「( , )」的符號取出純數字
	 	String[] tokens = rot_gps.split(",|\\)|\\(");
	 	List gpsList = new ArrayList();
	 	for(int i = 0; i < tokens.length; i++){
	 		if(!(tokens[i].trim()).equals("")){
	 			gpsList.add(tokens[i].trim());
	 		}
	 	}
	 	//	處理後的GPS純數字資料放入陣列中轉交JavaScript繼續處理
	 	gps = new String[gpsList.size()];
	 	for(int i =0; i < gpsList.size(); i++){
	 		gps[i] = (String)gpsList.get(i);
	 	}
	 %>

	<script type="text/javascript">
	
	//	為了show高度圖必須,不能省略
	google.load('visualization', '1', {packages: ['columnchart']});
	
	var rot_gpsArray = new Array();
	
	//	初始化地圖
	function initMap() {
	//	畫出路線圖
	directionsDisplay = new google.maps.DirectionsRenderer();
 	//	畫出高度圖
 	elevator = new google.maps.ElevationService;
	  
	//	處理純數字的GPS資料,重新整理成google map讀得懂的latlng物件
	<%for (int j = 0; j < gps.length; j++) {
		if (j % 2 == 0) {%>
			var p = new google.maps.LatLng(<%=gps[j]%>, <%=gps[j + 1]%>);
			rot_gpsArray.push(p);
			<%}
		}%>
	
	//	建立地圖本體的主要方法,起始位置與起始地圖大小
	map = new google.maps.Map(document.getElementById('map'), {
	  center: {lat: 25.101, lng: 121.452},
	  zoom: 15
	});
	
	//	繪製出此路徑的路線
	var path = new google.maps.Polyline({
	    path: rot_gpsArray,
	    strokeColor: '#0000cc',
	    strokeOpacity: 0.8,
	    strokeWeight: 4
	});
	 
	//	繪製出來的路徑放到地圖上
	path.setMap(map);
	
	//	加入起點圖標
	var startMarker = new google.maps.Marker({
	    position: rot_gpsArray[0],
		map : map,
		animation: google.maps.Animation.DROP,
	});
	
	//	加入路線1/4的座標(用作支援地圖大小顯示)
	var supportMarker1 = new google.maps.Marker({
	    position: rot_gpsArray[Math.round(rot_gpsArray.length / 4)],
		map : null,
	});
	
	//	加入路線2/4的座標(用作支援地圖大小顯示)
	var supportMarker2 = new google.maps.Marker({
	    position: rot_gpsArray[Math.round(rot_gpsArray.length / 2)],
		map : null,
	});
	
	//	加入路線3/4的座標(用作支援地圖大小顯示),若gps小於4點則直接取最後一點
	if(rot_gpsArray.length > 4){
		var supportMarker3 = new google.maps.Marker({
		    position: rot_gpsArray[Math.round(rot_gpsArray.length / 4 * 3)],
			map : null,
		});
	}else {
		var supportMarker3 = new google.maps.Marker({
		    position: rot_gpsArray[rot_gpsArray.length - 1],
			map : null,
		});
	}
	
	//	加入終點圖標
	var endMarker = new google.maps.Marker({
		position : rot_gpsArray[rot_gpsArray.length -1 ],
		map : map,
		animation: google.maps.Animation.DROP,
		
	});
	
	//	座標都加入陣列,讓地圖去處理邊界大小
	var markers = [ startMarker, supportMarker1, supportMarker2, supportMarker3, endMarker ];
	
	//	處理呈現的地圖邊界大小
	var newBoundary = new google.maps.LatLngBounds();
	for (var i = 0; i < markers.length; i++) {
		var position = markers[i].position;
		newBoundary.extend(position);
	}
	map.fitBounds(newBoundary);
	
	//	數據轉交繪製高度圖
	displayPathElevation(rot_gpsArray, elevator, map);
	}

	//	使用此數組創建PathElevationRequest對象。沿著該路徑詢問256個樣本。啟動路徑請求。
	function displayPathElevation(rot_gpsArray, elevator, map) {
		elevator.getElevationAlongPath({
			'path' : rot_gpsArray,
			'samples' : 512,
		}, plotElevation);
	} //繼續呼叫plotElevation()方法把路線資料傳入畫高度圖

	function plotElevation(elevations, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			
			//	chart_div DIV中創建一個新圖表。面積高度圖長出來的位置
			var charta = new google.visualization.AreaChart(document.getElementById('chart_div'));
			
			//	提取填充圖表的數據。
			var data = new google.visualization.DataTable();
	
			//	Y軸為下面取出來的資料,資料為number格式,放進來
			data.addColumn('number', '高度');
	
			//	X軸為i跑回圈的數值
			for (var i = 0; i < elevations.length; i++) {
				data.addRow([ elevations[i].elevation ]);
			}
		} else {
			window.alert('Directions request failed due to ' + status);
		}

		var chartOptions = {
			title : '',
			titleX : '距離(m)',
			titleY : '海拔高度(m)',
			legend : 'none',
			curveType : 'function',
			dataOpacity : 1.0,
			fontSize : 8,
			pointSize : 0,
		};

		//面積圖繪圖
		charta.draw(data, chartOptions);
	}
	
	function CancelRouteReport(){
		document.getElementById("rotRep_cont").value = "";
		document.getElementById("routeReportWindowTitle").innerText = "";
	}
	
	function RouteReport(obj){
		if(document.getElementById("rotRep_cont").value.trim() == ""){
			console.log("空白不能檢舉");
			document.getElementById("routeReportWindowTitle").innerText = " - 請輸入檢舉理由";
			document.getElementById("routeReportWindowTitle").style = "color:red; text-align:right;margin-right:16px";
			<!-- document.getElementById("cancelRouteReport").click(); -->
		}else{
			console.log("能檢舉");
			document.RouteReportForm.submit();
		}
	}
	
	function cancelRouteMessage(){
		document.getElementById("rotMes_cont").value = "";
	}
	
	function RouteMessageInsert(obj){
		if(document.getElementById("rotMes_cont").value.trim() == ""){
			console.log("空白不能留言");
			document.getElementById("cancelRouteMessageInsert").click();
		}else{
			document.routeMessageInsertForm.submit();
		}
	}
	
	function deleteRoute(obj){
		swal({
            title: "確定刪除？",
            html: "按下確定後路線會永久刪除",
            type: "warning",
            showCancelButton: true//顯示取消按鈕
        }).then(
            function (result) {
                if (result.value) {
                    //使用者按下「確定」要做的事
                    swal({
                    	title: "完成!",
                        html: "路線已經刪除",
                        type: "success",
                    }).then(
                    		function (result) {
                                if (result.value) {
                                	document.deleteRouteForm.submit();
                                }
                    })
                } else if (result.dismiss === "cancel"){
                     //使用者按下「取消」要做的事
                    swal("取消", "路線未被刪除", "error");
                }//end else  
            });//end then 
	}
	
	 window.onload=function(){
	    //如果location存有数据,跳到锚点
	    var goWhere='${goWhere}';
	    if(goWhere == 'collectionLocation'){
	        document.getElementById('collectionLocation').click();
	    }
	    if(goWhere == 'routeReport'){
	        document.getElementById('routeReport').click();
	    }
	    if(goWhere == 'routeMessageInsert'){
	        document.getElementById('routeMessageInsert').click();
	    }
	    console.log('哭哭喇哭哭'+'${goWhere}');
	}

	
	
	</script>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAi9yisZZ5stHkAAQCoqbM0DslJ1VvGe7c&callback=initMap" async defer></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>