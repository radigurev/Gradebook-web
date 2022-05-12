const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('grade')];
var infoBar=document.getElementById('add-info');
var table=document.getElementById('table');
var i=300;

table.style.top=50;

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
        console.log(g.childNodes[3])
        var html = 
                "<td id='info-row' class='no-border'> <p>" + g.childNodes[7].textContent+"</p></td><td class='no-border'><p>"+g.childNodes[5].textContent+
                "</p></td><td class='no-border'><p>"+g.childNodes[3].textContent+"</p></td><td class='no-border'><p>"+g.childNodes[9].textContent+"</p></td><td class='no-border'></td><td class='no-border'></td><td class='no-border'></td><td class='no-border'></td>";

        $('table > tbody> tr').eq(g.childNodes[11].textContent).after(html);
        
    })
})

