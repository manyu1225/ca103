<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="java.util.*"%>
<%@ page import="com.gp.model.*"%>
<%@ page import="com.msg_of_gp.model.*"%>
<%@ page import="com.reply_of_msg.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.joined_gp.model.*"%>
<%@ page import="com.favorite_gp.model.*"%>
<%@ page import="java.sql.*" %>
<!doctype html>
<html lang="en">
	<!-- 從Controller forward 一個 request scope 的 gpVO -->
  <head>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
   	
    
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    
<!--     	       jQueryUI select -->
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    
    <script type="text/javascript">
		var $jUI = $.noConflict(true);
	</script>
    
    
<!--     這裡為用ajax的版本 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript">
		var $aj = $.noConflict(true);
	</script>
    
    <title>揪團畫面</title>

  </head>
  <body>
  
  
  
		<c:set var="hasLogined" value="${false}"/>
		<c:if test="${memVO!=null}">
			<c:set var="hasLogined" value="${true}"/>
		</c:if>
	<%@ include file="/sources/file/Home/NavBar.file" %>

	<div id="gpRepSnackbar"></div>
	
	
	  			<!-- 設定圖片尺寸的Modal -->
		<div class="modal fade" id="setImageModal" tabindex="-1" role="dialog" aria-labelledby="setImageModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="setImageModalLabel">設定寬和高</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		      		<div class="form-group">
		        		<input class="form-control inline-Block" id="imgWidth" style="width:20%"type="number" min="1" max="500" placeholder="px"/>
		        		<span style="width:10%;">x</span>
		        		<input class="form-control inline-Block" id="imgHeight" style="width:20%" type="number" min="1" max="500" placeholder="px"/>
		        	</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary" id="dimensionConfirm">確認</button>
		      </div>
		    </div>
		  </div>
		</div>
	
	
	
	
<!-- 	檢舉揪團視窗 -->
	<div class="modal fade" id="GPRepModal" tabindex="-1" role="dialog" aria-labelledby="GPRepModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document" style="top:20%;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="GPRepModalLabel">揪團檢舉</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	            <label for="rep_detail" class="col-form-label">請輸入檢舉訊息:</label>
	            <textarea class="form-control" id="rep_detail" name="rep_detail"></textarea>
	          
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
	        <button id="GPRepSubmit" type="button" class="btn btn-primary">送出</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 訊息、回覆檢舉Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document" style="top:20%;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">警告訊息</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	       		 刪除就無法還原，你確定 ?
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	        <a class="btn btn-primary"id="msgdelSubmit" href="<%=request.getContextPath()%>">確定</a>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 更改揪團的燈箱 -->
	<div id="updatelightBox" style="display:none;">
        <div id="updatePage">
            <h2 class="title">更改揪團資訊</h2>
            <hr>
            <div class="updateInfo row">
                
                <form class="col-md-6 offset-md-3" id="formUpdate" method="post" action="<%=request.getContextPath() %>/gp.do" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="GP_TITLE">揪團標題</label>
                        <input type="text" class="form-control" id="GP_TITLE" name="GP_TITLE" maxlength="16" value="${gpVOupdate.gp_title}" placeholder="">
                        
                        <div class="error" id="error2">欄位請勿空白。長度請小於16</div>
                    </div>
                    
                    <div class="form-group">
                        <label for="GP_DATE" style="width:100%;">揪團日期、時間</label>
                        <input class="form-control inline-Block" type="text" id="GP_DATE" name="GP_DATE" value="${gpVO.gp_date}" style="width:42%;" disabled="disabled">
						<!-- Hour用JS去設定值 -->
                        <select class="form-control inline-Block" id="GP_HOUR" name="GP_HOUR" style="width:22%;" placeholder="">
                            <c:forEach var="myData" begin="0" end="24" >
                                <option value="${myData}" ${ (myData == gpVOupdate.gp_hour)?'selected':''}>${myData}:00</option>
                            </c:forEach>
                        </select>
                        
                        <label for="GP_TIME" class="inline-Block" style="width:10%;text-align:center;">持續</label>
                        <input type="text" class="form-control inline-Block" id="GP_TIME" name="GP_TIME" value="${gpVOupdate.gp_time}" style="width:10%;">
                        <span class="inline-Block" style="width:10%; text-align:center;">小時</span>
                        
                        <div class="error" id="error3">欄位請勿空白，且輸入正確格式</div>
                    </div>
                
                    <div class="form-group">
                        <label for="SIGN_UP_DD" style="width:100%;">報名截止日期</label>
                        <input class="form-control inline-Block" type="text" id="SIGN_UP_DD" name="SIGN_UP_DD" value="${gpVOupdate.sign_up_DD}" style="width:42%;" >

                        <label for="MIN_NUM">至少</label>
                        <input type="text" class="form-control inline-Block" id="MIN_NUM" name="MIN_NUM" value="${gpVOupdate.min_num}" style="width:10%;">
                        <span style="width:10%;">人，</span>
                        <label for="MAX_NUM">最多</label>
                        <input type="text" class="form-control inline-Block" id="MAX_NUM" name="MAX_NUM" value="${gpVOupdate.max_num}" style="width:10%;" placeholder="">
                        <span style="width:10%;">人</span>
                        
                        <div class="error" id="error4">欄位請勿空白，且輸入正確格式</div>
                    </div>
                    
                    <div class="form-group">
                        <label for="GP_DESC">揪團描述</label>
                        <input type="text" class="form-control" id="GP_DESC" name="GP_DESC" maxlength="66" value="${gpVOupdate.gp_desc}" placeholder="">
                        
                        <div class="error" id="error5">欄位請勿空白。長度請小於66</div>
                    </div>
