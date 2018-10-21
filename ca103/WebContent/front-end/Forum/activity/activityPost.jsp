<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



					<!--///////////////// 此處為活動單一頁面////////////// -->


<jsp:useBean id="routeSvc" scope="page" class="com.route.model.RouteService" />



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
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/sources/css/Forum/wsBtn.css" >
<!-- <link rel="stylesheet" type="text/css" href="css/css.css"> -->


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

#user-img {
	border-radius: 50%;
}

.btn-lg, .btn-group-lg>.btn {
	padding: 0.5rem 1rem;
	font-size: 2rem;
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



	.list-group-item {
		  position: relative;
		  display: block;
		  padding: 0.35rem 1.3rem;
		  margin-bottom: 1.5px;
		  background-color: #fff;
		  border: none;
		}


	.card-body{

		padding-bottom: 20px;


	}
	
	
	  div[name=myAminate]{
      position: fixed;
      bottom: 300px;
      right: -300px;
      width: 300px;
      border: 1px solid #ffc107;
      background-color: #ffc107;
      box-shadow: 0 2px 3px rgba(0,0,0,0.2);
      opacity: 0.8;
    }
    
/*     回前頁 */
    .button {
  display: inline-block;
  background: #3ed3e8;
  color: #fff;
  text-transform: uppercase;
  padding: 15px 20px;
  border-radius: 50%;
  box-shadow: 0px 17px 10px -10px rgba(0,0,0,0.4);
  cursor: pointer;
  transition: all ease-in-out 300ms;
  text-decoration: none;
}

.button:hover {
  box-shadow: 0px 37px 20px -15px rgba(0,0,0,0.2);
  transform: translate(0px, -10px);
}


    
    


</style>


<title>${activityVO.act_name}</title>
</head>
<body style="background-color: #e6e6e6" onload="connect();">


<%@ include file="/sources/file/Home/NavBar.file" %>

	<a id="topest"></a>
	
	<!--  			 返回前頁按鈕 -->
	
	 <a href="<%=request.getContextPath()%>/front-end/Forum/activity/activityList.jsp" class="button"><i class="far fa-hand-point-left"></i></a>
 
 <form action="<%=request.getContextPath()%>/Route/RouteServlet.do" method="post">
 
	<div class="container">
		<div class="row">
		
 			 <div class="hidden" id="wsNotise"></div>
 			 
 			 



			<!-- 文章標題+內文 -->

			<div class="col-md-9 col-12 mt-5  ">
						<div class="card">
							<div class="card-body"  style="padding-bottom: 20px">
								<div class="row">
									<h2 class="postTheme ml-4">${activityVO.act_name}</h2>
										<div class="col-2 text-center d-flex justify-content-end" >
						              <img data-like="no" id="ws" src="<%=request.getContextPath()%>/sources/icon/Forum/broadcast.png">
						            </div>
						           </div>
			
									<ul class="list-group">
									  <li class="list-group-item">
<!-- 										瀏覽人數 -->
										<small class="text-muted"><i class="far fa-eye"></i>  ${activityVO.act_click}</small>
									  </li>
									  <li class="list-group-item ">
										 <label class="text-secondary">活動名稱:</label> 
										  ${activityVO.act_name}
									  </li>
									  <li class="list-group-item">
									  	<label class="text-secondary">主辦單位:</label>
									  	${activityVO.act_holder}
									  </li>
									  <li class="list-group-item">
									  	<label class="text-secondary">活動日期:</label> 
									  	${activityVO.act_sdate==null?activityVO.act_sdate:activityVO.act_sdate}
									  </li>
									  
									  <c:if test="${not empty activityVO.act_edate}">
										  <li class="list-group-item">
										  	<label class="text-secondary">活動結束日期:</label>
										  	${activityVO.act_edate}
										  </li>
									  </c:if>	
									  	
									  	
									  	
									  <li class="list-group-item">
									  <label class="text-secondary">開始報名日期:</label> 
									  ${activityVO.act_regis_date}
									  </li>
									  
									  <li class="list-group-item ">
										<label class="text-secondary">活動連結:</label>  
									  	
									  	
									  	<a href="${activityVO.act_href}">連結1</a>
									  </li>
									  
									  
									  
									  
									  <li class="list-group-item ">
																		
			                        <lable class="text-secondary">路線地圖: </label>
			                        
			                        <button  class="btn btn-primary" type="submit" name="action" value="getRouteDetailed"
				                            id="favBtn" data-placement="top"
				                            title="加到我的收藏">
				                            <i class="fas fa-bicycle"></i>
			                        </button>	  
			                        
			                        <input type="hidden" name="rot_id" value="${activityVO.rot_id}">
									  	${routeSvc.getRouteDetailed(activityVO.rot_id).rot_name}
									  </li>
									  
									  
									  
									  <li class="list-group-item ">
									 	<label class="text-secondary"> 活動簡介: </label>
									 	<br>
									 	${activityVO.act_info}
									  </li>
									 	
									 	<div class="mx-auto">
										<img src="<%=request.getContextPath()%>/activity/activity.do?action=getOnePic&act_ID=${activityVO.act_ID}" style="width: 80%; height: 80%;">									 	
										</div>
									
									</ul>
								</div>
							</div>
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
					<!-- 							會衝到nav -->
				</div>
				
				<!--連結到最新活動 -->
<%-- 				<jsp:include page="<%=request.getContextPath()%>/fornt-end/Forum/activity/newestAct.jsp"></jsp:include>    --%>
				<%@ include file="/front-end/Forum/activity/newestAct.jsp" %>
				
			</div>
			
<!-- 			推播按鈕 -->
<!-- 			<label class="switch"> -->
<!-- 			  <input type="checkbox" id="ws"> -->
<!-- 			  <span class="slider round"></span> -->
<!-- 			</label> -->
			
			
		</div>
	</div>
</form>





	<a href="#topest">
		<button type="button"
			class="btn btn-secondary btn-circle btn-xl fixed-bottom">
			<i class="fas fa-angle-up"></i>
		</button>
	</a>





	<!-- 		<script type="text/javascript"> -->

<!-- 	 $(function () { // $('[data-toggle="tooltip"]').tooltip() // }) -->

	<!-- 		</script> -->

	<script src="https://code.jquery.com/jquery-3.2.1.min.js "></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js "></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js "></script>
<!-- <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.28.5/sweetalert2.all.js"></script>

<%-- var act_info = "${activityVO.act_info}" --%>
<%-- var act_regis_date =" ${activityVO.act_regis_date}" --%>

	
	<script>
	$('#ws').click(function(){
			

        sendMessage();
	})
		
	
	/* ============================ 新活動推播 ========================== */
    var MyPoint = "/ActivityEchoServer";/* 專案路徑 */
    var host = window.location.host;
    var path = window.location.pathname;
    var webCtx = path.substring(0, path.indexOf('/', 1));
    var endPointURL = "ws://" + window.location.host + webCtx + MyPoint; 
    var webSocket;
    
    function connect() {
      // 建立 websocket 物件
      webSocket = new WebSocket(endPointURL);

      webSocket.onopen = function(event) {
        console.log("我建立連線")

      };

      webSocket.onmessage = function(event) {  //{"productId":"PRD012","callPrice":20022}
    	    var act_name = "${activityVO.act_name}";
    	    var act_ID = "${activityVO.act_ID}";

		var act_img = "<%=request.getContextPath()%>/activity/activity.do?action=getOnePic&act_ID=${activityVO.act_ID}";
		var act_link = "<%=request.getContextPath()%>/activity/activity.do?act_ID=${activityVO.act_ID}&action=getOneAct_onPage&act_click=${activityVO.act_click}";
        var jsonObj = JSON.parse(event.data);
        var webProductId = jsonObj.act     //PRD012


        swal({
        	  title: act_name,
        	  text: '已成功推播活動!',
        	  imageUrl: act_img,
        	  imageWidth: 400,
        	  imageHeight: 200,
        	  imageAlt: 'Custom image',
        	  animation: false,
        	  customClass: 'animated tada',
        	  footer: '<a href="'+act_link+'">推播成功</a>'

        	})
        
        
       
      };
// ====================
      webSocket.onclose = function(event) {
        console.log("我離開連線")
      };
    }
    
    function disconnect () {
      webSocket.close();
       console.log("斷線")
    }

    function sendMessage() {
	   	var act_name = "${activityVO.act_name}";
	 	var act_ID = "${activityVO.act_ID}";
	
		var act_img = "<%=request.getContextPath()%>/activity/activity.do?action=getOnePic&act_ID=${activityVO.act_ID}";
		var act_link = "<%=request.getContextPath()%>/activity/activity.do?act_ID=${activityVO.act_ID}&action=getOneAct_onPage&act_click=${activityVO.act_click}";
	    var act_m ="有活動上架"
	    //傳送資料進後端
	    var jsonObj = {"act_m ":"有活動上架","act_name":act_name, "act_img":act_img, "act_link":act_link};
	    webSocket.send(JSON.stringify(jsonObj));
    }
      
    var wsNotiseCount = 1;
    function moveDiv(data){
      var count = wsNotiseCount
      wsNotiseCount++
      var addString = '<div class="p-1" name="myAminate" id="myAminate'+count+'s">'
      addString = addString + '<p class="font-smll2 p-1 text-white">'
      addString = addString + '<i class="fas fa-bell mr-1"></i>賽事活動更新通知</p>'
      addString = addString + '<span class="no_pricestr">NT$</span>'
      addString = addString + '<label class="smll_pricenum" id="wsPrice">'+data+'</label>'

      $('#wsNotise').after(addString)
      // $('#wsPrice').text(data)
      $('#myAminate'+count+'s').animate({'right':'10px'},1000,function(){
        setTimeout(function(){
          // $('#myAminate').animate({'right':'-300px'},1000)
          $('#myAminate'+count+'s').animate({'bottom':'-300px','opacity':'0'},1000,function(){
            $('#myAminate'+count+'s').remove()
          })

          // $('#myAminate'+count+'s').fadeOut(1000,function(){
          //   $('#myAminate'+count+'s').animate({'right':'-300px','opacity':'1'},100)
          // });
        },2000)
      })
    }
		
	
	
	</script>




</body>
</html>