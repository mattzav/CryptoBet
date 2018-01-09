/**
 * 
 */
function checkValue(){
		var value=$(".importo").val();
		$(".importo").addClass("btn-default");
		$(".importo").removeClass("btn-danger");
		if(!($.isNumeric( value ))){
			$(".importo").removeClass("btn-default");
			$(".importo").addClass("btn-danger");
		}
		else{
			$.ajax(
	   			{
		            url:'scommetti',
		            data:'importo='+value,
		            type:'POST',
		            cache:false,
		            error:function(){alert('error');}
	        	}
	    	).done(function(response){
				$(".vincita").text("Vincita : "+response);
	    	});
		}
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
				if(e1.length){
					if($('.'+res[0]+' > .'+res[1]).length){
						e1.remove();
						elemento.removeClass("btn-danger");
		    			elemento.addClass("btn-info");
					}
					else alert("Hai già selezionato un esito per questa partita");
				}
				else{
					if($(".scommessa").length){
						$(".scommessa").append('<tr class="'+res[0]+' info"><td colspan="2">'+dati[0]+' vs '+dati[1]+'</td><td>'+dati[2]+'</td><td class="'+res[1]+'">'+res[1]+'</td></tr>');
					}
					elemento.removeClass("btn-info");
	    			elemento.addClass("btn-danger");
				}
				$(".quotaTotale").text("Quota totale : "+dati[3]);
				$(".bonus").text("Bonus : "+dati[4]);
				$(".vincita").text("Vincita : "+dati[5]);
				
	    	});
	}
	function giocaScommessa(){
		if($(".importo").hasClass("btn-danger")){
			alert("Inserisci un importo valido");
		}
		else{
			$.ajax({
				url:'scommetti',
				data: 'giocaScommessa=null',
				type:'POST',
	            cache:false,
	            error:function(){alert('error');}
			}).done(function(response){
				alert(response);
				if(response=="utente non loggato"){
					$("#welcomeMessage").css({'display' : 'none'});
					$("#login").css({'display' : 'block'});
					alert("errore login");
				}else if(response=="credito non sufficente"){
					alert(response);
				}else if(response=="utente loggato come admin"){
					alert(response+" : effettua il logout ed accedi come user")
				}
				else{
					svuotaScommessa();
					alert("scommessa registrata con successo");
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
		$('.btn-danger').addClass("btn-info");
		$('.btn-danger').removeClass("btn-danger");
	}
	
	function pulisciScommessa(){
		$.ajax({
			url:'scommetti',
			data: 'svuota = null',
			type:'POST',
            cache:false,
            error:function(){alert('error');}
		}).done(function(response){

			svuotaScommessa();
		});
	}
