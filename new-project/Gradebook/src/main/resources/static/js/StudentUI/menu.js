const table=document.getElementsByClassName('tablee')[0];
jQuery(document).ready(function($){
	$('.card1').on('click', function(event){
        addChartsInfo('grades');
		event.preventDefault();
		$('.cd-popup').addClass('is-visible');
	});
	
	$('.cd-popup').on('click', function(event){
		if( $(event.target).is('.cd-popup-close') || $(event.target).is('.cd-popup') ) {
			resetCanvas();
			event.preventDefault();
			$(this).removeClass('is-visible');
		}
	});
	$(document).keyup(function(event){
    	if(event.which=='27'){
            resetCanvas();
    		$('.cd-popup').removeClass('is-visible');
	    }
    });
});


jQuery(document).ready(function($){
	$('.card2').on('click', function(event){
        addChartsInfo('absence');
		event.preventDefault();
		$('.cd-popup').addClass('is-visible');
	});
	
	$('.cd-popup').on('click', function(event){
		if( $(event.target).is('.cd-popup-close') || $(event.target).is('.cd-popup') ) {
			resetCanvas();
			event.preventDefault();
			$(this).removeClass('is-visible');
		}
	});
	$(document).keyup(function(event){
    	if(event.which=='27'){
            resetCanvas();
    		$('.cd-popup').removeClass('is-visible');
	    }
    });
});


jQuery(document).ready(function($){
	$('.card3').on('click', function(event){
        addChartsInfo('response');
		event.preventDefault();
		$('.cd-popup').addClass('is-visible');
	});
	
	$('.cd-popup').on('click', function(event){
		if( $(event.target).is('.cd-popup-close') || $(event.target).is('.cd-popup') ) {
			resetCanvas();
			event.preventDefault();
			$(this).removeClass('is-visible');
		}
	});
	$(document).keyup(function(event){
    	if(event.which=='27'){
            resetCanvas();
    		$('.cd-popup').removeClass('is-visible');
	    }
    });
});


$('[data-toggle=tooltip]').tooltip();

$('.hover-animation').hover(
  function() {
    $( this ).toggleClass( 'animated rubberBand ' + $( this ).attr('data-hover-color') + '-text' );
  }
);


function addChartsInfo(type) {
	table.style.zIndex= '-1';

    const ctx = document.getElementById('myChart').getContext('2d');
    var dataChart=new Array();

    if(type==='grades') {
        dataChart= new Array(4.55,4.89,5.04,5.12,5.14,5.18,5.20,5.36,5.56,5.70);
    }else if(type==='absence') {
        dataChart = new Array(1,2.2,5,5,5,5,5,5,12.5,24.5);
    }else if (type==='response') {
        dataChart= new Array(0,0,0,0,1,2,3,4,0,8,10);
    }

    console.log(dataChart)
const myChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: ['Януари','Февруари','Март','Април','Юни','Юли','Август','Септември','Октомври','Ноември'],
      datasets: [{ 
          data: dataChart,
          label: "Radoslav Gurev",
          borderColor: "#3e95cd",
          fill: false
        }
      ]
    },
    options: {
      title: {
        display: true,
      }
    }
  });
}

function resetCanvas(){
	table.style.zIndex='1';
        $('#myChart').remove();
        $('#cd-popup-cont').append('<canvas id="myChart"><canvas>');
        canvas = document.querySelector('#myChart');
        ctx = canvas.getContext('2d');
        ctx.canvas.width = $('#graph').width();
        ctx.canvas.height = $('#graph').height();
        var x = canvas.width/2;
        var y = canvas.height/2;
        ctx.font = '10pt Verdana';
        ctx.textAlign = 'center';
        ctx.fillText('This text is centered on the canvas', x, y);
}