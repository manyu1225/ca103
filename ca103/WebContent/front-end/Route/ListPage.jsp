<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.route.model.*"%>
<%@ page import="com.mem.model.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<!-- 下面這css進去排版要重新排 -->
	<!-- <link rel="stylesheet" type="text/css" href="/CA103G2/css/css.css"> -->  
	<!--      漂亮icon的引用-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	
	<title>Insert title here</title>
	
	<style>
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
		
		.nav-link{
			color: #fff;
		}
		
		
		#MyBlog{
		    position: fixed;  /*固定在網頁上不隨卷軸移動，若要隨卷軸移動用absolute*/
		    top: 85%;  /*設置垂直位置*/
		    right: -160px;  /*設置水平位置，依所放的內容多寡需要自行手動調整*/
		    background: #ffffff;  /*背景顏色*/
		    padding: 10px 20px;
		    border-radius: 10px;  /*圓角*/
		    -moz-border-radius: 10px;
		    -webkit-border-radius: 10px;
		    opacity:0.9;
		}

		#MyBlog:hover{  /*當滑鼠移至此區塊時，伸縮區塊*/
		    right: -10px;
		}
		
		#MyBlog #title{
		    padding-right: 5px;  /*讓標題與連結中間有空隙*/
		}
		
	</style>
	<script>
		$(function(){
			$('.nav-link').mouseover(function(){
				$(this).css("background-color",'rgba(230, 230, 230, 1)');
			})
			$('.nav-link').mouseout(function(){
				$(this).css("background-color",'rgba(0, 0, 0, 0)');
			})
			$('.active').mouseout(function(){
				$(this).css("background-color",'rgba(255, 255, 255, 1)');
			})
		})
		
		
	</script>
</head>
<body>
<a id="pageTop"></a>
<%@ include file="/sources/file/Home/NavBar.file" %>

<%
	//	這邊直接呼叫RouteService,跳過contorller
	//	當routeList==null代表第一次進入此頁面,所以自動撈出全部的路線
	//	當routeList!=null代表已經進入此頁面,所以把相關的查詢條件和查詢後的List設定好
	String hard, dis, slope, rot_loc, text, route_game, order;
	List<RouteVO> routeList = (List<RouteVO>)session.getAttribute("routeList");
	List<MemVO> memList = (List<MemVO>)session.getAttribute("memList");
	Map<String, Integer> routeMessageCountMap = (Map<String, Integer>)session.getAttribute("routeMessageCountMap");
	pageContext.setAttribute("routeList",routeList);
	pageContext.setAttribute("memList",memList);
	pageContext.setAttribute("routeMessageCountMap",routeMessageCountMap);
	
	hard = (String)session.getAttribute("hard");
	dis = (String)session.getAttribute("dis");
	slope = (String)session.getAttribute("slope");
	rot_loc = (String)session.getAttribute("rot_loc");
	text = (String)session.getAttribute("text");
	route_game = (String)session.getAttribute("route_game");
	order = (String)session.getAttribute("order");
	
%>

<br>
<br>
<div class="container">
		<div class="row">
			<div class="col">
	<img src="<%=request.getContextPath()%>/sources/img/Route/SelectYourRoute2.png" style="width:100%;opacity:1">
	</div>
	</div>
	</div>
