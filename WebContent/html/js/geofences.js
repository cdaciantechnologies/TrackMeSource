function initMap() {
	
	var placeSearch;
	var autocomplete = new google.maps.places.Autocomplete((document
			.getElementById('geocomplete')), {
		types : [ 'geocode' ]
	});
	
	// When the user selects an address from the dropdown, populate the address fields in the form.
	
	autocomplete.addListener('place_changed', fillInAddress);

	function fillInAddress() {

		var place = autocomplete.getPlace();
		
		var latitude = place.geometry.location.lat();
		var longitude = place.geometry.location.lng();

		var map = new google.maps.Map(document.getElementById('map'), {
			center : {
				lat : latitude,
				lng : longitude
			},
			zoom : 16
		});

		var drawingManager = new google.maps.drawing.DrawingManager(
				{
					drawingMode : google.maps.drawing.OverlayType.MARKER,
					drawingControl : true,
					drawingControlOptions : {
						position : google.maps.ControlPosition.TOP_CENTER,
						drawingModes : [ 'circle', 'polygon', 'rectangle' ]
					},
					markerOptions : {
						icon : 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png'
					},
					circleOptions : {
						strokeColor : '#20B2AA',
						strokeOpacity : 0.8,
						fillColor : '#20B2AA',
						fillOpacity : 0.35,
						strokeWeight : 2,
						clickable : true,
						editable : true,
						zIndex : 1
					},
					polygonOptions : {
						strokeColor : '#20B2AA',
						strokeOpacity : 0.8,
						fillColor : '#20B2AA',
						fillOpacity : 0.35,
						strokeWeight : 2,
						clickable : true,
						editable : true,
						zIndex : 1
					},
					rectangleOptions : {
						strokeColor : '#20B2AA',
						strokeOpacity : 0.8,
						fillColor : '#20B2AA',
						fillOpacity : 0.35,
						strokeWeight : 2,
						clickable : true,
						editable : true,
						zIndex : 1
					}
				});
		drawingManager.setMap(map);

		google.maps.event
				.addListener(
						drawingManager,
						"overlaycomplete",
						function(event) {
							var lat, lng, coordinates, radius, radiusLatDeg, radiusLongDeg, len, init, i, latNorth, latSouth, lngNorth, lngSouth;
							if (typeof drawingShape !== 'undefined') {
								drawingShape.setMap(null);
							}
							drawingShape = event.overlay;
							if (event.type === google.maps.drawing.OverlayType.CIRCLE) {
								overlayClickListenerCircle(event.overlay);
								lat = event.overlay.getCenter().lat();
								lng = event.overlay.getCenter().lng();
								radius = event.overlay.getRadius() / 1000;
								radiusLatDeg = (1 / 110.574) * radius;
								radiusLongDeg = (1 / (121 * Math.cos(lat)))
										* radius;
								coordinates = circle(lat - radiusLatDeg, lng
										- radiusLongDeg, lat + radiusLatDeg,
										lng + radiusLongDeg, 40);
								console.log(coordinates);
								$('#vertices').val(
										JSON.stringify([ coordinates ]));
							} else if (event.type === google.maps.drawing.OverlayType.POLYGON) {
								overlayClickListenerPolygon(event.overlay);
								len = event.overlay.getPath().getLength();
								coordinates = [];
								init = null;
								for (i = 0; i < len; i++) {
									lat = event.overlay.getPath().getAt(i)
											.lat();
									lng = event.overlay.getPath().getAt(i)
											.lng();
									if (i === 0) {
										init = [ lat, lng ];
									}
									coordinates.push([ lat, lng ]);
								}
								coordinates.push(init);
								$('#vertices').val(
										JSON.stringify([ coordinates ]));
							} else if (event.type == google.maps.drawing.OverlayType.RECTANGLE) {
								overlayClickListenerRectangle(event.overlay);
								coordinates = [];
								latNorth = event.overlay.getBounds()
										.getNorthEast().lat();
								latSouth = event.overlay.getBounds()
										.getSouthWest().lat();
								lngNorth = event.overlay.getBounds()
										.getNorthEast().lng();
								lngSouth = event.overlay.getBounds()
										.getSouthWest().lng();
								coordinates.push([ latNorth, lngNorth ]);
								coordinates.push([ latNorth, lngSouth ]);
								coordinates.push([ latSouth, lngSouth ]);
								coordinates.push([ latSouth, lngNorth ]);
								coordinates.push([ latNorth, lngNorth ]);
								$('#vertices').val(
										JSON.stringify([ coordinates ]));
							}
						});
	}

	function overlayClickListenerCircle(overlay) {
		var lat, lng, coordinates, radius, radiusLatDeg, radiusLongDeg;
		google.maps.event.addListener(overlay, "click", function(event) {
			lat = overlay.getCenter().lat();
			lng = overlay.getCenter().lng();
			radius = overlay.getRadius() / 1000;
			radiusLatDeg = (1 / 110.574) * radius;
			radiusLongDeg = (1 / (121 * Math.cos(lat))) * radius;
			coordinates = circle(lat - radiusLatDeg, lng - radiusLongDeg, lat
					+ radiusLatDeg, lng + radiusLongDeg, 40);
			$('#vertices').val(JSON.stringify([ coordinates ]));
		});
	}

	function overlayClickListenerPolygon(overlay) {
		"use strict";
		var lat, lng, coordinates, len, init, i;
		google.maps.event.addListener(overlay, "click", function(event) {
			len = overlay.getPath().getLength();
			coordinates = [];
			init = null;
			for (i = 0; i < len; i++) {
				lat = overlay.getPath().getAt(i).lat();
				lng = overlay.getPath().getAt(i).lng();
				if (i === 0) {
					init = [ lat, lng ];
				}
				coordinates.push([ lat, lng ]);
			}
			coordinates.push(init);
			$('#vertices').val(JSON.stringify([ coordinates ]));
		});
	}

	function overlayClickListenerRectangle(overlay) {
		"use strict";
		var latNorth, latSouth, lngNorth, lngSouth, coordinates;
		google.maps.event.addListener(overlay, "click", function(event) {
			coordinates = [];
			latNorth = overlay.getBounds().getNorthEast().lat();
			latSouth = overlay.getBounds().getSouthWest().lat();
			lngNorth = overlay.getBounds().getNorthEast().lng();
			lngSouth = overlay.getBounds().getSouthWest().lng();
			coordinates.push([ latNorth, lngNorth ]);
			coordinates.push([ latNorth, lngSouth ]);
			coordinates.push([ latSouth, lngSouth ]);
			coordinates.push([ latSouth, lngNorth ]);
			coordinates.push([ latNorth, lngNorth ]);
			$('#vertices').val(JSON.stringify([ coordinates ]));
		});
	}

	function circle(x1, y1, x2, y2, nsides) {
		"use strict";
		var lat, lng;
		var coordinates = [];
		var init = null;
		var rx = Math.abs(x2 - x1) / 2;
		var ry = Math.abs(y2 - y1) / 2;
		var cx = Math.min(x1, x2) + rx;
		var cy = Math.min(y1, y2) + ry;

		var angInc = 2 * Math.PI / nsides;
		// create ring in CW order
		for (var i = 0; i < nsides; i++) {
			var ang = -(i * angInc);
			lat = cx + rx * Math.cos(ang);
			lng = cy + ry * Math.sin(ang);
			if (i === 0) {
				init = [ lat, lng ];
			}
			coordinates.push([ lat, lng ]);
		}
		coordinates.push(init);
		return coordinates;
	}

	function clearGeofences() {
		for (var i = 0; i < geofencesArray.length; i++) {
			geofencesArray[i].setMap(null);
		}
		geofencesArray.length = 0;
	}

	// Default
	var map = new google.maps.Map(document.getElementById('map'), {
		center : {
			lat : 15.3173,
			lng : 75.7139
		},
		zoom : 8
	});

	var drawingManager = new google.maps.drawing.DrawingManager(
			{
				drawingMode : google.maps.drawing.OverlayType.MARKER,
				drawingControl : true,
				drawingControlOptions : {
					position : google.maps.ControlPosition.TOP_CENTER,
					drawingModes : [ 'circle', 'polygon', 'rectangle' ]
				},
				markerOptions : {
					icon : 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png'
				},
				circleOptions : {
					strokeColor : '#20B2AA',
					strokeOpacity : 0.8,
					fillColor : '#20B2AA',
					fillOpacity : 0.35,
					strokeWeight : 2,
					clickable : true,
					editable : true,
					zIndex : 1
				},
				polygonOptions : {
					strokeColor : '#20B2AA',
					strokeOpacity : 0.8,
					fillColor : '#20B2AA',
					fillOpacity : 0.35,
					strokeWeight : 2,
					clickable : true,
					editable : true,
					zIndex : 1
				},
				rectangleOptions : {
					strokeColor : '#20B2AA',
					strokeOpacity : 0.8,
					fillColor : '#20B2AA',
					fillOpacity : 0.35,
					strokeWeight : 2,
					clickable : true,
					editable : true,
					zIndex : 1
				}
			});
	drawingManager.setMap(map);
}