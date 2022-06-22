const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('gra')];
const infoBar = document.getElementById('add-info');
const table = document.getElementById('table');
let i = 300;
const table2 = document.getElementById('table2');
const icon = document.getElementById('icon');
const iconTable = document.getElementById('icon-table-change');
const secondButton=document.getElementById('second-button');
table.style.top=150;

rows.forEach(r => {
    setTimeout(() => {
        r.style.opacity=1;
    },i)
    i+=300;
});

function changeEditTables() {

}

function tbChange() {
    if (icon.classList.contains('fa-eye')) {
        table.style.left="-150%";

        table2.style.left="50%";
        icon.classList.remove("fa-eye");
        icon.classList.add('fa-pen');
        table2.style.width = 700;
        secondButton.classList.remove('second-button');
        setTimeout(function () {
            secondButton.style.opacity=1;
        },1000);
    } else {
        icon.classList.add("fa-eye");
        icon.classList.remove('fa-pen');
        table2.style.left="150%";
        table.style.left="50%";
        secondButton.classList.remove('second-button');
        setTimeout(function () {
            secondButton.classList.add('second-button');

        },1000);
        secondButton.style.opacity=0;
    }
}