const rows = [...document.getElementsByClassName('row')];
const grades= [...document.getElementsByClassName('gra')];
const students=document.getElementById('students-div');
const infoBar = document.getElementById('add-info');
const table = document.getElementById('table');
const tableOneRows=table.rows;
let i = 300;
const table2 = document.getElementById('table2');
const icon = document.getElementById('icon');
const iconTable = document.getElementById('icon-table-change');
const secondButton=document.getElementById('second-button');
const selects=document.getElementById('select-wrapper');
const aboveButtons=document.getElementById('buttons-div');
let numberInTable=1;
var studentsByClass=JSON.parse(test.replace(/&quot;/g, '"'));
table.style.top=150;

students.remove();

rows.forEach(r => {
    setTimeout(() => {
        r.style.opacity=1;
    },i)
    i+=300;
});



for (let i=0; i<tableOneRows.length;i++) {
    jQuery(document).ready(function($){
        $(tableOneRows[i]).on('click', function(event){
            event.preventDefault();
            const classNumber=tableOneRows[i].childNodes[3].childNodes[1].childNodes[1].textContent;
            for (let j = 0; j < Object.keys(studentsByClass).length; j++) {
                if(studentsByClass.classes[j]===classNumber) {
                    var html=' <tr>\n' +
                                '                                  <td>'+numberInTable+'</td>\n' +
                                '                                  <td>'+studentsByClass.names[j]+'</td>\n' +
                                '                                  <td>'+studentsByClass.classes[j]+'</td>\n' +
                                '                                  <td><a th:href="" class="remove-button">Remove</a></td>\n' +
                                '                              </tr>';
                            numberInTable++;
                    $('#table3 tbody>').eq(-1).after(html);
                }
            }

            $('.cd-popup').addClass('is-visible');

        });

        $('.cd-popup').on('click', function(event){
            if( $(event.target).is('.cd-popup-close') || $(event.target).is('.cd-popup') ) {
                event.preventDefault();
                $(this).removeClass('is-visible');
                $('#table3 tbody>tr>').remove();
            }
        });
        $(document).keyup(function(event){
            if(event.which=='27'){
                $('#table3 tbody>tr>').remove();
                $('.cd-popup').removeClass('is-visible');
            }
        });
    });
}

window.addEventListener('resize', function () {

    let width=$(window).width();
    //Checking so we can move elements in place for mobile or tablet users
   if(width<1200 && selects.style.left==='80%') {
       table2.style.left='50%';
       selects.style.left='67%';
       aboveButtons.style.left='23px';
       // Returning to normal positioning
   }else if(width>1200 && selects.style.left==='67%') {
       selects.style.left='80%';
       aboveButtons.style.left='50%';
       table2.style.left='30%';
   }
});

function addClass() {
    let width=$(window).width();
    console.log(width)
        if(width>1200) {
            selects.style.left = '80%';
            table2.style.left = '30%';
        }else {
         aboveButtons.style.left='23px';
            selects.style.left='67%';

        }
}

function tbChange() {
    if (icon.classList.contains('fa-eye')) {
        table.style.left="-150%";
        table2.style.left="50%";
        icon.classList.remove("fa-eye");
        icon.classList.add('fa-pen');
        secondButton.classList.remove('second-button');
        setTimeout(function () {
            secondButton.style.opacity=1;
        },1000);
    } else {
        if(aboveButtons.style.left==='23px')
            aboveButtons.style.left='50%';

        icon.classList.add("fa-eye");
        icon.classList.remove('fa-pen');
        selects.style.left='160%';
        table2.style.left="150%";
        table.style.left="50%";
        secondButton.classList.remove('second-button');
        setTimeout(function () {
            secondButton.classList.add('second-button');

        },1000);
        secondButton.style.opacity=0;
    }
}
