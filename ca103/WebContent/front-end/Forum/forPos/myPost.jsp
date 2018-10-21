<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.forPos.model.*"%>
<%@ page import="com.forPos_fav.model.*"%>
<%@ page import="com.forPos_res.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.forPos_rat.model.*"%>
<%@ page import="hashTag_Util.*"%>
<%@ page import=" redis.clients.jedis.*"%>





<jsp:useBean id="forClass_Svc" scope="page"
    class="com.forPos_class.model.Forum_class_Service" />


<jsp:useBean id="forFavSvc" scope="page"
    class="com.forPos_fav.model.Forum_post_fav_Service" />



					<!--///////////////// 此處為文章的單一頁面////////////// -->


<!-- ************************************************************************ -->
	
<%
//真實會員的創建!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	MemVO memVO = (MemVO)session.getAttribute("memVO");

// System.out.println("----Mem_id***-----" +memVO.getMem_id() );

%>
	


<%
//抓回返還的ajax值

     Integer forPost_ID = new Integer(request.getParameter("forPost_ID"));
    // 假會員的創建
//     HttpSession sessionCli = request.getSession();
//     sessionCli.setAttribute("cli", "M000079");
//     String cli = (String) sessionCli.getAttribute("cli");
//     System.out.println("forPost_ID isssssss:"+ forPost_ID );
	Forum_post_fav_VO fav_VO = forFavSvc.getOnePosFav(forPost_ID, memVO.getMem_id());

	
	
	

	
	
	
	

    //模擬會員收藏
//      Forum_post_fav_VO fav_VO = forFavSvc.getOnePosFav(forPosVO.getForPost_ID(), memVO.getMem_id());
    
    //真實會員
//     HttpSession session2 = request.getSession();
//     MemVO memVO = new MemVO();
//     MemService memSvc = new MemService();
//     memVO = memSvc.findMemById("M000015");
    
//     String memid = memVO.getMem_id();
//     session2.setAttribute("memVO", memVO);
//      memVO = (MemVO)request.getAttribute("memVO");

//     System.out.println("memid isssssss:"+ memVO.getMem_id() );

//     String mem_id = (String)request.getAttribute("memVO");
    
//     Forum_post_fav_VO fav_VO = (Forum_post_fav_VO) request.getAttribute("fav_VO");
    
//     System.out.println("fav_VO-------" + fav_VO);
    
    
//     if(fav_VO != null){
//     	 Forum_post_fav_Service favSvc = new Forum_post_fav_Service();
//     	 fav_VO =  favSvc.getOnePosFav(forPost_ID, cli);
    	
    	
//     }
   
    
    
%>


<!-- ************************************************************************ -->



<%
//     Forum_response_Service forResSvc = new Forum_response_Service();
//     List<Forum_response_VO> list = forResSvc.getAllForRes();

//     pageContext.setAttribute("list", list);
%>

<!-- ************************************************************************ -->



<%

 Forum_post_VO forPosVO = (Forum_post_VO)request.getAttribute("forPosVO");
System.out.println("forPosVO0000000:" + forPosVO);
//         Forum_response_VO forResVO = request. 
//  System.out.println("forPosVO.getForPost_ID()----:" + forPosVO.getForPost_ID());


    Forum_response_Service  forResSvc = new Forum_response_Service();
    
//     System.out.println("forResSvc----:" + forResSvc);
//     System.out.println("forPosVO----:" + forPosVO);

//     該會員的貼文的留言列
    List<Forum_response_VO> list = forResSvc.getResByPost_ID(forPosVO.getForPost_ID());
    System.out.println("list ----:" + list );
    System.out.println("forPosVO.getForPost_ID() peter----:" + forPosVO.getForPost_ID());
    pageContext.setAttribute("list",list);
%>


				<!-- 標籤redis -->

<%

//取得Service傳來了的標籤名
HashTag_Service hashTagSvc = new HashTag_Service();

 List<String> tagDisplay = hashTagSvc.getPostTag(forPost_ID);

 request.setAttribute("tagDisplay", tagDisplay);
 
 
 
 //取標籤數量
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		
		//取得標籤名稱(key)：貼文名稱 物件(為求擁有該標籤貼文的數量)

%>





<!-- 取得會員物件 -->
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService"/>







