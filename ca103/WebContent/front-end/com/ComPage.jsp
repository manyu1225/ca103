<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.com.model.*"%>
<%@ page import="com.comPo.model.*"%>
<%@ page import="com.comFile.model.*"%>
<%@ page import="com.comJoin.model.*"%>
<%@ page import="com.comRes.model.*"%>
<%@ page import="com.util.community.*"%>

<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/Community/ComPage.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
<link href='http://fonts.googleapis.com/css?family=Lobster' rel='stylesheet' type='text/css'> 

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

</style>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	
<title>ComPage</title>
</head>
<body>
<%@ include file="/sources/file/Home/NavBar.file"%>



<%
	//	取出Concroller ComServlet.java已存入request的ComVO物件
	
	List<String> comPo_idList = (List<String>) request.getAttribute("comPo_idList");
	List<List<String>> cuf_idsList = (List<List<String>>) request.getAttribute("cuf_idsList");
	List<List<String>> comResVOsList = (List<List<String>>) request.getAttribute("comResVOsList");
	
	
	List<ComPoVO> comPoList= (List<ComPoVO>) request.getAttribute("comPoList");
	
	ComVO comVO = (ComVO) request.getAttribute("comVO");
	
	List<MemVO> comMemberList = (List<MemVO>) request.getAttribute("comMemberList");
	
	List<MemVO> poMemVOList = (List<MemVO>) request.getAttribute("poMemVOList");
	
	List<String> siedeFileList = (List<String>) request.getAttribute("siedeFileList");
	
	List<String> comMem_idList = (List<String>) request.getAttribute("comMem_idList");
	
	List<JoinedComVO> waitForCheckList = (List<JoinedComVO>) request.getAttribute("waitForCheckList");
	
	List<String> allFileList = (List<String>) request.getAttribute("allFileList");
	
	//	同時使用JSTL與JSP有機會產生Bug而取不到值，需要再set一次來確保能取到值
	pageContext.setAttribute("comVO",comVO);
	pageContext.setAttribute("comMemberList", comMemberList);
	pageContext.setAttribute("comPo_idList", comPo_idList);
	pageContext.setAttribute("cuf_idsList", cuf_idsList);
	pageContext.setAttribute("comResVOsList", comResVOsList);
	pageContext.setAttribute("poMemVOList", poMemVOList);
	pageContext.setAttribute("siedeFileList", siedeFileList);
	pageContext.setAttribute("comMem_idList",comMem_idList);
	pageContext.setAttribute("waitForCheckList", waitForCheckList);
	pageContext.setAttribute("allFileList", allFileList);
	
	
	pageContext.setAttribute("memVO", request.getSession().getAttribute("memVO"));
	
	