<!-- 					Content用JS去設定值 -->
<!--                     <div class="form-group"> -->
<!--                         <label for="GP_CONTENT">揪團詳細內容</label> -->
<%--                         <textarea class="form-control AutoHeight" id="GP_CONTENT" name="GP_CONTENT" style="width:120%">${gpVOupdate.gp_content_edit}</textarea> --%>
<!--                     </div> -->

                    <div class="form-group">
                        <label for="GP_PHOTO">揪團封面照片</label>
                        <input type="file" class="form-control" id="GP_PHOTO" name="GP_PHOTO" placeholder="">
                        <img id="imgPreview" src="<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${gpVO.gp_id}" alt="" style="width:200;height:200px;"/>
                    </div>
                    <p>揪團公開設定</p>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="PUB_SET" id="inlineRadio1" value="0" ${gpVOupdate.pub_set == 0 ? "checked='true'":''}>
                        <label class="form-check-label" for="inlineRadio1">公開</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="PUB_SET" id="inlineRadio2" value="1" ${gpVOupdate.pub_set == 1 ? "checked='true'":''}>
                        <label class="form-check-label" for="inlineRadio2">不公開</label>
                    </div>
                    <div class="error" id="error0">${exceptionMessage}</div>
                    <input type="hidden" name="gp_id" value="${gpVOupdate.gp_id}">
                	<input type="hidden" name="action" value="GP_UPDATE">
                </form>
            </div>
            <div class="submit">
                <a class="btn btn-primary" id="updateSubmit">確定</a>
                <a class="btn btn-primary" id="updateCancel">取消</a>
            </div>
        </div>

    </div>
	<jsp:useBean id="memSrc" class="com.mem.model.MemService"/>
	<jsp:useBean id="rotSrc" class="com.route.model.RouteService"/>
	<%
// 	找揪團人數 
    	JoinedGPService jgpSrc = new JoinedGPService();
		GPVO gpVO = new GPVO();
		gpVO.setGp_id(request.getParameter("gp_id"));
    	int jgpNum = jgpSrc.findNumberOfGP(gpVO);
    	pageContext.setAttribute("jgpNum",jgpNum);
//     找揪團成員
		List<MemVO> memList = new ArrayList<>();
		memList = jgpSrc.SearchMemberByGP(gpVO);
		pageContext.setAttribute("memList",memList);
   	%>
   	<c:if test="${hasLogined}">
		   	<%	//判斷是否加入
		   		pageContext.setAttribute("isJoined", jgpSrc.isJoined((MemVO)request.getSession().getAttribute("memVO"), request.getParameter("gp_id")));
		   		//判斷是否為收藏
		   		Favorite_GPService fgpSrc = new Favorite_GPService();
		   		pageContext.setAttribute("isFavGP",fgpSrc.isFavGP((MemVO)request.getSession().getAttribute("memVO"), request.getParameter("gp_id")));
		   		//判斷是否為團長
		   		if(((MemVO)request.getSession().getAttribute("memVO")).getMem_id().equals(((GPVO)request.getAttribute("gpVO")).getMem_id())){
					pageContext.setAttribute("ismyGP",true);
		   		}else{
					pageContext.setAttribute("ismyGP",false);	
		   		}
		   		if((boolean)pageContext.getAttribute("isJoined")){
			   		//判斷是否為管理員
			   		JoinedGPVO myJgpVO = jgpSrc.SearchjGPVOByPK((MemVO)request.getSession().getAttribute("memVO"), (GPVO)request.getAttribute("gpVO"));
			   		if(myJgpVO.getPmsn_setting() == 1){
			   			pageContext.setAttribute("isManager", true);
			   		}else{
			   			pageContext.setAttribute("isManager", false);
			   		}
		   		}
		   		
		   	%>
	</c:if>
    <div class="container-fluid">
    	<div class="row paddingTop">
    		<div class="col-md-2 col-sm-12">
    			<a class="btn btn-warning returnBtnFixed" href="<%=request.getContextPath() %>/front-end/gp/GP_Browsing.jsp" style="margin-bottom:5px;">回瀏覽頁面</a>
    		</div>
            <div class="col-md-8 col-sm-12 hole_block">
				
                <h2>${gpVO.gp_title}</h2>
