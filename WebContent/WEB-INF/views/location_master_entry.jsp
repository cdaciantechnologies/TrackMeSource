<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>

<!-- Mirrored from kalkisoft.com/adhata/html/new-vehicle.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 30 Dec 2016 18:17:01 GMT -->
<head>
<meta charset="utf-8">
<meta path="viewport" content="width=device-width, initial-scale=1.0">
<title>NST GPS</title>
<link href="html/css/css.css" rel="stylesheet" type="text/css" />
<link href="html/css/bootstrap.css" rel="stylesheet">

<link href="html/css/dataTables.bootstrap.min.css" rel="stylesheet">
<link href="html/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="html/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="html/css/animate.css" rel="stylesheet">
<link href="html/css/style.css" rel="stylesheet">
<link href="html/css/custom.css" rel="stylesheet">
<link href="html/css/bootstrap-timepicker.css" rel="stylesheet">
<link href="html/css/datepicker3.css" rel="stylesheet">
<style type="text/css">
#mapCanvas1 {
	width: 100%;
	height: 500px;
}

input#addlocation {
	margin-top: 21px;
}
</style>
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDQTpXj82d8UpCi97wzo_nKXL7nYrd4G70"></script>
<script type="text/javascript">
	var geocoder = new google.maps.Geocoder();

	function geocodePosition(pos) {
		geocoder
				.geocode(
						{
							latLng : pos
						},
						function(responses) {
							if (responses && responses.length > 0) {
								updateMarkerAddress(responses[0].formatted_address);
							} else {
								updateMarkerAddress('Cannot determine address at this location.');
							}
						});
	}

	function updateMarkerPosition(latLng) {
		document.getElementById('Latitxt').value = [ latLng.lat() ];
		document.getElementById('Longtxt').value = [ latLng.lng() ];
	}
	function updateMarkerAddress(str) {
		document.getElementById('address').innerHTML = str;
		document.getElementById('address').value = str
	}
	function initialize() {
		var latLng = new google.maps.LatLng(18.5314, 73.8626);
		var map = new google.maps.Map(document.getElementById('mapCanvas1'), {
			zoom : 10,
			center : latLng,
			mapTypeId : google.maps.MapTypeId.roadmap
		});
		var marker = new google.maps.Marker({
			position : latLng,
			title : 'Point A',
			map : map,
			draggable : true
		});

		// Update current position info.
		updateMarkerPosition(latLng);
		geocodePosition(latLng);

		// Add dragging event listeners.

		google.maps.event.addListener(marker, 'drag', function() {
			updateMarkerPosition(marker.getPosition());

		});

		google.maps.event.addListener(marker, 'dragend', function() {

			geocodePosition(marker.getPosition());
			updateMarkerAddress('Dragging...');
		});
	}

	// Onload handler to fire off the app.
	google.maps.event.addDomListener(window, 'load', initialize);
</script>

</head>

