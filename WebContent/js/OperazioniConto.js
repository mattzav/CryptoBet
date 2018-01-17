/**
 * 
 */
function checkImporto() {
	var value = $(".importo").val();
	$(".importo").addClass("btn-default");
	$(".importo").removeClass("btn-danger");
	$(".effettuaMovimento").prop('disabled', false);
	var importoErrato = "false";
	if (!($.isNumeric(value))) {
		$(".importo").removeClass("btn-default");
		$(".importo").addClass("btn-danger");
		$(".effettuaMovimento").prop('disabled', true);
		impotoErrato = "true";
	}
}
function mostraUltimeScommesse() {
	$("#operazione").remove();
	$("#operazioni").append(
			"<div id=\"operazione\">" + "<h3 id=\"nomeOperazione\"> "
					+ "ECCO LE TUE SCOMMESSE:" + "</h3>" + "</div>");
	$("#operazione").append("<table class=\"table\"></table>");
	var firstRow = "<tr>" + "<th> codici scommessa </th>"
			+ "<th > data di emissione </th>" + "<th > importo scommessa </th>"
			+ "<th > numero esiti </th>" + "<th > vincita potenziale </th>"
			+ "<th > stato </th>" + "<th > verifica </th>" + "</tr>";
	$("#operazione>table").append(firstRow);
	$
			.ajax({
				type : 'GET',
				url : 'mostraScommesse',
				success : function(data) {
					var scommesse = data.split(";");
					for (var i = 0; i < scommesse.length - 1; i++) {
						var dati = scommesse[i].split(":");
						var classe = "warning";
						for (var j = 0; j < dati.length; j++) {
							var verifica = "";
							if (!(dati[5] == "vinta" || dati[5] == "persa")) {
								verifica = "<td id=\""+dati[0]+"\" class=\"verificata\"><button class=\"glyphicon glyphicon-ok\"  onclick=\"verificaScommessa("
										+ dati[0] + ");\"></td>";
							} else {
								if (dati[5] == "vinta")
									classe = "success";
								else
									classe = "danger";
								verifica = '<td> Gia\' verificata</td>';
							}

						}
						$("#operazione>table").append(
								"<tr id=\""+dati[0]+"\" class=\"" + classe + "\">" + "<td>"
										+ dati[0] + "</td>" + "<td>" + dati[1]
										+ "</td>" + "<td>" + dati[2] + "</td>"
										+ "<td>" + dati[3] + "</td>" + "<td>"
										+ dati[4] + "</td>"
										+ "<td id=\""+dati[0]+"\" class=\"stato\">" + dati[5]
										+ "</td>" + verifica + "</tr>");
					}
				},
			});
}

