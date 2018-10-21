<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<!-- <link rel="stylesheet" type="text/css" href="sources/css/css.css"> -->
	<!--      google-api,不可省略-->
	<script src="https://www.google.com/jsapi"></script>
	<script src="<%=request.getContextPath()%>/sources/js/Route/MapInsert.js"></script>
	<!--      漂亮跳窗的引用-->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.28.1/sweetalert2.all.min.js" type="text/javascript"></script>
	<!--      漂亮icon的引用-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<style>
        #map {
            height: 900px;
            width: 100%;
        }
        
        html {
  			height: 100%; 
	  	}
	  	
		body{
			font-family: Comic Sans MS,arial,helvetica,sans-serif;
		    background-position: center;
		    background-size: cover;
		    background-repeat: no-repeat;
	   		background-attachment: fixed;
		    background-image: url(<%=request.getContextPath()%>/sources/img/Route/bg13.jpg);
		    
	    }
	    
	    #MyBlog{
		    position: fixed;  /*固定在網頁上不隨卷軸移動，若要隨卷軸移動用absolute*/
		    top: 85%;  /*設置垂直位置*/
		    right: -130px;  /*設置水平位置，依所放的內容多寡需要自行手動調整*/
		    background: #ffffff;  /*背景顏色*/
		    padding: 10px 20px;
		    border-radius: 10px;  /*圓角*/
		    -moz-border-radius: 10px;
		    -webkit-border-radius: 10px;
		    opacity:0.9;
		}

		#MyBlog:hover{  /*當滑鼠移至此區塊時，伸縮區塊*/
		    right: -10px;
		}
		
		#MyBlog #title{
		    padding-right: 5px;  /*讓標題與連結中間有空隙*/
		}
    </style>
	<title>Insert title here</title>
</head>
<body>
<%@ include file="/sources/file/Home/NavBar.file" %>
<% 
	//String mem_id = (String)session.getAttribute("mem_id"); 
	MemVO memVO = (MemVO)session.getAttribute("memVO");
	String mem_id = memVO.getMem_id();
	String uuid = UUID.randomUUID().toString();
	session.setAttribute("uuid", uuid);

