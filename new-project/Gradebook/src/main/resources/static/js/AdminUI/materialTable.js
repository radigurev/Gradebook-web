const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('gra')];
var buttons = [...document.getElementsByClassName('button-a')];
var takenButtons =[...document.getElementsByClassName('taken-button')];
var takenP=document.getElementsByClassName('isItTaken');
var checkbox=document.getElementById('change-box');
var infoBar=document.getElementById('add-info');
var table=document.getElementById('table');
var table2=document.getElementById('table2');
var plusAndMinus=document.getElementById('add-remove-div');
var subjectSelect=document.getElementById('select-wrapper');

var subjectAtTheMoment=$('#subjects :selected').text();
var i=300;

let rowNum=1;

table.style.top=150;
console.log(subjectSelect)
rows.forEach(r => {
    setTimeout(() => {
        r.style.opacity=1;
    },i)
    i+=300;
});

subjectSelect.addEventListener('click', () => {
    subjectSelect.addEventListener("change", () => {
            if (rowNum!==0) {
                subjectAtTheMoment=$('#subjects :selected').text();
                rowNum=0;
                $('#table2 > tbody>').empty();
                addRow();
            }
    });
});

buttons.forEach((b,inx) => {

    b.addEventListener('click',function() {
        if(!b.classList.contains('clicked-button')) {
            b.classList.add('clicked-button');
            takenP[inx].textContent='taken';
        }
        else {
            b.classList.remove('clicked-button');
            takenP[inx].textContent='';
        }
    });
});

takenButtons.forEach((b,inx) => {
    b.addEventListener('click', function() {
        $('.no-border').remove();
        var html = 
                "<td id='info-row' class='no-border'><p>"+b.childNodes[1].childNodes[1].textContent+"</p></td> <td class='no-border'><p>"+b.childNodes[1].childNodes[3].textContent+"</p></td><td class='no-border'></td>";
                $('table > tbody> tr').eq(b.childNodes[1].childNodes[5].textContent).after(html);
    });
});

function addRow() {
    rowNum++;
    const html = "<tr class='row' style='opacity: 1'><td class='num'>" + rowNum + "</td> <td><div><p>" + "Предмет:" + subjectAtTheMoment + "</p></div></td><td><input class='material-input' type='text'></td></tr>";
    $('#table2>tbody>').eq(-1).after(html);

}

function removeRow() {
    $('#table2>tbody>').eq(rowNum-1).remove();
    rowNum--;
}

function tbChange() {
    if (icon.classList.contains('fa-eye')) {
        table.style.left="-150%";
        table2.style.left="50%";
        setTimeout(function () {
            plusAndMinus.style.opacity=1;
        },900);
        setTimeout(function () {
            subjectSelect.style.left='80%';
        },100);
        icon.classList.remove("fa-eye");
        icon.classList.add('fa-pen');
        table2.style.width = 700;
    } else {
        icon.classList.add("fa-eye");
        icon.classList.remove('fa-pen');
        plusAndMinus.style.opacity=0
        table2.style.left="150%";
        table.style.left="50%";
        subjectSelect.style.left='160%';
    }
    document.getElementById('table').style.top = 150;
}