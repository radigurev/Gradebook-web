const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('gra')];
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

console.log(grades)

grades.forEach((g) => {
    g.addEventListener('click', function(){
        $('.no-border').remove();
        var place=g.childNodes[3].childNodes[1].childNodes[9].textContent;
        console.log(g.childNodes[3].childNodes[1].childNodes[9]);
        var row=1;
        for(var i=1;i<=g.childNodes[3].childNodes.length/2;i++) {
            var html = 
                "<tr class='row>'<td id='info-row' class='no-border'></td><td class='no-border'></td><td class='no-border'><p><i class='fa-solid fa-calendar'></i> "+g.childNodes[3].childNodes[row].childNodes[1].textContent+" </p> </td>dasd<td class='no-border'><p>"+g.childNodes[3].childNodes[row].childNodes[3].textContent+"</p></td><td class='no-border'><p>"+g.childNodes[3].childNodes[row].childNodes[5].textContent+"</p></td><td class='no-border'>"+g.childNodes[3].childNodes[row].childNodes[7].textContent+"</td></tr>";
                $('table > tbody> tr').eq(place).after(html);

                place= parseInt(place)+1;
                row+=2;
        }
    })
})

