
$(document).ready(function () {
    $('#registerForm').hide();
    $('#dropdownMenuButton').click(function (e) {
        $('#registerForm').show();
    });
    
    $('#dropBack').click(function (e) {
        $('#registerForm').hide();
    });
    var errorMsg = $('span[id*="errorMsg"]').text();
    if(!(errorMsg === ""))
        notify(errorMsg, "warning");
    
   
});

function notify(msg, alertType) {

    $.notify({
        message: msg

    }, {
        element: 'body',
        position: null,
        type: alertType,
        allow_dismiss: true,
        newest_on_top: true,
        placement: {
            from: "top",
            align: "right"
        },
        offset: 20,
        spacing: 10,
        z_index: 1031,
        delay: 5000,
        timer: 1000,
        animate: {
            enter: 'animated fadeInDown',
            exit: 'animated fadeOutUp'
        }
    });

}

