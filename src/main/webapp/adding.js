

$(document).ready(function () {
    $('#registerForm').hide();

    $('#dropdownMenuButton').click(function (e) {
        $('#registerForm').show();
    });
    
    $('#dropBack').click(function (e) {
        $('#registerForm').hide();
    });
});
