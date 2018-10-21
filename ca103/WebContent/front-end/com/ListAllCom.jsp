<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.mem.model.*"%>   
<%@ page import="com.com.model.*"%>
<%@ page import="com.comPo.model.*"%>
<%@ page import="com.comJoin.model.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	
	<!-- 下面這支css進去排版要重新排 -->
	<!--<link rel="stylesheet" type="text/css" href="/CA103G2/css/css.css"> -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/Community/ListAllCom.css">
	<!--      漂亮跳窗的引用-->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.28.1/sweetalert2.all.min.js" type="text/javascript"></script>
	<!--      漂亮icon的引用-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

<style>

html {
     height: 100%; 
    }
    
  body{
      background-position: center;
      background-size: cover;
      background-repeat: no-repeat;
      background-attachment: fixed;
      background-image: url(<%=request.getContextPath()%>/sources/img/Route/bg13.jpg);
     }

	
	.nav-link{
		color: #fff;
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
	$('.stop').mouseout(function(){
		$(this).css("background-color",'rgba(255, 255, 255, 1)');
	})
})

</script>

<title>ListAllCom</title>
</head>
<body>
<%@ include file="/sources/file/Home/NavBar.file" %>

<br>
<br>
<br>
<%	
	//	這邊直接呼叫ComService,跳過contorller
	//	當comList==null代表第一次進入此頁面,所以自動撈出全部的社群
	//	當comList!=null代表已經進入此頁面,所以把相關的查詢條件和查詢後的List設定好

	//第一次要從首頁進入，因為navBar有送出action
	String text, com_order;
	List<ComVO> comList = (List<ComVO>)session.getAttribute("comList");
	pageContext.setAttribute("comList",comList);
	text = (String)session.getAttribute("text");
	com_order = (String)session.getAttribute("com_order");
	
	List<ComVO> joinedComList = (List<ComVO>)session.getAttribute("joinedComList");
	%>
	

 
<%
  	ComVO comVO = (ComVO) request.getAttribute("comVO");	
	//在建立社群時，需要宣告comVO
	//因為目前Attribute沒有comVO，所以取到的都會是null
	//先留著(ComVO) request.getAttribute("comVO")，看之後有沒有機會用到
%>


	<!--	判斷是否有登入 	--> 	
	<c:set var="hasLogined" value="${false}"/>
	<c:if test="${memVO!=null}">
		<c:set var="hasLogined" value="${true}"/>
	</c:if>
	
	
	