function verificaScommessa(codice) {
	$.ajax({
		type : 'POST',
		url : 'verificaScommessa',
		data : {
			codiceScommessa : codice
		},
		error : function() {
			alert("buonasera");
		},
		success : function(data) {
			alert(data);
			if (data != "non conclusa") {
				var stato=data.split(";")[0];
				if(data.contains(";")){
					var saldo=data.split(";")[1];
				}
				$("#" + codice + " .stato").text(stato);
				$("#" + codice + " .verificata").remove();
				$("#" + codice).append('<td> Gia\' verificata</td>');
				if(stato == "vinta"){
					$("#" + codice).removeClass("warning");
					$("#" + codice).addClass("success");
					$("#saldoConto").text("Saldo conto : "+saldo);
				}
				else{
					$("#" + codice).addClass("danger");
				}
			}
		}
	});
}
function effettuaPrelievo() {
	$("#operazione").remove();
	$("#operazioni")
			.append(
					"<div id=\"operazione\">"
							+ "<div class=\"col-sm-4\"></div>"
							+ "<div class=\"col-sm-8\">"
							+ "<div>"
							+ "<h3> EFFETTUA IL TUO PRELIEVO </h3>"
							+ "</div>"
							+ "<div>"
							+ "<span> Inserisci l'importo da prelevare :</span> <br>"
							+ "<input class=\"btn btn-default importo\" id=\"importoDaPrelevare\" type=\"text\" onkeyup=\"checkImporto()\"> <br>"
							+ "</div>"
							+ "<div>"
							+ "<input class=\"btn btn-primary effettuaMovimento\" type=\"button\" value=\"Preleva\" onclick=\"preleva()\" disabled=\"true\">"
							+ "</div>" + "</div>" + "</div>");
}
function preleva() {
	
	$.ajax({
		url : 'prelievoConto',
		data : 'importo=' + $("#importoDaPrelevare").val() + '',
		type : 'POST',
		cache : false,
		error : function() {
			alert('error');
		}
	}).done(function(response) {
		var format=/^Errore/;
		if (format.test(response)) {
			addResponse("danger",response.substr(8,response.length));
		}
		else{
			var dati=response.split(";");
			addResponse("success",dati[0].substr(16,response.length));
			$("#saldoConto").text("Saldo conto : "+dati[1]);
		}
	});
}
function effettuaVersamento() {

	$("#operazione").remove();
	$("#operazioni")
			.append(
					"<div id=\"operazione\">"
							+ "<div class=\"col-sm-4\"></div>"
							+ "<div class=\"col-sm-8\">"
							+ "<div>"
							+ "<h3> EFFETTUA IL TUO VERSAMENTO </h3>"
							+ "</div>"
							+ "<div>"
							+ "<span> Inserisci l'importo da versare :</span> <br>"
							+ "<input class=\"btn btn-default importo\" id=\"importoDaVersare\" type=\"text\" onkeyup=\"checkImporto()\"> <br>"
							+ "</div>"
							+ "<div>"
							+ "<input class=\"btn btn-primary effettuaMovimento\" type=\"button\" value=\"Versa\" onclick=\"versa()\" disabled=\"true\">"
							+ "</div>" + "</div>" + "</div>");
}
function versa() {
	$.ajax({
		url : 'versamentoConto',
		data : 'importo=' + $("#importoDaVersare").val() + '',
		type : 'POST',
		cache : false,
		error : function() {
			alert('error');
		}
	}).done(function(response) {
		var format=/^Errore/;
		if (format.test(response)) {
			addResponse("danger",response.substr(8,response.length));
		}
		else{
			var dati=response.split(";");
			addResponse("success",dati[0].substr(16,response.length));
			$("#saldoConto").text("Saldo conto : "+dati[1]);
		}
	});
}
function getListaMovimenti() {

	$.ajax({
				url : 'listaMovimenti',
				type : 'get',
				cache : false,
				error : function() {
					alert('error');
				}
			})
			.done(
					function(response) {
						$("#operazione").remove();
						$("#operazioni")
								.append(
										"<div id=\"operazione\">"
												+ "<h3> ECCO I TUOI MOVIMENTI </h3>"
												+ "<table id=\"tableMovimenti\" class=\"table\">"
												+ "<thead>"
												+ "<tr>"
												+ "<th> Descrizione </th>"
												+ "<th> Codice transazione</th>"
												+ "<th> Importo </th>"
												+ "<th> Tipo </th>"
												+ "<th> Scommessa </th>"
												+ "</tr>" + "</thead>"
												+ "<tbody id=\"lista\">"
												+ "</tbody>" + "</table>"
												+ "</div>");
						var rows = response.split("\n");
						$.each(rows, function(i, item) {
							if (item != "") {
								var dati = item.split(";");
								if (dati.length == 3) {
									var tipo = "VERSAMENTO";
									var classe = "success";
									if (dati[2] == 1) {
										tipo = "PRELIEVO";
										classe = "danger"
									}
									$("#lista").append(
											"<tr class=\"" + classe + "\">"
													+ "<td>"
													+ "Transazione dalla Carta"
													+ "</td>" + "<td>"
													+ dati[0] + "</td>"
													+ "<td>" + dati[1]
													+ "</td>" + "<td>" + tipo
													+ "</td>" + "<td>" + " / "
													+ "</td>" + "</tr>");
								} else {
									var classe = "success";
									if (dati[2] == "PRELIEVO") {
										classe = "danger";
									}
									$("#lista").append(
											"<tr class=\"" + classe + "\">"
													+ "<td>"
													+ "Transazione dal conto"
													+ "</td>" + "<td>"
													+ dati[0] + "</td>"
													+ "<td>" + dati[1]
													+ "</td>" + "<td>"
													+ dati[2] + "</td>"
													+ "<td>" + dati[3]
													+ "</td>" + "</tr>");
								}
							}
						});
					});
}
function addResponse(type,message){
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