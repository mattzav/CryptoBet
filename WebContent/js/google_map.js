var google;

function init() {
	var latlng = new google.maps.LatLng(39.363156, 16.226185);

	var opzioniMappa = {
		zoom : 10,
		center : latlng,

		scrollwheel : false,
		styles : [ {
			"featureType" : "administrative.land_parcel",
			"elementType" : "all",
			"stylers" : [ {
				"visibility" : "off"
			} ]
		}, {
			"featureType" : "landscape.man_made",
			"elementType" : "all",
			"stylers" : [ {
				"visibility" : "off"
			} ]
		}, {
			"featureType" : "poi",
			"elementType" : "labels",
			"stylers" : [ {
				"visibility" : "off"
			} ]
		}, {
			"featureType" : "road",
			"elementType" : "labels",
			"stylers" : [ {
				"visibility" : "simplified"
			}, {
				"lightness" : 20
			} ]
		}, {
			"featureType" : "road.highway",
			"elementType" : "geometry",
			"stylers" : [ {
				"hue" : "#f49935"
			} ]
		}, {
			"featureType" : "road.highway",
			"elementType" : "labels",
			"stylers" : [ {
				"visibility" : "simplified"
			} ]
		}, {
			"featureType" : "road.arterial",
			"elementType" : "geometry",
			"stylers" : [ {
				"hue" : "#fad959"
			} ]
		}, {
			"featureType" : "road.arterial",
			"elementType" : "labels",
			"stylers" : [ {
				"visibility" : "off"
			} ]
		}, {
			"featureType" : "road.local",
			"elementType" : "geometry",
			"stylers" : [ {
				"visibility" : "simplified"
			} ]
		}, {
			"featureType" : "road.local",
			"elementType" : "labels",
			"stylers" : [ {
				"visibility" : "simplified"
			} ]
		}, {
			"featureType" : "transit",
			"elementType" : "all",
			"stylers" : [ {
				"visibility" : "off"
			} ]
		}, {
			"featureType" : "water",
			"elementType" : "all",
			"stylers" : [ {
				"hue" : "#a1cdfc"
			}, {
				"saturation" : 30
			}, {
				"lightness" : 49
			} ]
		} ]
	};

	var elementoMappa = document.getElementById('map');

	var mappa = new google.maps.Map(elementoMappa, opzioniMappa);

	var input = (document.getElementById('pac-input'));

	var boxRicerca = new google.maps.places.SearchBox((input));
	
	google.maps.event.addListener(boxRicerca, 'places_changed', function() {
		var posti = boxRicerca.getPlaces();

		if (posti.length == 0) {
			return;
		}

		posti.forEach(function(posto) {
			calcolaPercorso(posto.geometry.location);
		});
	});

	var indirizzi = [ 'Dipartimento di matematica e informatica universita della calabria','Lamezia Terme'];

	for (var x = 0; x < indirizzi.length; x++) {
		$
				.getJSON(
						'http://maps.googleapis.com/maps/api/geocode/json?address='
								+ indirizzi[x] + '&sensor=false',
						null,
						function(data) {

							var p = data.results[0].geometry.location
							var latlng = new google.maps.LatLng(p.lat, p.lng);
							var marker = new google.maps.Marker({
								position : latlng,
								map : mappa,
								icon : 'images/loc.png'
							});
							var infowindow = new google.maps.InfoWindow();

							google.maps.event
									.addListener(
											marker,
											'click',
											function() {
												infowindow
														.setContent("Sede CryptoBet dove potrai giocare le tue scommesse!");
												infowindow.open(mappa, this);
											});

						});
	}

}
google.maps.event.addDomListener(window, 'load', init);

function calcolaEMostraPercorso(servizioDirezione, displayDirezione, puntoA,
		puntoB) {
	servizioDirezione.route({
		origin : puntoA,
		destination : puntoB,
		travelMode : google.maps.TravelMode.DRIVING
	}, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			displayDirezione.setDirections(response);
		} else {
			window.alert('Richiesta fallita ' + status);
		}
	});
}

function calcolaPercorso(posizioneArrivo) {

	var puntoA1 = {lat:39.363156,long: 16.226185};
	var puntoA2 = {lat:38.962912,long: 16.3092719};
	var punti = [puntoA1,puntoA2];
	
	var piuVicino = punti[0];
	
	
	if(Math.abs(punti[1]["lat"]-posizioneArrivo.lat())+Math.abs(punti[1]["long"]-posizioneArrivo.lng()) < Math.abs(punti[0]["lat"]-posizioneArrivo.lat())+Math.abs(punti[0]["long"]-posizioneArrivo.lng()))
	{
		piuVicino= punti[1];
	}
	
	var puntoA = new google.maps.LatLng(piuVicino["lat"], piuVicino["long"]), puntoB = posizioneArrivo, myOptions = {
		zoom : 7,
		center : puntoA
	}, map = new google.maps.Map(document.getElementById('map'), myOptions),

	servizioDirezione = new google.maps.DirectionsService, displayDirezione = new google.maps.DirectionsRenderer(
			{

				map : map
			}), markerA = new google.maps.Marker({
		position : puntoA,
		title : "punto A",
		label : "A",
		map : map
	}), markerB = new google.maps.Marker({
		position : puntoB,
		title : "punto B",
		label : "B",
		map : map
	});

	
	calcolaEMostraPercorso(servizioDirezione, displayDirezione, puntoA,
			puntoB);

}