    $("#favorite").click(
        function() {
            var obj= $(this).find("i").attr("class");
            if (obj == "fa-heart fas") {
                $(this).find("i").removeClass('fas').addClass('far');
            }else if(obj == "fa-heart far"){
                $(this).find("i").removeClass('far').addClass('fas');
            }
        });
    
    
    
    $("input[name=productSaleType]").click(function(){

        console.log("切換～～")
        if($(this).val()=='直購'){
            $(".BIDDING").css('display','none');
        }else{
            $(".BIDDING").css('display','');               
        }
    });

    $('#input-id').fileinput({
		theme:"fa",
		language:"zh-TW",
		showUpload:false,
		allowedFileExtensions:["jpg","png","jpeg"],
		maxFileCount:5,
    })


        $(function () {
          $('[data-toggle="tooltip"]').tooltip()

          $('[data-toggle="popover"]').popover({
            html: true
          })
        })
    