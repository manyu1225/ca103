<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
    
    
    
    
    
    					<!--///////////////// 此處為文章被檢舉後的錯誤頁面////////////// -->
    
    
    
    
    
    
<!doctype html>
<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">


	<style type="text/css">
	
	img{
		width: 610px;
		height: 750px;
	}



</style>
<!-- Bootstrap CSS -->


<title>Hello, world!</title>
</head>
<body>
	<div class="container">
		<div class="row">

			<div class="col-md-8 col-10 mx-auto mt-5">



			<img src="<%=request.getContextPath()%>/sources/img/Forum/pageNotFound.jpg">

				<div class="row">
					<div id="div1"></div>秒後挑轉文章發佈區
				</div>

			</div>
		</div>
	</div>



	<script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	
	<script>
	//设定倒数秒数 
	var count = 5;
	//写一个方法，显示倒数秒数  数到0后跳转页面  
	function countDown(){
		//将count显示在div中
		document.getElementById("div1").innerHTML= count;
		//没执行一次，count减1
		count -= 1;
		//count=0时，跳转页面
		if(count==0){
			location.href="<%=request.getContextPath()%>/front-end/Forum/forPos/forPostDisplayList.jsp";
                        //window.location.href="index.html";
		}
		//每秒执行一次,showTime()
		setTimeout("countDown()",1000);
	}
	//执行countDown方法
	countDown();

	
	</script>
</body>
</html>