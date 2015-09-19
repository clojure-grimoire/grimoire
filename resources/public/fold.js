// @license magnet:?xt=urn:btih:4c6a2ad0018cd461e9b0fc44e1b340d2c1828b22&dn=epl-1.0.txt
(function () {
    $(".section .autofold.prefold").hide();
    $(".section").find(".heading").click(
        function(){
            p=$(this);
            while(!p.hasClass("section"))
                p=p.parent();

            p.children(".autofold").slideToggle("slow");

            v=p.find("span.unhide");
            u=p.find("span.hide");

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
