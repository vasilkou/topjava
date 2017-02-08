var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });

    $('#filterForm').submit(function () {
        filter();
        return false;
    });

    makeEditable();
});

var filtered = false;

function filter() {
    filtered = true;
    updateTable();
}

function clearFilter() {
    filtered = false;
    $('#filterForm').trigger('reset');
    updateTable();
}

function updateTable() {
    if (filtered) {
        var form = $('#filterForm');
        $.ajax({
            type: "POST",
            url: ajaxUrl + "filter",
            data: form.serialize(),
            success: function (data) {
                refreshTable(data);
            }
        });
    } else {
        $.get(ajaxUrl, function (data) {
            refreshTable(data);
        });
    }
}
