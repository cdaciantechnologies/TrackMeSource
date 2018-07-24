<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>GroFence Service</title>
    <link href="html/css/css.css" rel="stylesheet" type="text/css" />
    <link href="html/css/bootstrap.min.css" rel="stylesheet">

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
    <link href="html/css/plugins/iCheck/custom.css" rel="stylesheet"></link>
    <link href="html/css/animate.css" rel="stylesheet">
    <link href="html/css/style.css" rel="stylesheet">
    <link href="html/css/custom.css" rel="stylesheet">
    <link href="html/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="html/css/buttons.dataTables.min.css" rel="stylesheet">

    <script type="text/javascript" src="html/js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="html/js/tether.min.js"></script>
    <script type="text/javascript" src="html/js/bootstrap.js"></script>
    <script type="text/javascript" src="html/js/angular.min.js"></script>
    <script type="text/javascript" src="html/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="html/js/dataTables.bootstrap.min.js"></script>

    <script type="text/javascript" src="html/js/dataTables.buttons.min.js"></script>
    <script type="text/javascript" src="html/js/buttons.flash.min.js"></script>
    <script type="text/javascript" src="html/js/jspdf.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
    <script type="text/javascript" src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
    <script type="text/javascript" src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.2.4/js/buttons.html5.min.js"></script>
    <script type="text/javascript" src="html/js/icheck.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.2.4/js/buttons.colVis.min.js"></script>
    
    
       


</head>

