<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.route.model.*"%>
<%@ page import="com.routemessage.model.*"%>
<%@ page import="com.routecollection.model.*"%>
<%@ page import="com.routereport.model.*"%>
<%@ page import="com.routemessagereport.model.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="sources/css/css.css">
	<!--      google-api,不可省略-->
	<script src="https://www.google.com/jsapi"></script>
	<!--      漂亮icon的引用-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<!--      漂亮跳窗的引用-->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.28.1/sweetalert2.all.min.js" type="text/javascript"></script>
	
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
	
	
	<style>
        #map {
            height: 500px;
            width: 100%;
        }
        .hidden{
        display: none;
    }
    	.cardclor{
      background-color: #007bff;
    }
    </style>
	<title>修改與查詢路線資訊-後台</title>
</head>
<body>
    <%@ include file="/sources/file/Home/backNav.file"%>
   
   <nav aria-label="breadcrumb">
  	  <ol class="breadcrumb">
  	    <li class="breadcrumb-item active"><a href="#">Home</a></li>
  	  </ol>
  	</nav>
    <div class="container-fluid">
    	<div class="row">
    	<%@ include file="/sources/file/Home/backSidebar.file"%>
<% 
	// 取得此路線的留言List,以便於在路線下方顯示留言內容
	RouteVO routeVO = (RouteVO)session.getAttribute("routeVO");
	RouteMessageService routeMessageSvc = new RouteMessageService();
	List<RouteMessageVO> listRouteMessage = routeMessageSvc.getByRouteId(routeVO.getRot_id());
	pageContext.setAttribute("listRouteMessage",listRouteMessage);
%>
<br>
<br>	
	<br>
	<br>
	<div class="container">
		<div class="row">
			<div class="col-12">
	<div class="container">
		<div class="row">
			<div class="col-12">
	<div id="map"></div>
    <!--      地圖出現的地方,style設定float是為了稍微排版,讓下面的經緯度顯示在地圖的右方-->
    </div>
	    </div>
    </div>
	<br>

    <div class="container">
		<div class="row">
			<div class="col-12">
			    <div id="chart_div" style="width: 100%; height: 250px;"></div>
			    <!--  面積高度圖長出來的位置    -->
		    </div>
	    </div>
    </div>
    
