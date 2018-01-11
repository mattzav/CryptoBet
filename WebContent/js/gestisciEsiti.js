/**
 * 
 */
function abilitaEsito(esito){
		$.ajax({
		  type: 'POST',
		  url: 'aggiornaQuote',
		  data: esito,
		  success: function(){
			  var selezionato = $('[name="'+esito+'"]');
			  if(selezionato.hasClass("btn-danger")){
				  selezionato.removeClass("btn-danger");
				  selezionato.addClass("btn-info");
			  }
			  else{

				  selezionato.removeClass("btn-info");
				  selezionato.addClass("btn-danger");
			  }
		  },
		});
	}
	
	function aggiornaEsito(partita){
		var esito=$("#inputModificaQuota"+partita+" "+'[name="'+"esitoScelto"+'"]').val();
		var quota=$("#inputModificaQuota"+partita+" "+'[name="'+"nuovaQuota"+'"]').val();
		
		$.ajax({
			  type: 'POST',
			  url: 'modificaQuota',
			  data: {
				  esitoScelto:esito,
				  nuovaQuota:quota,
				  partita:partita
			  },
			  success: function(){
				  $('[name="'+partita+";"+esito+'"]').val(quota);
				}
			});
	}
	
	function selezionaPartitaDaModificare(partita,disabilita){
		if(!disabilita){
			$(".partitaInModifica"+partita).parent().children().first().append("<div id=\"inputModificaQuota"+partita+"\"> <div class=\"modal-body\"><div class=\"form-group\"><label for=\"esitoScelto\" class=\"form-control-label\">Esito da modificare:</label><input type=\"text\" class=\"form-control\"name=\"esitoScelto\"></div><div class=\"form-group\"><label for=\"nuovaQuota\" class=\"form-control-label\">Nuova Quota:</label><textarea class=\"form-control\" name=\"nuovaQuota\"></textarea></div></div><div class=\"modal-footer\"><button name=\"annulla\" onclick=\"selezionaPartitaDaModificare("+partita+",true)\" class=\"btn btn-secondary btn-sm\"data-dismiss=\"modal\">Annulla</button><button name=\"aggiorna\" onclick=\"aggiornaEsito("+partita+")\" class=\"btn btn-primary btn-sm\">Aggiorna Esito</button><button name=\"suggerimento\" onclick=\"richiediSuggerimento("+partita+")\" class=\"btn btn-info btn-sm\"> Richiedi Suggerimento</button></div></div>");
		}
		else{
		    $("#inputModificaQuota"+partita).remove();	
		}
	}
	
	function richiediSuggerimento(partita){
		var esito=$("#inputModificaQuota"+partita+" "+'[name="'+"esitoScelto"+'"]').val();
		$.ajax({
			  type: 'POST',
			  url: 'modificaQuota',
			  data: {
				  suggerimento:partita,
				  esitoScelto:esito
			  },
			  success: function(quota){
					$("#inputModificaQuota"+partita+" "+'[name="'+"nuovaQuota"+'"]').val(quota);

			  }
			});
	}