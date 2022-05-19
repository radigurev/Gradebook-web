const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('gra')];
var buttons = [...document.getElementsByClassName('button')];
var infoBar=document.getElementById('add-info');
var table=document.getElementById('table');
var i=300;

table.style.top=150;

rows.forEach(r => {
    setTimeout(() => {
        r.style.opacity=1;
    },i)
    i+=300;
});

buttons.forEach((b,inx) => {

    b.addEventListener('click',function() {
        if(!b.classList.contains('clicked-button'))
        b.classList.add('clicked-button');
        else 
        b.classList.remove('clicked-button');
    });
});