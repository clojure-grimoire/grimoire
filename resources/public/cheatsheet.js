$('.grid').masonry({
    itemSelector: '.grid-item',
    columnWidth: '.grid-sizer'
});

$('#search').autocomplete({
    minLength: 1,
    appendTo: '#options',
    source: function (request, response) {
        $.getJSON("/search/autocomplete", {
            query: request.term
        }, function (data) {
            body = data['body'];
            if (data['result'] == 'success') {
                response(body);
            } else {
                response({});
            }
        });
    },
    select: function (event, ui) {
        $('#search').val(ui.item.label);
        window.location.href = ui.item.url;
    }
});