<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
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
<link rel="stylesheet" type="text/css" href="/source/css/css.css">

								<!-- hashtag 分類標籤 -->
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sources/css/Forum/tagInputText.css"> --%>



<style type="text/css">
h1 {
    font-family: 微軟正黑體;
}

h2 {
    font-family: 微軟正黑體;
}

h3 {
    font-family: 微軟正黑體;
}

h5 {
    font-family: 微軟正黑體;
}

h4 {
    font-family: 微軟正黑體;
}


/* 留言板卡片大小 */
#replyboard{


height:5.5rem;


}




#user-img {
    border-radius: 50%;
}


/* 留言板樣式 */
/* .btn-lg, .btn-group-lg>.btn { */
/*     padding: 0.5rem 1rem; */
/*     font-size: 2rem; */
/*     line-height: 1.5; */
/*     border-radius: none; */
/*     border-style: none; */
/*     background-color: transparent; */
/* } */


.btn-sm-1{
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
  line-height: 1.5;
  border-radius: none;
  border-style: none;
  background-color: transparent;
  
}


button:focus {
    outline: 0;
}

#favorite {
    border-radius: 50%;
    background-color: #ff4d4d;
    border-color: transparent;
}

.form-control-md {
    height: calc(2.875rem + 2px);
    padding: 0.5rem 1rem;
    font-size: 1.25rem;
    line-height: 1.5;
    border-radius: 0.8rem;
    border-color: #999999;
}

#resBg{
background-color:#cccccc;

}

/* 限制img大小 */


img {
    display: block;
    margin-left: auto;
    margin-right: auto;
    width: 60%;
}


/* 標籤text input */


.btn-tag{

 background-color: transparent;
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
  line-height: 1.5;
  border-radius: none;
  border-style: none;
}




		/**
 * Oscuro: #283035
 * Azul: #03658c
 * Detalle: #c7cacb
 * Fondo: #dee1e3
 ----------------------------------*/
 * {
 	margin: 0;
 	padding: 0;
 	-webkit-box-sizing: border-box;
 	-moz-box-sizing: border-box;
 	box-sizing: border-box;
 }

 a {
 	color: #03658c;
 	text-decoration: none;
 }

ul {
	list-style-type: none;
}

body {
	font-family: 'Roboto', Arial, Helvetica, Sans-serif, Verdana;
	background: #dee1e3;
}

/** ====================
 * Lista de Comentarios
 =======================*/
.comments-container {
	margin: 60px auto 15px;
	width: 768px;
}

.comments-container h1 {
	font-size: 36px;
	color: #283035;
	font-weight: 400;
}

.comments-container h1 a {
	font-size: 18px;
	font-weight: 700;
}

.comments-list {
	margin-top: 30px;
	position: relative;
}

/**
 * Lineas / Detalles
 -----------------------*/
/* .comments-list:before { */
/* 	content: ''; */
/* 	width: 2px; */
/* 	height: 100%; */
/* 	background: #c7cacb; */
/* 	position: absolute; */
/* 	left: 32px; */
/* 	top: 0; */
/* } */

.comments-list:after {
	content: '';
	position: absolute;
	background: #c7cacb;
	bottom: 0;
	left: 27px;
	width: 7px;
	height: 7px;
	border: 3px solid #dee1e3;
	-webkit-border-radius: 50%;
	-moz-border-radius: 50%;
	border-radius: 50%;
}

.reply-list:before, .reply-list:after {display: none;}
.reply-list li:before {
	content: '';
	width: 60px;
	height: 5px;
	background: #c7cacb;
	position: absolute;
	top: 25px;
	left: -55px;
}


.comments-list li {
	margin-bottom: 0px;
	display: block;
	position: relative;
}

.comments-list li:after {
	content: '';
	display: block;
	clear: both;
	height: 0;
	width: 0;
}

.reply-list {
	padding-left: 88px;
	clear: both;
	margin-top: 15px;
}
/**
 * Avatar
 ---------------------------*/
.comments-list .comment-avatar {
	width: 65px;
	height: 65px;
	position: relative;
	z-index: 99;
	float: left;
	border: 3px solid #FFF;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	-webkit-box-shadow: 0 1px 2px rgba(0,0,0,0.2);
	-moz-box-shadow: 0 1px 2px rgba(0,0,0,0.2);
	box-shadow: 0 1px 2px rgba(0,0,0,0.2);
	overflow: hidden;
}

