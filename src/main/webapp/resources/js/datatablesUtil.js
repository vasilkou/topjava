function makeEditable() {
    $('.delete').click(function () {
        deleteRow($(this).closest('tr').attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $('#filterForm').submit(function () {
        filter();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

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

function refreshTable(data) {
    datatableApi.clear();
    $.each(data, function (key, item) {
        datatableApi.row.add(item);
    });
    datatableApi.draw();
}

function add() {
    $('#id').val(null);
    $('#editRow').modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function save() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