%>





	<!-- 	判斷是否有登入 ******************************* -->
	<c:set var="hasLogined" value="${false}"/>
	<c:if test="${memVO!=null}">
		<c:set var="hasLogined" value="${true}"/>
	</c:if>
	
	<!-- 	判斷會員的權限等級 ******************************* -->
	<jsp:useBean id="joinedComSvc" class="com.comJoin.model.JoinedComService"/>
	<c:set var="pms" value="${joinedComSvc.getOneForCheck(comVO.com_id,memVO.mem_id).pm_setting}"/>


	<div class="container margin-top">
		<div class="row">			
			<div class="col-3">
				<!-- 社群名稱&狀態 ******************************* -->
				<div style="margin-bottom: 5px; margin-left: 5px">
					<h1 style="color:white"><b>${comVO.com_name}</b></h1>
					<c:if test="${comVO.privacy ==0}">
					<i class="fas fa-globe-americas" style="color:white"></i><b style="color:white"> 公開社團</b>
					</c:if>
					<c:if test="${comVO.privacy ==1}">
					<i class="fas fa-lock" style="color:white"></i><b style="color:white"> 不公開社團</b>
					</c:if>
				${joinedComSvc.getOneForCheck(comVO.com_id,memVO.mem_id).pm_setting}
				</div>
				<!-- FunctionBar ******************************* -->
				<div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical" style="background-color:white; opacity:0.9;border-radius:10px">
					<!-- Return Community List ******************************* -->
					<form action="<%=request.getContextPath()%>/com/ComServlet.do" Method="POST">
						<input type="hidden" value="getComList" name="action">
						<button type="submit" class="btn btn-light introduction" style="border:1px solid #e3e3e3;width:100%"><b>返回社群列表</b></button>
					</form>
					<!-- First Announcement ******************************* -->
					<a href="#one" class="nav-link allborder ${(pms == null or pms == 0)? 'active':'' }" id="v-pills-home-tab" data-toggle="pill" role="tab" aria-controls="v-pills-home" aria-selected="true">
					<i class="fa fa-bell margin-left" aria-hidden="true" style="margin-right:5px"></i><b>公告</b></a> 
					
					<!-- Second	comPoList ******************************* -->
					<a href="#two" class="nav-link allborder ${(pms == null or pms ==0)? 'disabled':'' }${(pms == 1 or pms == 2)? 'active':'' }" data-toggle="pill" role="tab" aria-controls="v-pills-profile" aria-selected="false">
					<img src="<%=request.getContextPath()%>/sources/img/Community/png/chat.png" /><b>貼文</b></a> 
					
					<!-- Third	memberList ******************************* -->
					<a href="#three" class="nav-link  allborder" data-toggle="pill" role="tab" aria-controls="v-pills-profile" aria-selected="false">
					<img src="<%=request.getContextPath()%>/sources/img/Community/png/group.png" /><b>成員</b></a> 
					
					<!-- Fourth	memberList ******************************* -->
					<a href="#four" class="nav-link  allborder ${(pms == null or pms ==0)? 'disabled':'' }" id="v-pills-settings-tab" data-toggle="pill" role="tab" aria-controls="v-pills-settings" aria-selected="false"> 
					<img src="<%=request.getContextPath()%>/sources/img/Community/png/image.png" /><b>相片</b></a> 
					
					<!-- Fifth	memberList ******************************* -->
					<c:if test="${comVO.mem_id == memVO.mem_id}">
					<a href="#five" class="nav-link  allborder" id="v-pills-settings-tab" data-toggle="pill" role="tab"aria-controls="v-pills-settings" aria-selected="false"> 
					<img src="<%=request.getContextPath()%>/sources/img/Community/png/pokemon-2.png" /><b>管理社團</b></a>
					</c:if>
				</div>
				<!-- /FunctionBar ******************************* -->
			 	
			 	
				<!-- ****************判斷是否為版主，是才能修改*************** -->
				<c:if test="${comVO.mem_id == memVO.mem_id}">
				<button type="button" class="btn btn-secondary " data-toggle="modal" data-target="#updateCom" style="width:126px;margin-top:2px">
				 <b>修改</b>
				</button>
				</c:if>
				
				<!-- ****************判斷是否已加入社群，不是才能選擇加入*************** -->
				<c:if test="${pms == null}">
				<div>
					<form action="<%=request.getContextPath()%>/com/JoinedComServlet.do" Method="POST">
						<input type="hidden" value="joinCom" name="action">
						<input type="hidden" value="${comVO.com_id}" name="com_id">
						<button type="submit" class="btn btn-primary introduction" style="width:126px margin-top:2px"><b>加入社團</b></button>
					</form>
				</div>
				</c:if>
				
				<!-- ****************判斷是否已加入社群，是才能選擇退出*************** -->
				<c:if test="${pms == 0 || pms == 1}">
				<div style="margin-top:2px">
					<form action="<%=request.getContextPath()%>/com/JoinedComServlet.do" Method="POST">
						<input type="hidden" value="exitCom" name="action">
						<input type="hidden" value="${comVO.com_id}" name="com_id">
						<button type="submit" class="btn btn-danger introduction" style="width:126px"><b>退出社團</b></button>
					</form>
				</div>
				</c:if>
				
				
				
				<!-- Button trigger modal -->
				
				<!-- Modal燈箱，修改社群 -->
				<FORM action="<%=request.getContextPath()%>/com/ComServlet.do"  id="updateForm"Method="Post" enctype="multipart/form-data" name="updateCom">
					<div class="modal fade" id="updateCom" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered" role="document">
							<div class="modal-content">
								<div class="modal-header" style="background-color:#DDDDDD">
									<h5 class="modal-title" id="exampleModalLongTitle">修改社群</h5>
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<p>社群名稱</p>
									<input type="text" id="update_com_name" name="update_com_name" size="15" value="${comVO.com_name}">
									
									<p style="margin-top:10px">社群封面圖片</p>
									<input type="file" id="update_cover_image" name="update_cover_image" onchange="cover_image_func(this)">
									<div id="update_cover_image_preview"></div>
									
									<p style="margin-top:10px">選擇隱私設定</p>
									<select class="form-control btn-light" style="background-color:#DDDDDD" name="update_privacy">
										<option value="1">不公開的社群</option>
										<option value="0">公開的社群</option>
									</select>
									
									<p style="margin-top:10px">社群公告</p>
									<textarea cols="50" rows="5" id="updateAn"placeholder="社群相關公告。" name="update_announcement">${comVO.announcement}</textarea>
									
									<p style="margin-top:10px">社群簡介</p>
									<textarea cols="50" rows="5" placeholder="告訴其他人這個社群的成立宗旨。" name="update_introduction">${comVO.introduction}</textarea>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
									<input type="hidden" name="action" value="updateCom">
									<input type="hidden" id="old_cover_image" name="old_cover_image" value="old_cover_image">
									<input type="hidden" name="cover_image" value="${comVO.cover_image}">
									<input type="hidden" name="com_id" value="${comVO.com_id}">
									<button type="submit" class="btn btn-primary" style="width:100px" value="修改社群" id="confirm_updateCom" onclick="confirm_updateCom(this)">修改</button>
								</div>
							</div>
						</div>
					</div>
				</FORM>
				<!-- /Modal -->
			</div>		
			<!-- /col-3 ******************************* -->
			
			
			<div class="col-9">
				<!-- coverImage ******************************* -->
				<div class="rounded-circle">
					<img id="cover_image"  class="cover rounded" alt="" style="height:360px; padding-left:30px; padding-right:30px"
						src="<%=request.getContextPath()%>/DBGetImage?com_id=${comVO.com_id}">
				</div>
				<!-- /coverImage ******************************* -->
				

				<div class="container">
					<div class="col-md-8" style="float:left;">	
						<div class="tab-content" id="v-pills-tabContent">
							<c:if test="${pms == 2 }">
							<div id="one" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-profile-tab">									
								<div class="card bg-light mb-3">
									<div class="card-header"><b>關於這個社團</b></div>
									<div class="card-body">
										<!-- 	社群簡介Introduction**************************************************************************************** -->
									    <h5 class="card-title">社群簡介</h5>
									    <p class="card-text" id="new">${comVO.introduction}</p>
									    <a data-toggle="collapse" id="textIn" href="#collapseIn" aria-expanded="false" aria-controls="collapseExample">
											<c:if test="${comVO.introduction==null}">
												<i class="fa fa-plus-square" aria-hidden="true">新增社群介紹</i>
											</c:if>	
											<c:if test="${comVO.introduction!=null}">
												<i class="fa fa-cog" aria-hidden="true">編輯社群介紹</i>
											</c:if>
									    </a>									
										<form action="<%=request.getContextPath()%>/com/ComServlet.do" Method="POST">
										    <div class=" collapse form-group" id="collapseIn">
												<textarea class="form-control" name="updateIntroduction" id="collapseIn" rows="3">${comVO.introduction}</textarea>
												<input type="hidden" name="com_id" value="${comVO.com_id}">
												<input type="hidden" name="action" value="updateIntroduction">
												<div style="margin-top:5px">
													<button type="submit" class="btn btn-primary introduction">確認</button>
													<button type="button" id="cancelIn" class="btn btn-secondary introduction" data-target="#collapseIn" aria-expanded="false" aria-controls="collapse">取消</button>     
										    	</div>
										    </div>								
										</form>	
										<div class="topborder">
										<!-- 	社群公告Announcement**************************************************************************************** -->
									    <h5 class="card-title">社群公告</h5>
									    <p class="card-text" id="newAn">${comVO.announcement}</p>
									    <a data-toggle="collapse" id="textAn" href="#collapseAn" aria-expanded="false" aria-controls="collapseExample">
											<c:if test="${comVO.announcement == null}">
												<i class="fa fa-plus-square" aria-hidden="true">新增社群公告</i>
											</c:if>	
											<c:if test="${comVO.announcement != null}">
												<i class="fa fa-cog" aria-hidden="true">編輯社群公告</i>
											</c:if>
									    </a>									
										<form action="<%=request.getContextPath()%>/com/ComServlet.do" Method="POST">
										    <div class=" collapse form-group" id="collapseAn">
												<textarea class="form-control" name="updateAnnouncement" id="collapseAn" rows="3">${comVO.announcement}</textarea>
												<input type="hidden" name="com_id" value="${comVO.com_id}">
												<input type="hidden" name="action" value="updateAnnouncement">
												<div style="margin-top:5px">
													<button type="submit" class="btn btn-primary introduction">確認</button>
													<button type="button" id="cancelAn" class="btn btn-secondary introduction" data-target="#collapseAn" aria-expanded="false" aria-controls="collapse">取消</button>     
										    	</div>
										    </div>								
										</form>	
										</div>
									</div>
								</div>
							</div>
							</c:if>
							<c:if test="${(pms != 2)}">
							<div id="one" class="tab-pane fade ${(pms == null or pms == 0)? 'show active':'' }" role="tabpanel" aria-labelledby="v-pills-profile-tab">									
								<div class="card bg-light mb-3">
									<div class="card-header"><b>關於這個社群</b></div>
									<div class="card-body">
										<h5 class="card-title">社群簡介</h5>
										<p class="card-text">${comVO.introduction}</p>
										<div class="topborder">
										<h5 class="card-title">社群公告</h5>
										<p class="card-text">${comVO.announcement}</p>
										</div>
									</div>
									
									
									
								</div>
							</div>
							</c:if>			
							<div id="two" class="tab-pane fade ${(pms == 1 or pms == 2)? 'show active':'' }" role="tabpanel" aria-labelledby="v-pills-profile-tab">
								<!-- CreateComPost ******************************* -->
								<form id="comPostForm" action="<%=request.getContextPath()%>/com/ComPoServlet.do" enctype="multipart/form-data" Method="POST">
								<input type="hidden" value="insertComPost" name="action">
								<div class="card gedf-card">
									<div class="card-header">
										<ul class="nav nav-tabs card-header-tabs" id="myTab" role="tablist">
											<img class="rounded-circle" width="45" height="45" style="margin-left:10px;margin-bottom:5px;margin-right:10px"
											src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${memVO.mem_id}" alt="" >
										<li class="nav-item"><a class="nav-link active" id="posts-tab" data-toggle="tab" href="#posts" role="tab" aria-controls="posts" aria-selected="true">
											撰寫貼文</a></li>
											<li class="nav-item"><a class="nav-link" id="images-tab" data-toggle="tab" role="tab" aria-controls="images" aria-selected="false" href="#images">
											Images</a></li>
										</ul>
									</div>
									<div class="card-body">
										<div class="tab-content" id="myTabContent">
											<div class="tab-pane fade show active" id="posts" role="tabpanel" aria-labelledby="posts-tab">
												<!-- 新增貼文 -->
												<div class="form-group">
													<label class="sr-only" for="message">post</label>
													<textarea class="form-control" id="message" rows="3" placeholder="在想些什麼?" name="cp_content"></textarea>
												</div>
											</div>
											<div class="tab-pane fade" id="images" role="tabpanel" aria-labelledby="images-tab">
												<!-- 新增上傳檔案 -->
												<div class="form-group">
												<div class=" text-center align-self-center" id="cuf"></div>
													<div class="custom-file">
														<input type="file" name="cuf" class="custom-file-input" id="uploadFile" onchange="cuf_func(this)" multiple> 
														<label class="custom-file-label"for="customFile">Upload image</label>
													</div>
												</div>
												<div class="py-4"></div>
											</div>
										</div>
										<div class="btn-toolbar justify-content-between">
											<div class="btn-group">
												<input type="hidden" name="com_id" value=${comVO.com_id}>
												<button id="inputText" type="button" class="btn btn-primary">發佈</button>
											</div>
										</div>
									</div>
								</div>
								</form>
								<!-- /CreateComPost ******************************* -->
								<jsp:useBean id="memSvc" class="com.mem.model.MemService"/>
								<!-- ComPost ******************************* -->
								<jsp:useBean id="comResSvc" class="com.comRes.model.ComResService"/>
								<c:if test="${ comPoList.size()>0 }">
								<c:forEach var="comPoVO" items="${comPoList}">
								<div class="card gedf-card margin-top-card">
									<div class="card-header">
										<div class="d-flex justify-content-between align-items-center">
											<div class="d-flex justify-content-between align-items-center">
												<div class="mr-2">
													<!-- 會員頭像******************************* -->
													<img class="rounded-circle" width="45" height="45" 
													src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${poMemVOList.get(comPo_idList.indexOf(comPoVO.comPo_id)).mem_id}" alt="" >
												</div>
												<div class="ml-2">
													<!-- 會員暱稱******************************* -->
													<div class="h5 m-0">${poMemVOList.get(comPo_idList.indexOf(comPoVO.comPo_id)).mem_nickname}</div>
													<div class="text-muted" style="font-size:14px">發文時間${ TimeTool.timeTool(comPoVO.cp_time)}</div>
												</div>
											</div>
											<div>
												<div class="dropdown">
													<button class="btn btn-link dropdown-toggle" type="button" id="gedf-drop1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
														<i class="fa fa-ellipsis-h"></i>
													</button>
													<div class="dropdown-menu dropdown-menu-right" aria-labelledby="gedf-drop1">
														<div class="h6 dropdown-header">Configuration</div>
														<a class="dropdown-item" href="#">Save</a> 
														<a class="dropdown-item" href="#">Hide</a> 
														<a class="dropdown-item" href="#">Report</a>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="card-body">								
										<p class="card-text">
											${comPoVO.cp_content}
										</p>
										<c:if test="${ comPoVO.cp_hf == 1 }">
											<div class=" text-center align-self-center" id="post_cuf" >
												<!-- 	貼文檔案******************************* -->	
												<c:forEach var="cuf_id" items="${ cuf_idsList.get(comPo_idList.indexOf(comPoVO.comPo_id))}">
													<img style="height:180px;vertical-align: middle;width:100%" 
													src="<%=request.getContextPath()%>/com/ComServlet.do?action=getFile&cuf_id=${cuf_id}">
												</c:forEach>
											</div>
										</c:if>
									</div>
									<div class="card-footer ">
										<i class="fas fa-bicycle"></i>
									</div>
								</div>
								<!-- /ComPost ******************************* -->
								<div class="card">
									<!-- ComResponse ******************************* -->
									<c:if test="${ comResSvc.getComResVOs(comPoVO.comPo_id).size()>0 }">
									<c:forEach var="comResVO" items="${comResSvc.getComResVOs(comPoVO.comPo_id)}">
	   									<!-- 社群回文文字內容 -->
	   									<div class="container">
	   										<div class="row">
	   											<div class="col-1" style="margin-right:3px;margin-top:8px;">
			   										<img class="rounded-circle" width="40px" height="40px" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${comResVO.mem_id}" alt="" >
			   									</div> 
			   									<div class="col" style="margin-top:15px">
			   					 					<span style="margin-left:5px;background-color:#DAE3DB;border-radius:15px;padding:5px">
				   										<b >${memSvc.findMemById(comResVO.mem_id).mem_nickname}</b>
		   												${comResVO.cr_content} 
	   												</span>
	   											</div>	
	   										</div>
   										</div>
									<c:if test="${ comResVO.cr_hf == 1 }">
										<!-- 社群回文圖片 -->
										<div class="rounded" id="post_cuf" style="padding:5px ;">
											<img style="height:180px;vertical-align: middle;width:auto;max-width:470px;border-radius:10px" 
											src="<%=request.getContextPath()%>/com/ComServlet.do?action=getResFile&comRes_id=${comResVO.comRes_id}">
										</div>
									</c:if>
									</c:forEach>
									</c:if>
									<!-- /ComResponse ******************************* -->
									<!-- CreateComResponse ******************************* -->
									<form action="<%=request.getContextPath()%>/com/ComResServlet.do" enctype="multipart/form-data" Method="POST">
										<div class="input-group">
											<!-- 文字輸入 -->
											<img class="rounded-circle" width="40px" height="40px" style="margin:3px" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${memVO.mem_id}" alt="" >
									  		<input type="text" class="form-control" name="cr_content"  placeholder="留言......" 
									  		style="background-color:#DDDDDD;border-radius:20px;margin:3px;"
									  		aria-label="Recipient's username" aria-describedby="basic-addon2">
										 	<!-- 圖片輸入&提交 -->
										 	<div class="input-group-append">
										 		<input type="file" id="comResFile" name="cr_file" style="display:none;" onchange="resfile_func(this)"/>
											    <button type="button" class="btn btn-outline-secondary" onclick="document.querySelector('#comResFile').click()">upfile</button>
											    <input type="hidden" value="insertComRes" name="action">
											    <input type="hidden" value="${comPoVO.com_id}" name="com_id">
											    <input type="hidden" value="${comPoVO.comPo_id}" name="comPo_id">
											    <button type="submit" class="btn btn-outline-secondary">回復</button>
										 	</div>
										</div>
									    <div class="rounded" id="resPreview" style="border-radius:10px;"></div>
									</form>	
									<!-- CreateComResponse ******************************* -->
								</div>
								</c:forEach>
								</c:if>
							</div>
							<div id="three" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-settings-tab">
								<div class="card">
									<div class="card-header">
										<b>成員</b>	${comVO.mem_count}
									</div>
								</div>
								<c:if test="${ comMemberList.size()>0 }">
								<c:forEach var="memVO" items="${comMemberList}">
								<div class="card">
									<div class="card-body">
										<img class="rounded-circle" width="45px" height="45px" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${memVO.mem_id}" alt="" >
										${ memVO.mem_nickname }
										<c:if test="${comVO.mem_id == memVO.mem_id}">
											<span style="font-weight:bold; color:#0044BB">版主</span>
										</c:if>
									</div>
								</div>
								</c:forEach>
								</c:if>
							</div>
							<div id="four" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-settings-tab">
								<div class="card">
									<div class="card-body">
								    	<c:forEach var="cuf_id" items="${allFileList}">
								    		<img style="height:180px;vertical-align: middle;width:49%" 
												src="<%=request.getContextPath()%>/com/ComServlet.do?action=getFile&cuf_id=${cuf_id}">
								    	</c:forEach>
									</div>
								</div>
							</div>
							<div id="five" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-settings-tab">
								<table class="table table-striped table-hover">
									<thead>
										<tr>
										<th scope="col">#</th>
										<th scope="col">會員暱稱</th>
										<th scope="col"></th>
										<th scope="col">社團加入申請</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="joinedComVO" items="${waitForCheckList}">
										<tr>
											<th scope="row">${waitForCheckList.indexOf(joinedComVO) + 1}</th>
											<td colspan="2">
											<img class="rounded-circle" width="45px" height="45px" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${joinedComVO.mem_id}" alt="" >
											${memSvc.findMemById(joinedComVO.mem_id).mem_nickname}
											</td> 
											<td>
												<form action="<%=request.getContextPath()%>/com/JoinedComServlet.do" Method="POST">
													<input type="hidden" name="action" value="joinCom_OK">
													<input type="hidden" name="com_id" value="${comVO.com_id}">
													<input type="hidden" name="mem_id" value="${memSvc.findMemById(joinedComVO.mem_id).mem_id}">
													<button type="submit" class="btn btn-primary introduction" >等待批准</button>
												</form>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- /col-md-8 ******************************* -->
							
					<!-- col-md-4 sidecolumn******************************* -->		
					<div class="col-md-4" style="float:right;">
						<div class="card gedf-card margin-bottom">
							<div class="card-body">
								<h6 class="card-subtitle mb-2 text-muted"><b>紀錄</b></h6>
								<i class="fa fa-flag" aria-hidden="true"></i>
								社群建立於${(comVO.create_time!=null)?comVO.create_time:CreateTime}
							</div>
						</div>
						<!-- FirstCard -->
						<div class="card gedf-card margin-bottom">
							<div class="card-body">
								<h6 class="card-subtitle mb-2 text-muted"><b>簡介</b></h6>
								<div>
									<p class="card-text">${(comVO.introduction!=null)?comVO.introduction:"告訴其他人這個社團的成立宗旨。"}</p>
								</div>
								<div class="topborder">
									<h6 class="card-subtitle mb-2 text-muted"><b>社群版主</b></h6>
									<img class="rounded-circle" width="45px" height="45px" src="<%=request.getContextPath()%>/Mem_photoReader?mem_id=${comVO.mem_id}" alt="" >
									${memSvc.findMemById(comVO.mem_id).mem_nickname}
								</div>
							</div>
						</div>
						<!-- /FirstCard -->

						<!-- SecondCard -->
						<div class="card gedf-card">
							<div class="card-body">
								<h5 class="card-title"><b>動態牆</b></h5> 
								<h6 class="card-subtitle mb-2 text-muted"><b>社團的最新相片</b></h6>
								<p class="card-text"></p>
								<c:forEach var="sideFile" items="${siedeFileList}">
									<img style="height:auto;vertical-align: middle;width:100%; margin-top:5px; border-radius:10px" 
									src="<%=request.getContextPath()%>/com/ComServlet.do?action=getFile&cuf_id=${sideFile}">
								</c:forEach>
								<a href="#" class="card-link">Another link</a> 
							</div>
						</div>
						<!-- /SecondCard -->
						<div style="text-align:center">CA103G2 © 2018</div> 
					</div>
					<!-- /col-4 -->
				</div>			
			</div>
			<!-- /col-9 ******************************* -->
		</div>
	</div>




