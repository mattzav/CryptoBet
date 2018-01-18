/**
 * 
 */

// questa funzione viene invocata quando l'amminstratore preme su un esito per abilitarlo/disabilitarlo.
// viene effettuata una chiamata ajax ad una servlet che applicherà la modifica sul db 
// e poi viene cambiato il colore, tramite jquery, del bottone relativo all'esito selezionato.

function abilitaEsito(esito) {
	$.ajax({
		type : 'POST',
		url : 'aggiornaQuote',
		data : esito,
		success : function() {
			var selezionato = $('[name="' + esito + '"]');
			if (selezionato.hasClass("btn-danger")) {
				selezionato.removeClass("btn-danger");
				selezionato.addClass("btn-info");
			} else {

				selezionato.removeClass("btn-info");
				selezionato.addClass("btn-danger");
			}
		},
	});
}


// questa funzione viene invocata ogni qual volta l'amministratore compila la form per la modifica di un esito
// viene effettuato un pattern matching per verificare che l'esito e la quota siano inseriti nel formato corretto.
// tramite jquery vengono mostrati eventuali errori al client.
function controllaEsitoEQuota(partita) {
	var esito = $(
			"#inputModificaQuota" + partita + " " + '[name="' + "esitoScelto"
					+ '"]').val();
	var quota = $(
			"#inputModificaQuota" + partita + " " + '[name="' + "nuovaQuota"
					+ '"]').val();

	var errore = "";
	var pattern_esito = /^(1|12|1X|X2|2|GG|NG|U|O|X)$/;
	var pattern_quota = /^\d+(\.\d+)*$/;
	if (!pattern_esito.test(esito) && !pattern_quota.test(quota)) {
		errore += "hai inserito un esito non valido ed anche una quota non valida";
		$("#inputModificaQuota" + partita + " .aggiorna")
				.prop("disabled", true);
		$("#inputModificaQuota" + partita + " .suggerimento")
		.prop("disabled", true);
	} else if (!pattern_esito.test(esito)) {
		$("#inputModificaQuota" + partita + " .aggiorna")
				.prop("disabled", true);
		$("#inputModificaQuota" + partita + " .suggerimento")
		.prop("disabled", true);
		errore += "hai inserito un esito non valido";
	} else if (!pattern_quota.test(quota)) {
		$("#inputModificaQuota" + partita + " .aggiorna")
				.prop("disabled", true);
		$("#inputModificaQuota" + partita + " .suggerimento").prop("disabled",
				false);
		errore += "hai inserito una quota non valida";
	} else {
		$("#inputModificaQuota" + partita + " .aggiorna").prop("disabled",
				false);
		$("#inputModificaQuota" + partita + " .suggerimento").prop("disabled",
				false);
	}

	$("#alert" + partita + ".alert").remove();
	if (errore != "") {
		$(".partitaInModifica" + partita)
				.parent()
				.children()
				.first()
				.append(
						"<div id=\"alert"
								+ partita
								+ "\" class=\"alert alert-danger alert-dismissable\">"
								+ "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"
								+ "<strong>Attenzione!</strong> " + errore
								+ "</div>");
	}
}


// questa funzione viene invocata quando, dopo aver compilato la form, l'amministratore decide di effettuare la modifica.
// vengono presi tramite jquery i dati inseriti e viene effettuata una chiamata alla servlet che rendera persistenti le modifiche
function aggiornaEsito(partita) {
	var esito = $(
			"#inputModificaQuota" + partita + " " + '[name="' + "esitoScelto"
					+ '"]').val();
	var quota = $(
			"#inputModificaQuota" + partita + " " + '[name="' + "nuovaQuota"
					+ '"]').val();

	$("#alert" + partita + ".alert").remove();
	$(".partitaInModifica" + partita)
			.parent()
			.children()
			.first()
			.append(
					"<div id=\"alert"
							+ partita
							+ "\" class=\"alert alert-success alert-dismissable\">"
							+ "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"
							+ "<strong>Congratulazioni!</strong> hai aggiornato correttamente la tua quota"
							+ "</div>");
	$.ajax({
		type : 'POST',
		url : 'modificaQuota',
		data : {
			esitoScelto : esito,
			nuovaQuota : quota,
			partita : partita
		},
		success : function() {
			$('[name="' + partita + ";" + esito + '"]').val(quota);
		}
	});

}


// questa funzione viene invocata quando l'amministratore decide di effettuare una modifica di un esito per una partita
// tramite jquery viene fornita all'amministratore la nuova form per compilare i dati relativi alla modifica
function selezionaPartitaDaModificare(partita, disabilita) {
	if ($("#inputModificaQuota" + partita).length) {
		disabilita = true;
	}
	if (!disabilita) {
		$(".partitaInModifica" + partita)
				.parent()
				.children()
				.first()
				.append(
						"<div id=\"inputModificaQuota"
								+ partita
								+ "\"> <div class=\"modal-body\"><div class=\"form-group\"><label for=\"esitoScelto\" class=\"form-control-label\">Esito da modificare:</label><input onkeyup=\"controllaEsitoEQuota("
								+ partita
								+ ")\" type=\"text\" class=\"form-control\"name=\"esitoScelto\"></div><div class=\"form-group\"><label for=\"nuovaQuota\" class=\"form-control-label\">Nuova Quota:</label><textarea onkeyup=\"controllaEsitoEQuota("
								+ partita
								+ ")\" class=\"form-control\" name=\"nuovaQuota\"></textarea></div></div><div class=\"modal-footer\"><button name=\"annulla\" onclick=\"selezionaPartitaDaModificare("
								+ partita
								+ ",true)\" class=\"btn btn-secondary btn-sm\"data-dismiss=\"modal\">Annulla</button><button disabled=\"disabled\" name=\"aggiorna\" onclick=\"aggiornaEsito("
								+ partita
								+ ")\" class=\"btn btn-primary btn-sm aggiorna\">Aggiorna Esito</button><button  disabled=\"disabled\" name=\"suggerimento\" onclick=\"richiediSuggerimento("
								+ partita
								+ ")\" class=\"btn btn-info btn-sm suggerimento\"> Richiedi Suggerimento</button></div></div>");
	} else {
		$("#inputModificaQuota" + partita).remove();
	}
}


// questa funzione viene invocata quando l'amministratore decide di richiede il suggerimento di una quota.
// viene invocata la servlet che si preoccuperà di restituire tale risultato
function richiediSuggerimento(partita) {

	var esito = $(
			"#inputModificaQuota" + partita + " " + '[name="' + "esitoScelto"
					+ '"]').val();
	$.ajax({
		type : 'POST',
		url : 'modificaQuota',
		data : {
			suggerimento : partita,
			esitoScelto : esito
		},
		success : function(quota) {
			$(
					"#inputModificaQuota" + partita + " " + '[name="'
							+ "nuovaQuota" + '"]').val(quota);

		}
	});
}
