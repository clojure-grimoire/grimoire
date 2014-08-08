(function () {
    $(".section .autofold.prefold").hide();
    $(".section .heading").click(
	function(){
	    $(this).parent().children(".autofold").toggle("slow");
	    
 	    v=$(this).children("span.unhide");
	    u=$(this).children("span.hide");

	    v.removeClass("unhide")
		.addClass("hide")
		.html("-");

	    u.removeClass("hide")
		.addClass("unhide")
		.html("+");
	});

    $(document).on("keydown",
		   function (e) {
                       if (e.keyCode == 70 && e.ctrlKey) {
                           $('.section .heading').click(); // show all flaps
                       }
                   });
})();
