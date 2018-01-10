/**
 * 
 */


function effettuaVersamento(){
	
	$("#operazione").remove();
	$("#operazioni").append(
			"<div id=\"operazione\">" +
				"<div class=\"col-sm-4\"></div>"+
				"<div class=\"col-sm-8\">" +
					"<div>" +
						"<h3> EFFETTUA IL TUO VERSAMENTO </h3>"+
					"</div>" +
					"<div>" +
						"<span> Inserisci l'importo da versare :</span> <br>" +
						"<input class=\"btn btn-default\" id=\"importoDaVersare\" type=\"text\"> <br>" +
					"</div>" +
					"<div>" +
						"<input class=\"btn btn-primary\" type=\"button\" value=\"Versa\" onclick=\"versa()\">"+
					"</div>" +
				"</div>" +
			"</div>");
			alert("ciao");
}
function versa(){
	$.ajax({
		url:'versamentoConto',
		data: 'importo='+$("#importoDaVersare").val()+'',
		type:'POST',
        cache:false,
        error:function(){alert('error');}
	}).done(function(response){
		if(response!="" && response!=null){
			alert(response);
		}
	});
}
function getListaMovimenti(){
	
	$.ajax({
		url:'listaMovimenti',
		type:'get',
        cache:false,
        error:function(){alert('error');}
	}).done(function(response){
		alert(response);
	});
}