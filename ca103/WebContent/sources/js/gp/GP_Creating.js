//燈箱
$(function(){
    var preSection;
    $("#routeBtn").click(function(){
        $("#lightBox")[0].style.display="block";
    });
    
//    $(".routeSection").click(function(e){
//        if(preSection != undefined){
//            preSection.toggleClass("selectedROUTE");
//        }
//        preSection = $(e.target);
//        $(e.target).toggleClass("selectedROUTE");
//    });
    $(".section").click(function(){
        if(preSection != undefined){
            preSection.toggleClass("selectedROUTE");
        }
        preSection = $(this);
        $(this).toggleClass("selectedROUTE");
    })
    
    $("#submit").click(function(){
        $("#ROUTE")[0].value = preSection[0].id;
        $("#routeName")[0].value = $("#" + preSection[0].id).attr("rotName");
//        console.log($("#ROUTE")[0].value);
        $("#lightBox")[0].style.display="none";
    })
    $("#cancel").click(function(){
        $("#lightBox")[0].style.display="none";
    });
//    $(".rotSubmit")[0].style.left = "calc(50% - " + $('.rotSubmit').width()/2 + "px)";
        
});

$(function(){
	
	$jUI("#GP_DATE").datepicker({
		onSelect :function(dateText,inst){
            $jUI("#SIGN_UP_DD").datepicker( "option", "maxDate", new Date(dateText));
        },
        minDate: "+1D",
        dateFormat:"yy-mm-dd",
//        maxDate: "+1M +10D"
    });
	
	$jUI("#SIGN_UP_DD").datepicker({
	    minDate: 0,
	    dateFormat:"yy-mm-dd",
	//    maxDate: "+1M +10D"
	});
});





//自動改變textarea的高，且預設為800px
$(function(){

	jQuery(function($) {  
		$("#GP_CONTENT_INPUT").css("overflow","hidden").bind("keydown keyup", function(){  
			$(this).height('500px').height($(this).prop("scrollHeight")+"px");  
			$("#GP_CONTENT_VIEW").height('800px').height($("#GP_CONTENT_VIEW").prop("scrollHeight")+"px");
		}).keydown();
	});  
	

});

//預覽圖片處理
$(function(){
	function readURL(input) {

        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function(e) {
                $('#imgPreview').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }
    $("#GP_PHOTO").change(function() {
      readURL(this);
    });
});

$(function(){
	$("#imgInsertReplace").click(function(){
		$('#imgWidth').val(500);
		$('#imgHeight').val(300);
		$('#exampleModal').modal('show');
	});
	$("#dimensionConfirm").click(function(){
		$('#exampleModal').modal('hide');
		$('#imgInsert').click();
	})
	
	
//	var arrMap = new Map();
//	var arr = new Array();
	
	
	$("#GP_CONTENT_INPUT").keyup(function(){
		
		var temp = $("#GP_CONTENT_INPUT").val().replace(/\n/g,'\n');
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
			
//				console.log(temp.indexOf('>',pos));
			
		}
		
//			console.log("test：",arr.toString());
		console.log(photoNum);
		$("#GP_CONTENT_VIEW").empty();
		//要用JQuery的html包起來的=============================
		document.getElementById('GP_CONTENT').value = "";
		for(i in arrDiv){	
			
			if(photoNum[i-1] !=null){
				$('#GP_CONTENT_VIEW').append('<img src=\"' + arrMap[photoNum[i-1]] + '\" style=\"width:' + imgWidth[i-1] + 'px;height:' + imgHeight[i-1] + 'px;display:block; margin:auto;\">');
//				console.log(arrMap[photoNum[i-1]]);
				
//				console.log(arr[i-1]);
				//要用JQuery的html包起來的=============================
				document.getElementById('GP_CONTENT').value += '<img src=\'' + arrMap[photoNum[i-1]] + '\' style=\'width:' + imgWidth[i-1] + 'px;height:' + imgHeight[i-1] + 'px;display:block; margin:auto;\'>';
			}
			$('#GP_CONTENT_VIEW').append("<div id='d" + i + "'>");
			document.getElementById('d' + i).innerText = arrDiv[i];
			
			//要用JQuery的html包起來的=============================
			document.getElementById('GP_CONTENT').value += "<div id='d" + i + "'>" + arrDiv[i] + "</div>";
			
		}
		
		$('#GP_CONTENT').val($('#GP_CONTENT').val().replace(/\n/g,'</br>'));
	});
	
	function readURL(input) {

        if (input.files && input.files[0]) {
            var reader = new FileReader();
			
            reader.onload = function(e) {
				document.getElementById('GP_CONTENT_INPUT').value += '\n<img' + arrMap.length + '(' + $('#imgWidth').val() + 'x' + $('#imgHeight').val() + ')>\n';
//				arrMap.set($('#imgInsert').val(),e.target.result);
				arrMap.push(e.target.result);
//				console.log(arrMap.toString());
//				arr.push($('#imgInsert').val());
				$("#GP_CONTENT_INPUT").keyup();//使圖片能夠載入
            }
            reader.readAsDataURL(input.files[0]);
        }
    }
    $("#imgInsert").change(function() {
      readURL(this);
      $("#insertedImgName").val($('#imgInsert').val());
    });
    
   
	$("#creSubmit").click(function(){
		$('#arrMap').val(arrMap.toString());		
		$('#GPcreForm').submit();
	});

    
});







