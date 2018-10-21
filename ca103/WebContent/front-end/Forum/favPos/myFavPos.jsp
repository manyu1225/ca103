<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.*"%>
<%@ page import="com.forPos_fav.model.*"%>
<%@ page import="com.mem.model.*"%>


					<!--///////////////// 此處為文章收藏列表////////////// -->





<!-- 真實會員資料 -->
<%
MemVO memVO = (MemVO)session.getAttribute("memVO");

%>




<%

Forum_post_fav_Service forPosFavSvc = new Forum_post_fav_Service();
List<Forum_post_fav_VO> list = forPosFavSvc.getAllPosFav(memVO.getMem_id());
// System.out.println("list:" + list);


System.out.println("memVOoooooiddddddd" + memVO.getMem_id());

System.out.println("listttttt" + list);

request.setAttribute("list", list);

%>






<%System.out.println("成功轉交myfavPOS頁面"); %>
<jsp:useBean id="forPosSvc" scope="page" class="com.forPos.model.Forum_post_Service"/>
<jsp:useBean id="fav_VO" scope="page" class="com.forPos_fav.model.Forum_post_fav_VO"/>

<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService"/>


<%System.out.println("fav_VO.getMem_ID=" + fav_VO.getMem_ID());%>

<!doctype html>
<html lang="en">
<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

  <link rel="stylesheet"
  href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
  integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ"
  crossorigin="anonymous">
  <link rel="stylesheet"
  href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="../css/css.css">



  <style type="text/css">

  

  h1{

font-family:微軟正黑體;

  }

  

  
  



</style>


<title>我的文章收藏列表</title>
</head>

<body style="background-color:#e6e6e6">



<%@ include file="/sources/file/Home/NavBar.file" %>



 <div class="container-fluid">
           <div class="row">
          
          <div class="col-md-3 col-12 mt-5">
			<%@ include file="/sources/file/Forum/forPosSidebar.file" %>
           </div> 
           
           
           
           
			        
			    
             <div class="col-md-7 col-12 mt-5" >
         
             <h1><img alt="" src="<%=request.getContextPath()%>/sources/icon/Forum/wishlist.png">${memVO.mem_nickname}的文章收藏列表</h1>
    
       
     
    
    
     <c:forEach var="fav_VO" items="${list}" >
     
     
     

         <div class="card shadow mt-2">       
             <div class="card w-100 mt-2  ">
              <div class="row">
                <div class="col-2 pl-4 mb-3 mt-3">
                  <img src="<%=request.getContextPath()%>/forPos/forPos.do?action=getOnePic&forPost_ID=${fav_VO.forPost_ID}" style="width: 130px; height: 130px" alt="">
                </div>

                  <div class="col-10 pl-4 ">
                    <div class="card-body">
                      <h5 class="card-title ml-2">
                      
			                      
                    <a href="<%=request.getContextPath()%>/forPos/forPos.do?forPost_ID=${fav_VO.forPost_ID}&mem_id=${fav_VO.mem_ID}&action=getOnePos_onPage&forPost_state=${forPosSvc.getOneForPos(fav_VO.forPost_ID).forPost_state}&forPost_view=${forPosSvc.getOneForPos(fav_VO.forPost_ID).forPost_view}&actionTag=getHashTag">
                      ${memSvc.findMemById(forPosSvc.getOneForPos(fav_VO.forPost_ID).mem_ID).mem_nickname}<i class="fas fa-caret-right"></i>
                      ${forPosSvc.getOneForPos(fav_VO.forPost_ID).forPost_theme}</a>
                      </h5>
						<div class="box">
<%--                         <p class="card-text JQellipsis">${forPosSvc.getOneForPos(fav_VO.forPost_ID).forPost_content}</p> --%>
                        </div>
                    </div>                
                  </div>
                </div>    
                <div class="row">
                  <div class="col-8 pl-4  mb-3">
		                    作者<a href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=goToMemberView&mem_id=${memVO.mem_id}"> ${memSvc.findMemById(forPosSvc.getOneForPos(fav_VO.forPost_ID).mem_ID).mem_nickname}</a>  於 <fmt:formatDate value="${forPosSvc.getOneForPos(fav_VO.forPost_ID).forPost_time}" pattern="yyyy-MM-dd HH:mm:ss"/> 發布
                    </div>
                    </div>             
                </div>
           	</div>
      </c:forEach>
      	</div>
      </div>
 </div>
    
    
    
        
        
       


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
 <!--  
 <script>
			$(function(){
			    var len = 30; // 超過50個字以"..."取代
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