%>

	
	
	
	<div id="map"></div>
    <!--      地圖出現的地方,style設定float是為了稍微排版,讓下面的經緯度顯示在地圖的右方-->
	<br>

			    <!--  面積高度圖長出來的位置    --> <!-- 好像改成全版會比較適合?10/9 -->
			    <div id="chart_div" style="width: 100%; height: 250px;"></div>
    <div class="container">
		<div class="row">
			<div class="col-12">
		    </div>
	    </div>
    </div>
        <br>
    <form action="<%=request.getContextPath()%>/Route/RouteServlet.do" Method="POST" enctype="multipart/form-data" name="insertRoute">
    <div class="container" style="opacity:0.9">
		<div class="row">
			<div class="col-12">
			    <div class="card" style="height:460px">
					<div class="card-header">
						<div><b>路線名稱：</b><input class="form-control form-control-sm" id="rotName" placeholder="請輸入路線名稱" type="text" name="rot_name" style="width:250px; display:inline;" maxlength="25"></div>
						<div style="margin-top:3px">
							<div class="custom-control custom-radio custom-control-inline" style="display:none" id="div1">
								<input class="custom-control-input" type="radio" name="rot_status" id="rot_status3" value="0" onclick="pri()">
								<label class="custom-control-label" for="rot_status3" id="afterRoute"><b>私人路線</b></label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input class="custom-control-input" type="radio" name="rot_status" id="rot_status1" value="1" onclick="pub()">
								<label class="custom-control-label" for="rot_status1" id="beforeRoute"><b>公開路線</b></label>
							</div>
							<div class="custom-control custom-radio custom-control-inline" id="div2">
								<input class="custom-control-input" type="radio" name="rot_status" id="rot_status2" value="0" checked  onclick="pri()">
								<label class="custom-control-label" for="rot_status2" id="afterRoute"><b>私人路線</b></label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<footer class="blockquote-footer font-weight-bold" style="font-size:14px;margin-top:2px;display:none" id="pub" ><cite title="Source Title">在公開的瀏覽路線中<b style="color:red">可以</b>查詢得到此次建立的路線，並且此路線會被加入到您的收藏列表中。但是您將<b style="color:red">無法</b>對路線做出修改。</cite></footer>
								<footer class="blockquote-footer font-weight-bold" style="font-size:14px;margin-top:2px;display:" id="pri"><cite title="Source Title">在公開的瀏覽路線中<b style="color:red">無法</b>查詢得到此次建立的路線，必須由您的收藏路線列表中詢得。您<b style="color:red">可以</b>後續修改此路線的相關敘述。</cite></footer>
							</div>
						</div>
					</div>
					<div class="card-body" style="height:150px">
					<div class="container">
					<div class="row">
					<div class="col-6">
						<div class="container">
							<div class="row">
								<div class="col-12" style="height:190px;">
									<div class=" text-center align-self-center " id="rot_photo" style="overflow:hidden;width:470px;height:180px;border:solid;"></div>
								</div>
								<div class="col-12" style="height:60px;">
									<input class="custom-range" id="spacing" type="range" name="spacing" min="-100" max="400" value="10" data-sizing="px">
									<p class="font-weight-bold">請拖曳調整您的圖片位置</p>
								</div>
							</div>
						</div>
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text">Upload</span>
							</div>
							<div class="custom-file">
								<input type="file" class="custom-file-input" id="rot_photo_file" name="rot_photo" onchange="rot_photo_func(this)" accept="image/*">
								<label class="custom-file-label" for="rot_photo_file">請為您的路線上傳一張照片</label>
							</div>
						</div>
					</div>
						<div class="col-6">
						<blockquote class="blockquote mb-0">
							<div class="input-group" style="margin-bottom:10px">
								<b>路線起點：</b>
								<span style="width:100px">
									<select class="form-control form-control-sm" name="rot_loc_start" >
										<option value="0">請選擇</option>
										<option value="基隆市">基隆市</option>
										<option value="台北市">台北市</option>
										<option value="新北市">新北市</option>
										<option value="桃園縣" id="downStar">桃園縣</option>
										<option value="新竹縣">新竹縣</option>
										<option value="苗栗縣">苗栗縣</option>
										<option value="台中市">台中市</option>
										<option value="彰化縣">彰化縣</option>
										<option value="雲林縣">雲林縣</option>
										<option value="嘉義縣">嘉義縣</option>
										<option value="台南市">台南市</option>
										<option value="高雄市">高雄市</option>
										<option value="屏東縣">屏東縣</option>
										<option value="宜蘭縣">宜蘭縣</option>
										<option value="花蓮縣">花蓮縣</option>
										<option value="台東縣">台東縣</option>
										<option value="南投縣">南投縣</option>
										<option value="外島">外島</option>
									</select>
								</span>　
								<span><b>地點描述：</b><input class="form-control form-control-sm" id="starScr" placeholder="請輸入路線起點描述" type="text" name="rot_loc_start_des" style="width:180px; display:inline;" maxlength="20"></span>
							</div>
							<div class="input-group" style="margin-bottom:10px">
								<b>路線終點：</b>
								<span style="width:100px">
									<select class="form-control form-control-sm" name="rot_loc_end" >
										<option value="0">請選擇</option>
										<option value="基隆市">基隆市</option>
										<option value="台北市">台北市</option>
										<option value="新北市">新北市</option>
										<option value="桃園縣" id="downEnd">桃園縣</option>
										<option value="新竹縣">新竹縣</option>
										<option value="苗栗縣">苗栗縣</option>
										<option value="台中市">台中市</option>
										<option value="彰化縣">彰化縣</option>
										<option value="雲林縣">雲林縣</option>
										<option value="嘉義縣">嘉義縣</option>
										<option value="台南市">台南市</option>
										<option value="高雄市">高雄市</option>
										<option value="屏東縣">屏東縣</option>
										<option value="宜蘭縣">宜蘭縣</option>
										<option value="花蓮縣">花蓮縣</option>
										<option value="台東縣">台東縣</option>
										<option value="南投縣">南投縣</option>
										<option value="外島">外島</option>
									</select>
								</span>　
								<span><b>地點描述：</b><input class="form-control form-control-sm" id="endScr" placeholder="請輸入路線終點描述" type="text" name="rot_loc_end_des" style="width:180px; display:inline;" maxlength="20"></span>
							</div>	
							<div class="input-group">
								<b>路線描述：</b><textarea class="form-control" id="rotScr" placeholder="請輸入路線描述" name="rot_describe" rows="8" maxlength="100"></textarea>
							</div>
						</blockquote>
						</div>
						</div>
						</div>
						
						<footer class="blockquote-footer text-right ">建立者： <cite><%= memVO.getMem_nickname() %></cite></footer>
					</div>
				</div>
		    </div>
	    </div>
    </div>

	<br>

	<div class="container" style="opacity:0.9">
		<div class="row">
			<div class="col-12">
				<div class="card">
					<div class="card-header">
						<b>路線詳細資訊：</b>
					</div>
					<div class="card-body">
						<div class="container">
							<div class="row">
								<div class="col-12">
									<div class="card-deck">
										<div class="card">
											<div class="card-body">
												<h5><b>路線難度：</b><b id="rot_hardText"></b></h5>
												<h5><b>路線距離：</b><b id="rot_disText"></b></h5>
												<input id="rot_hard" type="hidden" name="rot_hard">
												<input id="rot_dis" type="hidden" name="rot_dis">
											</div><span class="glyphicon glyphicon-question-sign"></span>
										</div>
										<div class="card">
											<div class="card-body">
												<h5><b>爬升高度：</b><b id="rot_height_upText"></b></h5>
												<h5><b>下降高度：</b><b id="rot_height_downText"></b></h5>
												<h5><b>海拔最高：</b><b id="rot_heightestText"></b></h5>
												<input id="rot_height_up" type="hidden" name="rot_height_up">
												<input id="rot_height_down" type="hidden" name="rot_height_down">
												<input id="rot_heightest" type="hidden" name="rot_height_ave">
											</div>
										</div>
										<div class="card">
											<div class="card-body">
												<h5 id="slopeUp" data-toggle="tooltip" data-placement="top" title="爬升高度 / 路線上坡長度 * 100%"><b>爬升累計坡度：</b><b id="rot_slope_upText"></b></h5>
												<h5 id="slopeUpAve" data-toggle="tooltip" data-placement="top" title="爬升高度 / 路線長度 * 100%"><b>爬升平均坡度：</b><b id="rot_slope_downText"></b></h5>
												<h5 id="slopeAve" data-toggle="tooltip" data-placement="top" title="(最高海拔 - 起點海拔) / 路線長度 * 100%"><b>高度平均坡度：</b><b id="rot_slope_aveText"></b></h5>
												<input id="rot_slope_up" type="hidden" name="rot_slope_up">
												<input id="rot_slope_down" type="hidden" name="rot_slope_down">
												<input id="rot_slope_ave" type="hidden" name="rot_slope_ave">
											</div>
										</div>
									</div>
									<br>
									
									
									<div class="container">
										<div class="row justify-content-end">
											<div class="col-6"></div>
											<div class="col-auto">
												<div style="display:none"> 
													<div><b>起點經緯度：</b><input id="Slocation" type="text" name="rot_start"></div>
												    <div><b>終點經緯度：</b><input id="Elocation" type="text" name="rot_end"></div>
												    <div><b>路線座標：</b><input id="rot_gps" type="text" name="rot_gps"></div>
												    <div><b>路線編號：</b><input type="text" name="rot_id" value="R000006"></div>
												    <div><b>會員編號：</b><input type="text" name="mem_id" value=<%= mem_id %>></div>
												    <div><b>地區狀態：此欄位待廢棄</b><input type="number" name="rot_loc_status" value="1"></div>
												    <div><b>路線地圖：</b><input type="text" name="rot_map" value="此欄位待廢棄"></div>
												    <div><b>坡度圖：　</b><input type="text" name="rot_map_slope" value="此欄位待廢棄"></div>
												    <div><b>路線圖片：</b><input type="number" name="rot_photo" value="1"></div>
											    </div>
											    <input type="hidden" id="rot_photo_loc" name="rot_photo_loc" value="0">
												<input type="hidden" value="insertRoute" name="action">
												<input type="hidden" name="uuid" value="<%=uuid %>"/>
												<button type="button" class="btn btn-primary" value="新增路線" onclick="addRoute(this)">新增路線</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<br>
