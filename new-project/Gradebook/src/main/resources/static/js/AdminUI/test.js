var table=document.getElementById('table');
var table2=document.getElementById('table2');
const icon=document.getElementById('icon');
const rows = [...document.getElementsByClassName('row')];
let i=300;


table.style.top=150;

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