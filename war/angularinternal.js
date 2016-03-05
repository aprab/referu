var myApp = angular.module('myApp', []);

myApp.controller('MyController', ['$scope','$location', function($scope,$location) {

	function gup( name, url ) {
		if (!url) url = location.href;
		name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
		var regexS = "[\\?&]"+name+"=([^&#]*)";
		var regex = new RegExp( regexS );
		var results = regex.exec( url );
		return results == null ? null : results[1];
	}

	$scope.allLocations = [{
	   "name": "Bates",
	   "url": "http://www.bates.edu/wp-content/uploads/2014/05/hathorn_bates_6637390405_f95e0a72d6_b.jpg"
	}, {
	   "name": "Claremont",
	   "url": "http://signaturebooks.com/wp-content/uploads/2010/03/claremont.jpg.jpeg"
	}, {
	   "name": "Georgetown",
	   "url": "https://uadmissions.georgetown.edu/sites/uadmissions/files/styles/rotator_image_overlay_unit_profile/public/aerialview.jpg"
	}, {
	   "name": "Cornell",
	   "url": "https://fe.fs.cornell.edu/img/mainPage/ArtsQuad.jpg"
	}, {
	   "name": "Notre Dame",
	   "url": "http://budget.nd.edu/assets/88150/original/budget.jpg"
	}];

   $scope.frontPageListings = [

	   {
		   "price": 1885,
		   "description": "Gables city Vista",
		   "address": "460 L St NW, Washington, DC 20001",
		   "phone": "(866) 484-8245",
		   "url": "http://gables.com/assets/images/215571/52cf7a5577aa0593.jpg",
		   "Latitude" : 38.9035370,
			"Longitude" : -77.0181230
	   }, {
		   "price": 1990,
		   "description": "Meridian at Mount Vernon",
		   "address": "425 L St NW, Washington, DC 20001",
		   "phone": "(202) 969-8484",
		   "url": "http://medialibrarycdn.entrata.com/media_library/1735/555f5be902ce3363.jpg",
		   	"Latitude" : 38.9041120,
			"Longitude" : -77.0174320

	   }, {
			"price": 1705,
			"description": "Meridian at Gallery Place",
			"address": "450 Massachusetts Ave NW, Washington, DC 20001",
			"phone": "(289)-291-0265",
			"url": "http://cdn.aptimg.com/review/ugc/images/g9tWxeMqWo6.jpg",
			"Latitude" : 38.9004920,
			"Longitude" : -77.0181900
	   }
   ];

   $scope.categories = ["Full Apartment","Shared Accomodation","Dorms"];

	$scope.currentLocation = gup("name");

	if($scope.currentLocation  == null)
		return;

	$scope.title = "Listings at " + $scope.currentLocation;
	
	$scope.currentObject = {reference:{}};

	for( var i = 0 ; i < $scope.allLocations.length ; i++){

		if($scope.allLocations[i].name == $scope.currentLocation) {

			$scope.currentObject.currentObject = $scope.allLocations[i];

			console.log("Found");
			
			break;
		}
	}

	L.mapbox.accessToken = 'pk.eyJ1IjoiaGljb2RlciIsImEiOiJjaWtxZGdsZGUwMHhxdWltNGFkOXczdjJtIn0.M7vzB1dGeBR1JoxBYWCpkA';
	var map = L.mapbox.map('map', 'mapbox.emerald')
	    .setView([38.903, -77.018], 15);


	// Credit Foursquare for their wonderful data
	// map.attributionControl
	//     .addAttribution('<a href="https://foursquare.com/">Places data from Foursquare</a>');

	// Create a Foursquare developer account: https://developer.foursquare.com/
	// NOTE: CHANGE THESE VALUES TO YOUR OWN:
	// Otherwise they can be cycled or deactivated with zero notice.
	var CLIENT_ID = 'L4UK14EMS0MCEZOVVUYX2UO5ULFHJN3EHOFVQFSW0Z1MSFSR';
	var CLIENT_SECRET = 'YKJB0JRFDPPSGTHALFOEP5O1NDDATHKQ2IZ5RO2GOX452SFA';

	// Keep our place markers organized in a nice group.
	var foursquarePlaces = L.layerGroup().addTo(map);

	for (var i = 0; i < $scope.frontPageListings.length; i++) {
      var venue = $scope.frontPageListings[i];
      var latlng = L.latLng(venue.Latitude, venue.Longitude);
      var marker = L.marker(latlng, {
          icon: L.mapbox.marker.icon({
            'marker-color': '#BE9A6B',
            'marker-symbol': 'building',
            'marker-size': 'large'
          })
        })
      .bindPopup('<strong><a href="https://foursquare.com/v/' + venue.id + '">' +
        venue.name + '</a></strong>')
        .addTo(foursquarePlaces);
    }

	



}]);