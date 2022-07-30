const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('grade')];
var enterGrades=[...document.getElementsByClassName('enter-grade')];
var gradesDiv=[...document.getElementsByClassName('grades')];
var infoBar=document.getElementById('add-info');
const icon=document.getElementById('icon');
var table=document.getElementById('table');
var backButtons=[...document.getElementsByClassName('back-btn')];
var checkbox=document.getElementById('change-box');
var table2=document.getElementById('table2');
const subjectSelect=document.getElementById('subjects');
const sumbitButton=document.getElementById('submit-button');
var i=300;

table.style.top=150;

//const program=JSON.parse(json.replace(/&quot;/g, '"'));


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

enterGrades.forEach((e,inx) => {
    e.addEventListener('click', function() {
                    var position=Math.floor(inx/5);
                    var input = document.getElementsByClassName('input')[position].childNodes[1];
                    input.value=e.textContent.trim();
                    gradesDiv[position].classList.add('display');
                    document.getElementsByClassName('input')[position].classList.remove('display');
            })
});

backButtons.forEach((b,inx)=>{

    b.addEventListener('click',function() {
        gradesDiv[inx].classList.remove('display');
        document.getElementsByClassName('input')[inx].classList.add('display');
    })
});

function tbChange() {
    if (icon.classList.contains('fa-eye')) {
        table.style.left="-150%";
        table2.style.left="50%";
        icon.classList.remove("fa-eye");
        icon.classList.add('fa-pen');
        sumbitButton.classList.remove('display');
    } else {
        icon.classList.add("fa-eye");
        icon.classList.remove('fa-pen');
        table2.style.left="150%";
        table.style.left="50%";
        sumbitButton.classList.add('display');
    }
    document.getElementById('table').style.top = 150;
}