<br>
<div class="container">
	<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST">
		<div class="row" style="opacity:0.9">
			<div class="col">
				<select class="form-control form-control-sm" name="hard">
					<option value=">-100">請選擇難度</option>
					<option value="<50" ${('<50'==hard)?'selected':'' }>難度小於50</option>
					<option value="<100" ${('<100'==hard)?'selected':'' }>難度小於100</option>
					<option value=">=100" ${('>=100'==hard)?'selected':'' }>難度大於100</option>
				</select>
			</div>
			<div class="col">
				<select class="form-control form-control-sm" name="dis">
					<option value=">-100">請選擇距離</option>
					<option value="<50" ${('<50'==dis)?'selected':'' }>距離小於50km</option>
					<option value="<100" ${('<100'==dis)?'selected':'' }>距離小於100km</option>
					<option value=">=100" ${('>=100'==dis)?'selected':'' }>距離大於100km</option>
				</select>
			</div>
			<div class="col">
				<select class="form-control form-control-sm" name="slope">
					<option value=">-100">請選擇坡度</option>
					<option value="<5" ${('<5'==slope)?'selected':'' }>爬升累計坡度小於5%</option>
					<option value="<10" ${('<10'==slope)?'selected':'' }>爬升累計坡度小於10%</option>
					<option value=">=10" ${('>=10'==slope)?'selected':'' }>爬升累計坡度大於10%</option>
				</select>
			</div>
			<div class="col">
				<select class="form-control form-control-sm" name="rot_loc">
					<option value="">請選擇地區</option>
					<option value="基隆市" ${('基隆市'==rot_loc)?'selected':'' }>基隆市</option>
					<option value="台北市" ${('台北市'==rot_loc)?'selected':'' }>台北市</option>
					<option value="新北市" ${('新北市'==rot_loc)?'selected':'' }>新北市</option>
					<option value="桃園縣" ${('桃園縣'==rot_loc)?'selected':'' }>桃園縣</option>
					<option value="新竹縣" ${('新竹縣'==rot_loc)?'selected':'' }>新竹縣</option>
					<option value="苗栗縣" ${('苗栗縣'==rot_loc)?'selected':'' }>苗栗縣</option>
					<option value="台中市" ${('台中市'==rot_loc)?'selected':'' }>台中市</option>
					<option value="彰化縣" ${('彰化縣'==rot_loc)?'selected':'' }>彰化縣</option>
					<option value="雲林縣" ${('雲林縣'==rot_loc)?'selected':'' }>雲林縣</option>
					<option value="嘉義縣" ${('嘉義縣'==rot_loc)?'selected':'' }>嘉義縣</option>
					<option value="台南市" ${('台南市'==rot_loc)?'selected':'' }>台南市</option>
					<option value="高雄市" ${('高雄市'==rot_loc)?'selected':'' }>高雄市</option>
					<option value="屏東縣" ${('屏東縣'==rot_loc)?'selected':'' }>屏東縣</option>
					<option value="宜蘭縣" ${('宜蘭縣'==rot_loc)?'selected':'' }>宜蘭縣</option>
					<option value="花蓮縣" ${('花蓮縣'==rot_loc)?'selected':'' }>花蓮縣</option>
					<option value="台東縣" ${('台東縣'==rot_loc)?'selected':'' }>台東縣</option>
					<option value="南投縣" ${('南投縣'==rot_loc)?'selected':'' }>南投縣</option>
					<option value="外島" ${('外島'==rot_loc)?'selected':'' }>外島</option>
				</select>
			</div>
			<div class="col-3">
				<input class="form-control form-control-sm" type="text" placeholder="請輸入關鍵字" name="text" value="${ (text != null)?text:'' }">
			</div>
			<div class="col-1">
				<input type="hidden" value="${ route_game }" name="route_game">
				<input type="hidden" value="getRouteList" name="action">
				<input type="submit" value="送出查詢" class="btn btn-light btn-sm">
			</div>
		</div>
	</form>
</div>

