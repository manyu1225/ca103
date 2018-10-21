<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gp.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.route.model.*"%>
<%@ page import="com.routecollection.model.*"%>
<%@ page import="java.sql.*" %>

<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">


	 <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

	    <!--    jQueryUI select-->
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    
<!--     nav的\css -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/sources/css/css.css">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/sources/css/bootstrap.css">
	
    <title>創建揪團</title>
  </head>
  <body>
  
  		<!--      navbar-->
        <nav id="nav" class="navbar header-top  navbar-expand-lg  navbar-light bg-light fixed-top">
                <a class="navbar-brand" href="#"></a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarText">
                    <ul class="navbar-nav ml-sm-auto d-sm-flex">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><i class="fas fa-user-circle"></i> 會員專區</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="#">管理會員資料</a>
                                <a class="dropdown-item" href="#"><i class="fas fa-address-card"></i> 管理個人資訊</a>
                                <a class="dropdown-item" href="#">會員資料</a>
                                <a class="dropdown-item" href="#"><i class="fas fa-envelope"></i> 訊息</a>
                                <a class="dropdown-item" href="#">瀏覽會員列表</a>
                                <a class="dropdown-item" href="#">管理自轉幣</a>
                            </div>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><i class="fas fa-tachometer-alt"></i> 路線分享區</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="#">瀏覽所有路線</a>
                                <a class="dropdown-item" href="#">建立路線</a>
                                <a class="dropdown-item" href="#">管理路線</a>
                            </div>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><i class="fas fa-calendar-alt"></i> 活動快訊</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="#">瀏覽活動快訊</a>
                                <a class="dropdown-item" href="#">收藏活動列表</a>
                            </div>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><i class="fas fa-bullhorn"></i> 討論區</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="#">討論區</a>
                                <a class="dropdown-item" href="#">討論區</a>
                                <a class="dropdown-item" href="#">討論區</a>
                            </div>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><i class="fas fa-users"></i> 社群</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="#">瀏覽社群列表</a>
                                <a class="dropdown-item" href="#">瀏覽我的社群</a>
                                <a class="dropdown-item" href="#">管理社群</a>
                            </div>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><i class="fas fa-gavel"></i> 二手拍賣</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="#">賣家中心</a>
                                <a class="dropdown-item" href="#">買家中心</a>
                                <a class="dropdown-item" href="#">瀏覽商品列表</a>
                            </div>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><i class="fas fa-trophy"></i> 成就</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="#">成就</a>
                                <a class="dropdown-item" href="#">成就</a>
                                <a class="dropdown-item" href="#">成就</a>
                            </div>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><i class="fas fa-bicycle"></i> 揪團</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="<%=request.getContextPath() %>/front-end/gp/GP_Browsing.jsp">瀏覽揪團列表</a>
                                <a class="dropdown-item" href="#">建立揪團</a>
                                <a class="dropdown-item" href="#">揪團管理</a>
                            </div>
                        </li>
                        <P>
                            <li class="nav-item ">
                                <a class="nav-link sign-out-alt" href="#"><i class="fas fa-home"></i> Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<%=request.getContextPath() %>/front-end/mem/Login.jsp"><i class="fas fa-sign-out-alt"></i> 登入</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="mem/register.jsp">註冊</a>
                            </li>
                    </ul>
                </div>
            </nav>
  
  
  
  
    <!--                            路線選擇-->
        <div id="lightBox">
            <div id="routeSelect">
                <h2 class="title">我收藏的路線</h2>
                <hr>
                <div id="routeSection" class="routeSection">
                
                		<% 
                			RouteCollectionService rotcolSrc = new RouteCollectionService();
                			List<RouteVO> rotList = new ArrayList<>();
                			rotList = rotcolSrc.findRouteCollectionList(((MemVO)request.getSession().getAttribute("memVO")).getMem_id(),"");
                			pageContext.setAttribute("rotList", rotList);
                		%>
                		<c:forEach var="rotVO" items="${rotList}" begin="0" end="${rotList.size()}">
                			
		                			<div class="section row align-items-center "  id="${rotVO.rot_id}" rotName = "${rotVO.rot_name}">
					                          <!-- 放圖片 -->
					                            <div class="col-5 routeimg1 route">
					                                <img style="height:180px; width: 100%" class="routeimg " src="<%=request.getContextPath()%>/img/route/mountent3.jpg">
					                            </div>
			                          
			                          <!-- 圖片右邊的說明 -->
			                          <div class="col-7 routeimg2 route" style="padding:0px;">
			                      
			                            <!-- 右邊最上面的說明 -->
		                                          <div class="row row2">
		                                            <!-- 路線名稱 -->
		                                            <div class="col-8 routename route " style="padding:0px;">