<!--                 上半部 -->
                <div class="row">
<%--                     <div class="imgGP col-md-6 col-sm-12" style="background-image: url(<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${gpVO.gp_id});"> --%>
					<div class="col-md-6 col-sm-12">
                    	<img src="<%=request.getContextPath() %>/GetGP_photoByGP_ID?gp_id=${gpVO.gp_id}" style="width:100%;height:100%;"/>
                    </div>
                    <div class="col-md-6 col-sm-12">
                        <div class="profileViewing">
<%--                             <a href="#" target="_blank" class="photoViewing" style="background-image: url(<%=request.getContextPath() %>/sources/img/gp/smile.gif)"></a> --%>
							<a target="_blank" href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=goToMemberView&mem_id=${gpVO.mem_id}">
								<img src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${gpVO.mem_id}" style="width:100px;height:100px; border-radius:50%;"/>
							</a>
                            <div class="inlineBlock">
                                <a target="_blank" href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=goToMemberView&mem_id=${gpVO.mem_id}" style="color: #3753df">${memSrc.findMemById(gpVO.mem_id).mem_nickname}</a>
<!--                                 <div>會員成就?</div> -->
                                
                            </div>
                            
                        </div>
                        <hr>
                        <div class="desc">
                            <div><img class="icon" src="<%=request.getContextPath() %>/sources/img/gp/calendar-2x.png"><span>${gpVO.gp_date} ${gpVO.gp_hour}:00 (${gpVO.gp_time}小時) </span></div>
                            <div>
<%-- 	                   			<form action="/CA103G2/Route/RouteServlet.do" method="POST" name="${gpVO.gp_id}${gpVO.rot_id}"> --%>
<!-- 									<input type="hidden" value="getRouteDetailed" name="action"> -->
<%-- 									<input type="hidden" value="${gpVO.rot_id}" name="rot_id"> --%>
<!-- 									<input type="hidden" value="rot_popu" name="rot_popu"> -->
<%-- 									<img class="icon" src="<%=request.getContextPath() %>/sources/img/gp/map-marker-2x.png"> --%>
<%-- 									<a style="color:#3753df;" href="javascript:document.${gpVO.gp_id}${gpVO.rot_id}.submit();">${rotSrc.getRouteDetailed(gpVO.rot_id).rot_name}</a> --%>
<!-- 								</form> -->
								<img class="icon" src="<%=request.getContextPath() %>/sources/img/gp/map-marker-2x.png">
								<a target="_blank" style="color:#3753df;" href="<%=request.getContextPath() %>/Route/RouteServlet.do?action=getRouteDetailed&rot_id=${gpVO.rot_id}&rot_popu=rot_popu">${rotSrc.getRouteDetailed(gpVO.rot_id).rot_name}</a>
							
                       		</div>
                       		
                            <div><img class="icon" src="<%=request.getContextPath() %>/sources/img/gp/people-2x.png"><span>${jgpNum + 1}/${gpVO.max_num}人</span></div>
                        </div>
                        <hr>
                        <div class="under"></div>
                        <div id="btnRow">
<!-- 							判定是否為揪團人 -->
							<c:if test="${hasLogined}">
								<c:if test="${gpVO.gp_status<=1}">
								
		                       		<c:if test="${ismyGP || isManager}">
		                          			<a class="btn btn-secondary btnSet">設定&nbsp;<i class="fas fa-cog"></i></a>
		                          			<a class="btn btn-danger" href="<%=request.getContextPath() %>/gp.do?action=GP_VIEWING_CANCEL&gp_id=${gpVO.gp_id}">取消揪團</a>
		                          		</c:if>
								
									<c:if test="${!ismyGP}">
										<c:if test="${!isJoined}">
			                        		<a class="btn btn-info" href="<%=request.getContextPath() %>/joined_gp.do?action=GP_VIEWING_JOIN&gp_id=${gpVO.gp_id}">加入揪團</a>
										</c:if>
										<c:if test="${isJoined}">
											<a class="btn btn-info" href="<%=request.getContextPath() %>/joined_gp.do?action=GP_VIEWING_UNJOIN&gp_id=${gpVO.gp_id}">退出揪團</a>
										</c:if>
										<c:if test="${!isFavGP}">
											<a class="btn btn-info" href="<%=request.getContextPath() %>/favorite_gp.do?action=GP_VIEWING_FAV&gp_id=${gpVO.gp_id}">加入收藏</a>
										</c:if>
										<c:if test="${isFavGP}">
											<a class="btn btn-info" href="<%=request.getContextPath() %>/favorite_gp.do?action=GP_VIEWING_UNFAV&gp_id=${gpVO.gp_id}">取消收藏</a>
										</c:if>
										
			                            <a class="btn btn-danger"  data-toggle="modal" data-target="#GPRepModal">檢舉揪團</a>
									</c:if>
