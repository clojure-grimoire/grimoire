function closeFunding() {
    $('#funding').slideUp('fast', function() {
        $('#funding').remove();
    });
}

function doReset() {
    localStorage.numVisits = 9;
    localStorage.hasFunded = null;
}

function setFunded() {
    localStorage.hasFunded = true;
}

(function() {
    function getVisits() {
        localStorage.numVisits = 1 + parseInt(localStorage.numVisits || 0);
        return parseInt(localStorage.numVisits);
    }

    function hasFunded() {
        localStorage.hasFunded = (localStorage.hasFunded || false);
        return localStorage.hasFunded === 'true';
    }

    visits = getVisits();
    if (hasFunded() || !(visits > 9 && (visits % 15 == 0 || Math.random() < 0.05))) {
        $('#funding').remove();
    } else {
        $('#funding').show();
    }
}());
