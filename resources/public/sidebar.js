// @license magnet:?xt=urn:btih:4c6a2ad0018cd461e9b0fc44e1b340d2c1828b22&dn=epl-1.0.txt
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
