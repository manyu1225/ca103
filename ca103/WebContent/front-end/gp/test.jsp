<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>

 <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
   	
    
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

	<style>
		.inline-Block{
			display:inline-block;
		}
	
	</style>

<title>test</title>
</head>
<body>
	<div class="container-fluid">
	<div class="row">
		<label class="btn btn-info" for="" id="testBtn">插入圖片</label>
		
		<input class="btn btn-info"id="test_PHOTO" type='file'/>
	</div>
		<div class="row">
			<div class="col-md-6">
				<textarea id="testArea" rows="50" style="width:100%;"></textarea>
			</div>
			
			<div class="col-md-6">
				<div id="cloneArea">
				
				</div>
			</div>
		</div>
		
	</div>
	
			<!-- Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">設定寬和高</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		      		<div class="form-group">
		      			
		        		<input class="form-control inline-Block" id="imgWidth" style="width:20%"type="number" placeholder="px"/>
		        		<span style="width:10%;">x</span>
		        		<input class="form-control inline-Block" id="imgHeight" style="width:20%" type="number" placeholder="px"/>
		        	</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary" id="dimensionConfirm">確認</button>
		      </div>
		    </div>
		  </div>
		</div>




</body>
	<script>

	
		$("#testBtn").click(function(){
			$('#imgWidth').val(300);
			$('#imgHeight').val(300);
			$('#exampleModal').modal('show');
		});
		$("#dimensionConfirm").click(function(){
			$('#exampleModal').modal('hide');
			$('#test_PHOTO').click();
		})
		
		
		var arrMap = new Map();
		var arr = new Array();
		
		$("#testArea").keyup(function(){
			
			var temp = $("#testArea").val().replace(/\n/g,'\n');
			var pos = temp.indexOf('<img');
			
			var arrDiv = temp.split(/<img\d+\(\d+x\d+\)>/);
			var photoNum = new Array();
			var imgWidth = new Array();
			var imgHeight = new Array();
			while(pos != -1){
				var formatDimension = temp.substring(temp.indexOf('(',pos)+1,temp.indexOf(')',pos)).split('x');
				if(!isNaN(Number(temp.substring(pos+4,temp.indexOf('(',pos))))){
					if(formatDimension.length == 2 && !isNaN(Number(formatDimension[0])) && !isNaN(Number(formatDimension[1])) && formatDimension[0].length!=0 && formatDimension[1].length!=0){
						//抓寬高像素
						imgWidth.push(Number(formatDimension[0]));
						imgHeight.push(Number(formatDimension[1]));
						//將照片編號存進arr
						photoNum.push(Number(temp.substring(pos+4,temp.indexOf('(',pos))));
					}
					
				}

				pos = temp.indexOf('<img',pos+1);
				
// 				console.log(temp.indexOf('>',pos));
				
			}
// 			console.log(arr);
			console.log(photoNum);
			$("#cloneArea").empty();
			for(i in arrDiv){	
				
				if(photoNum[i-1] !=null){
					$('#cloneArea').append('<img src=\"' + arrMap.get(arr[photoNum[i-1]]) + '\" style=\"width:' + imgWidth[i-1] + 'px;height:' + imgHeight[i-1] + 'px;\">');
					console.log(arr[i-1]);
				}
				$('#cloneArea').append("<div id='d" + i + "'>");
				document.getElementById('d' + i).innerText = arrDiv[i];
				
			}
			
		});
		
		function readURL(input) {

	        if (input.files && input.files[0]) {
	            var reader = new FileReader();
				
	            reader.onload = function(e) {
	            	
					document.getElementById('testArea').value += '\n<img' + arr.length + '(' + $('#imgWidth').val() + 'x' + $('#imgHeight').val() + ')>\n';
					arrMap.set($('#test_PHOTO').val(),e.target.result);
					
					arr.push($('#test_PHOTO').val());
					$("#testArea").keyup();//使圖片能夠載入
	            }
	            
	            reader.readAsDataURL(input.files[0]);
	        }
	    }
	    $("#test_PHOTO").change(function() {
	      readURL(this);
	    });
	
	</script>

</html>