<!-- 		                            <a class="btn btn-primary">分享</a> -->
	                           </c:if>
		                           
	                           <c:if test="${!(gpVO.gp_status<=1)}">
	                           		<c:if test="${!isFavGP}">
										<a class="btn btn-info" href="<%=request.getContextPath() %>/favorite_gp.do?action=GP_VIEWING_FAV&gp_id=${gpVO.gp_id}">加入收藏</a>
									</c:if>
									<c:if test="${isFavGP}">
										<a class="btn btn-info" href="<%=request.getContextPath() %>/favorite_gp.do?action=GP_VIEWING_UNFAV&gp_id=${gpVO.gp_id}">取消收藏</a>
									</c:if>
	                           </c:if>
                         	</c:if>
                         	
                            <div class="text-danger">報名倒數：
								<!-- 將list內的VO物件丟到Scope -->
                              <c:set var="temp">
                              	${gpVO.sign_up_DD.getTime()}
                              </c:set>
                              	<% 
	                              	Long currentTime = (new Timestamp(System.currentTimeMillis())).getTime();
	                              	Long totalHour = (Long.valueOf((String)pageContext.getAttribute("temp")) - currentTime)/3600/1000;
	                              	Long day = totalHour/24;
	                              	Long hour = totalHour%24;
	                              	pageContext.setAttribute("day", day);
	                              	pageContext.setAttribute("hour", hour);
								%>
                              ${day}天  ${hour}小時
							</div>
                        </div>
                    </div>
                </div>
                
<!--                 下半部分頁 -->
				<a name="tabpage">
				<div class="row">
				
					<div class="col-md-12 col-sm-12">
			        <!--    Tab分頁-->
			                <ul class="nav nav-pills mb-3 " id="pills-tab" role="tablist" style="width:50%;">
			                      <li class="nav-item flex-sm-fill text-sm-center">
			                        <a class="nav-link active" id="pills-1-tab" data-toggle="pill" href="#pills-1" role="tab" aria-controls="pills-1" aria-selected="true">行程內容</a>
			                      </li>
			                      <li class="nav-item flex-sm-fill text-sm-center">
			                        <a class="nav-link" id="pills-2-tab" data-toggle="pill" href="#pills-2" role="tab" aria-controls="pills-2" aria-selected="false">留言版</a>
			                      </li>
			                      <li class="nav-item flex-sm-fill text-sm-center">
			                        <a class="nav-link" id="pills-3-tab" data-toggle="pill" href="#pills-3" role="tab" aria-controls="pills-3" aria-selected="false">揪團成員</a>
			                      </li>
			                </ul>
							   	              
			                <div class="tab-content" id="pills-tabContent">
<!--                                 			                 行程內容 -->
			                     <div class="tab-pane fade show active" id="pills-1" role="tabpanel" aria-labelledby="pills-1-tab" style="word-break:break-all">
			                     		<div id="GP_CONTENT_Show" style="width:77%;">
			                     		
			                     			<c:if test="${ismyGP or isManager}">
<!-- 			                     				<a class="btn btn-info" id="contentEditBtn">編輯</a> -->
			                     				<a style="position:absolute;right:10%;" class="btn btn-info" id="contentEditBtn">編輯文章&nbsp;<i class="fas fa-edit"></i></a>
			                     			</c:if>
											${gpVO.gp_content}
			                     		</div>
										<div id="contentUpdateBlock" style="display:none;">
												<form id="GPContentForm"class="row" method="post" action="<%=request.getContextPath() %>/gp.do">
													<div class="col-md-6">
														<div class="form-group">
<!-- 									                        <label for="GP_CONTENT_INPUT">輸入揪團行程</label> -->
<!-- 									                        <label class="btn btn-info" for="" id="imgInsertReplace">插入圖片</label> -->
<!-- 									                        <a class="btn btn-info" id="contentSubmit">完成</a> -->
<!-- 									                        <a class="btn btn-info" id="contentCancel" href="javascript:window.location.reload()">取消</a> -->
																<label class="btn btn-info" for="" id="imgInsertReplace" style="opacity:0.5;"><i class="far fa-file-image"></i> 插入圖片</label>
									                        <input type='file' id="imgInsert" style="display: none;"/>
									                        <textarea class="form-control" id="GP_CONTENT_INPUT" name="GP_CONTENT_EDIT" style="width:100%">${(gpVO==null)?'':gpVO.gp_content_edit}</textarea>
								                        	<div id="ContentBtnRow" style="position:absolute;right:15px; opacity:0.5;">
										                        <a  class="btn btn-info" id="contentCancel" href="javascript:window.location.reload()">取消</a>
										                        <a  class="btn btn-info" id="contentSubmit">儲存</a>
															</div>
									                    </div>
		
		
													</div>
													<div class="col-md-6">
														<div class="form-group">
