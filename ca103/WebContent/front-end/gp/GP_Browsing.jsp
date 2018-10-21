<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gp.model.*"%>
<%@ page import="com.joined_gp.model.*"%>
<%@ page import="com.favorite_gp.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.route.model.*"%>
<%@ page import="java.sql.*" %>

<% 

	
// 	if(request.getAttribute("gpList") == null){
// 		GPService gpSrc = new GPService();
// 		List<GPVO> list = gpSrc.getAllGP();
// 		pageContext.setAttribute("list",list);
// 	}else{
// 		pageContext.setAttribute("list",request.getAttribute("gpList"));
// 	}
	if("true".equals(request.getParameter("hasSearched"))){
		pageContext.setAttribute("list",request.getSession().getAttribute("gpList"));
	}else{
		GPService gpSrc = new GPService();
		List<GPVO> list = gpSrc.getAllGP();
		pageContext.setAttribute("list",list);
	}
	
%>

<!doctype html>
	
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>自転車</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!--    for輪播-->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

    <!--    jQueryUI select-->
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- 	bar的CSS -->
<%-- 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/sources/css/css.css"> --%>
<%-- 	<link rel="stylesheet" href="<%=request.getContextPath() %>/sources/css/bootstrap.css"> --%>

</head>

<body style="background-image: url(<%=request.getContextPath() %>/sources/img/Route/bg13.jpg);">
    <div>
        
		<%@ include file="/sources/file/Home/NavBar.file" %>
		
		
		<jsp:useBean id="rotSrc" class="com.route.model.RouteService"/>
		<jsp:useBean id="memSrc" class="com.mem.model.MemService"/>
		<%
			//自己弄的Session