<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" enctype="multipart/form-data" name="updateRoute">
    <div class="container">
		<div class="row">
			<div class="col-12">
			    <div class="card" style="height:550px;margin-bottom:20px">
					<div class="card-header">
						<div><b>路線名稱：</b><input class="form-control form-control-sm" placeholder="請輸入路線名稱" type="text" name="rot_name" style="width:250px; display:inline;" value="${routeVO.rot_name}"></div>
 						<div style="margin-top:3px">
							<div class="custom-control custom-radio custom-control-inline" style="display:none" id="div1">
								<input class="custom-control-input" type="radio" name="rot_status" id="rot_status3" value="0" onclick="pri()">
								<label class="custom-control-label" for="rot_status3" id="afterRoute"><b>私人路線</b></label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input class="custom-control-input" type="radio" name="rot_status" id="rot_status1" value="1" onclick="pub()">
								<label class="custom-control-label" for="rot_status1" id="beforeRoute"><b>公開路線</b></label>
							</div>
							<div class="custom-control custom-radio custom-control-inline" id="div2">
								<input class="custom-control-input" type="radio" name="rot_status" id="rot_status2" value="0" checked  onclick="pri()">
								<label class="custom-control-label" for="rot_status2" id="afterRoute"><b>私人路線</b></label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<footer class="blockquote-footer font-weight-bold" style="font-size:14px;margin-top:2px;display:none" id="pub" ><cite title="Source Title">在公開的瀏覽路線中<b style="color:red">可以</b>查詢得到此次建立的路線，並且此路線會被加入到您的收藏列表中。但是您將<b style="color:red">無法</b>對路線做出修改。</cite></footer>
								<footer class="blockquote-footer font-weight-bold" style="font-size:14px;margin-top:2px;display:" id="pri"><cite title="Source Title">在公開的瀏覽路線中<b style="color:red">無法</b>查詢得到此次建立的路線，必須由您的收藏路線列表中詢得。您<b style="color:red">可以</b>後續修改此路線的相關敘述。</cite></footer>
							</div>
						</div>
					</div>
					<div class="card-body" style="height:150px">
					<div class="container">
					<div class="row">
					<div class="col-6">
						<div class="container">
							<div class="row">
								<div class="col-12" style="height:190px">
									<div class=" text-center align-self-center" id="rot_photo" style="overflow:hidden;width:470px;height:180px;border:solid;">
										<img class="routeimg" id="uploadImg" style="width:470px;margin-top:${routeVO.rot_photo_loc}px" src="<%=request.getContextPath()%>/Route/RouteServlet.do?action=getPhoto&rot_id=${ routeVO.rot_id }">
									</div>
								</div>
								<div class="col-12" style="height:60px;">
									<input class="custom-range" id="spacing" type="range" name="spacing" min="-100" max="400" value="${routeVO.rot_photo_loc}" data-sizing="px">
									<p class="font-weight-bold">請拖曳調整您的圖片位置</p>
								</div>
							</div>
						</div>
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">Upload</span>
							</div>
							<div class="custom-file">
								<input type="file" class="custom-file-input" id="rot_photo_file" name="rot_photo" onchange="rot_photo_func(this)" accept="image/*">
								<input type="hidden" id="old_rot_photo" name="old_rot_photo" value="${ routeVO.rot_photo }">
								<label class="custom-file-label" for="rot_photo_file">請為您的路線上傳一張照片</label>
							</div>
						</div>
					</div>
						<div class="col-6">
						<blockquote class="blockquote mb-0">
							<div class="input-group" style="margin-bottom:10px">
								<b>路線起點：</b>
								<span style="width:100px">
									<select class="form-control form-control-sm" name="rot_loc_start" >
										<option value="0">請選擇</option>
										<option value="基隆市" ${ (routeVO.rot_loc_start =='基隆市')?'selected':'' }>基隆市</option>
										<option value="台北市" ${ (routeVO.rot_loc_start =='台北市')?'selected':'' }>台北市</option>
										<option value="新北市" ${ (routeVO.rot_loc_start =='新北市')?'selected':'' }>新北市</option>
										<option value="桃園縣" ${ (routeVO.rot_loc_start =='桃園縣')?'selected':'' }>桃園縣</option>
										<option value="新竹縣" ${ (routeVO.rot_loc_start =='新竹縣')?'selected':'' }>新竹縣</option>
										<option value="苗栗縣" ${ (routeVO.rot_loc_start =='苗栗縣')?'selected':'' }>苗栗縣</option>
										<option value="台中市" ${ (routeVO.rot_loc_start =='台中市')?'selected':'' }>台中市</option>
										<option value="彰化縣" ${ (routeVO.rot_loc_start =='彰化縣')?'selected':'' }>彰化縣</option>
										<option value="雲林縣" ${ (routeVO.rot_loc_start =='雲林縣')?'selected':'' }>雲林縣</option>
										<option value="嘉義縣" ${ (routeVO.rot_loc_start =='嘉義縣')?'selected':'' }>嘉義縣</option>
										<option value="台南市" ${ (routeVO.rot_loc_start =='台南市')?'selected':'' }>台南市</option>
										<option value="高雄市" ${ (routeVO.rot_loc_start =='高雄市')?'selected':'' }>高雄市</option>
										<option value="屏東縣" ${ (routeVO.rot_loc_start =='屏東縣')?'selected':'' }>屏東縣</option>
										<option value="宜蘭縣" ${ (routeVO.rot_loc_start =='宜蘭縣')?'selected':'' }>宜蘭縣</option>
										<option value="花蓮縣" ${ (routeVO.rot_loc_start =='花蓮縣')?'selected':'' }>花蓮縣</option>
										<option value="台東縣" ${ (routeVO.rot_loc_start =='台東縣')?'selected':'' }>台東縣</option>
										<option value="南投縣" ${ (routeVO.rot_loc_start =='南投縣')?'selected':'' }>南投縣</option>
										<option value="外島" ${ (routeVO.rot_loc_start =='外島')?'selected':'' }>外島</option>
									</select>
								</span>　
								<span><b>地點描述：</b><input class="form-control form-control-sm" value="${ routeVO.rot_loc_start_des }" type="text" name="rot_loc_start_des" style="width:180px; display:inline;" maxlength="20"></span>
							</div>
							<div class="input-group" style="margin-bottom:10px">
								<b>路線終點：</b>
								<span style="width:100px">
									<select class="form-control form-control-sm" name="rot_loc_end" >
										<option value="0">請選擇</option>
										<option value="基隆市" ${ (routeVO.rot_loc_end =='基隆市')?'selected':'' }>基隆市</option>
										<option value="台北市" ${ (routeVO.rot_loc_end =='台北市')?'selected':'' }>台北市</option>
										<option value="新北市" ${ (routeVO.rot_loc_end =='新北市')?'selected':'' }>新北市</option>
										<option value="桃園縣" ${ (routeVO.rot_loc_end =='桃園縣')?'selected':'' }>桃園縣</option>
										<option value="新竹縣" ${ (routeVO.rot_loc_end =='新竹縣')?'selected':'' }>新竹縣</option>
										<option value="苗栗縣" ${ (routeVO.rot_loc_end =='苗栗縣')?'selected':'' }>苗栗縣</option>
										<option value="台中市" ${ (routeVO.rot_loc_end =='台中市')?'selected':'' }>台中市</option>
										<option value="彰化縣" ${ (routeVO.rot_loc_end =='彰化縣')?'selected':'' }>彰化縣</option>
										<option value="雲林縣" ${ (routeVO.rot_loc_end =='雲林縣')?'selected':'' }>雲林縣</option>
										<option value="嘉義縣" ${ (routeVO.rot_loc_end =='嘉義縣')?'selected':'' }>嘉義縣</option>
										<option value="台南市" ${ (routeVO.rot_loc_end =='台南市')?'selected':'' }>台南市</option>
										<option value="高雄市" ${ (routeVO.rot_loc_end =='高雄市')?'selected':'' }>高雄市</option>
										<option value="屏東縣" ${ (routeVO.rot_loc_end =='屏東縣')?'selected':'' }>屏東縣</option>
										<option value="宜蘭縣" ${ (routeVO.rot_loc_end =='宜蘭縣')?'selected':'' }>宜蘭縣</option>
										<option value="花蓮縣" ${ (routeVO.rot_loc_end =='花蓮縣')?'selected':'' }>花蓮縣</option>
										<option value="台東縣" ${ (routeVO.rot_loc_end =='台東縣')?'selected':'' }>台東縣</option>
										<option value="南投縣" ${ (routeVO.rot_loc_end =='南投縣')?'selected':'' }>南投縣</option>
										<option value="外島" ${ (routeVO.rot_loc_end =='外島')?'selected':'' }>外島</option>
									</select>
								</span>　
								<span><b>地點描述：</b><input class="form-control form-control-sm" value="${ routeVO.rot_loc_end_des }" type="text" name="rot_loc_end_des" style="width:180px; display:inline;" maxlength="20"></span>
							</div>	
							<div class="input-group">
								<b>路線描述：</b><textarea class="form-control" name="rot_describe" rows="8" maxlength="100">${ routeVO.rot_describe }</textarea>
							</div>
						</blockquote>
						</div>
						</div>
						</div>
						<footer class="blockquote-footer text-right ">建立者：<input value="${routeVO.mem_id}" name="mem_id"></footer>
						
					</div>
						
					</div>
				</div>
		    </div>
	    </div>
    </div>

	<br>

	<div class="container">
		<div class="row">
			<div class="col-12">
				<div class="card" style="height:500px;margin-bottom:20px">
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
												<h5><b>路線難度：</b><input type="number" name="rot_hard" value="${ routeVO.rot_hard }"></h5>
												<h5><b>路線距離：</b><input type="number" name="rot_dis" value="${ routeVO.rot_dis }"></h5>
											</div><span class="glyphicon glyphicon-question-sign"></span>
										</div>
										<div class="card">
											<div class="card-body">
												<h5><b>爬升高度：</b><input type="number" name="rot_height_up" value="${ routeVO.rot_height_up }"></h5>
												<h5><b>下降高度：</b><input type="number" name="rot_height_down" value="${ routeVO.rot_height_down }"></h5>
												<h5><b>海拔最高：</b><input type="number" name="rot_height_ave" value="${ routeVO.rot_height_ave }"></h5>
											</div>
										</div>
										<div class="card">
											<div class="card-body">
												<h5><b>爬升累計坡度：</b><input type="number" name="rot_slope_up" value="${ routeVO.rot_slope_up }"></b></h5>
												<h5><b>爬升平均坡度：</b><input type="number" name="rot_slope_down" value="${ routeVO.rot_slope_down }"></b></h5>
												<h5><b>高度平均坡度：</b><input type="number" name="rot_slope_ave" value="${ routeVO.rot_slope_ave }"></b></h5>
											</div>
										</div>
									</div>
									<br>
									
									
									<div class="container">
										<div class="row">
											<div class="col-6 align-self-start">
												<b>熱門度：</b><input type="number" name="rot_popu" value="${routeVO.rot_popu}" size=10>　
											</div>
											<div class="col-3"></div>
											<div class="col-auto">
												<input type="hidden" value="updataRoute" name="action">
												<input type="hidden" value="${ routeVO.rot_id }" name="rot_id">
												<input type="hidden" value="updataRoute" name="back_manager">
												<input type="hidden" id="rot_photo_loc" name="rot_photo_loc" value="${ routeVO.rot_photo_loc }">
												<button type="button" class="btn btn-primary" onclick="update(this)">修改</button>
												<button type="button" class="btn btn-primary" onclick="del(this)">刪除</button>
												<button type="button" class="btn btn-primary" onclick="cancel(this)">取消</button>
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
	</div>
	</form>
	<%-- 這邊的form表單是為了「刪除」的按鈕,刪除後會讓頁面跳轉回路線清單的頁面 --%>
	<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="deleteRoute">
		<input type="hidden" value="closeRoute" name="action">
		<input type="hidden" value="${ routeVO.rot_id }" name="rot_id">
	</form>
	<%-- 這邊的form表單是為了「取消」的按鈕,取消修改後會讓頁面跳轉回路線詳細資訊的頁面 --%>
	<form action="<%=request.getContextPath()%>/back-end/Route/ListPage.jsp" Method="POST" name="cancelUpdata">
		<input type="hidden" value="${ routeVO.rot_id }" name="rot_id">
	</form>
	<br>
    <div class="container">
		<div class="row">
			<div class="col-12">
				<div class="card">
					<div class="card-header">
						<b>路線留言：</b>
					</div>
					<div class="card-body">
						<blockquote class="blockquote mb-0">
							<c:if test="${ listRouteMessage.size()>0 }">
								<c:forEach var="routeMessageVO" items="${listRouteMessage}" begin="<%=0%>" end="<%=listRouteMessage.size()-1%>">
									<!-- 路線留言的內容!! -->
									<c:if test="${ routeMessageVO.rotMes_status != 0 }">
										<div class="container">
											<div class="row">
												<div class="col-12">
													<div class="alert bg-light text-dark border border-primary" role="alert" style="height:120px";>
														<div class="container" style="height:60px;">
															<div class="row">
																<div class="col-2"></div>
																	<div class="col-10" style=" border-width:1px;  border-bottom-style:solid; border-color:#555; height:70px" >
																		${routeMessageVO.rotMes_cont}
																	</div>
																	<div class="container" style="height:60px;">
																		<div class="row">
																			<div class="col-2"></div>
																			<div class="col-9">
																				${routeMessageVO.rotMes_time}
																			</div>
																			<div class="col-1">
																				<!-- 刪除路線留言按鈕!! -->
																				<form action="<%=request.getContextPath()%>/RouteMessage/RouteMessageServlet.do" Method="POST">
																				<input type="hidden" value="closeRouteMessage" name="action">
																				<input type="hidden" value="${ routeMessageVO.rotMes_id }" name="rotMes_id">
																				<input type="hidden" value="${ routeVO.rot_id }" name="rot_id">
																				<input type="submit" class="btn btn-primary" value="刪除留言">
																				</form>
																			</div>
																		</div>
																	</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</c:if>
							    </c:forEach>
	    					</c:if>
						</blockquote>
					</div>
				</div>
    		</div>
	    </div>
    </div> 
    </div>
	    </div>
    </div> 
    <br>
    <br>
