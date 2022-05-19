const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('gra')];
var buttons = [...document.getElementsByClassName('button-a')];
var takenButtons =[...document.getElementsByClassName('taken-button')];
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

buttons.forEach((b,inx) => {

    b.addEventListener('click',function() {
        if(!b.classList.contains('clicked-button'))
        b.classList.add('clicked-button');
        else 
        b.classList.remove('clicked-button');
    });
});

takenButtons.forEach((b,inx) => {
    b.addEventListener('click', function() {
        console.log()
        $('.no-border').remove();
        var html = 
                "<td id='info-row' class='no-border'><p>"+b.childNodes[1].childNodes[1].textContent+"</p></td> <td class='no-border'><p>"+b.childNodes[1].childNodes[3].textContent+"</p></td><td class='no-border'></td>";
                $('table > tbody> tr').eq(b.childNodes[1].childNodes[5].textContent).after(html);
    });
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