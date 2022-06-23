const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('gra')];
const infoBar = document.getElementById('add-info');
const table = document.getElementById('table');
let i = 300;
const table2 = document.getElementById('table2');
const icon = document.getElementById('icon');
const iconTable = document.getElementById('icon-table-change');
const secondButton=document.getElementById('second-button');
const selects=document.getElementById('select-wrapper');
table.style.top=150;

rows.forEach(r => {
    setTimeout(() => {
        r.style.opacity=1;
    },i)
    i+=300;
});

function addClass() {
    let width=$(window).width();
    console.log(width)
        if(width>1200) {
            selects.style.left = '70%';
            table2.style.left = '30%';
        }else {
            document.getElementById('above-table-buttons').style.left=0;
        }
}

function tbChange() {
    if (icon.classList.contains('fa-eye')) {
        table.style.left="-150%";

        table2.style.left="50%";
        icon.classList.remove("fa-eye");
        icon.classList.add('fa-pen');
        secondButton.classList.remove('second-button');
        setTimeout(function () {
            secondButton.style.opacity=1;
        },1000);
    } else {
        icon.classList.add("fa-eye");
        icon.classList.remove('fa-pen');
        selects.style.left='160%';
        table2.style.left="150%";
        table.style.left="50%";
        secondButton.classList.remove('second-button');
        setTimeout(function () {
            secondButton.classList.add('second-button');

        },1000);
        secondButton.style.opacity=0;
    }
}