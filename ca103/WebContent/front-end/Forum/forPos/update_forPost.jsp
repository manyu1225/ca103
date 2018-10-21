<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.forPos.model.*"%>

					<!--///////////////// 此處為文章update的頁面////////////// -->


<%
	Forum_post_VO forPosVO = (Forum_post_VO) request.getAttribute("forPosVO");
// 		System.out.println(forum_post_VO.getForClass_ID());
 		System.out.println(forPosVO==null);
 		System.out.println(forPosVO);
//  		request.setAttribute("forum_post_VO", forum_post_VO); //舊專案解法
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF_8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
	integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- <link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.css" rel="stylesheet"> -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.css"
	rel="stylesheet">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote-bs4.js"></script>
<!-- css from local path -->
<link rel="stylesheet" type="text/css" href="../css/css.css">




<style type="text/css">
textarea.form-control {
	width: 100%;
	height: 500px;
}

.header {
	font-family: 微軟正黑體;
}
</style>


<title>討論區貼文更新</title>
</head>




<body>
<!-- navbar開始 -->


	<%@ include file="/sources/file/Home/NavBar.file" %>


<!-- navbar結束 -->

				<!-- 測試文章編號否進入編輯模式 -->
				<div class="container">
						<div class="row">
							<div class="col-10 mx-auto mt-5">
								<h1 class="header">文章編輯區</h1>
<%-- 								${forPosVO.forPost_ID} 測試用	 --%>
								
										
								<%--錯誤列表 --%>
								<c:if test="${not empty errorMsgs}">
									<font style="color: red">請修正以下錯誤</font>
									<ul>
										<c:forEach var="message" items="${errorMsgs}">
											<li style="color: red">${message}</li>
										</c:forEach>
									</ul>
								</c:if>
							</div>
						</div>
					</div>
					
							
							
		<div class="container">
			<div class="row">
				<div class="col-10 mx-auto ">
					<form method="post" action="forPos.do" name="form1">
									<div class="form-group">
										<label for="">標題</label> 
										<input class="form-control" type="text" name="theme" size="50" value="<%=forPosVO.getForPost_theme()%>" />
									</div>
									
									<!--選擇文章類別 -->
							<label>請選擇全站類別</label>
							<jsp:useBean id="forClassSvc" scope="page"
								class="com.forPos_class.model.Forum_class_Service" />
								<select size="1" name="class_ID">
									<c:forEach var="forum_class_VO"
										items="${forClassSvc.all_ForClass}">
										<option value="${forum_class_VO.forClass_ID}"
											${(forPosVO.forClass_ID == forum_class_VO.forClass_ID)? 'selected':'' }>${forum_class_VO.forClass_name}
									</c:forEach>
								</select>

						<br>

					<!-- 選擇文章狀態 -->
					<label>請選擇文章狀態</label>
					<select size="1" name="state">
						<%System.out.println("測試文章狀態: "+forPosVO.getForPost_state()); %>
					
				
						<option value="${forPosVO.forPost_state}" ${(forPosVO.forPost_state == 0)? 'selected':''}>請選擇  </option>
						<option value="${forPosVO.forPost_state}" ${(forPosVO.forPost_state == 1)? 'selected':''}>公開</option>
						<option value="${forPosVO.forPost_state}" ${(forPosVO.forPost_state == 2)? 'selected':''}>不公開</option>
						<option value="${forPosVO.forPost_state}" ${(forPosVO.forPost_state == 3)? 'selected':''}>草稿</option>	
					</select>

					<br>
					<br>
					

					
						<div class="row">
							<div class="form-group">
								<label for="user-message" class=" control-label">文章內容 </label>
									<div class="col-lg-12 mx-auto">
											<textarea id="summernote" name="content" placeholder="寫點什麼吧"><%=(forPosVO == null) ? "" : forPosVO.getForPost_content()%></textarea>
														
				<%-- 						<textarea class="form-control border-info" name="content"><%=(forPosVO == null) ? "" : forPosVO.getForPost_content()%> --%>
				<!-- 						</textarea> -->
					<br>

					<!-- 				貼文張貼時間					 -->
					<input type="hidden" name="time"> 
					
						<input type="hidden" name="action" value="update">
						<input type="hidden" name="forPost_ID" value="<%=forPosVO.getForPost_ID()%>">
						<input type="submit" class="btn btn-primary"name="submit" value="送出修改"> 
						<input type="hidden" class="btn btn-primary"name="mem_ID" value="<%=forPosVO.getMem_ID()%>"> 
						<input type="hidden" class="btn btn-primary"name="time" value="<%=forPosVO.getForPost_time()%>">
						<input type="hidden" class="btn btn-primary"name="view" value="<%=forPosVO.getForPost_view()%>"> 
						 
						 <%
						 java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                         String formatDate = df.format(new java.util.Date().getTime());
						 %>
						 
<!-- 						 本次修改時間 -->
						 
						最後修改時間<%=formatDate%>
						</div>
							</div>
						</div>
				</form>
			</div>
		</div>
	</div>
	

	







	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
		integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
		crossorigin="anonymous"></script>
		
	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.js"></script>
		
		<script>
		$('#summernote').summernote({
			placeholder : 'Hello ! 寫點東西吧',
			tabsize : 3,
			height : 500,

		});
		
		</script>



</body>
</html>