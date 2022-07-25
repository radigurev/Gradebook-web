const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('gra')];
var buttons=[...document.getElementsByClassName('abs-button')];
var infoBar=document.getElementById('add-info');
var table=document.getElementById('table');
var checkbox=document.getElementById('change-box');
var table2=document.getElementById('table2');
var icon=document.getElementById("icon");
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
        var place=g.childNodes[3].childNodes[1].childNodes[7].textContent;
        // console.log(g.childNodes[3].childNodes[1].childNodes[1]);
        var row=1;
        for(var i=1;i<=g.childNodes[3].childNodes.length/2;i++) {
            var html = 
                "<tr class='row>'<td id='info-row' class='no-border'></td><td class='no-border'></td><td class='no-border'><p><i class='fa-solid fa-calendar'></i> "+g.childNodes[3].childNodes[row].childNodes[1].textContent+"</p></td><td class='no-border'><p>"+g.childNodes[3].childNodes[row].childNodes[3].textContent+"</p></td><td class='no-border'><a class='remove-button' href="+g.childNodes[3].childNodes[row].childNodes[5]+">Remove</a></td><td class='no-border'></td><td class='no-border'></td></tr>";
                $('table > tbody> tr').eq(place).after(html);
                place= parseInt(place)+1;
                row+=2;
        }
    })
})



buttons.forEach((b,i)=> {
    b.addEventListener('click', function() {
        if(i%2==0 && b.classList.contains('not-selected-absence')) {
          b.classList.remove('not-selected-absence');
          b.classList.add('selected-absence');
        } else if (i%2==0 && b.classList.contains('selected-absence')) {
            b.classList.remove('selected-absence');
            b.classList.add('not-selected-absence');
        } else if (i%2!=0 && b.classList.contains('not-selected-late')) {
            b.classList.remove('not-selected-late');
            b.classList.add('selected-late');
        } else {
            b.classList.remove('selected-late');
            b.classList.add('not-selected-late');
        }
    });
});


function tbChange() {
    if (icon.classList.contains('fa-eye')) {
        table.style.left="-150%";
        table2.style.left="50%";
        icon.classList.remove("fa-eye");
        icon.classList.add('fa-pen');
    } else {
        icon.classList.add("fa-eye");
        icon.classList.remove('fa-pen');
        table2.style.left="150%";
        table.style.left="50%";
    }
}