// 			Object temp = request.getSession().getAttribute("memVO");
// 			if(temp == null){
// 				MemVO memVO = new MemVO();
// 				memVO.setMem_id("M000001");
// 				memVO.setMem_firstname("柯");
// 				memVO.setMem_lastname("文哲");
// 				memVO.setMem_nickname("柯P");
// 				HttpSession session2 = request.getSession(true);
// 				session2.setAttribute("memVO", memVO);
// 			}
		%>
		<c:set var="hasLogined" value="${false}"/>
		<c:if test="${memVO!=null}">
			<c:set var="hasLogined" value="${true}"/>
		</c:if>
		
		<div id="joinSnackbar">已加入揪團</div>
        <div id="favoriteSnackbar">已加入收藏</div>
        
        
        <img src="<%=request.getContextPath() %>/sources/img/gp/87.png"style="width:90%;margin:50px;"/>
        
        <div class="container-fluid paddingTop">
            <!--                    搜尋bar-->
            <div class="row">

                <div class="mx-auto search">
                
					<form action="<%=request.getContextPath() %>/gp.do" method="post">
		                    <input class="rounded" type="text" placeholder="  關鍵字" name="search" value="${search}">
		                    <select class="searchBar" name="rot_loc" id="rot_loc">
		                                <option value="" >地區</option>
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
										<option value="外島" 	${('外島'==rot_loc)?'selected':'' }>外島</option>
		                    </select>
		
		                    <select class="searchBar" name="hard" id="hard">
		                                <option value=">0">難度</option>
		                                <option value="<50"${('<50'==hard)?'selected':'' }>&lt;50</option>
		                                <option value="<100 AND ROT_HARD>=50"${('<100 AND ROT_HARD>=50'==hard)?'selected':'' }>&gt;50</option>
		                                <option value=">=100"${('>=100'==hard)?'selected':'' }>&gt;100</option>
		                            </select>
		
		                    <select class="searchBar" name="limitNum" id="limitNum">
		                                <option value="0">人數</option>
		                                <option value="10" ${('10'==limitNum)?'selected':'' }>&lt;10</option>
		                                <option value="20" ${('20'==limitNum)?'selected':'' }>10~20</option>
		                                <option value="30" ${('30'==limitNum)?'selected':'' }>20~30</option>
		                                <option value="40" ${('40'==limitNum)?'selected':'' }>30~40</option>
		                                <option value="50" ${('50'==limitNum)?'selected':'' }>40~50</option>
		                                <option value="50" ${('50'==limitNum)?'selected':'' }>&gt;50</option>
							</select>
		                    <select class="searchBar" name="dis" id="dis">
		                                <option value=">0">公里數</option>
		                                <option value="<50"${('<50'==dis)?'selected':'' }>&lt;50km</option>
		                                <option value="<100 AND ROT_DIS>=50" ${('<100 AND ROT_DIS>=50'==dis)?'selected':'' }>&gt;50km</option>
		                                <option value=">=100"  ${('>=100'==dis)?'selected':'' }>&gt;100km</option>
		                    </select>
		                    <select class="searchBar" name="slope" id="slope">
		                    	<option value=">0">均坡度</option>
                                <option value="<5" ${('<5'==slope)?'selected':'' }>&lt;5%</option>
                                <option value="<10 AND ROT_SLOPE_AVE>=5" ${('<10 AND ROT_SLOPE_AVE>=5'==slope)?'selected':'' }>&lt;10%</option>
                                <option value=">=10"  ${('>=10'==slope)?'selected':'' }>&gt;10%</option>
		                    </select>
		                    
		                    
		                    
		                    <input class="rounded-left border-right-0" type="text" id="dateStart" name="dateStart" placeholder="開始月份" value="${dateStart}">
		                    <span style="z-index: 100 ;position: relative">~</span>
		                    <input class="rounded-right border-left-0" type="text" id="dateEnd" name="dateEnd" placeholder="結束月份" value="${dateEnd}">
		                    
		                    <select class="searchBar" id="order" name="order">
		                    	<option value="">排序</option>
		                    	<option value="0" ${('0'==order)?'selected':'' }>排序(新&rarr;舊) </option>
		                    	<option value="1" ${('1'==order)?'selected':'' }>排序(舊&rarr;新)</option>
		                    	<option value="2" ${('2'==order)?'selected':'' }>難度(低&rarr;高)</option>
		                    	<option value="3" ${('3'==order)?'selected':'' }>難度(高&rarr;低)</option>
		                    	<option value="4" ${('4'==order)?'selected':'' }>公里數(小&rarr;大)</option>
		                    	<option value="5" ${('5'==order)?'selected':'' }>公里數 (大&rarr;小)</option>
		                    	<option value="6" ${('6'==order)?'selected':'' }>平均坡度 (小&rarr;大)</option>
		                    	<option value="7" ${('7'==order)?'selected':'' }>平均坡度 (大&rarr;小)</option>
		                    	<option value="8" ${('8'==order)?'selected':'' }>揪團日期 (近&rarr;遠)</option>
		                    	<option value="9" ${('9'==order)?'selected':'' }>揪團日期 (遠&rarr;近)</option>
		                    </select>
		                    
		                    <input type="hidden" name="action" value="GPs_FIND">
		                    <button class="btn btn-primary" type="submit">找揪團</button>
<!-- 		                    <button type="reset" class="btn reset">重置搜尋(有點問題)</button> -->
		                	<span style="color:blue;">${gpList == null? '':gpList.size()}${gpList == null?'': '筆資料'}</span>
					</form>  
                </div>
            </div>
            
            
            <div class="row marginBottom bgImg">

                    <!--                                左邊-->
                <div class="col-sm-12 col-md-2 disappear">
                    <div class="row">
<%-- 	                    <a class="btn btn-info" href="<%=request.getContextPath() %>/front-end/gp/GP_Creating.jsp">創建揪團</a> --%>
<%-- 	                    <a class="btn btn-info" href="<%=request.getContextPath() %>/front-end/gp/GP_List.jsp?listPage=1">我的揪團</a> --%>
                    </div>

                </div>
                
                <!--                    右邊-->
                <div class="col-md-10 col-sm-12">