<%-- 		                                                <form action="<%=request.getContextPath()%>/Route/RouteServlet.do" method="POST" name="${rotVO.rot_id}"> --%>
<!-- 		                                                    <input type="hidden" value="getRouteDetailed" name="action"> -->
<%-- 		                                                    <input type="hidden" value="${rotVO.rot_id}" name="rot_id"> --%>
		                                                    
		                                                    <a>名稱：${rotVO.rot_name}</a>
<%-- 		                                                     href="javascript:document.${rotVO.rot_id}.submit();" --%>
<!-- 		                                                </form> -->
		                                            </div>
		                                            <!-- 創建的會員暱稱 -->
		                                            <div class="col-4 routemem route" style="padding:0px;">創建者：${rotVO.mem_id}</div>
		                                          </div>
			                            
			                                    <!-- 右邊中間的說明 -->
			                                      <div class="row row3">
			                                        <!-- 路線說明 -->
			                                        <div class="col-10 routedes route" style="padding:0px;">路線說明：${rotVO.rot_describe}</div>
			                                        <div class="col-2 routeno route text-right " style="padding:0px;">${rotVO.rot_id}預計要換成排名數字</div>
			                                      </div>
			                                    
			                                    <!-- 右邊下面的說明 -->
			                                      <div class="row row4">
			                                        <!-- 路線難度 -->
			                                        <div class="col-2 routehard route" style="padding:0px;">難度：${rotVO.rot_hard}</div>
			                                        <!-- 路線長度 -->
			                                        <div class="col-2 routedis route" style="padding:0px;">長度：${rotVO.rot_dis}</div>
			                                        <!-- 平均坡度 -->
			                                        <div class="col-2 routeslope route" style="padding:0px;">坡度：${rotVO.rot_slope_ave}</div>
			                                        <!-- 空格 -->
			                                        <div class="col-2 routeslope route" style="padding:0px;"></div>
			                                        <!-- 熱門排名 -->
			                                        <div class="col-2 routecount route" style="padding:0px;">瀏覽數：${rotVO.rot_popu}</div>
			                                      </div>
			
			                          </div>
			                        </div>
                		</c:forEach>
                </div>
                <div class="rotSubmit">
                    <a class="btn btn-primary" id="submit">確定</a>
                    <a class="btn btn-primary" id="cancel">取消</a>
                </div>
                
            </div>
        </div>
		
		
		<div class="container-fluid paddingTop" style="margin-bottom:250px;">
			<div class="row">
				<div class="col-md-1 offset-md-1 col-xl-1 offset-xl-1"><a class="btn btn-warning" href="<%=request.getContextPath() %>/front-end/gp/GP_Browsing.jsp" style="margin-bottom:5px;">回瀏覽頁面</a></div>
				<div class="col-xl-4 offset-xl-2 col-md-5 offset-md-1">
					<h1 style="text-align:center; margin-bottom:20px;">開始創建您的揪團</h1>
					
					<form action="<%=request.getContextPath() %>/gp.do" method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <a class="btn btn-success" id="routeBtn">請先選擇路線</a>
                                <input class="border-0 btn" type="hidden" name="ROUTE" id="ROUTE" value="${(gpVOcre==null)?'':gpVOcre.rot_id}">
                                <input class="border-0 btn" type="text" id="routeName" name="routeName" value="${routeName}" readonly>
                                
                                <div class="error" id="error1">請選擇路線!!!</div>
                            </div>
                            
                            <div class="form-group">
                                <label for="GP_TITLE">揪團標題</label>
                                <input type="text" class="form-control" id="GP_TITLE" name="GP_TITLE" maxlength="16" value="${(gpVOcre==null)?'':gpVOcre.gp_title}" placeholder="">
                             	
                                <div class="error" id="error2">欄位請勿空白。長度在XX以下</div>
                            </div>
                            
                            <div class="form-group">
                                <label for="GP_DATE" style="width:100%;">揪團日期、時間</label>
                                <input class="form-control inline-Block" type="text" id="GP_DATE" name="GP_DATE" value="${(gpVOcre==null)?'':gpVOcre.gp_date}" style="width:42%;" placeholder="揪團日期">

                                <select class="form-control inline-Block" id="GP_HOUR" name="GP_HOUR"  style="width:22%;" placeholder="">
                                	<c:set var="flag" value="false"></c:set>
                                    <c:forEach var="myData" begin="0" end="24" >
                                        <option value="${myData}" 
                                         <c:if test="${myData==gpVOcre.gp_hour}" var="result">
                                         	<c:set var="flag" value="true"></c:set>
                                         	selected
                                         </c:if>
                                         ${ (myData == 9) and (!flag) ? 'selected':''}>
                                        
                                        ${myData}:00 </option>
                                        			   			  
                                    </c:forEach>
                                </select>
                                <label for="GP_TIME" class="inline-Block" style="width:10%;text-align:center;">持續</label>
                                <input type="text" class="form-control inline-Block" id="GP_TIME" name="GP_TIME" value="${gpVOcre==null?'':gpVOcre.gp_time}" style="width:10%;">
                                <span class="inline-Block" style="width:10%; text-align:center;">小時</span>
                                
                                <div class="error" id="error3">欄位請勿空白，且輸入正確格式</div>
                            </div>
                            
                            <div class="form-group">
                                <label for="SIGN_UP_DD" style="width:100%;">報名截止日期</label>
                                <input class="form-control inline-Block" type="text" id="SIGN_UP_DD" name="SIGN_UP_DD" value="${(gpVOcre==null)?'':gpVOcre.sign_up_DD}" style="width:42%;" placeholder="截止日期">
                                
                                <label for="MIN_NUM">至少</label>
                                <input type="text" class="form-control inline-Block" id="MIN_NUM" name="MIN_NUM" value="${(gpVOcre==null)?'':gpVOcre.min_num}" style="width:10%;">
                                <span style="width:10%;">人，</span>
                                <label for="MAX_NUM">最多</label>
                                <input type="text" class="form-control inline-Block" id="MAX_NUM" name="MAX_NUM" value="${(gpVOcre==null)?'':gpVOcre.max_num}" style="width:10%;" placeholder="">
                                <span style="width:10%;">人</span>
                                
                                <div class="error" id="error4">欄位請勿空白，且輸入正確格式</div>
                            </div>
                            
                            
                            <div class="form-group">
                                <label for="GP_DESC">揪團描述</label>
                                <input type="text" class="form-control" id="GP_DESC" name="GP_DESC" maxlength="66" value="${(gpVOcre==null)?'':gpVOcre.gp_desc}" placeholder="">
                                
                                <div class="error" id="error5">欄位請勿空白。長度在XX以下</div>
                            </div>
                           
                            <div class="form-group">
                                <label for="GP_CONTENT">揪團詳細內容</label>
                                <textarea class="form-control AutoHeight" id="GP_CONTENT" name="GP_CONTENT" style="width:120%">${(gpVOcre==null)?'':gpVOcre.gp_content}</textarea>
                            </div>
                            
                            <div class="form-group">
                                <label for="GP_PHOTO">揪團封面照片</label>
                                <input type="file" class="form-control" id="GP_PHOTO" name="GP_PHOTO" placeholder="">
                                <img id="imgPreview" src="" alt="" style="width:200;height:200px;"/>
                            </div>
                            <p>揪團公開設定</p>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="PUB_SET" id="inlineRadio1" value="0" checked>
                                <label class="form-check-label" for="inlineRadio1">公開</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="PUB_SET" id="inlineRadio2" value="1">
                                <label class="form-check-label" for="inlineRadio2">不公開</label>
                            </div>
                            
                            <div class="error" id="error0">${exceptionMessage}</div>
                            <input type="hidden" name="action" value="GP_CREATING">
                            <button class="btn btn-primary creBtn d-flex ml-auto" type="submit">創建揪團</button>
                	</form>
				
				
				</div>
				
			</div>
		
		</div>


		
   
      
    <link rel="stylesheet" href="<%=request.getContextPath() %>/sources/css/GP_Creating.css">
    <script src="<%=request.getContextPath() %>/sources/js/GP_Creating.js"></script>  
      
      
      <script>
			$(function(){
				<c:forEach begin="0" end="5" varStatus="loop">
					if(${(errorIndex==null)?false:errorIndex.contains(loop.index)}){
						
						$('#error${loop.index}')[0].style.display='block';
					}
				</c:forEach>
				
			});

    </script>
    
    
  </body>
</html>