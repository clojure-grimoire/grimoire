// @license magnet:?xt=urn:btih:366d4f9f43fd2b7a9634f299d3b78800ac50f681&dn=epl-1.0.txt
(function(document) {
    var toggle = document.querySelector('.sidebar-toggle');
    var sidebar = document.querySelector('#sidebar');
    var checkbox = document.querySelector('#sidebar-checkbox');

    document.addEventListener('click', function(e) {
        var target = e.target;

        if(!checkbox.checked ||
           sidebar.contains(target) ||
           (target === checkbox || target === toggle)) return;

        checkbox.checked = false;
    }, false);
})(document);
// @license-end