.comments-list .comment-avatar img {
	width: 100%;
	height: 100%;
}

.reply-list .comment-avatar {
	width: 50px;
	height: 50px;
}

.comment-main-level:after {
	content: '';
	width: 0;
	height: 0;
	display: block;
	clear: both;
}
/**
 * Caja del Comentario
 ---------------------------*/
.comments-list .comment-box {
	width: 680px;
	float: right;
	position: relative;
	-webkit-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
	-moz-box-shadow: 0 1px 1px rgba(0,0,0,0.15);
	box-shadow: 0 1px 1px rgba(0,0,0,0.15);
}

.comments-list .comment-box:before, .comments-list .comment-box:after {
	content: '';
	height: 0;
	width: 0;
	position: absolute;
	display: block;
	border-width: 10px 12px 10px 0;
	border-style: solid;
	border-color: transparent #FCFCFC;
	top: 8px;
	left: -11px;
}

.comments-list .comment-box:before {
	border-width: 11px 13px 11px 0;
	border-color: transparent rgba(0,0,0,0.05);
	left: -12px;
}

.reply-list .comment-box {
	width: 610px;
}
.comment-box .comment-head {
	background: #FCFCFC;
	padding: 10px 12px;
	border-bottom: 1px solid #E5E5E5;
	overflow: hidden;
	-webkit-border-radius: 4px 4px 0 0;
	-moz-border-radius: 4px 4px 0 0;
	border-radius: 4px 4px 0 0;
}

.comment-box .comment-head i {
	float: right;
	margin-left: 14px;
	position: relative;
	top: 2px;
	color: #A6A6A6;
	cursor: pointer;
	-webkit-transition: color 0.3s ease;
	-o-transition: color 0.3s ease;
	transition: color 0.3s ease;
}

.comment-box .comment-head i:hover {
	color: #03658c;
}

.comment-box .comment-name {
	color: #283035;
	font-size: 14px;
	font-weight: 700;
	float: left;
	margin-right: 10px;
}

.comment-box .comment-name a {
	color: #283035;
}

.comment-box .comment-head span {
	float: left;
	color: #999;
	font-size: 13px;
	position: relative;
	top: 1px;
}

.comment-box .comment-content {
	background: #FFF;
	padding: 12px;
	font-size: 15px;
	color: #595959;
	-webkit-border-radius: 0 0 4px 4px;
	-moz-border-radius: 0 0 4px 4px;
	border-radius: 0 0 4px 4px;
}

.comment-box .comment-name.by-author, .comment-box .comment-name.by-author a {color: #03658c;}
.comment-box .comment-name.by-author:after {
	content: 'autor';
	background: #03658c;
	color: #FFF;
	font-size: 12px;
	padding: 3px 5px;
	font-weight: 700;
	margin-left: 10px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
}

/** =====================
 * Responsive
 ========================*/
@media only screen and (max-width: 766px) {
	.comments-container {
		width: 480px;
	}

	.comments-list .comment-box {
		width: 390px;
	}

	.reply-list .comment-box {
		width: 320px;
	}
}


#tag-input{

  border: 0;
  outline: 0;
  background: transparent;
  border-bottom: 1px solid black;
  border-style: dashed;

}



</style>


<title>${forPosVO.forPost_theme}</title>
</head>
<body style="background-color: #e6e6e6">


	<%@ include file="/sources/file/Home/NavBar.file" %>

    <%
        System.out.println("文章張貼頁面接收成功");
    %>
    
    

    <a id="topest"></a>






    <div class="container">
        <div class="row">
        
        

        

            <!-- 文章標題+內文 -->
            <div class="col-md-9 col-12 mt-5  ">
            
            
            
            <!--*****************新增分類標籤******************* -->
            
            

            
            
            
            
            
            
            
            
                <form action="<%=request.getContextPath()%>/ForPos_Report/ForPos_ReportServlet.do" method="post">
            
                <div class="card shadow-sm rounded-0 mt-4 ">
                    <div class="card-body">
                        <h2 class="postTheme">${forPosVO.forPost_theme}</h2>