<body class="top-navigation">
	<jsp:directive.include file="header.jsp" />

	<div id="wrapper">

		<div id="page-wrapper2" class="gray-bg">
			<div class="rowx wrapper border-bottom white-bg page-heading">
				<div class="col-sm-12">
					<h2>Add Location</h2>
				</div>
			</div>
			<div class="wrapper wrapper-content  animated fadeInRight">
				<div class="ibox-content">
					<h3 class="m-t-none m-b">Location</h3>
					<div class="row">
						<form:form action="AddOrUpdateLocationRecord"
							commandName="location">
							<form:input type="hidden" path="status" value="Active" />


							<div class="col-sm-6">
								<div class="form-group">
									<label>Location Zone</label>
									<c:if test="${location.editFlag==true }">
										<form:input required="true" path="locationName" type="text"
										id="geocomplete" placeholder="Type in an address"
										class="form-control"></form:input>
										<%-- To Remove <form:input required="true" type="text"  path="locationName" disabled="true" id="geocomplete" class="form-control" /> --%>
										<form:hidden path="editFlag" value="${location.editFlag}" />
										<form:hidden path="createdBy" value="${location.createdBy}" />
										<form:hidden path="createdDateShow"
											value="${location.createdDateShow}" />
									</c:if>

									<c:if test="${location.editFlag==false }">
										<%-- To Remove  <form:input type="text" required="true" path="locationName"
										id="primaryKey" class="form-control" /> --%>
										<form:input required="true" path="locationName" type="text"
											id="geocomplete" placeholder="Type in an address"
											class="form-control"></form:input>
										<form:hidden path="vertices" id="vertices" />
										<form:hidden path="editFlag" value="${location.editFlag}" />
									</c:if>
								</div>
							 
								<div class="form-group">
									<label>Location Name</label>
									<form:input type="text" required="true"
										path="locationDescription" id="Desc"
										name="locationdescription" class="form-control" />
								</div>
														 
								 <!-- 
								<div class="form-group">
									<label>Latitude</label>
									<form:input type="text" required="true" path="latitude"
										id="Latitxt" name="latitude" readonly="true"
										class="form-control" />

								</div>
								 -->
								 <!-- 
								<div class="form-group">
									<label>Longitude</label>
									<form:input type="text" required="true" path="longitude"
										id="Longtxt" name="longitude" readonly="true"
										class="form-control" />

								</div>
								
								<div class="form-group">
									<label>Closest matching address</label>
									<form:input type="text" required="true" path="address"
										id="address" name="address" readonly="true"
										class="form-control" />

								</div>
								
								<p>Note : Please click and drag the marker for
									Latitude/Longitude</p>
								-->
								<c:if test="${location.editFlag==true }">

									<input name="action" type="submit" onclick=" return validate()"
										class="btn  btn-primary" id="Submit1" value="Update Device" />
								</c:if>
								<c:if test="${location.editFlag==false }">
									<input name="action" type="submit" onclick=" return validate()"
										class="btn  btn-primary" id="Submit1" value="Save Device" />
								</c:if>

								<input type="button" class="btn btn-danger" id="button3"
									value="Close"
									onclick="location.href = 'LocationMasters'" />

							</div>
							<!-- This div contain map  -->
							<div class="col-sm-6">
								<div id="map" style="width: 100%; height: 300px"></div>
								<script
									src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCeQdAwrHm8Zap7jwX_gNRA3dhH-CxdCWQ&libraries=drawing,places&callback=initMap"
									async defer>
									
								</script>
							</div>
						</form:form>
					</div>

				</div>
			</div>



		</div>



	</div>


	<jsp:directive.include file="footer.jsp" />
	<script>
		$(document).ready(function() {

			$('#hdr_settings').addClass("dropdown active");
			$('#hdr_settings_link').click();
			$('#hdr_vehicleLocation').addClass("active");
		});
	</script>


	<!-- Mainly scripts -->
	<script type="text/javascript" src="html/js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="html/js/tether.min.js"></script>
	<script type="text/javascript" src="html/js/bootstrap.js"></script>
	<script type="text/javascript" src="html/js/angular.min.js"></script>
	<script type="text/javascript" src="html/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript"
		src="html/js/dataTables.bootstrap.min.js"></script>

	<script type="text/javascript" src="html/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="html/js/bootstrap-timepicker.js"></script>
	<script type="text/javascript" src="html/js/dataTables.buttons.min.js"></script>
	<script type="text/javascript" src="html/js/buttons.flash.min.js"></script>
	<script type="text/javascript" src="html/js/jspdf.min.js"></script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
	<script type="text/javascript"
		src="https://cdn.datatables.net/buttons/1.2.4/js/buttons.html5.min.js"></script>
	<script type="text/javascript" src="html/js/icheck.min.js"></script>
	<script src="html/js/jquery.geocomplete.js"></script>
	<script src="html/js/geofences.js"></script>
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>


	<script type="text/javascript">
		$('#hdr_settings').addClass("dropdown active");
		$('#hdr_settings_link').click();
		$('#hdr_vehicleLocation').addClass("active");

		$('#data_1 .input-group.date').datepicker({
			autoclose : true
		});
		$('#data_2 .input-group.date').datepicker({
			autoclose : true
		});
		$('#data_3 .input-group.date').datepicker({
			autoclose : true
		});
		$('#data_4 .input-group.date').datepicker({
			autoclose : true
		});
		$(".timepicker").timepicker({
			showInputs : false
		});
		$('.i-checks').iCheck({
			checkboxClass : 'icheckbox_square-green',
			radioClass : 'iradio_square-green',
		});

		$('#errorMessage').fadeOut(5000);
		function validate() {
			$('#primaryKey').prop('disabled', false);
			return true;
		}
	</script>
</body>
</html>
