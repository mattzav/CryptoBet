/**
 * 
 */
function checkValue(){
		var value=$(".importo").val();
		$(".importo").addClass("btn-default");
		$(".importo").removeClass("btn-danger");
		$("#scommetti").prop('disabled',false);
		if(!($.isNumeric( value )) || value==""){
			$(".importo").removeClass("btn-default");
			$(".importo").addClass("btn-danger");
			$("#scommetti").prop('disabled',true);
			addMessage("danger","Importo non valido");
			return false;
		}else{
			$("#messageDivision").remove();
			$.ajax({
				url:'scommetti',
				type:'post',
				data:{
					importo:value
				},
				error:function(){alert("Errore Richiesta");}
			}).done(function(response){
				var dati=response.split(";");
				$(".bonus").text("Bonus : "+dati[1]);
				$(".vincita").text("Vincita : "+dati[0]);
			});
			return true;
		}
	}
	function addMessage(type,message){
		$("#messageDivision").remove();
		var titleMessage = "Errore : ";
		if(type=="success"){
			titleMessage= "Congratulazioni : ";
		}
		$("#response").append(
				"<div id=\"messageDivision\" class=\"alert alert-"+type+" alert-dismissable\">" +
					"<a class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a>"+
					"<strong>"+titleMessage+"</strong>"+message+
				"</div>");
	}
	function esitoSelezionato(esito){
			$.ajax(
					{
						url:'scommetti',
						data: esito+" = null",
						type:'POST',
						cache:false,
						error:function(){alert('error');}
					}
			).done(function(response){
				
				var dati=response.split(";");
				var elemento=$('[name="'+ esito +'"]');
				var res = esito.split(";");
				var e1=$("."+res[0]);
				var format=/^Errore/;
				if(e1.length){
					if($('.'+res[0]+' > .'+res[1]).length){
						e1.remove();
						$("#messageDivision").remove();
						elemento.removeClass("btn-primary");
						elemento.addClass("btn-info");
					}
					else{
						addMessage("danger"," Hai già selezionato un esito per questa partita.");
					}
				}else if(format.test(response)){
					addMessage("danger",response.substr(8,response.length));
				}
				else{
					$("#messageDivision").remove();
					if($(".scommessa").length){
						$(".scommessa").append('<tr class="'+res[0]+' info"><td colspan="2">'+dati[0]+' vs '+dati[1]+'</td><td>'+dati[2]+'</td><td class="'+res[1]+'">'+res[1]+'</td></tr>');
					}
					elemento.removeClass("btn-info");
					elemento.addClass("btn-primary");
					$(".quotaTotale").text("Quota totale : "+dati[3]);
					$(".bonus").text("Bonus : "+dati[4]);
					$(".vincita").text("Vincita : "+dati[5]);
				}
			});
	}
	function giocaScommessa(){
		if(checkValue()){
			$.ajax({
				url:'scommetti',
				data: 'giocaScommessa=null',
				type:'POST',
	            cache:false,
	            error:function(){alert('error');}
			}).done(function(response){
				var format=/^Errore/;
	    		if(format.test(response)){
					if(response=="Errore : Utente non loggato"){
						$("#welcomeMessage").css({'display' : 'none'});
						$("#login").css({'display' : 'block'});
					}
					else{
						addMessage("danger",response.substr(8,response.length));
					}
	    		}
				else{
					svuotaScommessa();
					addMessage("success"," scommessa registrata con successo");
					var arrayResponse=response.split('\n');
					$("#saldoConto").text("Saldo conto : "+arrayResponse[1]);
				}
				
			});
		}
	}
	
	function svuotaScommessa(){
		$.each($(".scommessa > tr"),function(i,item){
			item.remove();
		});
		$(".quotaTotale").text("Quota totale :");
		$(".bonus").text("Bonus :");
		$(".vincita").text("Vincita :");
		$('.btn-primary.esitoAttivo').addClass("btn-info");
		$('.btn-primary.esitoAttivo').removeClass("btn-primary");
	}
	
	function pulisciScommessa(){
		$.ajax({
			url:'scommetti',
			data: 'svuota',
			type:'POST',
            cache:false,
            error:function(){alert('error');}
		}).done(function(response){

			svuotaScommessa();
		});
	}