<%--                         ----mem_ID ${forPosVO.mem_ID} --%>
                        
                        <div id="favorite" class="btn btn-primary btn-sm btn-circle"
                            id="favBtn" data-toggle="tooltip" data-placement="top"
                            title="加到我的收藏">
                            <i id="icon" class="far fa-heart"></i>
                        </div>


                        <small class="forPost-detail"><fmt:formatDate value="${forPosVO.forPost_time}" pattern="yyyy-MM-dd HH:mm:ss"/> |
                            作者: <a href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=goToMemberView&mem_id=${memVO.mem_id}">${memSvc.findMemById(forPosVO.mem_ID).mem_nickname}</a> | 分類:${forClass_Svc.getOneClass(forPosVO.forClass_ID).forClass_name}
                            |  瀏覽人數:${forPosVO.forPost_view} | </small>
<!--                         <button type="submit" id="reportClick"><i class="fas fa-exclamation-triangle">檢舉</i></button> -->
<!--                                 貼文檢舉 -->
                        <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#Report" data-whatever="@fat">
                        <i class="fas fa-exclamation-triangle">檢舉</i></button>
                        
                        


                        <div class="forPost_content blog-post">
                            ${forPosVO.forPost_content}</div>
                            
                    </div>
                </div>
            </form>
        </div>

            <!-- 電子報訂閱專區+最新文章 -->

            <div class="col-md-3  mx-auto mt-5 ">
                <h4>訂閱電子報</h4>
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <button class="btn btn-warning">
                            <i class="fa fa-envelope-o"></i>
                        </button>
                    </div>
                    <input type="text" class="form-control"
                        aria-label="Sizing example input"
                        aria-describedby="inputGroup-sizing-lg">

                    <!-- 最新文章 -->
                    <!--                             會衝到nav -->
                </div>
                <jsp:include page="newestPos.jsp"></jsp:include>
            </div>
        </div>
        
               <div class="mx-auto mt-4">
 
<%-- 		${forResVO.forRes_ID} --%>

								<!-- 顯示+點選分類標籤 -->
<form action="<%=request.getContextPath()%>/hashTag/hashTag.do" method="post">	
								
		<c:forEach var="forTag" items="${tagDisplay}">
		
		<%Long tagNum = hashTagSvc.getPostTagNum((String)pageContext.getAttribute("forTag"));%>
		
		<%request.setAttribute("tagNum", tagNum); %>
			<%System.out.println("aaaaa="+ pageContext.getAttribute("forTag")); %>
		
					<button type="submit" class="btn btn-sm btn-secondary mt-2" name="action" value="get_One_Tag">
			  				${forTag} <span class="badge badge-light">${tagNum}</span>
					</button>
					<input type="hidden" name="forPost_ID" value="${forPosVO.forPost_ID}">
					<input type="hidden" name="tag" value="${forTag}">
					
		</c:forEach>
		
</form>
		
		
				<form action="<%=request.getContextPath()%>/forPos/forPos.do">
						<div class="my-auto">
							<input type="hidden" name="action" value="addhashTag" >
							<input type="hidden" name="forPost_ID" value="${forPosVO.forPost_ID}" >
							<input type="hidden" name="forRes_ID" value="${forResVO.forRes_ID}" >
						    <input type="text" id="tag-input" name="tag" class="center" placeholder="請輸入標籤" />
						    <button class="btn-tag" type="submit"><i class="fas fa-tags"></i></button>
				  		</div>
				</form>
        </div>
<!--         *************************** -->


        <div class="row">
        
            <div class="col-md-9 col-12">


                <h3 class="text-center mt-5">評論</h3>
                <!--  *********************************************** -->
<%--                 "-----現在登入會員:  ${memVO.mem_id} --%>
<%--                 文章編號${forPosVO.forPost_ID} --%>
                <!--  *********************************************** -->

                <!--                 //////////留言張貼區 /////////   //////////////備註:如未登入，應燈彈跳視窗(狀態:未做)//////////// -->

                <form action="<%=request.getContextPath()%>/forPos/forPos2.do">
<!--                     <div class="card shadow-sm round-0 mt-3 resCard-size " id="resBg"> -->