<body class="top-navigation">
    <jsp:directive.include file="header.jsp" />
    <div id="page-wrapper2" class="gray-bg" style="top:128px !important">
        <div class="rowx wrapper border-bottom white-bg page-heading">
            <div class="col-sm-12">
                <h2>Add GeoFence</h2>


            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="wrapper wrapper-content fadeInUp pad-bot-0">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="ibox float-e-margins pad-bot-0" style="background:#fff;">
                                <div class="row">
                                    <div id="same-height" class="col-md-6 md-pad-left" style="padding-right:0;">

                                        <form:form action="addOrUpdateGeoFenceDetail.html" commandName="GeoFenceDetail">
                                            <div class="form-group col-sm-12">
                                                <!-- <input id="geocomplete" type="text" placeholder="Type in an address" size="90" /> -->
                                                <label>GeoFence Zone:</label>
                                                <form:input required="true" path="location" type="text" id="geocomplete" placeholder="Type in an address" class="form-control"></form:input>
                                                <form:hidden path="vertices" id="vertices" />
                                           
                                            </div>
                                            <div class="form-group col-sm-12">
                                                <label>GeoFence Name :</label>
                                                <form:input required="true" path="geoFenceName" type="text" id="geoFenceNameId" class="form-control"></form:input>
                                                <form:hidden path="latitude" id="latId"  />
                                                <form:hidden path="longitude" id="lngId"  />
                                                <form:hidden path="radius" id="radiusId"  />
                                                <form:hidden path="id" id="geoFenceId" />
                                            </div>
                                             <div class="form-group col-sm-12"  id="infoDiv" style="visibility: hidden; display:inline;">
                                                <label >Radius:</label>
                                               <span id="displayInfo" style="font-size:14px"></span><span style="font-size:12px" id="unit">&nbsp;K.M.</span>
                                            </div>
                                            
                                            
                                             <div class="rowx wrapper white-bg">
                                <div class="row">
                                    <div class="form-group col-sm-4">
                                        <label for="txtvehicle">Vehicle No </label>
                                      
                                    <select name="vehicle" id="vehicle" class="form-control">
                                    <c:forEach var="vehicle" items="${vehicles}">
                                     <option value="${vehicle.vehicleNo }">${vehicle.vehicleNo }</option>
                                    </c:forEach>
                                       
                                    </select>
                                      
                                    </div>
                                    <div class="form-group col-sm-4">
                                    
                                    
                                        <input name="buttaddvehicle" type="button" onclick="addVehicle()" class="btn btn-primary" id="buttaddvehicle" value="Add Vehicle" />
                                        <input type="hidden" name="rows" id="rows" />
                                    </div>
                                </div>
                            </div>
                            
                            <div class="rowx wrapper white-bg">
                                <div class="table-responsive" style="width: 50%; border: 1px solid;">
                                    <table width="50%" border="1" cellpadding="0" cellspacing="0" id="entrydata" class="table table-striped table-bordered new-tbl">
                                        <tbody>
                                            <tr>
                                                <th width="20%" align="center" bgcolor="#CCC4C4">Sl no</th>
                                                <th width="50%" align="center" bgcolor="#CCC4C4">Vehicle</th>
                                                <th width="30%" align="center" bgcolor="#CCC4C4">Action</th>
                                            </tr> 
                                             
                                             <c:if test="${isEdit=='1' }">
										
										<c:forEach var="vehicleVal" items="${GeoFenceDetail.vehicles}" varStatus="i">
									
	<tr class="leftMenu">
	<td>${i.index+1}</td>
	<td><input type="input" id="vehicles${i.index+1}" value="${vehicleVal}" name="vehicleShow" class="form-control" readonly=""></td>
	<td><input type="button" class="btn btn-primary deleterow" value="Delete">
	
	</c:forEach>
									</c:if>
                                            
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            
                            
                                            <div class="form-group col-sm-12">
                                                
                                                    <input type="submit" class="btn  btn-primary"  name="action" value="Save GeoFence" />
                                                	 <input type="button" class="btn  btn-danger" id="Exit" onclick="window.location.href='listGeoFenceDetails.html';" value="Close" />
                                                <!--<c:if test='true'>
                                                    <input type="submit" class="btn  btn-primary"  name="action" value="Update GeoFence">

                                                </c:if> -->
                                              
                                            </div>
                                        </form:form>
                                    </div>
                                     <c:if test="${not empty GeoFenceJSON}">
                                       
                                         <script>
                                          var geoFenceJSON = ${GeoFenceJSON};
                                        </script>
                                    </c:if>
                                     <c:if test="${empty GeoFenceJSON}">
                                         <script>
                                          var geoFenceJSON = null;
                                        </script>
                                    </c:if>
                                   
                                    <div id="same-height2" class="col-md-6 md-pad-right" style=" width:50%;height:80%;padding-left:0;">

                                        <div id="maptoggle" class="ibox-content">
                                            <div id="map" style="width:100%;height:400px"></div>
                                            <!-- <div id="info"></div>  -->

                                            <!--       <script async src="https://maps.googleapis.com/maps/api/js?libraries=geometry,places&key=AIzaSyCeQdAwrHm8Zap7jwX_gNRA3dhH-CxdCWQ&callback=initialize&ext=.js"></script>  -->

                                            <!-- <script async src="https://maps.googleapis.com/maps/api/js?libraries=geometry,places&key=AIzaSyCeQdAwrHm8Zap7jwX_gNRA3dhH-CxdCWQ&ext=.js"></script> -->
                                                 <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCeQdAwrHm8Zap7jwX_gNRA3dhH-CxdCWQ&libraries=drawing,places&callback=initMap" async defer> </script>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:directive.include file="footer.jsp" />

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script type="text/javascript" src="html/js/jquery.geocomplete.js"></script>
    <script src="html/js/geofences.js"></script>
    </body>
    <script>
    $('#hdr_settings').addClass("dropdown active");
		$('#hdr_settings_link').click();
		$('#hdr_goeFence').addClass("active");
    function addVehicle() {
        var location = document.getElementById("vehicle");
        var table = document.getElementById("entrydata");
        var rowCount = document.getElementById('entrydata').rows.length;
        row = table.insertRow(rowCount);
        row.className = "leftMenu";
        cell = row.insertCell(0);
        cell.innerHTML = rowCount;
        cell = row.insertCell(1);
        var input = document.createElement('input');
        input.type = "input";
        input.value = location.value;
        input.id = "vehicles" + rowCount;
        input.name = "vehicleShow";
        input.className = "form-control";

        input.readOnly = true;
        cell.appendChild(input);

        cell = row.insertCell(2);
        var btn = document.createElement('input');
        btn.type = "button";
        btn.className = "btn btn-primary";
        btn.value = "Delete";
        btn.onclick = (
            function () {
                this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);
                var rows = document.getElementById("rows");
                rows.value = document.getElementById("rows").value - 1;
            }
        );
        cell.appendChild(btn);

        var rows = document.getElementById("rows");
        rows.value = rowCount;
    }
    
    $('.deleterow').click(function () {
        this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);
        var rows = document.getElementById("rows");
        rows.value = document.getElementById("rows").value - 1;
	 });
    
    
    $(document).ready(function () {
	    
    	var prev_locations = document.getElementsByName("locationsOfRoute");
    	for(var i=0;i< prev_locations.length;i++){
        var location = prev_locations[i];
        var table = document.getElementById("entrydata");
        var rowCount = document.getElementById('entrydata').rows.length;
        row = table.insertRow(rowCount);
        row.className = "leftMenu";
        cell = row.insertCell(0);
        cell.innerHTML = rowCount;
        cell = row.insertCell(1);
        var input = document.createElement('input');
        input.type = "input";
        input.value = location.value;
        input.id = "route" + rowCount;
        input.name = "locations";
        input.className = "form-control";

        input.readOnly = true;
        cell.appendChild(input);

        cell = row.insertCell(2);
        var btn = document.createElement('input');
        btn.type = "button";
        btn.className = "btn btn-primary";
        btn.value = "Delete";
        btn.onclick = (
            function () {
                this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);
                var rows = document.getElementById("rows");
                rows.value = document.getElementById("rows").value - 1;
            }
        );
        cell.appendChild(btn);

        var rows = document.getElementById("rows");
        rows.value = rowCount;
	}
    });

    
    </script>
</html>
