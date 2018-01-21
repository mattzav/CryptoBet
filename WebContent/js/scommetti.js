/**
 * 
 */

//funzione che controlla il valore inserito come importo
//notifica alla servlet il nuovo valore
//riceve i dati della scommessa aggiornati
function controlloImporto(){
		var valore=$(".importo").val();
		$(".importo").addClass("btn-default");
		$(".importo").removeClass("btn-danger");
		$("#scommetti").prop('disabled',false);
		if(!($.isNumeric( valore )) || valore==""){
			$(".importo").removeClass("btn-default");
			$(".importo").addClass("btn-danger");
			$("#scommetti").prop('disabled',true);
			addMessage("danger","Importo non valido");
			return false;
		}else{
			$("#messageDivision").remove();
			$.ajax({
				url:'inserisciImporto',
				type:'post',
				data:{
					importo:valore
				},
				error:function(){alert("Errore Richiesta");}
			}).done(function(risposta){
				var dati=risposta.split(";");
				$(".bonus").text("Bonus : "+dati[1]);
				$(".vincita").text("Vincita : "+dati[0]);
			});
			return true;
		}
	}

	//funzione di utilità per notificare l'esito di alcune operazioni
	function aggiungiMessaggio(tipo,messaggio){
		$("#messageDivision").remove();
		var titoloMessaggio = "Errore : ";
		if(tipo=="success"){
			titoloMessaggio= "Congratulazioni : ";
		}
		$("#response").append(
				"<div id=\"messageDivision\" class=\"alert alert-"+tipo+" alert-dismissable\">" +
					"<a class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a>"+
					"<strong>"+titoloMessaggio+"</strong>"+messaggio+
				"</div>");
	}
	
	//viene chiamata ogni volta che si clicca su un esito
	//lo notifica alla servlet
	//riceve i dati aggiornati
	function esitoSelezionato(esito){
			$.ajax(
					{
						url:'selezionaEsito',
						data: {
							"bottone":esito,
						},
						type:'POST',
						cache:false,
						error:function(){alert('error');}
					}
			).done(function(risposta){
				
				var dati=risposta.split(";");
				var elemento=$('[name="'+ esito +'"]');
				var risultato = esito.split(";");
				var esitoSelezionato=$("."+risultato[0]);
				var formato=/^Errore/;
				if(esitoSelezionato.length){
					if($('.'+risultato[0]+' > .'+risultato[1]).length){
						esitoSelezionato.remove();
						$("#messageDivision").remove();
						elemento.removeClass("btn-primary");
						elemento.addClass("btn-info");
						$(".quotaTotale").text("Quota totale : "+dati[3]);
						$(".bonus").text("Bonus : "+dati[4]);
						$(".vincita").text("Vincita : "+dati[5]);
					}
					else{
						aggiungiMessaggio("danger"," Hai gia' selezionato un esito per questa partita.");
						window.location.href = "#response";
					}
				}else if(formato.test(risposta)){
					aggiungiMessaggio("danger",risposta.substr(8,risposta.length));
					window.location.href = "#response";
					
				}
				else{
					$("#messageDivision").remove();
					if($(".scommessa").length){
						$(".scommessa").append('<tr class="'+risultato[0]+' info"><td colspan="2">'+dati[0]+' vs '+dati[1]+'</td><td>'+dati[2]+'</td><td class="'+risultato[1]+'">'+risultato[1]+'</td></tr>');
					}
					elemento.removeClass("btn-info");
					elemento.addClass("btn-primary");
					$(".quotaTotale").text("Quota totale : "+dati[3]);
					$(".bonus").text("Bonus : "+dati[4]);
					$(".vincita").text("Vincita : "+dati[5]);
				}
			});
	}
	
	//invoca sul click del bottone SCOMMETTI
	//notifica alla servlet la richiesta
	//viene visualizzato l'esito della richiesta
	function giocaScommessa(){
		if(controlloImporto()){
			$.ajax({
				url:'scommetti',
				data: {
					"bottone": "giocaScommessa"
				},
				type:'POST',
	            cache:false,
	            error:function(){alert('error');}
			}).done(function(risposta){
				var formato=/^Errore/;
	    		if(formato.test(risposta)){
					if(risposta=="Errore: Utente non loggato"){
						$("#welcomeMessage").css({'display' : 'none'});
						$("#login").css({'display' : 'block'});
						aggiungiMessaggio("danger",risposta.substr(8,risposta.length));
						window.location.href = "#response";
					}
					else{
						aggiungiMessaggio("danger",risposta.substr(8,risposta.length));
						window.location.href = "#response";
					}
	    		}
				else{
					svuotaScommessa();
					aggiungiMessaggio("success"," scommessa registrata con successo");
					var arrayRisposta=risposta.split('\n');
					$("#saldoConto").text("Saldo conto : "+arrayRisposta[1]);
					window.location.href = "#response";
				}
				
			});
		}
	}
	
	//elimina tutta la tabella contenente i dati della scommessa
	function svuotaScommessa(){
		$.each($(".scommessa > tr"),function(i,elemento){
			elemento.remove();
		});
		$(".quotaTotale").text("Quota totale :");
		$(".bonus").text("Bonus :");
		$(".vincita").text("Vincita :");
		$('.btn-primary.esitoAttivo').addClass("btn-info");
		$('.btn-primary.esitoAttivo').removeClass("btn-primary");
	}
	
	//notifica alla servlet la richiesta di svuotare la scommessa
	function pulisciScommessa(){
		$.ajax({
			url:'svuotaScommessa',
			data: {
				"bottone":"svuota" 
			},
			type:'POST',
            cache:false,
            error:function(){alert('error');}
		}).done(function(risposta){
			svuotaScommessa();
		});
	}
