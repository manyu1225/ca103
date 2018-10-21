<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.route.model.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
	 <style type="text/css">
      .hidden{
        display: none;
    }
    	.cardclor{
      background-color: #007bff;
    }
  </style>
	
	
	<title>路線管理</title>
</head>
<body>
<%
	//	這邊直接呼叫RouteService,跳過contorller
	//	當routeList==null代表第一次進入此頁面,所以自動撈出全部的路線
	//	當routeList!=null代表已經進入此頁面,所以把相關的查詢條件和查詢後的List設定好
	List<RouteVO> list;
	String hard, dis, slope, text;
	if(session.getAttribute("routeList") == null){
		RouteService rotSvc = new RouteService();
	    list = rotSvc.getRouteList(" ");
	    pageContext.setAttribute("list",list);
	}else{
		list = (List<RouteVO>)session.getAttribute("routeList");
		pageContext.setAttribute("list",list);
		if(session.getAttribute("hard") != null){
			hard = (String)session.getAttribute("hard");
			dis = (String)session.getAttribute("dis");
			slope = (String)session.getAttribute("slope");
			text = (String)session.getAttribute("text");
		}
	}
%>
   <%@ include file="/sources/file/Home/backNav.file"%>
   
   <nav aria-label="breadcrumb">
  	  <ol class="breadcrumb">
  	    <li class="breadcrumb-item active"><a href="#">Home</a></li>
  	  </ol>
  	</nav>
    <div class="">
    	<div class="row">
    	<%@ include file="/sources/file/Home/backSidebar.file"%>
    			<div class="col-10">
    			<h2 class="text-center bg-warning p-4 text-white">
           			路線管理-修改路線資訊
           		</h2>
			

<div class="container">
	<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST">
		<div class="row">
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
					<option value="<5" ${('<5'==slope)?'selected':'' }>平均坡度小於5%</option>
					<option value="<10" ${('<10'==slope)?'selected':'' }>平均坡度小於10%</option>
					<option value=">=10" ${('>=10'==slope)?'selected':'' }>平均坡度大於10%</option>
				</select>
			</div>
			<div class="col-3">
				<input class="form-control form-control-sm" type="text" placeholder="請輸入關鍵字" name="text" value="${ (text != null)?text:'' }">
			</div>
			<div class="col-1">
				<input type="hidden" value="getRouteList" name="action">
				<input type="hidden" value="updataRoute" name="back_manager">
				<input type="submit" value="送出查詢" class="btn btn-outline-secondary btn-sm">
			</div>
		</div>
	</form>
</div>

<div class="container" style="margin-top:15px;">
		<div class="row">
			<ul class="nav nav-tabs" id="myTab" role="tablist">
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route" name="order">
						<input type="hidden" value="updataRoute" name="back_manager">
						<a class="nav-link ${('route'==route_order || null==route_order)?'active':'' }" id="home-tab" href="javascript:document.route.submit();" role="tab" aria-controls="home" aria-selected="true">全部路線</a>
					</form>
				</li>
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route_popu">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route_popu" name="order">
						<input type="hidden" value="updataRoute" name="back_manager">
						<a class="nav-link ${('route_popu'==route_order)?'active':'' }" id="profile-tab" href="javascript:document.route_popu.submit();" role="tab" aria-controls="profile" aria-selected="false">熱門排名</a>
					</form>
				</li>
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route_hard">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route_hard" name="order">
						<input type="hidden" value="updataRoute" name="back_manager">
						<a class="nav-link ${('route_hard'==route_order)?'active':'' }" id="contact-tab" href="javascript:document.route_hard.submit();" role="tab" aria-controls="contact" aria-selected="false">難度排名</a>
					</form>
				</li>
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route_dis">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route_dis" name="order">
						<input type="hidden" value="updataRoute" name="back_manager">
						<a class="nav-link ${('route_dis'==route_order)?'active':'' }" id="contact-tab" href="javascript:document.route_dis.submit();" role="tab" aria-controls="contact" aria-selected="false">距離排名</a>
					</form>
				</li>
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route_slope_ave">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route_slope_ave" name="order">
						<input type="hidden" value="updataRoute" name="back_manager">
						<a class="nav-link ${('route_slope_ave'==route_order)?'active':'' }" id="contact-tab" href="javascript:document.route_slope_ave.submit();" role="tab" aria-controls="contact" aria-selected="false">坡度排名</a>
					</form>
				</li>
				<li class="nav-item">
					<form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" name="route_close">
						<input type="hidden" value="getRouteList" name="action">
						<input type="hidden" value="route_close" name="order">
						<input type="hidden" value="updataRoute" name="back_manager">
						<a class="nav-link ${('route_close'==route_order)?'active':'' }" id="contact-tab" href="javascript:document.route_close.submit();" role="tab" aria-controls="contact" aria-selected="false">已封存路線</a>
					</form>
				</li>
			</ul>

		</div>
</div>


<c:if test="${ list.size()>0 }">
<% int routeRanking = 0; %>
	<c:forEach var="rotVO" items="${list}" begin="<%=0%>" end="<%=list.size()-1%>">
<% routeRanking++; %>

<!-- 路線的格子 -->
<div class="container border rounded" id="block" style="background-color: #ecf5ff; margin-top:15px; box-shadow: 7px 7px 2px rgba(0, 0, 0, 0.2); -webkit-transition-duration: 0.4s; /* Safari */
    transition-duration: 0.4s;" onmouseover="this.style.background='#c5def9'" onmouseout="this.style.background='#ecf5ff'">
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
							<input type="hidden" value="updataRoute" name="back_manager">
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
            <div class="col-2 text-right" style="padding:0px;font-size:70px; margin-top:15px; color:#007BFF;opacity:0.5;text-shadow: black 0.1em 0.1em 0.2em" >${routeList.indexOf(rotVO) + 1}</div>
          </div>
        </div>
        
        <!-- 右邊下面的說明 -->
        <div class="container">
          <div class="row" style="margin-top: 5px;">
            <!-- 平均坡度 -->
            <div class="col-2" style="padding:0px;">
            	<span class="d-inline-block" tabindex="0" data-toggle="tooltip"	title="路線坡度">
            		<i class="fas fa-chart-line">
            			：${rotVO.rot_slope_ave} %
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
</div>
		</div>
	</div>
	<br>
	<br>
	<br>

	
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>