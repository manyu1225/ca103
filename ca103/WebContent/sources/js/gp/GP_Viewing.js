//浮動按鈕
$(function(){
    var flag300 = false;
   $(document).scroll(function(){

       if($(document).scrollTop() > 400 && !flag300){
           $("#btnRow").toggleClass("btnRowFixed");
           flag300 = true;
       }else if($(document).scrollTop() < 400 && flag300){
           $("#btnRow").toggleClass("btnRowFixed");
           flag300 = false;
       }
       
   });
   
});

//更改資訊介面
$(function(){
	//set按鈕
	$(".btnSet").click(function(){
//		jQuery(function($) {  
//		    $("#GP_CONTENT").css("overflow","hidden").bind("keydown keyup", function(){  
//		          $(this).height('300px').height($(this).prop("scrollHeight")+"px");  
//		    }).keydown();  
//		});  
		$("#updatelightBox")[0].style.display="block";
	});
	//submit按鈕
	$("#updateSubmit").click(function(){
		$("#formUpdate").submit();
	});
	//取消按鈕
	$("#updateCancel").click(function(){
		$("#updatelightBox")[0].style.display="none";
	});
});

//更改資訊日期設定
$(function(){
	
	$jUI("#GP_DATE").datepicker({
		
        minDate: 0,
        dateFormat:"yy-mm-dd",
//        maxDate: "+1M +10D"
    });
	
	$jUI("#SIGN_UP_DD").datepicker({
	    minDate: 0,
	    dateFormat:"yy-mm-dd",
	    maxDate: $("#GP_DATE")[0].value
	});
});


//留言區控制
$(function(){
//		送出留言表單
		$(".cmtInputSubmit").click(function(){
			$(this).parent().submit();
		});
//		送出留言回應表單
		$(".repInputSubmit").click(function(){
			$(this).parent().submit();
		});
		//按進分頁就執行
		$("#pills-2-tab").on('shown.bs.tab', function (e) {
			//  自動調整textArea高度
			  jQuery(function($) {  
			      $("textarea.AutoHeight").css("overflow","hidden").bind("keydown keyup", function(){  
			          $(this).height('0px').height($(this).prop("scrollHeight")+"px");  
			      }).keydown();  
			  });  
			//  如果comment太長就進入
			  $(".commentTextBox").each(function(){
			      if ($(this).height() > 50) {
			          var commentTextBox = $(this);
			          var expandComment = $(this).next("div").find(".expandComment");
			          var shinkComment = $(this).find(".shinkComment");
			          expandComment.css("display", "inline");
			          $(this).removeClass("visible");
			          shinkComment.css("display", "inline ");
			          shinkComment.click(function() {
			              expandComment.css("display", "inline");
			              commentTextBox.removeClass("visible");
			          });
			          expandComment.click(function() {
			              expandComment.css("display", "none");
			              commentTextBox.addClass("visible");
			          })
			      }
			  });
			  
		});
  
  
  
//  查看回應內容
  $(".replyCheck").click(function(){
      $(this).parents(".msgBox").children(".replyRow").css("display","flex");
      $(this).css("display","none");
  });
  

//	使顯現出來的留言換行
	$(function(){
		$(".commentText").each(function(){
			var temp = $(this).text().replace(/\n/g,'<br/>');
			$(this).html(temp);
		});
	})
	
	//	使顯現出來的回應換行
	$(function(){
		$(".replyText").each(function(){
			var temp = $(this).text().replace(/\n/g,'<br/>');
			$(this).html(temp);
		});
	})
  
  
//  顯示回覆留言input,並設置高度正常
  $(".replyBtn").click(function(){
      $(this).parents(".msgBox").children(".rowRepInput").css("display", "flex");
      $(this).parents(".msgBox").find(".repInput").css("height","30px");
  });
  
});

//刪除觸發事件
$(function(){
	$(".cmtDelete").each(function(){
		$(this).click(function(){
			$("#msgdelSubmit").attr("href",$("#msgdelSubmit").attr("href") + $(this).attr('deleteString'));
		});
	});
	
	$(".repDelete").each(function(){
		$(this).click(function(){
			$("#msgdelSubmit").attr("href",$("#msgdelSubmit").attr("href") + $(this).attr('deleteString'));
		});
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


//編輯揪團內容
$(function(){
	$("#imgInsertReplace").click(function(){
		$('#imgWidth').val(500);
		$('#imgHeight').val(300);
		$('#setImageModal').modal('show');
	});
	$("#dimensionConfirm").click(function(){
		$('#setImageModal').modal('hide');
		$('#imgInsert').click();
	})
	
	
	
	
//	var arrMap = new Array();
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
//			console.log(arr);
		console.log(photoNum);
		$("#GP_CONTENT_VIEW").empty();
		//要用JQuery的html包起來的=============================
		document.getElementById('GP_CONTENT').value = "";
		for(i in arrDiv){	
			
			if(photoNum[i-1] !=null){
				$('#GP_CONTENT_VIEW').append('<img src=\"' + arrMap[photoNum[i-1]] + '\" style=\"width:' + imgWidth[i-1] + 'px;height:' + imgHeight[i-1] + 'px;display:block; margin:auto;\">');
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
				arrMap.push(e.target.result);
				
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
    
    $('#contentSubmit').click(function(){
    	$('#arrMap').val(arrMap.toString());
    	$('#GPContentForm').submit();
    });
    
});
//額外加的
$(function(){

	$('#contentEditBtn').click(function(){
		
		$('#GP_CONTENT_Show').empty();
		$('#contentUpdateBlock').css('display','block');
		jQuery(function($) {  
			$("#GP_CONTENT_INPUT").css("overflow","hidden").bind("keydown keyup", function(){  
				$(this).height('500px').height($(this).prop("scrollHeight")+"px");  
				$("#GP_CONTENT_VIEW").height('800px').height($("#GP_CONTENT_VIEW").prop("scrollHeight")+"px");
				$('#ContentBtnRow').css("top",$("#GP_CONTENT_INPUT").height()+20);
			}).keydown();
		});  
		
		
		$("#GP_CONTENT_INPUT").keyup()
	});
	
});



