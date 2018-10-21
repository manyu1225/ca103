<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="sources/css/css.css">
	<!--      漂亮icon的引用-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	
	<title>自轉車-首頁</title>
</head>
<body onload="connect();">
<%@ include file="/sources/file/Home/NavBar.file" %>
<%@ include file="/sources/file/Home/Broadcast.file" %>
<%@ page import="com.mem.model.*"%>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.28.5/sweetalert2.all.js"></script>

<script>
	
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
        	  title: jsonObj.act_name,        //後端送來的活動名稱
        	  text: '有一個新活動剛剛新增!',
        	  imageUrl: jsonObj.act_img,
        	  imageWidth: 400,
        	  imageHeight: 200,
        	  imageAlt: 'Custom image',
        	  animation: false,
        	  footer: '<a href="'+jsonObj.act_link+'">立即前往活動頁面</a>'

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
      var act_ID ="有活動上架"
      var jsonObj = {"act":"有活動上架"};
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