var google;

function init() {
	var myLatlng = new google.maps.LatLng(39.363156, 16.226185);

	var mapOptions = {
		zoom : 10,
		// The latitude and longitude to center the map (always required)
		center : myLatlng,

		// How you would like to style the map.
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

	// Get the HTML DOM element that will contain your map
	// We are using a div with id="map" seen below in the <body>
	var mapElement = document.getElementById('map');

	// Create the Google Map using out element and options defined above
	var map = new google.maps.Map(mapElement, mapOptions);

	var input = (document.getElementById('pac-input'));
	map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

	var searchBox = new google.maps.places.SearchBox((input));
	
	google.maps.event.addListener(searchBox, 'places_changed', function() {
		var places = searchBox.getPlaces();

		if (places.length == 0) {
			return;
		}

		places.forEach(function(place) {
			calcolaPercorso(place.geometry.location);
		});
	});

	var addresses = [ 'Dipartimento di matematica e informatica universita della calabria	' ];

	for (var x = 0; x < addresses.length; x++) {
		$
				.getJSON(
						'http://maps.googleapis.com/maps/api/geocode/json?address='
								+ addresses[x] + '&sensor=false',
						null,
						function(data) {

							var p = data.results[0].geometry.location
							var latlng = new google.maps.LatLng(p.lat, p.lng);
							var marker = new google.maps.Marker({
								position : latlng,
								map : map,
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
												infowindow.open(map, this);
											});

						});
	}

}
google.maps.event.addDomListener(window, 'load', init);

function calculateAndDisplayRoute(directionsService, directionsDisplay, pointA,
		pointB) {
	directionsService.route({
		origin : pointA,
		destination : pointB,
		travelMode : google.maps.TravelMode.DRIVING
	}, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
		} else {
			window.alert('Directions request failed due to ' + status);
		}
	});
}

function calcolaPercorso(pos) {

	var pointA = new google.maps.LatLng(39.363156, 16.226185), pointB = pos, myOptions = {
		zoom : 7,
		center : pointA
	}, map = new google.maps.Map(document.getElementById('map'), myOptions),
	// Instantiate a directions service
	directionsService = new google.maps.DirectionsService, directionsDisplay = new google.maps.DirectionsRenderer(
			{

				map : map
			}), markerA = new google.maps.Marker({
		position : pointA,
		title : "point A",
		label : "A",
		map : map
	}), markerB = new google.maps.Marker({
		position : pointB,
		title : "point B",
		label : "B",
		map : map
	});

	// get route from A to
	calculateAndDisplayRoute(directionsService, directionsDisplay, pointA,
			pointB);

}