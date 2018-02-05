function register() {
    var reg = document.getElementsById("j_idt6");
    var msg = document.createElement("span");
    msg.setAttribute("id", "testt");
    msg.setAttribute("class", "test");
    msg.innerHTML = "Hellohello";
    reg.appendChild(msg);
}