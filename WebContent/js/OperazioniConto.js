/**
 * 
 */

function mostraUltimeScommesse(){
		 if($("table").length){
			  $("#nomeOperazione").remove();
			  $("table").remove();
			  
		  }
		 else{
			$.ajax({
			  type: 'GET',
			  url: 'mostraScommesse',
			  success: function(data){
		 
			  $("#operazione").append("<h2 id=\"nomeOperazione\"> ECCO LE TUE SCOMMESSE: </h1>");
    		  $("#risultatoGestione").append("<table class=\"col-sm-12\">");
			  var firstRow = "<th > codici scommessa </th>"+
							"<th > data di emissione </th>"+
							"<th > importo scommessa </th>"+
							"<th > numero esiti </th>"+
							"<th > vincita potenziale </th>"+
							"<th > stato </th>"+
							"<th > verifica </th>";
		      $("#risultatoGestione>table").append(firstRow);
			  var scommesse = data.split(";");
			  for(var i = 0; i<scommesse.length-1; i++){
				 var dati = scommesse[i].split(":");
				 for(var j=0; j<dati.length; j++){
					var verifica="";
					 if(!(dati[5] == "vinta" || dati[5] == "persa")){
					 	verifica='<td class=\"verificata\"><button class=\"glyphicon glyphicon-ok\"  onclick=\"verificaScommessa('+dati[0]+');\"></td>';
					 }
					 else{
						 verifica='<td> Già verificata</td>';
					 }
					 var row = $("<tr id= "+dati[0]+" class=\"success\">"+
							'<td>'+dati[0]+'</td>'+
							'<td >'+dati[1]+'</td>'+
							'<td >'+dati[2]+'</td>'+
							'<td >'+dati[3]+'</td>'+
							'<td >'+dati[4]+'</td>'+
							'<td class=\"stato\">'+dati[5]+'</td>'+
							verifica+
							'</tr>');	
					
					}
				 $("#risultatoGestione>table").append(row);
			 }
		  },
		 });
	  }
	}
	
	function verificaScommessa(codice){
		$.ajax({
			  type: 'POST',
			  url: 'verificaScommessa',
			  data: {codiceScommessa: codice},
			  success: function(data){
				  if(data != "non conclusa"){
				 		$("#"+codice+" .stato").text(data);
				 		$("#"+codice+" .verificata").remove();
				 		$("#"+codice).append('<td> Già verificata</td>');
				  }
			  }
		 });
	}
	
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