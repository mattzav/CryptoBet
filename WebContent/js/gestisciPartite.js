/**
 * 
 */
function getSquadre() {
	
	$("#aggiornaSquadre").append("<div id=\"loadingSquadre\" class=\"loader\"></div>");
	$("#squadre").prop("disabled",true);
	var squadre = [];
	var campionati = [];
	var scudetti = [];

	$.ajax({
		headers : {
			'X-Auth-Token' : '9c8c10fb6ee545a5a46161e402d73dee'
		},
		url : 'http://api.football-data.org/v1/competitions/?season=2017',
		dataType : 'json',
		type : 'GET',
		async : false,
	}).done(function(response) {
		$.each(response, function(index, item) {
			campionati.push(item.id);
			campionati.push(item.caption);
			var _url = item._links.teams.href;
			$.ajax({
				headers : {
					'X-Auth-Token' : '9c8c10fb6ee545a5a46161e402d73dee'
				},
				url : _url,
				dataType : 'json',
				type : 'GET',
				async : false,
			}).done(function(_response) {
				$.each(_response, function(i, _item) {
					if (i == "teams") {
						$.each(_item, function(_i, item2) {
							squadre.push(item2.name);
						});
					}
				});
			});
			_url="http://api.football-data.org/v1/competitions/"+item.id+"/teams";
			$.ajax({
				headers : {
					'X-Auth-Token' : '9c8c10fb6ee545a5a46161e402d73dee'
				},
				url : _url,
				dataType : 'json',
				type : 'GET',
				async : false,
			}).done(function(_response) {
				$.each(_response, function(i, _item) {
						if (i == "teams") {
							$.each(_item, function(_i, item2) {
								if(item2.crestUrl != null){
									scudetti.push(item2.name+"endScudetto"+item2.crestUrl+"endSquadra");
								}
							});
						}
				});
			});
		});
	});
	var result_squadre = "";
	var result_campionati = "";
	var result_scudetti="";
	$.each(squadre, function(i, item) {
		result_squadre = result_squadre.concat(item);
		result_squadre = result_squadre.concat(";");
	});

	$.each(campionati, function(i, item) {
		result_campionati = result_campionati.concat(item);
		if (i % 2 == 0)
			result_campionati = result_campionati.concat(":");
		else
			result_campionati = result_campionati.concat(";");
	});
	
	$.each(scudetti, function(i, item) {
		result_scudetti+=item;
	});
	$.ajax({
		url : 'aggiornaSquadreCampionati',
		type : 'POST',
		data : {
			squadre : result_squadre,
			campionati : result_campionati,
			scudetti : result_scudetti,
			aggiorna : "Aggiorna"
		},
		error : function() {
			alert("error passaggio dati");
			$("#loadingSquadre").remove();
			$("#squadre").prop("disabled",false);
		},
	}).done(function(response){
			$("#loadingSquadre").remove();
			$("#squadre").prop("disabled",false);
	});
	
}

function getPartite() {
	$("#aggiornaPartite").append("<div id=\"loadingPartite\" class=\"loader\"></div>");
	$("#partite").prop("disabled",true);
	var data = new Date();
	var data_inizio = new Date(data.getTime() - 1000 * 60 * 60 * 24 * 7);
	var data_inizio_string = data_inizio.getFullYear() + "-"
			+ (data_inizio.getMonth() + 1) + "-" + data_inizio.getDate();
	if (data_inizio_string[7] != '-') {
		data_inizio_string = data_inizio_string.substr(0, 5) + "0"
				+ data_inizio_string.substr(5, 4);
	}
	if (data_inizio_string.length != 10) {
		data_inizio_string = data_inizio_string.substr(0, 8) + "0"
				+ data_inizio_string[8];
	}
	var data_fine = new Date(data.getTime() + 1000 * 60 * 60 * 24 * 7);
	var data_fine_string = data_fine.getFullYear() + "-"
			+ (data_fine.getMonth() + 1) + "-" + data_fine.getDate();
	if (data_fine_string[7] != '-') {
		data_fine_string = data_fine_string.substr(0, 5) + "0"
				+ data_fine_string.substr(5, 4);
	}
	if (data_fine_string.length != 10) {
		data_fine_string = data_fine_string.substr(0, 8) + "0"
				+ data_fine_string[8];
	}
	var _url = 'http://api.football-data.org/v1/fixtures/?timeFrameStart='
			+ data_inizio_string + '&timeFrameEnd=' + data_fine_string;

	var partite = [];
	$.ajax({
		headers : {
			'X-Auth-Token' : '9c8c10fb6ee545a5a46161e402d73dee'
		},
		url : _url,
		dataType : 'json',
		type : 'GET',
		async : false,
	}).done(
			function(response) {

				$.each(response, function(index, item) {
					if (index == "fixtures") {
						$.each(item, function(index2, item2) {
							var str = item2._links.competition.href;
							partite.push(str.substr(str.length - 3, str.length)
									+ "@" + item2.homeTeamName + "@"
									+ item2.awayTeamName + "@"
									+ item2.result.goalsHomeTeam + "@"
									+ item2.result.goalsAwayTeam + "@"
									+ item2.status + "@" + item2.date);
						});
					}
				});

			});
	var result = "";
	$.each(partite, function(i, item) {
		result = result.concat(item);
		result = result.concat(";");
	});
	$.ajax({
		url : 'aggiornaPartite',
		type : 'POST',
		data : {
			aggiorna : "Aggiorna Partite",
			partite : result
		},
		error : function() {
			alert("error partite");
			$("#loadingPartite").remove();
			$("#partite").prop("disabled",false);
		},
	}).done(function(){
		$("#loadingPartite").remove();
		$("#partite").prop("disabled",false);
	});
}