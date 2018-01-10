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
			"</div>")
}
function versa(){
	alert($("#importoDaVersare").val());
	$.ajax({
		url:'effetuaOperazioneConto',
		data: 'operazione=versamento &importo='+$("#importoDaVersare").val()+'',
		type:'POST',
        cache:false,
        error:function(){alert('error');}
	}).done(function(response){
		
	});
}