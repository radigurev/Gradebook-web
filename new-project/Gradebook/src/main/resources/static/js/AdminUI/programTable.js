const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('gra')];
const select=document.getElementById('classSelect');
const body=document.getElementById('body');
var infoBar=document.getElementById('add-info');
var table=document.getElementById('table');
var table2=document.getElementById('table2');
var icon=document.getElementById("icon");
var i=300;


table.style.top=150;

const program=JSON.parse(json.replace(/&quot;/g, '"'));

for (let j = 0; j < Object.keys(program).length; j++) {
    var option=document.createElement('option');
    option.value=Object.keys(program)[j];
    option.innerHTML=Object.keys(program)[j];
    select.appendChild(option);
}
const daysOfWeek=['monday','tuesday','wednesday','thursday','friday'];
 select.addEventListener("change", () => {
        var c=$('#classSelect option:selected').text();
        $('#table>tbody').empty();
     let longestDay=0;
        for (let j = 0; j < 5; j++) {
            let length=program[c][daysOfWeek[j]].length;
            for (let k = 0; k < length; k++) {
                if (longestDay<length) {
                    longestDay=length;
                }
            }
        }

     for (let j = 0; j < longestDay; j++) {
         var html = "   <tr class=\"row\" style='opacity: 1'>\n" +
             "                    <td class=\"num\">"+(j+1)+"</td>\n";
         var model={
             subject: ' ',
             classes: ' ',
             room: ' '
         };
         for (let k = 0; k < 5; k++) {
             model.room= ' ';
             model.classes= ' ';
             model.subject= ' ';
                var classSubject=program[c][daysOfWeek[k]][j];
                if (classSubject!=null) {
                    model.room = program[c][daysOfWeek[k]][j]['room'];
                    model.classes = c;
                    model.subject = program[c][daysOfWeek[k]][j]['name'];

                }
                html+= " <td>\n" +
                    "                       <p>"+model.subject+"</p>\n" +
                    "                       <div class=\"class-info\">\n" +
                    "                        <p class=\"teacher\">"+model.classes+"</p>\n" +
                    "                        <p class=\"room\">"+model.room+"</p>\n" +
                    "                      </div>\n" +
                    "                    </td>"
         }
         html+="</tr>";
         $('#table tbody ').append(html);
     }
    });




rows.forEach(r => {
    setTimeout(() => {
        r.style.opacity=1;
    },i)
    i+=300;
});


function tbChange() {
    let width=$(window).width();

    if (icon.classList.contains('fa-eye')) {
        table.style.left="-150%";
        if (width>1200)
        table2.style.left="50%";
        else {
            table2.style.left ="70%";
            body.style='overflow-x: auto;'
        }
        icon.classList.remove("fa-eye");
        icon.classList.add('fa-pen');

    } else {
        icon.classList.add("fa-eye");
        icon.classList.remove('fa-pen');
        if(width<1200) {
            table2.style.left = "300%";
            body.style='overflow-x: hidden;';
        }
        else
        table2.style.left="150%";
        table.style.left="50%";
    }
    document.getElementById('table').style.top = 150;
}