<!-- 									                        <label for="GP_CONTENT_VIEW">預覽</label> -->
									                        <div id="GP_CONTENT_VIEW" class="form-control" style="margin-top:45px;word-break:break-all;width:170%;"></div>
									                        <input type="hidden" id="GP_CONTENT" name="GP_CONTENT" />
									                    </div>
													</div>
													<input type="hidden" name="action" value="GP_CONTENT_UPDATE"/>
													<input type="hidden" name="gp_id" value="${gpVO.gp_id}"/>
													<input type="hidden" id="arrMap" name="GP_CONTENT_PHOTO"/>

												</form>
										</div>
			                     </div>
			                     
<!--                                留言板-->
			                     <div class="tab-pane fade" id="pills-2" role="tabpanel" aria-labelledby="pills-2-tab">
			                     
			                     		<div class="bg">
<!-- 			                     		會員輸入框 -->
											<c:if test="${isJoined or ismyGP}">
											
					                     		<div class="row rowCmtInput">
							                        <img class="imgCmtInputer" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${memVO.mem_id}">
							                        <div class="cmtInputBox">
							                                <div class="cmtInputer">${memVO.mem_nickname}</div>
							                            <form action="<%=request.getContextPath() %>/msg_of_gp.do" method="post">
							                                <textarea class="AutoHeight cmtInput" name="msg_content" maxlength="166" placeholder=" 請輸入留言"></textarea>
							                                <input type="hidden" name="action" value="NEW_MSG">
							                                <input type="hidden" name="gp_id" value="${gpVO.gp_id}">
							                                <a class="btn btn-info cmtInputSubmit">送出</a>
							                            </form>
							                        </div>
							                    </div>
			                     			</c:if>
			                     			
			                     			
			                     			
			                     			<% 
			                     				MSG_OF_GPService msgSrc = new MSG_OF_GPService();
												List<MSG_OF_GPVO> msglist = msgSrc.findMessages((GPVO)request.getAttribute("gpVO"));
												pageContext.setAttribute("msglist",msglist);
											%>	
			                     			<c:if test="${!isEmp}">
							                     			<c:forEach var="msgVO" items="${msglist}" begin="0" end="${msglist.size()}">
																
																<% 
								                     				Reply_of_MSGService replySrc = new Reply_of_MSGService();
																	List<Reply_of_MSGVO> replylist = replySrc.findReplies((MSG_OF_GPVO)pageContext.getAttribute("msgVO"));
																	pageContext.setAttribute("replylist",replylist);
																%>	
																
									                     		<div class="msgBox">
									                     		
									                     			
				<!-- 							                        comment抓來的訊息 -->
											                        <div class="row">
											                            <img class="imgCommenter" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${msgVO.mem_id}">
											                            <div class="commentBox">
											                                <a class="commenter">${msgVO.mem_id==gpVO.mem_id?'(團長)':''}${memSrc.findMemById(msgVO.mem_id).mem_nickname}</a>
											                                <div class="commentTextBox visible" ><span class="commentText">${msgVO.msg_content}
											                                    </span>
											                                    <a class="shinkComment smallText" style="display: none">...縮回</a>
											                                </div>
											                                <div class="smallText">
											                                    <c:if test="${isJoined or ismyGP}">
											                                    	<a class="replyBtn">回覆</a>
											                                    </c:if>
											                                    <c:if test="${(memVO.mem_id == msgVO.mem_id) or ismyGP or isManager}">
											                                    	<a class="cmtDelete" data-toggle="modal" data-target="#exampleModal" deleteString="/msg_of_gp.do?action=DEL_MSG&gp_id=${param.gp_id}&msg_id=${msgVO.msg_id}">刪除</a>
											                                    </c:if>
											                                    	
											                                    <span class="commentTs">
											                                    	<%
											                                    		Long msgTime = (((MSG_OF_GPVO)pageContext.getAttribute("msgVO")).getMsg_time()).getTime();
											                                    		Long now = new Timestamp(System.currentTimeMillis()).getTime();
											                                    		Long msg_hour = (now-msgTime)/1000/60/60;
											                                    		pageContext.setAttribute("msg_day", msg_hour/24);
											                                    		pageContext.setAttribute("msg_dayStr", msg_hour/24 + "天");
											                                    		pageContext.setAttribute("msg_hour", msg_hour%24);
											                                    	%>
											                                    	${msg_day == 0?'':msg_dayStr} ${msg_hour}小時前
											                                    </span>
											                                    <a class="expandComment" style="display: none">...查看更多</a>
											                                </div>
											                                <div class="replyCheck" ${replylist.size() == 0? "style=\"display:none;\"":''}>
											                                    <i class="fas fa-reply"></i>
											                                    <a class="smallText">查看${replylist.size()}則回覆</a>
											                                </div>
				<!-- 							                               如果沒有回應=>無查看回復div -->
											                            </div>
											                        </div>
											                        
											                        
											                        
				<!-- 							                       	 回應 -->
																	<c:forEach var="replyVO" items="${replylist}" begin="0" end="${replylist.size()}">
																		
													                        <div class="row replyRow" style="display: none">
													                            <img class="imgReplier" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${replyVO.mem_id}">
													                            <div class="replyBox">
													                                <a class="replier">${replyVO.mem_id==gpVO.mem_id?'(團長)':''}${memSrc.findMemById(replyVO.mem_id).mem_nickname}</a>
													                                <div class="replyTextBox"><span class="replyText">${replyVO.reply_content}</span>
													                                </div>
													                                <div class="smallText">
														                                <c:if test="${(memVO.mem_id == replyVO.mem_id) or ismyGP or isManager}">
													                                    	<a class="repDelete"   data-toggle="modal" data-target="#exampleModal" deleteString="/reply_of_msg.do?action=DEL_REPLY&gp_id=${param.gp_id}&reply_id=${replyVO.reply_id}">刪除</a>
													                                    </c:if>
														                                <span class="replyTs">
															                                <%
													                                    		Long replyTime = (((Reply_of_MSGVO)pageContext.getAttribute("replyVO")).getReply_time()).getTime();
													                                    		now = new Timestamp(System.currentTimeMillis()).getTime();
													                                    		Long reply_hour = (now-replyTime)/1000/60/60;
													                                    		pageContext.setAttribute("reply_day", reply_hour/24);
													                                    		pageContext.setAttribute("reply_dayStr", reply_hour/24 + "天");
													                                    		pageContext.setAttribute("reply_hour", reply_hour%24);
													                                    	%>
													                                    	${reply_day == 0?'':reply_dayStr} ${reply_hour}小時前
														                                </span>
																					</div>
													                            </div>
													
													                        </div>
												                        
																	</c:forEach>
																
				<!-- 													輸入區 -->
																	<c:if test="${isJoined or ismyGP}">
												                        <div class="row rowRepInput" style="display: none">
												                            <img class="imgRepInputer" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${memVO.mem_id}">
												                            <div class="repInputBox">
												                                <div class="repInputer">${memVO.mem_nickname}</div>
												                                <form action="<%=request.getContextPath() %>/reply_of_msg.do" method="post">
													                                <textarea class="AutoHeight repInput" name="reply_content" maxlength="166" placeholder=" 請輸入回覆"></textarea>
													                                <input type="hidden" name="action" value="NEW_REPLY">
													                                <input type="hidden" name="gp_id" value="${gpVO.gp_id}">
													                                <input type="hidden" name="msg_id" value="${msgVO.msg_id}">
													                                <a class="btn btn-info repInputSubmit">送出</a>
												                                </form>
												                            </div>
												                        </div>
											                        </c:if>
											                        
											                    </div>
											                    
											                    
											                  </c:forEach>  
							                  </c:if>  
							                  
							              		<c:if test="${isEmp}">
							              					<% 
																List<MSG_OF_GPVO> msglistForEmp = msgSrc.findMessagesForEmp((GPVO)request.getAttribute("gpVO"));
																pageContext.setAttribute("msglistForEmp",msglistForEmp);
															%>	
							                  				<c:forEach var="msgVO" items="${msglistForEmp}" begin="0" end="${msglistForEmp.size()}">
																
																<% 
								                     				Reply_of_MSGService replySrc = new Reply_of_MSGService();
																	List<Reply_of_MSGVO> replylistForEmp = replySrc.findRepliesForEmp((MSG_OF_GPVO)pageContext.getAttribute("msgVO"));
																	pageContext.setAttribute("replylistForEmp",replylistForEmp);
																%>	
																
									                     		<div class="msgBox">
									                     		
									                     			
				<!-- 							                        comment抓來的訊息 -->
											                        <div class="row">
											                            <img class="imgCommenter" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${msgVO.mem_id}">
											                            <div class="commentBox">
											                                <a class="commenter">${msgVO.mem_id==gpVO.mem_id?'(團長)':''}${memSrc.findMemById(msgVO.mem_id).mem_nickname}</a>
											                                
											                                
											                                <div class="commentTextBox visible" >
												                                <c:if test="${msgVO.msg_status == 1}"><del style="color:#d5d5d5;"></c:if>
												                                	<span class="commentText">${msgVO.msg_content}
												                                    </span>
											                                    <c:if test="${msgVO.msg_status == 1}"></del></c:if>
											                                    <a class="shinkComment smallText" style="display: none">...縮回</a>
											                                </div>
											                                <div class="smallText">
											                                    	
											                                    <span class="commentTs">
											                                    	<%
											                                    		Long msgTime = (((MSG_OF_GPVO)pageContext.getAttribute("msgVO")).getMsg_time()).getTime();
											                                    		Long now = new Timestamp(System.currentTimeMillis()).getTime();
											                                    		Long msg_hour = (now-msgTime)/1000/60/60;
											                                    		pageContext.setAttribute("msg_day", msg_hour/24);
											                                    		pageContext.setAttribute("msg_dayStr", msg_hour/24 + "天");
											                                    		pageContext.setAttribute("msg_hour", msg_hour%24);
											                                    	%>
											                                    	${msg_day == 0?'':msg_dayStr} ${msg_hour}小時前
											                                    </span>
											                                    <a class="expandComment" style="display: none">...查看更多</a>
											                                </div>
											                                <div class="replyCheck" ${replylistForEmp.size() == 0? "style=\"display:none;\"":''}>
											                                    <i class="fas fa-reply"></i>
											                                    <a class="smallText">查看${replylistForEmp.size()}則回覆</a>
											                                </div>
				<!-- 							                               如果沒有回應=>無查看回復div -->
											                            </div>
											                        </div>
											                        
											                        
											                        
				<!-- 							                       	 回應 -->
																	<c:forEach var="replyVO" items="${replylistForEmp}" begin="0" end="${replylistForEmp.size()}">
																		
													                        <div class="row replyRow" style="display: none">
													                            <img class="imgReplier" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${replyVO.mem_id}">
													                            <div class="replyBox">
													                                <a class="replier">${replyVO.mem_id==gpVO.mem_id?'(團長)':''}${memSrc.findMemById(replyVO.mem_id).mem_nickname}</a>
													                                
													                                
													                                <c:if test="${replyVO.reply_status == 1}"><del style="color:#d5d5d5;"></c:if>
													                                	<div class="replyTextBox"><span class="replyText">${replyVO.reply_content}</span>
													                                <c:if test="${replyVO.reply_status == 1}"></del></c:if>
													                                
													                                </div>
													                                <div class="smallText">
														                                <span class="replyTs">
															                                <%
													                                    		Long replyTime = (((Reply_of_MSGVO)pageContext.getAttribute("replyVO")).getReply_time()).getTime();
													                                    		now = new Timestamp(System.currentTimeMillis()).getTime();
													                                    		Long reply_hour = (now-replyTime)/1000/60/60;
													                                    		pageContext.setAttribute("reply_day", reply_hour/24);
													                                    		pageContext.setAttribute("reply_dayStr", reply_hour/24 + "天");
													                                    		pageContext.setAttribute("reply_hour", reply_hour%24);
													                                    	%>
													                                    	${reply_day == 0?'':reply_dayStr} ${reply_hour}小時前
														                                </span>
																					</div>
													                            </div>
													
													                        </div>
												                        
																	</c:forEach>
											                    </div>
											                    
											                    
											                  </c:forEach> 
							                  
							                  
							                  
							                  
							            		</c:if>
							                  
			                     		</div>
			                     	
			                     </div>