<div class="container">
	<div class="row">
		<!--	左邊功能欄 	-->  
		<div class="col-3"> 
			<jsp:useBean id="memSvc" class="com.mem.model.MemService"/>
			<img  style="border-radius:10px;width:100%"
			src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${memVO.mem_id}" alt="" >
			<div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical" style="margin-top:3px"> 
			  <a href="#one" class="nav-link stop active" style="background-color:white; opacity:0.9; color:black"  
			  		id="v-pills-home-tab" data-toggle="pill" role="tab" aria-controls="v-pills-home" aria-selected="true"><b>社群搜尋列表</b></a>
			  <a href="#two" class="nav-link stop ${(hasLogined == false)? 'disabled':'' } " style="background-color:white; opacity:0.9; color:black"
			  		id="v-pills-profile-tab" data-toggle="pill" role="tab" aria-controls="v-pills-profile" aria-selected="false"><b>我加入的社群</b></a>
			  <a href="#three" class="nav-link stop" style="background-color:white; opacity:0.9; color:black" 
			  		id="v-pills-messages-tab" data-toggle="pill" role="tab" aria-controls="v-pills-messages" aria-selected="false"><b>我管理的社群</b></a>
			</div>	
			
			
			<!-- Modal燈箱，建立新的社群 -->
			<!-- Button trigger modal -->
			<button type="button" class="btn btn-primary " data-toggle="modal" data-target="#exampleModalCenter" style="width:200px;margin-top:3px">
			  建立社群
			</button>							
			<FORM action="<%=request.getContextPath()%>/com/ComServlet.do"  Method="Post" name="insertCom">
				<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLongTitle">建立新社群</h5>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<p>替你的社團取個名字</p>
								<input type="text" id="insert_com_name" name="com_name" size="15" value="<%= (comVO==null)? "遊戲王" : comVO.getCom_name()%>" >
								<p>選擇隱私設定</p>
								<select class="form-control btn-light" name="privacy">
									<option value="1">不公開的社群</option>
									<option value="0">公開的社群</option>
								</select>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
								<input type="hidden" name="action" value="insertCom">
								<button type="button" class="btn btn-primary" style="width:100px" value="建立社群" onclick="addCom(this)">建立</button>
							</div>
						</div>
					</div>
				</div>
			</FORM>
			<!-- /Modal -->		
		</div>
		<!-- 	/左邊功能欄 	-->
		
		
		<!-- 右邊欄位*******************************  	-->
		<div class="col-9">
			<div class="tab-content" id="v-pills-tabContent">
				<!-- ************************************** 社群搜尋列表 *********************************************************-->
				<div class="tab-pane fade show active" id="one" role="tabpanel" aria-labelledby="v-pills-home-tab">		  
					<div class="container" style="margin-top:15px;">
						<div class="row">
							<!-- 社群List排序條件******************************* -->		
							<ul class="nav nav-tabs" id="myTab" role="tablist">
								<li class="nav-item">
									<form action="<%=request.getContextPath()%>/com/ComServlet.do" Method="POST" name="com_id">
										<input type="hidden" value="getComList" name="action">
										<input type="hidden" value="com_id" name="order">
										<a class="nav-link stop ${('com_id'==com_order || null==com_order)?'active':'' }" id="home-tab" href="javascript:document.com_id.submit();" role="tab" aria-controls="home" aria-selected="true"><b>全部社群</b></a>
									</form>
								</li>
								<li class="nav-item">
									<form action="<%=request.getContextPath()%>/com/ComServlet.do" Method="POST" name="post_count">
										<input type="hidden" value="getComList" name="action">
										<input type="hidden" value="post_count" name="order">
										<input type="hidden" value="${ (text == null)?'':text }" name="text">
										<a class="nav-link stop ${('post_count'==com_order)?'active':'' }" id="contact-tab" href="javascript:document.post_count.submit();" role="tab" aria-controls="contact" aria-selected="false"><b>今日貼文數排名</b></a>
									</form>
								</li>
								<li class="nav-item">
									<form action="<%=request.getContextPath()%>/com/ComServlet.do" Method="POST" name="mem_count">
										<input type="hidden" value="getComList" name="action">
										<input type="hidden" value="mem_count" name="order">
										<a class="nav-link stop ${('mem_count'==com_order)?'active':'' }" id="profile-tab" href="javascript:document.mem_count.submit();" role="tab" aria-controls="profile" aria-selected="false"><b>社群人數排名</b></a>
									</form>
								</li>
								<li class="nav-item">
									<form action="<%=request.getContextPath()%>/com/ComServlet.do" Method="POST" name="create_time">
										<input type="hidden" value="getComList" name="action">
										<input type="hidden" value="create_time" name="order">
										<a class="nav-link stop ${('create_time'==com_order)?'active':'' }" id="contact-tab" href="javascript:document.create_time.submit();" role="tab" aria-controls="contact" aria-selected="false"><b>社群建立時間</b></a>
									</form>
								</li>						
							</ul>
							<!-- /社群List排序條件 *******************************	-->
							<!-- 社群List模糊搜尋 *******************************	-->			
							<form action="<%=request.getContextPath()%>/com/ComServlet.do" Method="POST" class="form-inline ml-auto" >
								<div class="input-group">
									<input type="hidden" value="getComList" name="action">
									<input type="hidden" value="${ (com_order == null)?'':com_order }" name="order">
									<input type="search" name="text" class="form-control" aria-label="Recipient's username" aria-describedby="button-addon2" placeholder="請輸入關鍵字">
									<div class="input-group-append">
										<button class="btn btn-outline-primary" type="submit" id="text">
											<i class="fa fa-search"></i>
										</button>
									</div>
								</div>
							</form>
							<!-- /社群List模糊搜尋 *******************************	-->
						</div>
					</div>	
					<%@ include file="/sources/file/Community/ComListPageTop.file" %>
					<c:if test="${ comList.size()>0 }">
					<c:forEach var="comVO" items="${comList}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<!-- 社群的cardList *******************************-->
					<div class="container">
						<div class="container border rounded" id="block" style="background-color: #ecf5ff; margin-top:15px; box-shadow: 7px 7px 2px rgba(0, 0, 0, 0.2); -webkit-transition-duration: 0.4s; /* Safari */
   							transition-duration: 0.4s;" onmouseover="this.style.background='#c5def9'" onmouseout="this.style.background='#ecf5ff'">
						<div class="row cardborder" style="height: 180px;">
							<!-- 放圖片 -->
							<div class="col-5 comimg1 align-items-center">
								<a href="<%=request.getContextPath()%>/com/ComServlet.do?action=getComContent&com_id=${comVO.com_id}">
									<img class="comimg" src="<%=request.getContextPath()%>/DBGetImage?com_id=${comVO.com_id}">	
								</a>
							</div>				
							<div class="col-7 comimg2">
								<div class="container">
									<div class="row" style="margin-top: 5px;"> 
										<!-- 社群名稱 -->
										<div style="margin-top:5px;">
											<form action="<%=request.getContextPath()%>/com/ComServlet.do" Method="Post" name="joinedComList${comVO.com_id}">
												<input type="hidden" value="getComContent" name="action">
												<input type="hidden" value="${comVO.com_id}" name="com_id">
												<a href="javascript:document.joinedComList${comVO.com_id}.submit();"><h3>${comVO.com_name}</h3></a>
											</form>								
										</div> 
										<div class="container">
									        <div class="row" style="height:70px; margin-top:5px;">
										        <div>						
													<!-- 社群簡介 -->
													${(comVO.introduction!=null)?comVO.introduction:"告訴其他人這個社團的成立宗旨。"}
										        </div>
										    </div>    
								        </div>
								        <div class="container">
											<!-- 社群相關資訊 -->
									        <div class="row" style="margin-top:5px;">
										        <div>						
													<!-- 社群管理員暱稱 -->
													管理員： ${memSvc.findMemById(comVO.mem_id).mem_nickname}
													<!-- 社群熱度 -->
													<img class="listIcon" alt="" src="<%=request.getContextPath()%>/sources/img/Community/png/valor.png">
													熱度：${(comVO.post_count)*3 + (comVO.mem_count) *2}
													<!-- 社群貼文數 -->
													<img class="listIcon" alt="" src="<%=request.getContextPath()%>/sources/img/Community/png/file.png">
													貼文：${comVO.post_count}
													<!-- 社群人數 -->
													<img class="listIcon" alt="" src="<%=request.getContextPath()%>/sources/img/Community/png/group.png">
													人數：${comVO.mem_count}
										        </div>
								        	</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						</div>
					</div>
					<!-- /社群的cardList *******************************-->
					</c:forEach>
					</c:if>
					<br>
					<%@ include file="/sources/file/Community/ComListPageBottom.file" %>						
				</div>
				
				
				<!-- ************************************** 我的社群清單 *********************************************************-->
				<div class="tab-pane fade" id="two" role="tabpanel" aria-labelledby="v-pills-profile-tab">
				<c:if test="${hasLogined = true}">
					<%--@ include file="pages/ComListPageTop.file" --%>
					<c:if test="${ joinedComList.size()>0 }">
					<c:forEach var="comVO" items="${joinedComList}">	<%-- begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>"--%>
					<!-- 社群的cardList *******************************-->
						<div class="container">
						<div class="container border rounded" id="block" style="background-color: #ecf5ff; margin-top:15px; box-shadow: 7px 7px 2px rgba(0, 0, 0, 0.2); -webkit-transition-duration: 0.4s; /* Safari */
   							transition-duration: 0.4s;" onmouseover="this.style.background='#c5def9'" onmouseout="this.style.background='#ecf5ff'">
						<div class="row cardborder" style="height: 180px;">
							<!-- 放圖片 -->
							<div class="col-5 comimg1 align-items-center">
								<a href="<%=request.getContextPath()%>/com/ComServlet.do?action=getComContent&com_id=${comVO.com_id}">
									<img class="comimg" src="<%=request.getContextPath()%>/DBGetImage?com_id=${comVO.com_id}">	
								</a>
							</div>				
							<div class="col-7 comimg2">
								<div class="container">
									<div class="row" style="margin-top: 5px;"> 
										<!-- 社群名稱 -->
										<div style="margin-top:5px;">
											<form action="<%=request.getContextPath()%>/com/ComServlet.do" Method="Post" name="joinedComList${comVO.com_id}">
												<input type="hidden" value="getComContent" name="action">
												<input type="hidden" value="${comVO.com_id}" name="com_id">
												<a href="javascript:document.joinedComList${comVO.com_id}.submit();"><h3>${comVO.com_name}</h3></a>
											</form>								
										</div> 
										<div class="container">
									        <div class="row" style="height:70px; margin-top:5px;">
										        <div>						
													<!-- 社群簡介 -->
													${(comVO.introduction!=null)?comVO.introduction:"告訴其他人這個社團的成立宗旨。"}
										        </div>
										    </div>    
								        </div>
								        <div class="container">
											<!-- 社群相關資訊 -->
									        <div class="row" style="margin-top:5px;">
										        <div>						
													<!-- 社群管理員暱稱 -->
													管理員： ${memSvc.findMemById(comVO.mem_id).mem_nickname}
													<!-- 社群熱度 -->
													<img class="listIcon" alt="" src="<%=request.getContextPath()%>/sources/img/Community/png/valor.png">
													熱度：${(comVO.post_count)*3 + (comVO.mem_count) *2}
													<!-- 社群貼文數 -->
													<img class="listIcon" alt="" src="<%=request.getContextPath()%>/sources/img/Community/png/file.png">
													貼文：${comVO.post_count}
													<!-- 社群人數 -->
													<img class="listIcon" alt="" src="<%=request.getContextPath()%>/sources/img/Community/png/group.png">
													人數：${comVO.mem_count}
										        </div>
								        	</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						</div>
					</div>
					<!-- /社群的cardList *******************************-->
					</c:forEach>
					</c:if>
				</c:if>
				</div>
				
				
				
				<div class="tab-pane fade" id="three" role="tabpanel" aria-labelledby="v-pills-messages-tab">
				<jsp:useBean id="joinedComSvc" class="com.comJoin.model.JoinedComService"/>
			
				<c:if test="${hasLogined = true}">
					<%--@ include file="pages/ComListPageTop.file" --%>
					<c:if test="${joinedComSvc.getCreatedComList(memVO.mem_id).size()>0 }">
					<c:forEach var="comVO" items="${ joinedComSvc.getCreatedComList(memVO.mem_id)}">	<%-- begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>"--%>
					<!-- 社群的cardList *******************************-->
						<div class="container">
						<div class="container border rounded" id="block" style="background-color: #ecf5ff; margin-top:15px; box-shadow: 7px 7px 2px rgba(0, 0, 0, 0.2); -webkit-transition-duration: 0.4s; /* Safari */
   							transition-duration: 0.4s;" onmouseover="this.style.background='#c5def9'" onmouseout="this.style.background='#ecf5ff'">
						<div class="row cardborder" style="height: 180px;">
							<!-- 放圖片 -->
							<div class="col-5 comimg1 align-items-center">
								<a href="<%=request.getContextPath()%>/com/ComServlet.do?action=getComContent&com_id=${comVO.com_id}">
									<img class="comimg" src="<%=request.getContextPath()%>/DBGetImage?com_id=${comVO.com_id}">	
								</a>
							</div>				
							<div class="col-7 comimg2">
								<div class="container">
									<div class="row" style="margin-top: 5px;"> 
										<!-- 社群名稱 -->
										<div style="margin-top:5px;">
											<form action="<%=request.getContextPath()%>/com/ComServlet.do" Method="Post" name="joinedComList${comVO.com_id}">
												<input type="hidden" value="getComContent" name="action">
												<input type="hidden" value="${comVO.com_id}" name="com_id">
												<a href="javascript:document.joinedComList${comVO.com_id}.submit();"><h3>${comVO.com_name}</h3></a>
											</form>								
										</div> 
										<div class="container">
									        <div class="row" style="height:70px; margin-top:5px;">
										        <div>						
													<!-- 社群簡介 -->
													${(comVO.introduction!=null)?comVO.introduction:"告訴其他人這個社團的成立宗旨。"}
										        </div>
										    </div>    
								        </div>
								        <div class="container">
											<!-- 社群相關資訊 -->
									        <div class="row" style="margin-top:5px;">
										        <div>						
													<!-- 社群管理員暱稱 -->
													管理員： ${memSvc.findMemById(comVO.mem_id).mem_nickname}
													<!-- 社群熱度 -->
													<img class="listIcon" alt="" src="<%=request.getContextPath()%>/sources/img/Community/png/valor.png">
													熱度：${(comVO.post_count)*3 + (comVO.mem_count) *2}
													<!-- 社群貼文數 -->
													<img class="listIcon" alt="" src="<%=request.getContextPath()%>/sources/img/Community/png/file.png">
													貼文：${comVO.post_count}
													<!-- 社群人數 -->
													<img class="listIcon" alt="" src="<%=request.getContextPath()%>/sources/img/Community/png/group.png">
													人數：${comVO.mem_count}
										        </div>
								        	</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						</div>
					</div>
					<!-- /社群的cardList *******************************-->
					</c:forEach>
					</c:if>
				</c:if>
				
				</div>
			</div>
		</div>
		<!-- /右邊欄位*******************************  	-->
	</div>
</div>


  

    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>




<script type="text/javascript">

<!-- 建立社群錯誤驗證 -->
function addCom(obj){
		if(document.getElementById("insert_com_name").value == ""){
			swal({
                title: "您尚未輸入社群名稱",
                html: "沒有名稱我是不會讓你過的",
                type: "info",
                confirmButtonText:"確定"/*改這裡*/
			});
		}else{
			document.insertCom.submit();
		}
	}
	
	
</script>

</body>
</html>