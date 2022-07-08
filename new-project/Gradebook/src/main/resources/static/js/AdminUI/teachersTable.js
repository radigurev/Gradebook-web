const rows = [...document.getElementsByClassName('row')];
const grades = [...document.getElementsByClassName('gra')];
var buttons = [...document.getElementsByClassName('button-a')];
var checkbox = document.getElementById('change-box');
var infoBar = document.getElementById('add-info');
var table = document.getElementById('table');
var table2 = document.getElementById('table2');
var icon=document.getElementById("icon");
var i = 300;

table.style.top = 150;

rows.forEach(r => {
    setTimeout(() => {
        r.style.opacity = 1;
    }, i)
    i += 300;
});

function tbChange() {
    if (icon.classList.contains('fa-eye')) {
        table.style.left="-150%";

        table2.style.left="50%";
        icon.classList.remove("fa-eye");
        icon.classList.add('fa-pen');

    } else {
        icon.classList.add("fa-eye");
        icon.classList.remove('fa-pen');
        table2.style.left="150%";
        table.style.left="50%";
    }
}