<!--                                揪團成員-->
			                     <div class="tab-pane fade" id="pills-3" role="tabpanel" aria-labelledby="pills-3-tab">
			                     	
			                     	
			                     	<table class="table table-striped" style="width:100%">
			                              <thead>
			                                <tr>
			                                  <th scope="col" id="c1">#</th>
			                                  <th scope="col" id="c2">暱稱</th>
			                                  <th scope="col" id="c3">入群時間</th>
			                                  <th scope="col" id="c4">權限</th>
<!-- 			                                  <th scope="col" id="c5">最高成就</th> -->
			                                  <th scope="col" id="c6">action</th>
			                                </tr>
			                                
			                              </thead>
			                              
			                     			<tbody>
			                     			
			                     			
			                     				<tr>
				                                  <th scope="row"><img src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${gpVO.mem_id}"></th>
				                                  <td>${memSrc.findMemById(gpVO.mem_id).mem_nickname}</td>
				                                  <td><fmt:formatDate value="${gpVO.cre_time}" pattern="yyyy-MM-dd"/></td>
				                                  <td>團長</td>
<!-- 				                                  <td>最高成就?</td> -->
				                                  <td><a target="_blank" href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=goToMemberView&mem_id=${gpVO.mem_id}" class="btn btn-info">查看</a></td>
				                                </tr>
			                     				
			                     				<c:forEach var="memVO_jgp" items="${memList}" begin="0" end="${memList.size()}">
													
													<%//抓某成員的jGPVO物件
														JoinedGPVO jgpVO = jgpSrc.SearchjGPVOByPK((MemVO)pageContext.getAttribute("memVO_jgp"), (GPVO)request.getAttribute("gpVO"));
														pageContext.setAttribute("jgpVO", jgpVO);
													%>
													
					                                <tr>
					                                  <th scope="row"><img src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${memVO_jgp.mem_id}" style="width: 70px;height: 70px;"></th>
					                                  <td>${memVO_jgp.mem_nickname}</td>
					                                  <td><fmt:formatDate value="${jgpVO.join_time}" pattern="yyyy-MM-dd"/></td>
					                                  <td>
					                                  		${jgpVO.pmsn_setting=='0'?'一般成員':''}
					                                  		${jgpVO.pmsn_setting=='1'?'管理員':''}
					                                  </td>