<br>
<br>




	<br>
	<br>
	<%! String[] gps; %>
	<% 	String rot_start = ((RouteVO)session.getAttribute("routeVO")).getRot_start();
		String rot_end = ((RouteVO)session.getAttribute("routeVO")).getRot_end();
		String rot_gps = routeVO.getRot_gps();
		
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
	    position: new google.maps.LatLng<%=rot_start%>,
		map : map
	});
	
	//	加入終點圖標
	var endMarker = new google.maps.Marker({
		position : new google.maps.LatLng<%=rot_end%>,
		map : map
	});

	var markers = [ startMarker, endMarker ];
	
	//	處理呈現的地圖邊界大小
	var newBoundary = new google.maps.LatLngBounds();
	for (var i = 0; i < 2; i++) {
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
	
//	圖片進度條的事件
    document.getElementById('spacing').addEventListener('input', marginTopChange);
	
	//	圖片的進度條調整函式
	function marginTopChange() {
		let margintop = -this.value;
		document.getElementById("uploadImg").style.marginTop = margintop + 'px';
		document.getElementById("rot_photo_loc").value = margintop;
		console.log("margintop" + margintop);
	};
	
	
//	圖片上傳預覽的函式,錯誤處理-圖片上傳格式不符合時
	var file;
	function rot_photo_func (obj) {
		file = obj.files[0].type;
		if(!file.match(/^([0-9a-zA-Z_\-~ :\\])+(.jpg|.JPG|.jpeg|.JPEG|.bmp|.BMP|.gif|.GIF|.png|.PNG)$/)){
			swal({
                title: "您上傳的圖片格式錯誤",
                html: "請「選擇」一張圖片",
                type: "error",
                confirmButtonText:"確定"/*改這裡*/
			});
		}else{
			var reader = new FileReader();
			reader.readAsDataURL(obj.files[0]);
			reader.onloadend = function (e){
				document.getElementById("rot_photo").innerHTML = "<img id='uploadImg' style='width:470px;margin-top:0px' src='" + e.target.result + "' style='height:160px'>";
				document.getElementById("old_rot_photo").value = "new_rot_photo";
				document.getElementById("rot_photo_loc").value = 0 ;
			}
		}
	}
	
	
	
	function deleteRoute(obj){
		
		swal({
            title: "確定刪除？",
            html: "按下確定後資料會永久刪除",
            type: "warning",
            showCancelButton: true//顯示取消按鈕
        }).then(
            function (result) {
                if (result.value) {
                    //使用者按下「確定」要做的事
                    swal({
                    	title: "完成!",
                        html: "資料已經刪除",
                        type: "success",
                    }).then(
                    		function (result) {
                                if (result.value) {
                                	document.deleteRouteForm.submit();
                                }
                    })
                } else if (result.dismiss === "cancel"){
                     //使用者按下「取消」要做的事
                    swal("取消", "資料未被刪除", "error");
                }//end else  
            });//end then 
		
	}
	
	
	function del(obj){
		document.deleteRoute.submit();
	}
	
	function update(obj){
		document.updateRoute.submit();
	}
	
	function cancel(obj){
		document.cancelUpdata.submit();
	}
	
	function pub(){
		document.getElementById("pub").style.display = '';
		document.getElementById("pri").style.display = 'none';
		
	};
	
	function pri(){
		document.getElementById("pub").style.display = 'none';
		document.getElementById("pri").style.display = '';
	};
	</script>
	
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAi9yisZZ5stHkAAQCoqbM0DslJ1VvGe7c&callback=initMap" async defer></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>