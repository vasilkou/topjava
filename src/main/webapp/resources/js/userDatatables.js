var ajaxUrl = 'ajax/admin/users/';
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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
                "asc"
            ]
        ]
    });
    makeEditable();
});

function updateTable() {
    $.get(ajaxUrl, function (data) {
        refreshTable(data);
    });
}

function enable(checkbox) {
    var enabled = checkbox.is(':checked');
    $.ajax({
        type: "POST",
        url: ajaxUrl + checkbox.closest('tr').attr("id"),
        data: {'enabled': enabled},
        success: function () {
            checkbox.closest('tr').toggleClass('grayout');
            successNoty(enabled ? 'User enabled' : 'User disabled');
        }
    });
}