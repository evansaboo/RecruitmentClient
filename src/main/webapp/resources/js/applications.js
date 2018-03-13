$(document).ready(function () {
    $('#appListingTable').DataTable({
        "language": {
            "lengthMenu": "#{msg['lengthMenu']}",
            "emptyTable": "#{msg['emptyTable']}",
            "info": "#{msg['info']}",
            "InfoEmpty": "#{msg['infoEmpty']}",
            "infoFiltered": "#{msg['infoFiltered']}",
            "loadingRecords": "#{msg['loadingRecords']}",
            "processing": "#{msg['processing']}",
            "search": "#{msg['table_search']}",
            "zeroRecords": "#{msg['zeroRecords']}",
            "paginate": {
                "first": "#{msg['paginate_first']}",
                "last": "#{msg['paginate_last']}",
                "next": "#{msg['paginate_next']}",
                "previous": "#{msg['paginate_previous']}"
            },
            "araia": {
                "sortAscending": "#{msg['aria_sortAscending']}",
                "sortDescending": "#{msg['aria_sortDescending']}"
            }
        }
    });
});