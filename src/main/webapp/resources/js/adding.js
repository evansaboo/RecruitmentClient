
$(document).ready(function () {
    $('#registerForm').hide();
    $('#dropdownMenuButton').click(function (e) {
        $('#registerForm').show();
    });
    
    $('#dropBack').click(function (e) {
        $('#registerForm').hide();
    });
    var msgToUser = $('span[id*="errorMsg"]').text().split("##");
    var msgType = msgToUser[1];
    msgToUser = msgToUser[0];
    if(!(msgToUser === ""))
        notify(msgToUser, msgType);
});