<!-- 				scrollBar 拿掉了 -->
                    <div class="tab-content" id="nav-tabContent">
                            <div class="row" style="margin-bottom:100px;">
                                <div class="col-sm-12 col-md-8 offset-md-1">
                                    <!--                                                迴圈跑出所有card-->
                                    <c:forEach var="gpVO" items="${list}" begin="${param.currentPage==null?'0':10*param.currentPage-10}" end="${param.currentPage==null?9:10*param.currentPage-1}">
                                    
                                    	<%
	                                    	JoinedGPService jgpSrc = new JoinedGPService();
	                                    	int jgpNum = jgpSrc.findNumberOfGP((GPVO)pageContext.getAttribute("gpVO"));
	                                    	pageContext.setAttribute("jgpNum",jgpNum);
                                    	%>
                                    	
                                    	
                                    	<div class="card cardUpdate">
	                                        <div class="row marginBottom">
	                                            <div class="col-md-4 ">

	                                                <img class="card-img-top cardImgUpdate " src="<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${gpVO.gp_id}">
	                                            </div>
	                                            <div class="col-md-8 lrpadding0">
	                                                <div class="card-body">
	                                                    <a target="_blank" href="<%=request.getContextPath() %>/gp.do?gp_id=${gpVO.gp_id}&action=GP_SEARCH">
	                                                        <h5 class="card-title">${gpVO.gp_title}</h5>
	                                                        <span class="subTitle">${gpVO.gp_desc}</span>
	                                                    </a>
	                                                    <div class="btn-group btn-md threePoint">
	                                                        <a type="button" class="btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></a>
	                                                        <div class="dropdown-menu dropdown-menu-right">
	                                                            <button class="dropdown-item" type="button">分享</button>
	                                                            <button class="dropdown-item" type="button">檢舉</button>
	                                                        </div>
	                                                    </div>
	
	                                                </div>
	                                                <ul class="list-group list-group-flush">
	
	                                                    <li class="list-group-item">

	                                                    	
	                                                    	
	                                                        <!--                                                        <div class="row">-->
	                                                        <!--                                                            <div class="col-md-6">-->
	                                                        <div><img class="icon" src="<%=request.getContextPath() %>/sources/img/gp/calendar-2x.png"><span>${gpVO.gp_date} ${gpVO.gp_hour}:00 (${gpVO.gp_time}小時) </span></div>
	                                                        <div>
<%-- 	                                                        	<form action="/CA103G2/Route/RouteServlet.do" method="POST" name="${gpVO.gp_id}${gpVO.rot_id}"> --%>
<!-- 																	<input type="hidden" value="getRouteDetailed" name="action"> -->
<%-- 																	<input type="hidden" value="${gpVO.rot_id}" name="rot_id"> --%>
																	
<!-- 																	<input type="hidden" value="rot_popu" name="rot_popu"> -->
<!-- 																	<a><p style="font-size:22px;font-family: Comic Sans MS,arial,helvetica,sans-serif;margin-bottom:0px">未命名</p></a> -->
<%-- 																	<img class="icon" src="<%=request.getContextPath() %>/sources/img/gp/map-marker-2x.png"> --%>
<%--                                                         			<a style="color:#3753df;" href="javascript:document.${gpVO.gp_id}${gpVO.rot_id}.submit();">${rotSrc.getRouteDetailed(gpVO.rot_id).rot_name}</a> --%>
<!-- 																</form> -->
																<img class="icon" src="<%=request.getContextPath() %>/sources/img/gp/map-marker-2x.png">
																<a target="_blank" style="color:#3753df;" href="<%=request.getContextPath() %>/Route/RouteServlet.do?action=getRouteDetailed&rot_id=${gpVO.rot_id}&rot_popu=rot_popu">${rotSrc.getRouteDetailed(gpVO.rot_id).rot_name}</a>
	                                                        </div>
	                                        
                                                            <div><img class="icon" src="<%=request.getContextPath() %>/sources/img/gp/people-2x.png"><span>${jgpNum + 1}/${gpVO.max_num}人</span></div>
	                                                        
	                                                        <div class="btnDiv">
	                                                        	<c:if test="${hasLogined}">
																	<% if(((MemVO)request.getSession().getAttribute("memVO")).getMem_id().equals(((GPVO)pageContext.getAttribute("gpVO")).getMem_id())){ %>
		                                                            	<a class="btn btn-info" target="_blank" href="<%=request.getContextPath() %>/gp.do?gp_id=${gpVO.gp_id}&action=GP_SEARCH"><i class="fas fa-eye"></i>&nbsp;查看我的揪團</a>
																	<%}else{ %>	                                                            
			                                                            <a class="btn btn-info joinGPSubmit" joinId="${gpVO.gp_id}"><i class="fas fa-plus"></i>加入</a>
			                                                            <a class="btn btn-info favoriteGPSubmit" favoriteId="${gpVO.gp_id}"><i class="fas fa-star"></i>收藏</a>
																	<%} %>
																</c:if>
																<c:if test="${!hasLogined or memVO.mem_id!=gpVO.mem_id}">
																	<a class="btn btn-info favoriteGPSubmit" target="_blank" href="<%=request.getContextPath() %>/gp.do?gp_id=${gpVO.gp_id}&action=GP_SEARCH"><i class="fas fa-eye"></i>&nbsp;查看</a>
																</c:if>
	                                                        </div>
	
	
	                                                        <div class="profile">