<!-- ************************************************************************************************************************************************************* -->








<script type="text/javascript">


$('#clickableLiElement').click(function() {
    $('#theFileInput').click();
});

//使顯現出來的回應換行(貼文)
$(function(){
		$('#inputText').click(function(){
			var temp = $('#message').val().replace(/\n/g,'<br/>');
			$('#message').val(temp);
			$('#comPostForm').submit();
		});
		
});

//使顯現出來的回應換行公告)
$(function(){
		$('#confirm_updateCom').click(function(){
			var temp = $('#updateAn').val().replace(/\n/g,'<br/>');
			$('#updateAn').val(temp);
			$('#updateForm').submit();
		});
		
});

//修改社群跳窗事件
function confirm_updateCom(obj){
		if(document.getElementById("update_com_name").value == null){
			swal({
                title: "您尚未輸入社群名稱 QAQ",
                html: "修改的名稱不能空白!",
                type: "info",
                confirmButtonText:"確定"/*改這裡*/
			});
		}else{
			document.updateCom.submit();
		}
	}
	
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
			document.getElementById("cover_image").innerHTML = "<img id='update_cover_image' style='width:470px;margin-top:0px' src='" + e.target.result + "' style='height:160px'>";
			document.getElementById("old_cover_image").value = "update_cover_image";
			
		}
	}
}	

	
//	update_cover_image_preview圖片預覽
function cover_image_func () {
	
	var files = document.getElementById("update_cover_image").files;
	var preview=document.getElementById("update_cover_image_preview");  
	preview.innerHTML = "";
	for (i = 0; i< files.length; i ++) {
		var reader = new FileReader();
		reader.readAsDataURL(files[i]);
		reader.onloadend = function (e){
			preview.innerHTML = preview.innerHTML + "<img src='" + this.result + "' style='height:160px'>";
		}
	}
		document.getElementById("old_cover_image").value = "update_cover_image";
	
}