<!-- 					                                  <td>最高成就?</td> -->

				                                  	  <td>
				                                  		<a target="_blank" href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=goToMemberView&mem_id=${memVO_jgp.mem_id}" class="btn btn-info">查看</a>
					                                  	<c:if test="${ismyGP or (isManager and memVO_jgp.mem_id != memVO.mem_id)}">
						                                  	<a href="<%=request.getContextPath() %>/joined_gp.do?action=GP_JOINED_KICK&gp_id=${gpVO.gp_id}&mem_id=${memVO_jgp.mem_id}" class="btn btn-info">剔除</a>
						                                  	
						                                  	<c:if test="${jgpVO.pmsn_setting == 0}">
						                                  		<a href="<%=request.getContextPath() %>/joined_gp.do?action=SET_PMSN&gp_id=${gpVO.gp_id}&mem_id=${memVO_jgp.mem_id}&status=1" class="btn btn-info">設為管理員</a>
						                                  	</c:if>
						                                  	<c:if test="${jgpVO.pmsn_setting == 1}">
						                                  		<a href="<%=request.getContextPath() %>/joined_gp.do?action=SET_PMSN&gp_id=${gpVO.gp_id}&mem_id=${memVO_jgp.mem_id}&status=0" class="btn btn-info">設為一般成員</a>
						                                  	</c:if>
					                                  	</c:if>
						                                
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
    </div>
    
    
      
    <link rel="stylesheet" href="<%=request.getContextPath() %>/sources/css/gp/GP_Viewing.css">
    
    <script>
    		var arrMap = new Array(); 
    		
    		//顯示更改的錯誤訊息
			$(function(){
				<c:forEach begin="0" end="5" varStatus="loop">
					if(${(errorIndex==null)?false:errorIndex.contains(loop.index)}){
						
						$('#error${loop.index}')[0].style.display='block';
					}
				</c:forEach>
				
			});
    		//燈箱操作
    		$(function(){
    			if(${(showLB==null)?false:showLB}){
    				$("#updatelightBox")[0].style.display="block";
    			}
    			
    		});
    		//從留言回來跳到第二頁
    		$(function(){
    			if(${param.tab == '2'?true:false}){
    				$('#pills-2-tab').tab('show');
    			}else if(${param.tab == '3'?true:false}){
    				$('#pills-3-tab').tab('show');
    			}
    		});
			//揪團檢舉Snack
    		$(function(){
    			$("#GPRepSubmit").click(function(){
    				$('#GPRepModal').modal('hide')
    				$aj.ajax({
       	    			url: "<%=request.getContextPath()%>/reported_gp.do", 
       	    			data: {"action": 'GP_REPORTED',"gp_id":'${gpVO.gp_id}',"rep_detail":$("#rep_detail").val()},
       	    			success: function(result){
       		    			if(result == "false"){
       		    				$("#gpRepSnackbar").text("勿重複檢舉");
       		    			}else if(result == "true"){
       		    				$("#gpRepSnackbar").text("檢舉已送出");
       		    			}
       		    			$("#gpRepSnackbar").addClass("show");
    			        	setTimeout(function(){ $("#gpRepSnackbar").removeClass("show"); }, 3000);
       	          	}});
    			});
    	    	
    	    });
			
    		$(function(){
    			var arrMap0 = "${gpVO.gp_content_photo}".split(',');
    			for(var i=0;i<arrMap0.length/2;i++){
        			arrMap[i] = arrMap0[2*i].concat(',',arrMap0[2*i+1]);
        			
    			}
    		});
    		

    </script>
    <script src="<%=request.getContextPath() %>/sources/js/gp/GP_Viewing.js"></script>
  </body>
</html>