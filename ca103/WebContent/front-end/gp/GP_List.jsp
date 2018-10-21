<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gp.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.joined_gp.model.*"%>
<%@ page import="com.favorite_gp.model.*"%>
<%@ page import="java.sql.*" %>



<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">



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


    <title>GP_List</title>
  </head>
  <body>

		<%@ include file="/sources/file/Home/NavBar.file" %>
		




		<div class="container-fluid padding_Top">



			


			
			<div class="row">
				<div class="col-md-2">
					<a class="btn btn-warning" href="<%=request.getContextPath() %>/front-end/gp/GP_Browsing.jsp" style="margin-bottom:5px;">回瀏覽頁面</a>
					<div class="list-group" id="list-tab" role="tablist">
						<a class="list-group-item list-group-item-action ${ param.listPage== '1'?'active':''}" id="list-1-list" data-toggle="list" href="#list-1" role="tab" aria-controls="1">已加入揪團</a>
						<a class="list-group-item list-group-item-action ${ param.listPage== '2'?'active':''}" id="list-2-list" data-toggle="list" href="#list-2" role="tab" aria-controls="2">收藏的揪團</a>
						<a class="list-group-item list-group-item-action ${ param.listPage== '3'?'active':''}" id="list-3-list" data-toggle="list" href="#list-3" role="tab" aria-controls="3">我管理的揪團</a>
					</div>
				</div>
				<div class="col-md-10">
<!-- 					include -->
						
						<%
							String mem_id = ((MemVO) (request.getSession().getAttribute("memVO"))).getMem_id();
							if (request.getParameter("listPage")!=null && request.getParameter("listPage").equals("3")){
								GPService gpSrc = new GPService();
								List<GPVO> list = gpSrc.searchCreGP(mem_id);
								pageContext.setAttribute("list",list);
						%>
						      	<jsp:include page="GP_Mine.jsp"/>
						<%}else if (request.getParameter("listPage")!=null && request.getParameter("listPage").equals("1")){%>		
								<jsp:include page="GP_Joined.jsp"/>
						<%
								JoinedGPService joined_gpSrc = new JoinedGPService();
								List<GPVO> list = joined_gpSrc.SearchJoinedGPByMember(mem_id);
								pageContext.setAttribute("list",list);
							}else if (request.getParameter("listPage")!=null && request.getParameter("listPage").equals("2")){%>
								<jsp:include page="GP_Favorite.jsp"/>
						<%
								Favorite_GPService favSrc = new Favorite_GPService();
								List<GPVO> list = favSrc.searchFavGP(mem_id);
								pageContext.setAttribute("list",list);
							} %>
				</div>
			</div>
			
			<div class="row">
	                 	<script>
	              	    $(function(){
	              	    	var minPage = 1;
	              	    	var maxPage = Math.floor(${(list.size()-1)/10+1});
	              	    	$("#currentPage").change(function(){
	              	    		window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_List.jsp?listPage=${param.listPage}&currentPage=" + $(this).val();
	              	    	});
	              	    	if($("#currentPage").val()>minPage){
	              	    		$("#prePage").click(function(){
	              	    			$("#currentPage").val(function(i, oldval) {
	              	    			    return --oldval;
	              	    			});
	              	    			window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_List.jsp?listPage=${param.listPage}&currentPage=" + $("#currentPage").val();
	              	    		});
	              	    	}
	              	    	if($("#currentPage").val()<maxPage){
	              	    		$("#nextPage").click(function(){
	              	    			$("#currentPage").val(function(i, oldval) {
	              	    			    return ++oldval;
	              	    			});
	              	    			window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_List.jsp?listPage=${param.listPage}&currentPage=" + $("#currentPage").val();
	              	    		});
	              	    	}
	              	    	$("#firstPage").click(function(){
	              	    		window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_List.jsp?listPage=${param.listPage}&currentPage=" + minPage;
	              	    	});
	              	    	$("#lastPage").click(function(){
	              	    		window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_List.jsp?listPage=${param.listPage}&currentPage=" + maxPage;
	              	    	});
	              	    });
	              	    
	                 	
	                 	</script>
	                 	
	                 	<div class="pageRow mx-auto">
	                 		<a class="btn btn-info" id="firstPage">first</a>
		                  	<a class="btn btn-info" id="prePage">上一頁</a>
							<select id="currentPage">
								<c:forEach var="myData" begin="1" end="${list.size()/10+9/10}" >
			                         	   <option value="${myData}" ${param.currentPage == myData?'selected':''}>第${myData}頁</option>
			                        </c:forEach>
							</select>
							<a class="btn btn-info" id="nextPage">下一頁</a>
							<a class="btn btn-info" id="lastPage">last</a>
	                 	</div>
			</div>


		</div>




	<link rel="stylesheet" href="<%=request.getContextPath() %>/sources/css/gp/GP_List.css">
    <script src="<%=request.getContextPath() %>/sources/js/gp/GP_List.js"></script>
    
    <script>

	    $(function(){
	    	$("#list-1-list").click(function(){
				window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_List.jsp?listPage=1";

	    	});
	    	$("#list-2-list").click(function(){
				window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_List.jsp?listPage=2";

	    	});
	    	$("#list-3-list").click(function(){
				window.location.href = "<%=request.getContextPath() %>/front-end/gp/GP_List.jsp?listPage=3";

	    	});
	    });
    
    </script>
</body>
</html>