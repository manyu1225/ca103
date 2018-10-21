<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.forPos.model.*"%>
<%@ page import="com.mem.model.*"%>

					<!--///////////////// 此處為文章新增的頁面////////////// -->
								


<%
	Forum_post_VO forPosVO = (Forum_post_VO) request.getAttribute("forPosVO");
	// if(forPos_VO == null){
	// forPos_VO = new Forum_post_VO();
	// }
	
%>


<%
MemVO memVO = (MemVO)session.getAttribute("memVO");
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

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />

<!-- css from local path -->

<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src='https://cloud.tinymce.com/stable/tinymce.min.js'></script>
<script src="https://cloud.tinymce.com/stable/tinymce.min.js?apiKey=vw4752nfcq0dfb8ck1o1io0wdmaika6lz8kmzqhlvg0izv80"></script>

<link rel="stylesheet" type="text/css" href="../css/css.css">





<style type="text/css">


textarea.form-control {
	width: 100%;
	height: 500px;
}

.header {
	font-family: 微軟正黑體;
}


.hidden{display:none};





/* ////////////////濾淨功能///////////////// */

#imgPreview {
  border: 2px solid grey;
  border-radius: 10px;
  padding: 5px;
  width: 65%;
  max-width: 600px;
  float: left;
  margin: 20px;
}

#imgPreview img {
  border-radius: 10px;
  width: 100%;
}

/* Styles for sliders*/

.sliders {
  float: left;
  border: 2px solid grey;
  border-radius: 10px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
}

.sliders p {
  margin: 18px 0;
  vertical-align: middle;
}

.sliders label {
  display: inline-block;
  margin: 10px 0 0 0;
  width: 100px;
  font-size: 16px;
  color: #22313F;
  text-align: left;
  vertical-align: middle;
}

.sliders input {
  position: relative;
  margin: 10px 20px 0 10px;
  vertical-align: middle;
}

input[type=range] {
  /*removes default webkit styles*/
  -webkit-appearance: none;
  /*fix for FF unable to apply focus style bug */
  border-radius: 5px;
  /*required for proper track sizing in FF*/
  width: 150px;
}

input[type=range]::-webkit-slider-runnable-track {
  width: 300px;
  height: 7px;
  background: #ABB7B7;
  border: none;
  border-radius: 3px;
}

input[type=range]::-webkit-slider-thumb {
  -webkit-appearance: none;
  border: none;
  height: 20px;
  width: 20px;
  border-radius: 50%;
  background: #4B77BE;
  margin-top: -6px;
  vertical-align: middle;
}
input[type=range]:focus {
  outline: none;
}

input[type=range]:hover {
	cursor: pointer;
}


#reset {
  display: inline-block;
  height: 40px;
  width: 100px;
  background-color: transparent;
  padding: 0px;
  border: 4px solid #b3b3b1;
  border-radius: 10px;
  box-shadow: none;
  cursor: pointer;
  outline: none;
  text-align: center;
  font-size: 20px;
  font-family: monospace;
  font-weight: 100;
  color: #000;
  margin: 0 0 10px 0;
}

.p {
  clear: both;
  text-align: center;
  padding:  40px 0 40px;
}
















</style>


<title>討論區貼文新增</title>

</head>



<body style="background-color:#e6e6e6">

	<!-- navbar開始 -->

<%@ include file="/sources/file/Home/NavBar.file" %>







	<div class="container">
		<div class="row">
			<div class="col-10 mx-auto mt-5 ">


				<h1 class="header">文章新增區</h1>


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



	<form method="post" id="target" action="<%=request.getContextPath()%>/forPos/forPos.do" enctype="multipart/form-data" >

		<div class="container-fluid">
			<div class="row">
			
			<div class="col-md-3 col-12">
				<%@ include file="/sources/file/Forum/forPosSidebar.file" %>
			</div>
			
			
				<div class="col-md-9 col-12 mx-auto ">

					<div class="form-group">
						 <input class="form-control" type="text"
							name="theme" size="50" placeholder="請輸入標題"
							value="<%=(forPosVO == null) ? "" : forPosVO.getForPost_theme()%>" />
					</div>

					<!-- 插入附件    upload-->