<div class="container" style="margin-top:15px;">
		<div class="row">
		<!-- 全部路線/賽事路線/非賽路線 -->
		<div class="col-auto">
			<ul class="nav nav-tabs " id="myTab" role="tablist" style="opacity:0.8">
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route" name=order>
						<input type="hidden" value="${ route_order }" name="route_order">
						<a class="nav-link ${('route'== route_order || null == route_game || '' == route_game)?'active':'' }" id="home-tab" href="javascript:document.route.submit();" role="tab" aria-controls="home" aria-selected="true">全部路線</a>
					</form>
				</li>
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route_game">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route_game" name="route_game">
						<input type="hidden" value="${ route_order }" name="route_order">
						<input type="hidden" value="${ (hard == null)?'>-100':hard }" name="hard">
						<input type="hidden" value="${ (dis == null)?'>-100':dis }" name="dis">
						<input type="hidden" value="${ (slope == null)?'>-100':slope }" name="slope">
						<input type="hidden" value="${ (rot_loc == null)?'':rot_loc }" name="rot_loc">
						<input type="hidden" value="${ (text == null)?'':text }" name="text">
						<a class="nav-link ${('route_game' == route_game)?'active':'' }" id="contact-tab" href="javascript:document.route_game.submit();" role="tab" aria-controls="home" aria-selected="false">賽事路線</a>
					</form>
				</li>
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route_not_game">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route_not_game" name="route_game">
						<input type="hidden" value="${ route_order }" name="route_order">
						<input type="hidden" value="${ (hard == null)?'>-100':hard }" name="hard">
						<input type="hidden" value="${ (dis == null)?'>-100':dis }" name="dis">
						<input type="hidden" value="${ (slope == null)?'>-100':slope }" name="slope">
						<input type="hidden" value="${ (rot_loc == null)?'':rot_loc }" name="rot_loc">
						<input type="hidden" value="${ (text == null)?'':text }" name="text">
						<a class="nav-link ${('route_not_game' == route_game)?'active':'' }" id="contact-tab" href="javascript:document.route_not_game.submit();" role="tab" aria-controls="home" aria-selected="false">非賽事路線</a>
					</form>
				</li>
			</ul>
			</div>
			<!-- 排名條件 -->
			<div class="col-auto">
			<ul class="nav nav-tabs " id="myTab" role="tablist" style="opacity:0.8">
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route_popu">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route_popu" name="order">
						<input type="hidden" value="${ route_game }" name="route_game">
						<input type="hidden" value="${ (hard == null)?'>-100':hard }" name="hard">
						<input type="hidden" value="${ (dis == null)?'>-100':dis }" name="dis">
						<input type="hidden" value="${ (slope == null)?'>-100':slope }" name="slope">
						<input type="hidden" value="${ (rot_loc == null)?'':rot_loc }" name="rot_loc">
						<input type="hidden" value="${ (text == null)?'':text }" name="text">
						<a class="nav-link ${('route_popu' == route_order)?'active':'' }" id="profile-tab" href="javascript:document.route_popu.submit();" role="tab" aria-controls="profile" aria-selected="false">熱門排名</a>
					</form>
				</li>
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route_hard">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route_hard" name="order">
						<input type="hidden" value="${ route_game }" name="route_game">
						<input type="hidden" value="${ (hard == null)?'>-100':hard }" name="hard">
						<input type="hidden" value="${ (dis == null)?'>-100':dis }" name="dis">
						<input type="hidden" value="${ (slope == null)?'>-100':slope }" name="slope">
						<input type="hidden" value="${ (rot_loc == null)?'':rot_loc }" name="rot_loc">
						<input type="hidden" value="${ (text == null)?'':text }" name="text">
						<a class="nav-link ${('route_hard' == route_order)?'active':'' }" id="contact-tab" href="javascript:document.route_hard.submit();" role="tab" aria-controls="contact" aria-selected="false">難度排名</a>
					</form>
				</li>
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route_dis">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route_dis" name="order">
						<input type="hidden" value="${ route_game }" name="route_game">
						<input type="hidden" value="${ (hard == null)?'>-100':hard }" name="hard">
						<input type="hidden" value="${ (dis == null)?'>-100':dis }" name="dis">
						<input type="hidden" value="${ (slope == null)?'>-100':slope }" name="slope">
						<input type="hidden" value="${ (rot_loc == null)?'':rot_loc }" name="rot_loc">
						<input type="hidden" value="${ (text == null)?'':text }" name="text">
						<a class="nav-link ${('route_dis' == route_order)?'active':'' }" id="contact-tab" href="javascript:document.route_dis.submit();" role="tab" aria-controls="contact" aria-selected="false">距離排名</a>
					</form>
				</li>
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route_slope_ave">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route_slope_ave" name="order">
						<input type="hidden" value="${ route_game }" name="route_game">
						<input type="hidden" value="${ (hard == null)?'>-100':hard }" name="hard">
						<input type="hidden" value="${ (dis == null)?'>-100':dis }" name="dis">
						<input type="hidden" value="${ (slope == null)?'>-100':slope }" name="slope">
						<input type="hidden" value="${ (rot_loc == null)?'':rot_loc }" name="rot_loc">
						<input type="hidden" value="${ (text == null)?'':text }" name="text">
						<a class="nav-link ${('route_slope_ave' == route_order)?'active':'' }" id="contact-tab" href="javascript:document.route_slope_ave.submit();" role="tab" aria-controls="contact" aria-selected="false">坡度排名</a>
					</form>
				</li>
			</ul>
			</div>
		</div>
</div>
<%@ include file="/sources/file/Route/RouteListPageTop.file" %> 

<c:if test="${ routeList.size()>0 }">
<% int routeRanking = 0; %>
	<c:forEach var="rotVO" items="${routeList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
<% routeRanking++; %>

