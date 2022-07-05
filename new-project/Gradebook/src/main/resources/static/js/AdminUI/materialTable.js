const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('gra')];
var buttons = [...document.getElementsByClassName('button-a')];
var takenButtons =[...document.getElementsByClassName('taken-button')];
var checkbox=document.getElementById('change-box');
var infoBar=document.getElementById('add-info');
var table=document.getElementById('table');
var table2=document.getElementById('table2');
var plusAndMinus=document.getElementById('add-remove-div');
var subjectSelect=document.getElementById('select-wrapper');
const addButton=document.getElementById('add-button');
var subjectAtTheMoment=$('#subjects :selected').text();
var classAtTheMoment=$('#classes :selected').text();
var i=300;

let rowNum=1;

var tbRows, switching, inx, x, y, shouldSwitch;
switching = true;

while (switching) {
    switching = false;
    tbRows = table.rows;

    for (inx = 1; inx < (tbRows.length - 1); inx++) {
        shouldSwitch = false;
        x = tbRows[inx].getElementsByTagName("TD")[0];
        y = tbRows[inx + 1].getElementsByTagName("TD")[0];
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
            shouldSwitch = true;
            break;
        }
    }
    if (shouldSwitch) {

        tbRows[inx].parentNode.insertBefore(tbRows[inx + 1], tbRows[inx]);
        switching = true;
    }
}

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

takenButtons.forEach((b,inx) => {
    b.addEventListener('click', function() {
        $('.no-border').remove();
        var html = 
                "<td id='info-row' class='no-border'><p><i class=\"fa-solid fa-person-chalkboard\"></i> "+b.childNodes[1].childNodes[1].textContent+"</p></td> <td class='no-border'><p><i class=\"fa-solid fa-calendar\"></i> "+b.childNodes[1].childNodes[3].textContent+"</p></td><td class='no-border'></td><td class='no-border'></td><td class='no-border'><a href='/admin/material/undo/"+b.childNodes[1].childNodes[5].textContent+"' class='remove-button'>undo</a></td>";
                $('table > tbody> tr').eq(b.childNodes[1].childNodes[7].textContent).after(html);
    });
});

function changeTest() {
    const materials=[];
    let length=table2.rows.length;
    for (let j = 1; j <length; j++) {
        // table2.rows[j].cells[2].children[0].value
        var materialRow={};
        materialRow.subject=subjectAtTheMoment;
        materialRow.classes=classAtTheMoment;
        materialRow.material=table2.rows[j].cells[2].children[0].value;
        materials.push(materialRow);
    }
   document.getElementById('json-input').value=JSON.stringify(materials);

}

function addRow() {
    rowNum++;
    const html = "<tr class='row' style='opacity: 1'><td class='num'>" + rowNum + "</td> <td><div><p>" + "Предмет:" + subjectAtTheMoment + "</p></div></td><td><input class='material-input' type='text'></td></tr>";
    $('#table2>tbody>').eq(-1).after(html);

}

function removeRow() {
    if(rowNum!==1) {
        $('#table2>tbody>').eq(rowNum - 1).remove();
        rowNum--;
    }
}

function tbChange() {
    if (icon.classList.contains('fa-eye')) {
        table.style.left="-150%";
        table2.style.left="50%";
        setTimeout(function () {
            plusAndMinus.style.opacity=1;
        },900);
        setTimeout(function () {
                subjectSelect.style.right='3%';

        },100);
        icon.classList.remove("fa-eye");
        icon.classList.add('fa-pen');
        addButton.style.left="30%";
    } else {
        icon.classList.add("fa-eye");
        icon.classList.remove('fa-pen');
        plusAndMinus.style.opacity=0;
        table2.style.left="150%";
        table.style.left="50%";
        addButton.style.left="150%";
        subjectSelect.style.right='-160%';
    }
    document.getElementById('table').style.top = 150;
}