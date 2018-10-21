//$('#region')[0].fancySelect({
//    showText: true,
//    showImages: false
//});
//$('#level')[0].fancySelect({
//    showText: true,
//    showImages: false
//});

$(".searchBar").selectmenu();

$("#rot_loc").selectmenu().selectmenu( "menuWidget" ).addClass( "overflow" );

$(function () {
    $("#dateStart").datepicker({
        onSelect :function(dateText,inst){
            $("#dateEnd").datepicker( "option", "minDate", new Date(dateText));
            $("#dateEnd").datepicker( "setDate", new Date(dateText));
        },
        minDate: 0,
        dateFormat:"yy-mm-dd",
//        maxDate: "+1M +10D"
    });
    $("#dateEnd").datepicker({
        minDate: 0,
        dateFormat:"yy-mm-dd",
//        maxDate: "+1M +10D"
    });
});

$(function(){
    console.log($(window).height());
    $(".scrollBar").height($(window).height()-120);
});

	