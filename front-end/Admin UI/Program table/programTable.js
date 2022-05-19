const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('gra')];
var checkbox=document.getElementById('change-box');
var infoBar=document.getElementById('add-info');
var table=document.getElementById('table');
var table2=document.getElementById('table2');
var switchIcon=document.getElementById('switch-icon');
var i=300;

table.style.top=150;

rows.forEach(r => {
    setTimeout(() => {
        r.style.opacity=1;
    },i)
    i+=300;
});


checkbox.addEventListener('click', function() {
    if(table.id==='table') {
        table.id='table2';
        table2.id='table';
        switchIcon.classList.remove('fa-eye');
        switchIcon.classList.add('fa-pen');
    }else {
        table2.id='table2';
        table.id='table';
        switchIcon.classList.remove('fa-pen');
        switchIcon.classList.add('fa-eye');
    }
    document.getElementById('table').style.top=150;
});