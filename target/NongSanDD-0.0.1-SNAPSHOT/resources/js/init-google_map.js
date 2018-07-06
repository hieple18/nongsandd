	var initAdress = "Huyện Đơn Dương, Tỉnh Lâm Đồng";
	var communeAdress;
	var map;
	var geocoder = new google.maps.Geocoder();
	var myMarker = null;
	function initialize() {
		map = new google.maps.Map(document.getElementById('upload-sale-map'), {
			center : currentLocation,
			zoom : mapZoom,
			mapTypeId : 'roadmap'
		});
		/* loadGeocoder(initAdress, 10); */
		
		
		map.addListener('click', function(e) {
			placeMarkerAndPanTo(e.latLng, map);
			$("#map_lat").val(e.latLng.lat());
		  	$("#map_lng").val(e.latLng.lng());
		});

		var input = document.getElementById('pac-input');
		var searchBox = new google.maps.places.SearchBox(input);
		map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
		$('#pac-input').val(initAdress);
		
		if(updateFlag === true){
			placeMarkerAndPanTo(currentLocation, map);
			$('#pac-input').val(previousAddress);
			communeAdress = $("#select-village option:selected").text() + initAdress;
		}

		// Bias the SearchBox results towards current map's viewport.
		map.addListener('bounds_changed', function() {
			searchBox.setBounds(map.getBounds());
		});

		searchBox.addListener('places_changed', function() {
			var places = searchBox.getPlaces();

			if (places.length == 0) {
				return;
			}

			var markers = [];
			// Clear out the old markers.
			markers.forEach(function(marker) {
				marker.setMap(null);
			});
			markers = [];

			// For each place, get the icon, name and location.
			var bounds = new google.maps.LatLngBounds();
			places.forEach(function(place) {
				if (!place.geometry) {
					console.log("Returned place contains no geometry");
					return;
				}
				var icon = {
					url : place.icon,
					size : new google.maps.Size(71, 71),
					origin : new google.maps.Point(0, 0),
					anchor : new google.maps.Point(17, 34),
					scaledSize : new google.maps.Size(25, 25)
				};

				// Create a marker for each place.
				markers.push(new google.maps.Marker({
					map : map,
					icon : icon,
					title : place.name,
					position : place.geometry.location
				}));

				if (place.geometry.viewport) {
					// Only geocodes have viewport.
					bounds.union(place.geometry.viewport);
				} else {
					bounds.extend(place.geometry.location);
				}
			});

			map.fitBounds(bounds);
		});
	}
	google.maps.event.addDomListener(window, 'load', initialize);
	
	
	
	function placeMarkerAndPanTo(latLng, map) {
		if (myMarker != null) {
			myMarker.setMap(null);
		}
		myMarker = new google.maps.Marker({
			position : latLng,
			map : map
		});
		map.panTo(latLng);
	}

	$('#select-village').on('change', function() {
		var name = $("#select-village option:selected").text();
		name = name + ", " + communeAdress;
		loadGeocoder(name, 15);
		return true;
	});
		
	$('#select-commune').on('change', function() {
		$('#select-village option').remove();

		var lstmaxa = $("#select-commune").val();
		
		$.ajax({
			data : {
				"lstmaxa" : lstmaxa
			},
			url : "/NongSanDD/lay-ds-thon",
			type : "POST",

			success : function(data) {
				$("#select-village").append($('<option>', {
					value : null,
					text : null
				}));
				$.each(data, function(index) {
					$("#select-village").append($('<option>', {
						value : data[index][0],
						text : data[index][1]
					}));
				})
			}
		});

		var name = $("#select-commune option:selected").text();
		communeAdress = name + ", " + initAdress;
		loadGeocoder(communeAdress, 12);

		return true;
	});

	function loadGeocoder(myAdress, zoom) {
		if (geocoder) {
			geocoder.geocode({
				'address' : myAdress
			}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					if (status != google.maps.GeocoderStatus.ZERO_RESULTS) {
						map.setCenter(results[0].geometry.location);
						map.setZoom(zoom);
						$('#pac-input').val(myAdress);
					}
				}
			});
		}
	};