<%-- 	                                                            <a href="#" target="_blank" class="photo" style="background-image: url(<%=request.getContextPath() %>/sources/img/gp/%E6%9D%B0%E5%80%ABedited.jpg)"></a> --%>
																<a target="_blank" href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=goToMemberView&mem_id=${gpVO.mem_id}"><img class="photo" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${gpVO.mem_id}"/></a>
	                                                            <div class="inlineBlock">
	                                                                <a href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=goToMemberView&mem_id=${gpVO.mem_id}" target="_blank" style="color: #3753df">${memSrc.findMemById(gpVO.mem_id).mem_nickname}</a>
<!-- 	                                                                <div>個人成就?</div> -->
	                                                            </div>
	
	                                                        </div>
	                                                        
	                                                        <div id="expiryDate"><span>報名倒數：
	                                                        <!-- 將list內的VO物件丟到Scope -->
	                                                        <c:set var="temp">
	                                                        	${gpVO.sign_up_DD.getTime()}
	                                                        </c:set>
	                                                        <% 
	                                                        	Long currentTime = (new Timestamp(System.currentTimeMillis())).getTime();
	                                                        	Long totalHour = (Long.valueOf((String)pageContext.getAttribute("temp")) - currentTime)/3600/1000;
	                                                        	pageContext.setAttribute("day", totalHour/24);
	                                                        	pageContext.setAttribute("hour", totalHour%24);
															%>
	                                                        ${day}天  ${hour}小時
	                                                        </span></div>
	                                                        
	                                                    </li>
	
	                                                </ul>
	                                            </div>
	
	
	                                        </div>
	
	                                    </div>
                                    	
                                    </c:forEach>

                                
                                
                                </div>

									
                            </div>

                    </div>





                </div>
				
				
            </div>
            
 	              	<div id="fixedDiv" class="row" style="width:100%;height:50px;position:fixed;bottom:0px;z-index:100;background-color:#17a2b8;background: rgba(0,0,0,0.1)">
                    	
                    	
                    	<div id="appearDiv" class="pageRow mx-auto" style="opacity: 0.1;z-index:1000;">
                    		<a class="btn btn-info" id="firstPage">first</a>
		                    <a class="btn btn-info" id="prePage">上一頁</a>
							<select id="currentPage">
								<c:forEach var="myData" begin="1" end="${ list.size()/10+9/10}" >
	                             	   <option value="${myData}" ${param.currentPage == myData?'selected':''}>第${myData}頁</option>
	                            </c:forEach>
							</select>
							<a class="btn btn-info" id="nextPage">下一頁</a>
							<a class="btn btn-info" id="lastPage">last</a>
                    	</div>
					</div>
            
		            
        </div>
    </div>

    <link rel="stylesheet" href="<%=request.getContextPath() %>/sources/css/gp/GP_Browsing.css">
    <script src="<%=request.getContextPath() %>/sources/js/gp/GP_Browsing.js"></script>
    
	<c:if test="${hasLogined}">
<!--                                     尋找已加入的揪團並處理-->
                         <% 
                          JoinedGPService jgpSrc = new JoinedGPService();
                          List<GPVO> jgpList = jgpSrc.SearchPublicJGP((MemVO)request.getSession().getAttribute("memVO"));
                  		pageContext.setAttribute("jgpList",jgpList);
                         %>
                         <script>
                          <c:forEach var="jgpVO" items="${jgpList}" begin="0" end="${jgpList.size()}">
                         		$("[joinId='${jgpVO.gp_id}']").text("已加入");
                         		$("[joinId='${jgpVO.gp_id}']").addClass("disabled");
                          </c:forEach>
                         </script>
                         
