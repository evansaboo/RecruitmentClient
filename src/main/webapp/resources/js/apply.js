/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    var msgToUser = $('span[id*="msgToUser"]').text().split("##");
    var msgType = msgToUser[1];
    msgToUser = msgToUser[0];
    if (!(msgToUser === ""))
        notify(msgToUser, msgType);
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