
function showRegForm() {
    document.getElementById("regger").style.display = '';
}



$(document).ready(function () {
    $('#registerForm').hide();

    $('#dropdownMenuButton').click(function (e) {
        $('#registerForm').show();
        $(this).hide();
    });
});