<!--                                     尋找已加入收藏的揪團並處理 -->
                         <% 
                          Favorite_GPService fgpSrc = new Favorite_GPService();
                          List<GPVO> fgpList = fgpSrc.searchPubfGP((MemVO)request.getSession().getAttribute("memVO"));
                  		pageContext.setAttribute("fgpList",fgpList);
                         %>
                         <script>
                          <c:forEach var="fgpVO" items="${fgpList}" begin="0" end="${fgpList.size()}">
                         		$("[favoriteId='${fgpVO.gp_id}']").text("已收藏");
                         		$("[favoriteId='${fgpVO.gp_id}']").addClass("disabled");
                          </c:forEach>
                         </script>
		</c:if>   
    
    
   			 <script>
	                	    $(function(){
	                	    	var minPage = 1;
	                	    	var maxPage = Math.floor(${(list.size()-1)/10+1});
	                	    	$("#currentPage").change(function(){
	                	    		window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_Browsing.jsp?hasSearched=${param.hasSearched}&currentPage=" + $(this).val();
	                	    	});
	                	    	if($("#currentPage").val()>minPage){
	                	    		$("#prePage").click(function(){
	                	    			$("#currentPage").val(function(i, oldval) {
	                	    			    return --oldval;
	                	    			});
	                	    			window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_Browsing.jsp?hasSearched=${param.hasSearched}&currentPage=" + $("#currentPage").val();
	                	    		});
	                	    	}
	                	    	if($("#currentPage").val()<maxPage){
	                	    		$("#nextPage").click(function(){
	                	    			$("#currentPage").val(function(i, oldval) {
	                	    			    return ++oldval;
	                	    			});
	                	    			window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_Browsing.jsp?hasSearched=${param.hasSearched}&currentPage=" + $("#currentPage").val();
	                	    		});
	                	    	}
	                	    	$("#firstPage").click(function(){
	                	    		window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_Browsing.jsp?hasSearched=${param.hasSearched}&currentPage=" + minPage;
	                	    	});
	                	    	$("#lastPage").click(function(){
	                	    		window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_Browsing.jsp?hasSearched=${param.hasSearched}&currentPage=" + maxPage;
	                	    	});
	                	    	
	                	    	$('#fixedDiv').mouseover(function(){
	                	    		$('#fixedDiv').css("background","rgba(0,0,0,0.3)");
	                	    		$('#appearDiv').css("opacity","1");
	                	    	});
	                	    	$('#fixedDiv').mouseout(function(){
	                	    		$('#fixedDiv').css("background","rgba(0,0,0,0.1)");
	                	    		$('#appearDiv').css("opacity","0.1");
	                	    	});
	                	    	
	                	    	
	                	    });
                    	
                    	</script>
    
    
    
    
    
    <script>
	    $(function(){
	    	
	    	$(".joinGPSubmit").click(function(e){
	    		$.ajax({
	    			url: "<%=request.getContextPath() %>/joined_gp.do", 
	    			data: {"action": 'GP_JOIN',"gp_id":$(e.target).attr('joinId')},
	    			success: function(result){
// 		    			if(result == "false"){
// 		    				$("#joinSnackbar").addClass("show");
// 				        	setTimeout(function(){ $("#joinSnackbar").removeClass("show"); }, 3000);
// 		    			}
		    			if(result == "true"){
		    				$(e.target).text("已加入");
		    				$(e.target).addClass("disabled");
		    				$("#joinSnackbar").addClass("show");
				        	setTimeout(function(){ $("#joinSnackbar").removeClass("show"); }, 3000);
		    			}
	            }});
	    	});
	    	
	    	$(".favoriteGPSubmit").click(function(e){
	    		$.ajax({
	    			url: "<%=request.getContextPath() %>/favorite_gp.do", 
	    			data: {"action": 'GP_FAVORITE',"gp_id":$(e.target).attr('favoriteId')},
	    			success: function(result){
		    			console.log(result);
// 		    			if(result == "false"){
// 		    				$("#favoriteSnackbar").addClass("show");
// 				        	setTimeout(function(){ $("#favoriteSnackbar").removeClass("show"); }, 3000);
// 		    			}
		    			if(result == "true"){
		    				$(e.target).text("已收藏");
		    				$(e.target).addClass("disabled");
		    				$("#favoriteSnackbar").addClass("show");
				        	setTimeout(function(){ $("#favoriteSnackbar").removeClass("show"); }, 3000);
		    			}
					
	            }});
	    	});
	    	
	    });
	    

    
    
    </script>
    
</body>

</html>