<!--                         <div class="card-body"> -->
                        
                        
                            <div class="row" id="replyboard">

                                <div class="col-md-2 col-4">
                                    <img id="user-img"
                                        src="https://api.fnkr.net/testimg/90x90/00CED1/FFF/?text=img+placeholder">
                                </div>

                                <div class="col-md-7 col-8 my-auto">

                                        <input type="text" class="form-control form-control-sm"
                                            placeholder="留下你的足跡吧" name="forRes_content">
                                    <p>${errorMsgs.content}</p>

                                    <input type="hidden" name="forPost_ID"
                                        value="${forPosVO.forPost_ID}">
                                </div>
                            </div>
                            <!--                             <hr> -->
                            <%--                             <p class="text-muted">回應於 ${forResVO.forRes_time}</p> --%>
                            <div class="d-flex justify-content-end">
                                <input type="hidden" name="action" value="addOneForRes">
                                <input type="hidden" name="mem_id" value="${memVO.mem_id}">
                                <input type="submit" class="btn btn-primary mt-3 "
                                    name="sendResponse" value="留言">
                                <input type="hidden" name="${forPosVO.forPost_ID}">
                            </div>
                            <hr>
                            
                        <c:if test="${empty memVO.mem_id}">
                        <h4>請先登入會員 </h4> <button class="btn btn-sm btn-danger">登入</button>
                        </c:if>    
                                        
<!--                         </div> -->
<!--                     </div> -->
                    
                </form>
                

                <form action="<%=request.getContextPath()%>/forPos/forPos2.do">

                    <!-- //////////留言發布區 /////////-->
<%--                     --########${forResVO.forRes_content} --%>
                    
                    
                    
                    
     <c:forEach var="forResVO" items="${list}">
     <ul id="comments-list" class="comments-list">
     
			<li>
				<div class="comment-main-level">
					<!-- Avatar -->
					<div class="comment-avatar"><img src="http://i9.photobucket.com/albums/a88/creaticode/avatar_1_zps8e1c80cd.jpg" alt=""></div>
					<!-- Contenedor del Comentario -->
					<div class="comment-box">
						<div class="comment-head">
							<h6 class="comment-name by-author"><a href="http://creaticode.com/blog">${forResVO.mem_ID}</a></h6>
							<span>${forPosVO.forPost_time}</span>
							<i class="fa fa-reply"></i>
							<i class="fa fa-heart"></i>
						</div>
						<div class="comment-content">
							${forResVO.forRes_content}
						</div>
					</div>
				</div>
                                
<!--                                 </div> -->
<!--                             </div> -->
                        <input type="hidden" name="forPost_ID" value="${forPosVO.forPost_ID}">
                        <input type="hidden" name="action" value="getResp">
                  </li>      
            </ul>
        </c:forEach>
                    
                </form>

            </div>
        </div>
    </div>

<!--貼文檢舉modal -->

 <div class="modal fade" id="Report" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      
      <div class="modal-body">
          <div class="form-group">
          <h5>寫下您的檢舉<span class="text-primary">${forPosVO.forPost_theme}</span>此文的理由</h5>
          </div>
          <div class="form-group">
            <label for="message-text" class="col-form-label">Message:</label>
            <textarea class="form-control" id="forPos_rep_reason" name="forPos_rep_reason" rows="4" cols="60"></textarea>
            <p class="text-danger" id="errorReport"></p>
            <button class="btn btn-primary btn-sm" id="ReportSummit">送出</button>
          </div>
      </div>
      
    </div>
  </div>
</div>


<%-- <c:forEach ></c:forEach> --%>


            
                                                <!-- 留言檢舉Modeal -->
<%--         <c:forEach var="forResVO" items="${list}"> --%>

            <div class="modal fade" id="ReportRes${forResVO.forRes_ID}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  
                  <div class="modal-body">
                      <div class="form-group">
                      <h5>寫下您的檢舉會員<span class="text-primary"></span>留言的理由</h5>
                      </div>
                      <div class="form-group">
                        <label for="message-text" class="col-form-label">原因:</label>
                        <textarea class="form-control" id="forRes_rep_reason" name="forRes_rep_reason" rows="4" cols="60"></textarea>
                        <p class="text-danger" id="errorResReport"></p>
                        <button class="btn btn-primary btn-sm" id="ResReportSummit${forResVO.forRes_ID}">送出</button>
                       