//comPost圖片預覽
function cuf_func () {
	
	var files = document.getElementById("uploadFile").files;
	var cuf=document.getElementById("cuf");  
	 cuf.innerHTML = "";
	for (i = 0; i< files.length; i ++) {
		var reader = new FileReader();
		reader.readAsDataURL(files[i]);
		reader.onloadend = function (e){
			cuf.innerHTML = cuf.innerHTML + "<img src='" + this.result + "' style='height:180px;width:100% '>";
		}
	}
	
}

//comRes圖片預覽
function resfile_func () {
	
	var files = document.getElementById("comResFile").files;
	var resPreview=document.getElementById("resPreview");  
	resPreview.innerHTML = "";
	for (i = 0; i< files.length; i ++) {
		var reader = new FileReader();
		reader.readAsDataURL(files[i]);
		reader.onloadend = function (e){
			resPreview.innerHTML = resPreview.innerHTML + "<img src='" + this.result + "' style='height:160px;border-radius:10px;margin:3px';>";
		}
	}
	
}


//	社群簡介model切換
//   $(document).ready(function() {
//     $("#textIn").click(function() {
//       $(this).hide();
//     });

//     $("#cancelIn").click(function() {
//       $("#textIn").show();
//       $("#collapseIn").hide();
//     });

//     $("#newIn").click(function() {
//       $("#collapseIn").show();
//     });
//   });
  

//	按讚icon切換
  var id = 1; 
  function next(){ 
	  id = (id + 1) % 3;  
	  if(id==0){id=1};
    	document.getElementById("image").src = '<%=request.getContextPath()%>/sources/img/Community/png/like' + id + '.png'; 
    	console.log(id);}
  
  
</script>




</body>
</html>