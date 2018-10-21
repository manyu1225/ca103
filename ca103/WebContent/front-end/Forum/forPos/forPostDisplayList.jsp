<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.forPos.model.*"%>
<%@ page import="hashTag_Util.*"%>
<%@ page import=" redis.clients.jedis.*"%>
<%@ page import=" com.forPos_pic.model.*"%>


<!-- 此處為貼文的總覽頁面 -->





<%
	Forum_post_Service forPosSvc = new Forum_post_Service();
	List<Forum_post_VO> list = forPosSvc.getAllByState(1); //狀態1才可以顯示在瀏覽列表
	// 	List<Forum_post_VO> list = forPosSvc.getAllForPos(); 
	pageContext.setAttribute("list", list);
%>


<%
//活動列表圖片
ForPost_picture_VO forpost_pic_service = new ForPost_picture_VO();




%>




<!-- 取得全部標籤 -->

<%

//取得Service傳來了的標籤名
HashTag_Service hashTagSvc = new HashTag_Service();

 Set<String> listAllTag = hashTagSvc.getAllPostTag(); //為了要求不重複 故用Set

 request.setAttribute("listAllTag", listAllTag);
 
 
 
 //取標籤數量
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

%>





<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
<link rel="stylesheet" type="text/css" href="../css/css.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
	integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ"
	crossorigin="anonymous">
<style>

/*
Ref:
Thanks to:
https://www.jqueryscript.net/layout/Simple-jQuery-Plugin-To-Create-Pinterest-Style-Grid-Layout-Pinterest-Grid.html
*/
body {
	background-color: #eee;
}

#pinBoot {
	position: relative;
	max-width: 100%;
	width: 100%;
}

#thumbnail {
	width: 100%;
	max-width: 100%;
	height: auto;
}

.white-panel {
	position: absolute;
	background: white;
	box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.3);
	padding: 10px;
}
/*
stylize any heading tags withing white-panel below
*/
.white-panel h1 {
	font-size: 1em;
}

.white-panel h1 a {
	color: #A92733;
}

.white-panel:hover {
	box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.5);
	margin-top: -5px;
	-webkit-transition: all 0.3s ease-in-out;
	-moz-transition: all 0.3s ease-in-out;
	-o-transition: all 0.3s ease-in-out;
	transition: all 0.3s ease-in-out;
}

#headline {
	font-family: 微軟正黑體;
}

#forPost_theme {
	font-family: 微軟正黑體;
	font-size: 15px;
}




/* body{ */
/* 			font-family: Comic Sans MS,arial,helvetica,sans-serif; */
/* 		    background-position: center; */
/* 		    background-size: cover; */
/* 		    background-repeat: no-repeat; */
/* 	   		background-attachment: fixed; */
<%-- 		    background-image: url(<%=request.getContextPath()%>/sources/img/Forum/dawn-landscape-nature.jpg); --%>
		    
/* 	    } */
</style>



<title>Insert title here</title>
</head>
<body style="background-color:#e6e6e6">
	<%@ include file="/sources/file/Home/NavBar.file"%>

	<div class="container-fluid">
		<div class="row">


			<!-- sidebar -->
			<div class="col-md-3 col-12 mt-5">



				<%@ include file="/sources/file/Forum/forPosSidebar.file"%>

			<!-- 顯示全部標籤選項 -->
			
			<hr>
				
				<h3 class="mt-5" > <img src="<%=request.getContextPath()%>/sources/icon/Forum/price-tag.png"/>熱門標籤</h3>
				<hr>
			<div class="row ml-1">
				<c:forEach var="forTag" items="${listAllTag}">
				<div class="mr-1">
				<%Long tagNum = hashTagSvc.getPostTagNum((String)pageContext.getAttribute("forTag"));%>
				<%request.setAttribute("tagNum", tagNum); %>
				
				
				<form action="<%=request.getContextPath()%>/hashTag/hashTag.do" method="post">	
					<button type="submit" class="btn btn-sm btn-secondary mt-2" name="action" value="get_One_Tag">
				  				${forTag} <span class="badge badge-light">${tagNum}</span>
					</button>
<%-- 					<input type="hidden" name="forPost_ID" value="${forPosVO.forPost_ID}"> --%>
<%-- 					<input type="hidden" name="forPostVO" value="${forPosVO}"> --%>
					<input type="hidden" name="tag" value="${forTag}">
					</form>
				</div>
				</c:forEach>
			</div>

			</div>




			<div class="col-md-9 col-12 mt-5">

				<hr>
				<%@ include file="page1.file"%>



				<section id="pinBoot">

					<c:forEach var="forPosVO" items="${list}" begin="<%=pageIndex%>"
						end="<%=pageIndex+rowsPerPage-1%>">
						<article class="white-panel">
<%-- 							<c:forEach var="forPicVO" items="${picList}" > --%>
								<img id="thumbnail" src="<%=request.getContextPath()%>/forPos/forPos.do?action=getOnePic&forPost_ID=${forPosVO.forPost_ID}" style="width: 350px; height: 200px" alt="">
<%-- 							</c:forEach>	 --%>
							<h4>
								<a id="forPost_theme"
									href="<%=request.getContextPath()%>/forPos/forPos.do?forPost_ID=${forPosVO.forPost_ID}&mem_ID=${forPosVO.mem_ID}&action=getOnePos_onPage&forPost_state=${forPosVO.forPost_state}&forPost_view=${forPosVO.forPost_view}&actionTag=getHashTag">
									${forPosVO.forPost_theme}</a>
							</h4>
							


							<p>
								<i class="far fa-eye"></i>${forPosVO.forPost_view}</p>
						</article>
					</c:forEach>

				</section>

				<hr>

			</div>

		</div>

	</div>

	<!-- 分頁 -->
	<div class="row">
		<div class="mx-auto">
			<%@ include file="page2.file"%>
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


	<script
		src="<%=request.getContextPath()%>/sources/js/Forum/forPostDisplay.js"></script>




	<!-- 限制顯示字數 -->
	<!-- 
<script>
$(function(){
    var len = 50; // 超過50個字以"..."取代
    $(".JQellipsis").each(function(i){
        if($(this).text().length>len){
            $(this).attr("title",$(this).text());
            var text=$(this).text().substring(0,len-1)+"...";
            $(this).text(text);
        }
    });
});
</script>

 -->

</body>
</html>