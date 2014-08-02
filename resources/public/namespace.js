(function () {
    function switcher(btn,id) {
        return (function() {
            $(id).toggle("slow",
                         function() {
                             if($(btn).html() == "+") {
                                 $(btn).html("-");
                             } else {
                                 $(btn).html("+");
                             }
                         });
        });
    }

    $("#sforms").hide();
    $("#macros").hide();
    $("#vars").hide();
    $("#fns").hide();

    $("#sff").click(switcher("#sff", "#sforms"));
    $("#mf").click(switcher("#mf", "#macros"));
    $("#vf").click(switcher("#vf", "#vars"));
    $("#ff").click(switcher("#ff", "#fns"));

    $(document).on("keydown",
		   function (e) {
                       if (e.keyCode == 70 && e.ctrlKey) {
                           $('.page div').show(); // show all flaps
			   $('.page h3 a').html("-");
                       }
                   });
})();
