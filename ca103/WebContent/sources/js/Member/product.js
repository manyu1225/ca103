    $("#favorite").click(
        function() {
            var obj= $(this).find("i").attr("class");
            if (obj == "fa-heart fas") {
                $(this).find("i").removeClass('fas').addClass('far');
            }else if(obj == "fa-heart far"){
                $(this).find("i").removeClass('far').addClass('fas');
            }
        });