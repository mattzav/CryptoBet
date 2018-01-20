/**
 * 
 */

function caricaScudettiIniziali(){
	$(".page").text("1");
	$.ajax({
		type : 'GET',
		dataType: 'json',
		url : 'partiteEScudetti',
		success : function(data) {
			$.each(data, function(i,item){
				if(i.includes("squadra"))
					$(".tabellaScudetti").append("<tr class=\"success\">"+"<td><strong> "+item.nome+"</strong></td>"+"<td><img width=\"75\" height=\"75\" src=\""+item.scudetto+"\"></td><tr>");
				else if(i== "ultimaPagina" && item.ultima)
					$(".next").addClass("disabled");
			});
		}
	});
}


function caricaScudetti(metodo){
	if((metodo && !$(".previous").hasClass("disabled")) || (!metodo && !$(".next").hasClass("disabled"))){
			if(metodo){
				$(".next").removeClass("disabled");
				$(".page").text(parseInt($(".page").text())-1);
			}
			else
				$(".page").text(parseInt($(".page").text())+1);
	}
	
	var paginaCorrente = parseInt($(".page").text());
	$(".tabellaScudetti tr").remove();
	$.ajax({
		type : 'POST',
		dataType : 'json',
		data : {numeroPagina:paginaCorrente},
		url : 'partiteEScudetti',
		success : function(data) {
			$.each(data, function(i,item){
				if(i.includes("squadra"))
					$(".tabellaScudetti").append("<tr class=\"success\">"+"<td> <strong>"+item.nome+" </strong></td>"+"<td><img width=\"75\" height=\"75\" src=\""+item.scudetto+"\"></td><tr>");
				else if(i== "ultimaPagina" && item.ultima){
					if(metodo)
						$(".previous").addClass("disabled");
					else
						$(".next").addClass("disabled");
				}
			});
		}
	});
	if(parseInt(paginaCorrente) == 1){
		$(".previous").addClass("disabled");
	}
	else{
		$(".previous").removeClass("disabled");

	}
}
