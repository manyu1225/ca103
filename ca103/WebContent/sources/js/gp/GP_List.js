$(".searchBar").selectmenu();

$(function () {
    $("#dateStart").datepicker({
        onSelect :function(dateText,inst){
            $("#dateEnd").datepicker( "option", "minDate", new Date(dateText));
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
