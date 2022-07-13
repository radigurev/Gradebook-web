const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('gra')];
var buttons = [...document.getElementsByClassName('button-a')];
var infoBar=document.getElementById('add-info');
var table=document.getElementById('table');
var table2=document.getElementById('table2');
var subjectSelect=document.getElementById('select-wrapper');
const input = document.getElementById("fontuser");
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

function tbChange() {
    if (icon.classList.contains('fa-eye')) {
        table.style.left="-150%";
        table2.style.left="50%";
        icon.classList.remove("fa-eye");
        icon.classList.add('fa-pen');
        // input.classList.remove('display');
        // setTimeout(function () {
        //     input.style.opacity=1;
        // },200);
    } else {
        icon.classList.add("fa-eye");
        icon.classList.remove('fa-pen');
        table2.style.left="150%";
        table.style.left="50%";
        // input.style.opacity=0;
        // setTimeout(function () {
        //     input.classList.add('display');
        //
        // },200);
    }
    document.getElementById('table').style.top = 150;
}

function searchEngine() {

        var filter, table, tr, td, i, txtValue,rowClass,tableId;

        if(icon.classList.contains('fa-eye')) {
            table = document.getElementById('table');
            tr = table.getElementsByClassName('students');
        }
        else {
            table = document.getElementById('table2');
            tr = table.getElementsByClassName('users');
        }
        filter = document.getElementById('search-filter').value.toUpperCase();
        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[2];
            if (td) {
                txtValue = td.textContent || td.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }

}