<!-- 路線的格子 -->
  <div class="container border rounded" id="block" style="background-color: rgba(230, 230, 230, 0.9); margin-top:15px; box-shadow: 7px 7px 2px rgba(0, 0, 0, 0.2); -webkit-transition-duration: 0.4s; /* Safari */
    transition-duration: 0.4s;" onmouseover="this.style.background='rgba(230, 230, 230, 1)'" onmouseout="this.style.background='rgba(230, 230, 230, 0.9)'">
    <div class="row align-items-center" style="height: 180px;">
      <!-- 放圖片 -->
      <div class="col-5" name="divForImg" style="overflow:hidden;padding:0px;text-align:center;width:470px;height:170px;">
		<a href="javascript:document.${rotVO.rot_id}.submit();" ><img id="${ rotVO.rot_id }" style="padding:5px; vertical-align:middle; width:470px; margin-top:${ rotVO.rot_photo_loc }px" src="<%=request.getContextPath()%>/Route/RouteServlet.do?action=getPhoto&rot_id=${ rotVO.rot_id }"></a>
      </div>
      <%-- ${rotVO.rot_photo} --%>
      <!-- 圖片右邊的說明 -->
      <div class="col-7" style="padding:5px;height:170px ">
  
        <!-- 右邊最上面的說明 -->
        <div class="container">
          <div class="row">
            <!-- 路線名稱 -->
				<div class="col-1" style="padding:0px;">
					<p style="font-size:22px;font-family: Comic Sans MS,arial,helvetica,sans-serif;"><i class="fas fa-bicycle" style="color:#007bff"></i></p>
				</div>
	            <div class="col-8 routename" style="padding:0px;">
		           	<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="${rotVO.rot_id}">
						<input type="hidden" value="getRouteDetailed" name="action">
						<input type="hidden" value="${rotVO.rot_id}" name="rot_id">
						<input type="hidden" value="rot_popu" name="rot_popu">
						<a href="javascript:document.${rotVO.rot_id}.submit();" ><p style="font-size:22px;font-family: Comic Sans MS,arial,helvetica,sans-serif;margin-bottom:0px">${rotVO.rot_name}</p></a>
					</form>
				</div>
            <!-- 創建的會員暱稱 -->
            <div class="col-3" style="padding:0px;">
            	創建者：
            	<c:forEach var="memVO" items="${memList}">
	            	<c:if test="${rotVO.mem_id == memVO.mem_id}">
	          			${memVO.mem_nickname}
	          		</c:if>
          		</c:forEach>
          		<c:if test="${ rotVO.mem_id == null}">
	          			官方建立
	          	</c:if>
            </div>
          </div>
        </div>
        
        <!-- 右邊中間的說明 -->
        <div class="container">
          <div class="row" style="height:65px; margin-top:5px;">
            <!-- 路線說明 -->
            <div class="col-10" style="padding:0px;font-size:14px"><b>路線說明：</b>${rotVO.rot_describe}</div>
            <div class="col-2 text-right" style="padding:0px;font-size:70px; margin-top:15px; color:#91e6f9;opacity:0.3;text-shadow: black 0.1em 0.1em 0.2em; font-family: Comic Sans MS,arial,helvetica,sans-serif;" >${routeList.indexOf(rotVO) + 1}</div>
          </div>
        </div>
        
        <!-- 右邊下面的說明 -->
        <div class="container">
          <div class="row" style="margin-top: 5px;">
            <!-- 爬升累計坡度 -->
            <div class="col-2" style="padding:0px;">
            	<span class="d-inline-block" tabindex="0" data-toggle="tooltip"	title="爬升累計坡度">
            		<i class="fas fa-chart-line">
            			：${rotVO.rot_slope_up} %
            		</i>
            	</span>
            </div>
            <!-- 路線長度 -->
            <div class="col-2" style="padding:0px;">
            	<span class="d-inline-block" tabindex="0" data-toggle="tooltip"	title="路線距離">
            		<i class="fas fa-map-marker-alt">
            			：${rotVO.rot_dis} m
            		</i>
            	</span>
            </div>
            <!-- 路線難度 -->
            <div class="col-2" style="padding:0px;">
            	<span class="d-inline-block" tabindex="0" data-toggle="tooltip"	title="路線難度">
            		<i class="fas fa-exclamation-triangle">
            			：${rotVO.rot_hard}
            		</i>
            	</span>
            </div>
            <!-- 空格 -->
            <div class="col-2" style="padding:0px;"></div>
            <!-- 熱門排名 -->
            <div class="col-3" style="padding:0px;">
            	<span class="d-inline-block" tabindex="0" data-toggle="tooltip"	title="點閱數">
            		<i class="fab fa-hotjar">
            			：${rotVO.rot_popu}
            		</i>
            	</span>　
            	<span class="d-inline-block" tabindex="0" data-toggle="tooltip"	title="留言數">
            		<i class="far fa-comment-dots">
            			<c:if test="${routeMessageCountMap.get(rotVO.rot_id)!=null}">
						：${routeMessageCountMap.get(rotVO.rot_id)}
						</c:if>
						<c:if test="${routeMessageCountMap.get(rotVO.rot_id)==null}">
						：0
						</c:if>
            		</i>
            	</span>
            </div>
            <div class="col-1" style="padding:0px;">
            </div>
            
            
          </div>
        </div>
      </div>
    </div>
  </div>

	
	</c:forEach>
	</c:if>
	<br>
<%@ include file="/sources/file/Route/RouteListPageBottom.file" %> 
	<br>
	<br>
	<br>
	<div id="MyBlog">
		<span id="title">回頁首</span>
		<a href="#pageTop">Select Your Route</a>
	</div>
</body>
</html>