<br>
<div id="MyBlog">
		<span id="title">小按鈕</span>
		<a href="javascript: void(0)" onclick="magic()">See The Magic</a>
	</div>

<br>
	</form>
    <!--  距離表格長出來的位置,暫時設定為頁面不可見,把style的display:none刪除即為可見    -->
    <div id="directionsPanel" style="float:right;width:30%;height:100%;float:left;display:none;"></div>  

	
	<script type="text/javascript">

	//	圖片進度條的事件
    document.getElementById('spacing').addEventListener('input', marginTopChange);
	
	//	圖片的進度條調整函式
	function marginTopChange() {
		let margintop = -this.value;
		document.getElementById("uploadImg").style.marginTop = margintop + 'px';
		document.getElementById("rot_photo_loc").value = margintop;
		console.log("margintop" + margintop);
	};

	//	圖片上傳預覽的函式
	var file;
	function rot_photo_func (obj) {
		var reader = new FileReader();
		reader.readAsDataURL(obj.files[0]);
		file = obj.files[0].type;
		reader.onloadend = function (e){
			document.getElementById("rot_photo").innerHTML = "<img id='uploadImg' src='" + e.target.result + "' style='width:470px'>";
		}
	}
	
	//	錯誤處理-1.無設定路線時 2.無上傳圖片時 3.圖片上傳格式不符合時
	function addRoute(obj){
		if(document.getElementById("rot_dis").value == ""){
			swal({
                title: "您尚未製作路線",
                html: "請點選地圖創造屬於您的路線",
                type: "info",
                confirmButtonText:"確定"/*改這裡*/
			});
			console.log(document.getElementById("rot_photo_file").value == "");
		}else if(document.getElementById("rot_photo_file").value == ""){
			swal({
                title: "您尚未上傳圖片",
                html: "請為您製作的路線選擇一張獨特的圖片",
                type: "info",
                confirmButtonText:"確定"/*改這裡*/
			});
		}else if(!file.match(/^([0-9a-zA-Z_\-~ :\\])+(.jpg|.JPG|.jpeg|.JPEG|.bmp|.BMP|.gif|.GIF|.png|.PNG)$/)){
			swal({
                title: "您上傳的圖片格式錯誤",
                html: "請「選擇」一張圖片",
                type: "error",
                confirmButtonText:"確定"/*改這裡*/
			});
		}else{
			document.insertRoute.submit();
		}
	}
	
	//	公開路線-私人路線的選擇函式
	function pub(){
		document.getElementById("pub").style.display = '';
		document.getElementById("pri").style.display = 'none';
		document.getElementById("div2").style.display = 'none';
		document.getElementById("div1").style.display = '';
	};
	
	//	公開路線-私人路線的選擇函式
	function pri(){
		document.getElementById("pub").style.display = 'none';
		document.getElementById("pri").style.display = '';
		document.getElementById("div1").style.display = 'none';
		document.getElementById("div2").style.display = '';
		document.getElementById("rot_status2").checked = true;
	};
	
	//	神奇小按鈕
	function magic(){
		document.getElementById("starScr").value="桃園縣高鐵站/青埔特區/貴貴房價";
		document.getElementById("endScr").value="桃園縣中央大學/四中之首/周遭便當難吃";
		document.getElementById("rotScr").innerText="中大在台灣為國立中央大學的簡稱，然而鄉民對此非常不滿，認為台灣以「中」字開頭的大學很多，尤其還有四大四「中」、俗稱中字輩的四所等級差不多的中字開頭大學，但卻是只有其中一所能簡稱中大。";
		document.getElementById("rotName").value="伙食好！住得好！各個學習情緒高！";
		document.getElementById("downStar").selected="true";
		document.getElementById("downEnd").selected="true";
	}
	</script>
	
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAi9yisZZ5stHkAAQCoqbM0DslJ1VvGe7c&callback=initMap"	async defer></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>