<%--                             <%request.setAttribute("forResVO", forResVO); %> --%>
                      </div>
                  </div>
                  
                </div>
              </div>
            </div>
            
<%--         </c:forEach> --%>



    <a href="#topest">
        <button type="button"
            class="btn btn-secondary btn-circle btn-xl fixed-bottom">
            <i class="fas fa-angle-up"></i>
        </button>
    </a>





    <!--         <script type="text/javascript"> -->

<!--      $(function () { // $('[data-toggle="tooltip"]').tooltip() // }) -->

    <!--         </script> -->


    <script src="https://code.jquery.com/jquery-3.2.1.min.js "></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js "></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js "></script>
      <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>


  
<!--   *******************************我的收藏**************************************** -->
    <script type="text/javascript">

var forPost_ID ="${forPosVO.forPost_ID}"
      var mem_id = "${memVO.mem_id}" //瀏覽的會員
      
      
            <%System.out.println("文");%>
  <%String favoritejsp = "no";

            if (fav_VO != null) {
                favoritejsp = "yes";
            }%> 
  
  
  var favorite = "<%=favoritejsp%>"; 
  
//如果收藏紀錄不為空，顯示實心愛心
 if(favorite == "yes"){
     $('#icon').attr('class','fas fa-heart');
     
 } 

  
  $('#favorite').click(function(){
      console.log('forPost_ID='+forPost_ID); 

      if(favorite == "no" ){
          
            $.ajax({
              type:"POST",
              url:"<%=request.getContextPath()%>/favPos/favPos.do",
              data:{"forPost_ID":forPost_ID,"mem_id":mem_id,"action":"addFavorite"},
              dataType:"json",
              success:function(data){
                  favorite = "yes"
                  console.log('ready to adddddddddd'+favorite); 

               $('#icon').attr('class','fas fa-heart');
              },
              
              error : function(){
                alert("新增收藏發生錯誤")
              }
              
            }) 
          }
      
      
      
      
      if(favorite == "yes"){
          
          $.ajax({
              type:"POST",
              url:"<%=request.getContextPath()%>/favPos/favPos.do",
                    data : {
                        "forPost_ID" : forPost_ID,
                        "mem_id" : mem_id,
                        "action" : "deleteFavorite"
                    },
                    dataType : "json",
                    success : function(data) {
                        console.log('ready to deleteeeeeeeeeeee ' + favorite);
                        favorite = "no"

                        $('#icon').attr('class', 'far fa-heart');
                    },

                    error : function() {
                        alert("刪除收藏發生錯誤")
                    }
                })
            }
        })
    </script>
    
    
    
                    <!--     *******************以下為貼文檢舉系統 ********************-->
    
                    <script>
                    $('#ReportSummit').click(function(){

                         $('#errorReport').empty()
//                             var memIdforReport = "${memVO.mem_id}";
                        var forPost_ID= "${forPosVO.forPost_ID}";
//                         var mem_id = '${memVO.mem_id}';
                        var mem_id = "${memVO.mem_id}"; 
                        var forPos_rep_reason = $('#forPos_rep_reason').val();
//                             console.log(forPos_rep_reasoned);
                        console.log('pos_id'+ forPost_ID);


                        $.ajax({
                          type:"POST",
                          url:"<%=request.getContextPath()%>/ForPos_Report/ForPos_ReportServlet.do",
                          data:{"forPost_ID":forPost_ID,"action":"addReport","forPos_rep_reason":forPos_rep_reason, "mem_id":mem_id},
                          dataType:"json",
                          success:function(data){
                            if(data.error!=null){
                              $('#errorReport').text(data.error);
                                console.log('檢舉錯誤訊息驗證成功');

                            }else{
                                console.log('檢舉sweetalert');

//                                $('#forPos_rep_reason').empty();
                                $('#Report').modal('hide');
                              swal({
                                title: "檢舉成功",
                                text: "已經收到您對該貼文的檢舉，我們會盡快處理",
                                icon: "success",
                                button: "確定",
                              });
                            }
                          
                          },
                          error:function(){  
                              
                              swal({
                                    title: "您無法對同一篇貼文檢舉兩次",
                                    text: "您需要等待客服對上一次檢舉的審核",
                                    icon: "warning",
                                    button: "返回",
                                  });
                          
                          }
                        })
                      })
                    </script>
                        
    
    
                                            <!--/////////////////留言檢舉////////////// -->
    
    <script>
                    
    
    
    
                    $('#ResReportSummit').click(function(){
                        console.log('dfsdfgegymtrolkjyo6rtijyhuoi65tjhuiohyiou5hyiuh5iyhi');

                         $('#errorResReport').empty();
//                             var memIdforReport = "${memVO.mem_id}";
//                         var mem_id = '${memVO.mem_id}';
                        var mem_id = "${memVO.mem_id}";
                        
                        
                        var forRes_ID = $('#ryan').val();
                        var forRes_rep_reason = $('#forRes_rep_reason').val(); //用id的value取回文編號
//                             console.log(forPos_rep_reasoned);
                        console.log('res_id='+ forRes_ID);
                        console.log('mem_id='+ mem_id);

                        console.log('檢舉留言');

                        $.ajax({
                          type:"POST",
                          url:"<%=request.getContextPath()%>/forPos/forResRep.do",
                          data:{"memVO":memVO,"forRes_ID":forRes_ID,"action":"addResReport","forRes_rep_reason":forRes_rep_reason},
                          dataType:"json",
                          success:function(data){
                            if(data.error!=null){
                              $('#errorResReport').text(data.error);
                                console.log('檢舉留言錯誤訊息驗證成功');

                            }else{
                                console.log('檢舉sweetalert');

//                                $('#forPos_rep_reason').empty();
                                $('#Report').modal('hide');
                              swal({
                                title: "檢舉成功",
                                text: "已經收到您對該留言的檢舉，我們會盡快處理",
                                icon: "success",
                                button: "確定",
                              });
                            }
                          
                          },
                          error:function(){  
                              
                              swal({
                                    title: "您無法對同一篇留言檢舉兩次",
                                    text: "您需要等待客服對上一次檢舉的審核",
                                    icon: "warning",
                                    button: "返回",
                                  });
                          
                          }
                        })
                      })
        </script>
        
        
        
        
        
<!--         按正評價 -->
        
        
        <script>
        
        var forPost_ID ="${forPosVO.forPost_ID}"
            var mem_id = "${memVO.mem_id}" //瀏覽的會員
            
            
                  <%System.out.println("評價前端");%>

<%--         <%String ratingjsp = "no"; --%>

//                   if (fav_VO != null) {
//                 	  ratingjsp = "yes";
<%--                   }%>  --%>
        
        
<%--         var favorite = "<%=favoritejsp%>";  --%>
        
        
        <%ForPost_rat_VO forPost_rat_VO = new ForPost_rat_VO();%>
      //如果正評紀錄不為空，顯示實心讚
       if(ratingjsp == "yes"){
           $('#posBtn').attr('class','fa fa-thumbs-up');
           
       } 

        
        $('#posBtn').click(function(){
            console.log('forPost_ID='+forPost_ID); 

            if(favorite == "no" ){
                
                  $.ajax({
                    type:"POST",
                    url:"<%=request.getContextPath()%>/forPos/forPos.do",
                    data:{"forPost_ID":forPost_ID,"mem_id":mem_id,"action":"addPosRat"},
                    dataType:"json",
                    success:function(data){
                        favorite = "yes"
                        console.log('ready to adddddddddd'+favorite); 

                     $('#posBtn').attr('class','fas fa-heart');
                    },
                    
                    error : function(){
                      alert("新增正面評價發生錯誤")
                    }
                    
                  }) 
                }
            
            
            
            
            if(favorite == "yes"){
                
                $.ajax({
                    type:"POST",
                    url:"<%=request.getContextPath()%>/favPos/favPos.do",
                          data : {
                              "forPost_ID" : forPost_ID,
                              "mem_id" : mem_id,
                              "action" : "deleteFavorite"
                          },
                          dataType : "json",
                          success : function(data) {
                              console.log('ready to deleteeeeeeeeeeee ' + favorite);
                              favorite = "no"

                              $('#icon').attr('class', 'far fa-heart');
                          },

                          error : function() {
                              alert("刪除正面評價發生錯誤")
                          }
                      })
                  }
              })
        
        </script>
        
        
        
        
        
        
        
        
        
        
        
        
    
    
    
    


</body>
</html>