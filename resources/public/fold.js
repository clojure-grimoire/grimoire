// @license magnet:?xt=urn:btih:4c6a2ad0018cd461e9b0fc44e1b340d2c1828b22&dn=epl-1.0.txt
(function () {
    $(".section .autofold.prefold").hide();
    $(".section .heading").click(
	function(){
	    p=$(this).parent();
	    while(!p.hasClass("section"))
		p=p.parent();

	    p.find(".autofold").toggle("slow");

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
