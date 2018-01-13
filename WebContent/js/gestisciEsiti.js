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

		var pattern_esito = /^(1|1X|12|X2|2|U|O|GG|NG)$/;
		var pattern_quota= /^\d+(\.\d+)*$/;
		if(pattern_esito.test(esito) && pattern_quota.test(quota)){
			$("#alert"+partita+".alert").remove();
			$(".partitaInModifica"+partita).parent().children().first().append(
					"<div id=\"alert"+partita+"\" class=\"alert alert-success alert-dismissable\">"+
					 "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"+
					"<strong>Congratulazioni!</strong> hai aggiornato correttamente la tua quota"+
					"</div>");
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
		else{
			var errore ="";
			if(!pattern_esito.test(esito) && !pattern_quota.test(quota)){
				errore+="hai inserito un esito non valido ed anche una quota non valida";
			}
			else if(!pattern_esito.test(esito)){
				errore+="hai inserito un esito non valido";
			}
			else{
				errore+="hai inserito una quota non valida";
			}
			$("#alert"+partita+".alert").remove();
			$(".partitaInModifica"+partita).parent().children().first().append(
					"<div id=\"alert"+partita+"\" class=\"alert alert-danger alert-dismissable\">"+
					 "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"+
					"<strong>Attenzione!</strong> "+errore+
					"</div>");
		}
	}
	
	function selezionaPartitaDaModificare(partita,disabilita){
		if($("#inputModificaQuota"+partita).length){
			disabilita=true;
		}
		if(!disabilita){
			$(".partitaInModifica"+partita).parent().children().first().append("<div id=\"inputModificaQuota"+partita+"\"> <div class=\"modal-body\"><div class=\"form-group\"><label for=\"esitoScelto\" onkeyup=\"controllaEsito()\" class=\"form-control-label\">Esito da modificare:</label><input type=\"text\" class=\"form-control\"name=\"esitoScelto\"></div><div class=\"form-group\"><label for=\"nuovaQuota\" class=\"form-control-label\">Nuova Quota:</label><textarea class=\"form-control\" name=\"nuovaQuota\"></textarea></div></div><div class=\"modal-footer\"><button name=\"annulla\" onclick=\"selezionaPartitaDaModificare("+partita+",true)\" class=\"btn btn-secondary btn-sm\"data-dismiss=\"modal\">Annulla</button><button name=\"aggiorna\" onclick=\"aggiornaEsito("+partita+")\" class=\"btn btn-primary btn-sm\">Aggiorna Esito</button><button name=\"suggerimento\" onclick=\"richiediSuggerimento("+partita+")\" class=\"btn btn-info btn-sm\"> Richiedi Suggerimento</button></div></div>");
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
	
	function controllaEsito(){
		
		
	}