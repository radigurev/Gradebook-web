const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('grade')];
var enterGrades=[...document.getElementsByClassName('enter-grade')];
var gradesDiv=[...document.getElementsByClassName('grades')];
var backButtons=[...document.getElementsByClassName('back-btn')];
var infoBar=document.getElementById('add-info');
var table=document.getElementById('table');
var checkbox=document.getElementById('change-box');
var table2=document.getElementById('table2');
var switchIcon=document.getElementById('switch-icon');
var pg=1;
var i=300;

table.style.top=150;

rows.forEach(r => {
    setTimeout(() => {
        r.style.opacity=1;
    },i)
    i+=300;
});

grades.forEach((g) => {
    g.addEventListener('click', function(){
        $('.no-border').remove();
        var html = 
                "<td id='info-row' class='no-border'> <p>" + g.childNodes[7].textContent+"</p></td><td class='no-border'><p>"+g.childNodes[5].textContent+
                "</p></td><td class='no-border'><p>"+g.childNodes[3].textContent+"</p></td><td class='no-border'><p>"+g.childNodes[9].textContent+"</p></td><td class='no-border'></td><td class='no-border'></td><td class='no-border'></td><td class='no-border'></td>";

        $('table > tbody> tr').eq(g.childNodes[11].textContent).after(html);
        
    })
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

enterGrades.forEach((e,inx) => {
    e.addEventListener('click', function() {
                    var position=Math.floor(inx/5);
                    var input = document.getElementsByClassName('input')[position].childNodes[1];
                    input.value=e.textContent.trim();
                    gradesDiv[position].classList.add('display');
                    document.getElementsByClassName('input')[position].classList.remove('display');
            })
});
console.log(backButtons)
backButtons.forEach((b,inx)=>{

    b.addEventListener('click',function() {
        gradesDiv[inx].classList.remove('display');
        document.getElementsByClassName('input')[inx].classList.add('display');
    })
});
