// @license magnet:?xt=urn:btih:366d4f9f43fd2b7a9634f299d3b78800ac50f681&dn=epl-1.0.txt
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
// @license-end
