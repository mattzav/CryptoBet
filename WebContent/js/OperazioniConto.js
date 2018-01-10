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
function effettuaPrelievo(){
	$("#operazione").remove();
	$("#operazioni").append(
			"<div id=\"operazione\">" +
				"<div class=\"col-sm-4\"></div>"+
				"<div class=\"col-sm-8\">" +
					"<div>" +
						"<h3> EFFETTUA IL TUO PRELIEVO </h3>"+
					"</div>" +
					"<div>" +
						"<span> Inserisci l'importo da prelevare :</span> <br>" +
						"<input class=\"btn btn-default\" id=\"importoDaPrelevare\" type=\"text\"> <br>" +
					"</div>" +
					"<div>" +
						"<input class=\"btn btn-primary\" type=\"button\" value=\"Preleva\" onclick=\"preleva()\">"+
					"</div>" +
				"</div>" +
			"</div>");
}
function preleva(){
	$.ajax({
		url:'prelievoConto',
		data: 'importo='+$("#importoDaPrelevare").val()+'',
		type:'POST',
        cache:false,
        error:function(){alert('error');}
	}).done(function(response){
		if(response!="" && response!=null){
			alert("response");
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
		$("#operazione").remove();
		$("#operazioni").append(
				"<div id=\"operazione\">" +
					"<h3> ECCO I TUOI MOVIMENTI </h3>"+
					"<table id=\"tableMovimenti\" class=\"table\">" +
						"<thead>" +
							"<tr>" +
								"<th> Descrizione </th>" +
								"<th> Codice transazione</th>" +
								"<th> Importo </th>" +
								"<th> Tipo </th>" +
								"<th> Scommessa </th>" +
							"</tr>" +
						"</thead>" +
						"<tbody id=\"lista\">" +
						"</tbody>" +
					"</table>" +
				"</div>");
		var rows=response.split("\n");
		$.each(rows,function(i,item){
			if(item!=""){
				var dati=item.split(";");
				var classe="success";
				if(dati[2]=="PRELIEVO"){
					classe="danger";
				}
				if(dati.length==3){
					$("#lista").append(
						"<tr class=\""+classe+"\">" +
							"<td>" +
								"Transazione dalla Carta" +
							"</td>"+
							"<td>"+
								dati[0]+
							"</td>" +
							"<td>"+
								dati[1]+
							"</td>" +
							"<td>"+
								dati[2]+
							"</td>" +
							"<td>"+
								" / "+
							"</td>" +
						"</tr>");
				}
				else{
					$("#lista").append(
						"<tr class=\""+classe+"\">" +
							"<td>" +
								"Transazione dal conto" +
							"</td>"+
							"<td>"+
								dati[0]+
							"</td>" +
							"<td>"+
								dati[1]+
							"</td>" +
							"<td>"+
								dati[2]+
							"</td>" +
							"<td>"+
								dati[3]+
							"</td>" +
						"</tr>");
				}
			}
		});
	});
}