<!-- 					<input type="button" class="btn btn-secondary" name="pic" -->
<!-- 						value="插入附件"> <br> <br> -->







					<!--選擇文章類別 -->

					<jsp:useBean id="forClassSvc" scope="page" class="com.forPos_class.model.Forum_class_Service" />



					<label>請選擇全站類別</label> <select size="1" name="class_ID">

						<c:forEach var="forum_class_VO"
							items="${forClassSvc.all_ForClass}">
							<option value="${forum_class_VO.forClass_ID}"
								${(forPosVO.forClass_ID == forum_class_VO.forClass_ID)? 'selected':'' }>${forum_class_VO.forClass_name}
						</c:forEach>

					</select> <br>



					<!-- 選擇文章狀態 -->



					<label>請選擇文章狀態</label> <select size="1" name="state">

						<option value=0 ${(forPosVO.forPost_state == 0)? 'selected':''}>---請選擇---</option>
						<option value=1 ${(forPosVO.forPost_state == 1)? 'selected':''}>公開</option>
						<option value=2 ${(forPosVO.forPost_state == 2)? 'selected':''}>不公開</option>
<%-- 						<option value=3 ${(forPosVO.forPost_state == 3)? 'selected':''}>因檢舉被封鎖</option> --%>

					</select> <br> <br>











					<div class="row">



						<div class="form-group">
<!-- 							<label for="user-message" class=" control-label">文章內容 </label> -->
							<div class="col-lg-12 mx-auto">
								<%--        <textarea name="content" id="user-message" class="form-control" cols="20" rows="10"  placeholder="寫點什麼吧"><%=(forPosVO == null) ? "" : forPosVO.getForPost_content()%></textarea> --%>
								<textarea id="mytextarea" name="content" placeholder="寫點什麼吧"><%=(forPosVO == null) ? "" : forPosVO.getForPost_content()%></textarea>
 								<input name="image" type="file" id="upload" class="hidden" onchange="">


<!-- 								<br> 請輸入您會員ID: <input type="text" name="mem_ID"> -->



					
						<hr color="grey">
						
						<!--Controls for CSS filters via range input-->
						<div class="sliders">
						    <p>
						      <label for="gs">Grayscale</label>
						      <input id="gs" name="gs" type="range" min="0" max="100" value="0">
						    </p>
						
						    <p>
						      <label for="blur">Blur</label>
						      <input id="blur" name="blur" type="range" min="0" max="10" value="0">
						    </p>
						
						    <p>
						      <label for="br">Exposure</label>
						      <input id="br" name="br" type="range" min="0" max="200" value="100">
						    </p>
						
						    <p>
						      <label for="ct">Contrast</label>
						      <input id="ct" name="ct" type="range" min="0" max="200" value="100">
						    </p>
						
						    <p>
						      <label for="huer">Hue Rotate</label>
						      <input id="huer" name="huer" type="range" min="0" max="360" value="0">
						    </p>
						
						    <p>
						      <label for="opacity">Opacity</label>
						      <input id="opacity" name="opacity" type="range" min="0" max="100" value="100">
						    </p>
						
						    <p>
						      <label for="invert">Invert</label>
						      <input id="invert" name="invert" type="range" min="0" max="100" value="0">
						    </p>
						
						    <p>
						      <label for="saturate">Saturate</label>
						      <input id="saturate" name="saturate" type="range" min="0" max="500" value="100">
						    </p>
						
						    <p>
						      <label for="sepia">Sepia</label>
						      <input id="sepia" name="sepia" type="range" min="0" max="100" value="0">
						    </p>
						
						    <input type="reset" form="imageEditor" id="reset" value="Reset" />
						
						</div>	

<!--container where image will be loaded-->
					<div class="row d-flex justify-content-center">
						<label for="inputCity">封面圖片</label>
					</div>
					<div class="row d-flex justify-content-center">
						<br> <input type="file"  onchange="PreviewImage(this)" name="forPostPhoto">
					</div>
					<div class="row">
						<div id="uploadimg ">
							<div name="act_pic" id="imgPreview" class="center ml-5"  style="width: 399px; height: 300px;">
							
							</div>
						</div>
					</div>


<br>
	
							</div>
						</div>
					</div>
					
								<input type="hidden" name="time">
								<input class="btn btn-primary" id="btn1" type="submit" value="發表文章">
								<input type="hidden" name="action" value="insert">
								<input type="hidden" name="mem_id" value="${memVO.mem_id}">
								<%
									System.out.println("action呼叫成功");
								%>
				</div>
			</div>
		</div>

	</form>


<%-- <form action="<%=request.getContextPath()%>/forPos/forPos.do"> --%>

