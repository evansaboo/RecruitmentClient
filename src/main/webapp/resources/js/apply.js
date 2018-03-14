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
