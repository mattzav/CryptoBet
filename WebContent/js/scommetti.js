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
				$(".vincita").text("Vincita potenziale : "+response);
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
	    		
	    		var elemento=$('[name="'+ esito +'"]');
				var dati=response.split(";");
	    		if(elemento.hasClass("btn-danger")){
	    			elemento.removeClass("btn-danger");
	    			elemento.addClass("btn-info");
	    		}
	    		else{
	    			elemento.removeClass("btn-info");
	    			elemento.addClass("btn-danger");
	    		}
	    		var res = esito.split(";");
	    		var e1=$("."+res[0]);
				if(e1.length){
					e1.remove();
				}
				else{
					if($(".scommessa").length){
						$(".scommessa").append('<tr class="'+res[0]+' info"><td colspan="2">'+dati[0]+' vs '+dati[1]+'</td><td>'+dati[2]+'</td><td>'+res[1]+'</td></tr>');
					}
				}
				$(".quotaTotale").text("Quota totale : "+dati[3]);
				$(".bonus").text("Bonus : "+dati[4]);
				$(".vincita").text("Vincita potenziale : "+dati[5]);
				
	    	});
	}
	function giocaScommessa(){
		if($(".importo").hasClass("btn-danger")){
			alert("Inserisci un importo valido");
		}
		else{
			$.ajax({
				url:'scommetti',
				data: 'giocaScommessa = null',
				type:'POST',
	            cache:false,
	            error:function(){alert('error');}
			}).done(function(response){
				svuotaScommessa();
				alert("Scommessa creata correttamente");
			});
		}
	}
	
	function svuotaScommessa(){
		$.each($(".scommessa > tr"),function(i,item){
			item.remove();
		});
		
		$.each($('.btn-danger'),function(i,item){
			alert(item.length);
		});
		
		var elements=$('.btn-danger');
		for(var i=0;i<elements.length;i++){
			alert(elements[i].lenght);
		}
	}