<!-- 				<input type="hidden" name="addhashTag" > -->
<!-- 				<input type="text" name="tag" > -->
<!-- 				<button class="btn" type="submit"></button> -->
				
<!-- </form> -->


















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
		src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.js"></script>



	<!--     <script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.js"></script> -->



  <script type="text/javascript">
    	$(document).ready(function() {
  tinymce.init({
    selector: "textarea",
    theme: "modern",
    width:900,
    height:400,
    paste_data_images: true,
    plugins: [
      "advlist autolink lists link image charmap print preview hr anchor pagebreak",
      "searchreplace wordcount visualblocks visualchars code fullscreen",
      "insertdatetime media nonbreaking save table contextmenu directionality",
      "emoticons template paste textcolor colorpicker textpattern"
    ],
    toolbar1: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
    toolbar2: "print preview media | forecolor backcolor emoticons",
    image_advtab: true,
    file_picker_callback: function(callback, value, meta) {
      if (meta.filetype == 'image') {
        $('#upload').trigger('click');
        $('#upload').on('change', function() {
          var file = this.files[0];
          var reader = new FileReader();
          reader.onload = function(e) {
            callback(e.target.result, {
              alt: ''
            });
          };
          reader.readAsDataURL(file);
        });
      }
    },
    templates: [{
      title: 'Test template 1',
      content: 'Test 1'
    }, {
      title: 'Test template 2',
      content: 'Test 2'
    }]
  });
}
    	

    	);
    	
    </script>
    
    
<!-- *******************圖片預覽*********************** -->
    
    <script type="text/javascript">
		function PreviewImage(imgFile) {
			var pattern = /(\.*.jpg$)|(\.*.png$)|(\.*.jpeg$)|(\.*.gif$)|(\.*.bmp$)/;
			if (!pattern.test(imgFile.value)) {
				alert("只支援jpg/jpeg/png/gif/bmp之格式檔案");
				imgFile.focus();
			} else {
				var path;
				if (document.all) { // IE
					imgFile.select();
					imgFile.blur();
					path = document.selection.createRange().text;
					document.getElementById("imgPreview").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\""
							+ path + "\")";// 濾鏡
				} else { // FF 或 Chrome 等
					path = URL.createObjectURL(imgFile.files[0]);
					document.getElementById("imgPreview").innerHTML = "<img src='"+ path +"'  width='429' height='350'/>";
				}
			}
		}
	</script>
	
	
<!-- 	/////////////////////////濾鏡功能/////////////////////////////////// -->
	
	  <script type="text/javascript">
    	
	//on click of go(submit) button, addImage() will be called
$("#go").click(addImage);

//on pressing return, addImage() will be called
$("#urlBox").submit(addImage);


// editing image via css properties
function editImage() {
	var gs = $("#gs").val(); // grayscale
	var blur = $("#blur").val(); // blur
	var br = $("#br").val(); // brightness
	var ct = $("#ct").val(); // contrast
	var huer = $("#huer").val(); //hue-rotate
	var opacity = $("#opacity").val(); //opacity
	var invert = $("#invert").val(); //invert
	var saturate = $("#saturate").val(); //saturate
	var sepia = $("#sepia").val(); //sepia

	$("#imgPreview img").css(
    "filter", 'grayscale(' + gs+
    '%) blur(' + blur +
    'px) brightness(' + br +
    '%) contrast(' + ct +
    '%) hue-rotate(' + huer +
    'deg) opacity(' + opacity +
    '%) invert(' + invert +
    '%) saturate(' + saturate +
    '%) sepia(' + sepia + '%)'
  );

	$("#imgPreview img").css(
    "-webkit-filter", 'grayscale(' + gs+
    '%) blur(' + blur +
    'px) brightness(' + br +
    '%) contrast(' + ct +
    '%) hue-rotate(' + huer +
    'deg) opacity(' + opacity +
    '%) invert(' + invert +
    '%) saturate(' + saturate +
    '%) sepia(' + sepia + '%)'
  );
}

//When sliders change image will be updated via editImage() function
$("input[type=range]").change(editImage).mousemove(editImage);

// Reset sliders back to their original values on press of 'reset'
$('#imageEditor').on('reset', function () {
	setTimeout(function() {
		editImage();
	}, 0);
});

// adding an image via url box
function addImage(e) {
	var imgUrl = $("#imgUrl").val();
	if (imgUrl.length) {
		$("#imgPreview img").attr("src", imgUrl);
	}
	e.preventDefault();	
}

    </script>
	

	

</body>

</html>

