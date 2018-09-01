<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>

<!-- Mirrored from kalkisoft.com/adhata/html/new-vehicle.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 30 Dec 2016 18:17:01 GMT -->
<head>
    <meta charset="utf-8">
    <meta path="viewport" content="width=device-width, initial-scale=1.0">
    <title>Route Schedule</title>
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
</head>

<body class="top-navigation">
 <jsp:directive.include file="header.jsp" />
    <div id="wrapper">
        <div id="page-wrapper2" class="gray-bg">
            <div class="rowx wrapper border-bottom white-bg page-heading">
                <div class="col-sm-12">
                    <h2>Route Schedule Entry </h2>
                </div>
            </div>
            <div class="wrapper wrapper-content  animated fadeInRight"></div>
            <div class="ibox-content">
                <h3 class="m-t-none m-b">Route Schedule </h3>
                <div class="row">
               <form:form action="AddOrUpdateRouteScheduleRecord" commandName ="routeSchedule" >
			      <div class="row">
                            <div class="rowx ">
                                <div class="form-group col-sm-6">
                                    <label for="txtschedule">Schedule Name:</label>
                                    <c:if test="${routeSchedule.editFlag==true }">
							
                                    <form:input required="true" type="text"  path="scheduleName" disabled="true" id="primaryKey" class="form-control" />
                                    	<form:hidden path="editFlag" value="${routeSchedule.editFlag}"/>
											<form:hidden path="createdby" value="${routeSchedule.createdby}"/>
								<form:hidden path="createdDateShow" value="${routeSchedule.createdDateShow}"/>
								<form:hidden path="id" value="${routeSchedule.id}"/>
						
							</c:if>
							<c:if test="${routeSchedule.editFlag==false }">
							 	  <form:input  required="true" placeholder="Schedule Name" path="scheduleName"  id="primaryKey" class="form-control" />
                                  
							 <form:hidden path="editFlag" value="${routeSchedule.editFlag}"/>
							</c:if></div>
                                <div class="form-group col-sm-6">
                                    <label for="txtroute">Select Route :</label>
                                    <form:select class="form-control" required="true" path="routeId.id" items="${routes}" itemLabel="routeName" itemValue="id"></form:select>
                                </div>

                                <div class="col-sm-6">
                                    <label for="lblEffective">Effective from</label>
                                    <div class="clearfix"></div>
                                    <div class="form-group col-sm-6" style="padding-left: 0px;">
                                        <label for="txtsdate">Start Date:</label>
                                        <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                               
                                        <form:input path="startDateShow" type="text" id="startDate" class="form-control" autocomplete="off" />
                                   </div>
                                    </div>
                                    <div class="form-group col-sm-6" style="padding-right: 0px;">
                                        <label for="txtedate">End Date:</label>
                                       <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                               
                                        <form:input path="endDateShow" type="text" id="endDate" class="form-control" autocomplete="off" />
                                   </div>
                                    </div>
                                </div>
                                <div class="bootstrap-timepicker col-sm-6">
                                            <div class="form-group">
                                                <label>Start Time:</label>
                                                <div class="input-group">
                                                    <form:input placeholder="Start Time" path="startTime" id="startTime" class="form-control timepicker"/>
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-clock-o"></i>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="bootstrap-timepicker col-sm-6">
                                            <div class="form-group">
                                                <label>End Time:</label>
                                                <div class="input-group">
                                                   <form:input placeholder="Start Time" path="endTime" id="endTime" class="form-control timepicker"/>
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-clock-o"></i>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    
                                
                                
                                <br />

                                <div class="form-group col-sm-6">
                                    <label for="RepeatOn"><b>Repeat On :</b></label>
                                    <div class="clearfix"></div>
                                  <label for="checkbox" class="checkbox-inline">Sunday </label>
                                <form:checkbox class="i-checks" path="sunday" id="sunday"  style="text-align: center; vertical-align: middle;"/>
                                <label for="checkbox2" class="checkbox-inline">Monday </label>
                                <form:checkbox class="i-checks" path="monday" id="monday"  style="text-align: center; vertical-align: middle;"/>
                                <label for="checkbox3" class="checkbox-inline">Tueday </label>
                                <form:checkbox class="i-checks" path="tuesday" id="tuesday"  style="text-align: center; vertical-align: middle;"/>
                                <label for="checkbox4" class="checkbox-inline">Wednesday</label>
                                <form:checkbox class="i-checks" path="wednesday" id="wednesday" style="text-align: center; vertical-align: middle;"/>
                                <label for="checkbox5" class="checkbox-inline">Thursday</label>
                                <form:checkbox class="i-checks" path="thursday" id="thursday" style="text-align: center; vertical-align: middle;" />
                                <label for="checkbox5" class="checkbox-inline">Friday</label>
                                <form:checkbox class="i-checks" path="friday"  id="friday" style="text-align: center; vertical-align: middle;"/>

                                <label for="checkbox5" class="checkbox-inline">Saturday</label>
                                <form:checkbox class="i-checks" path="saturday"  id="saturday" style="text-align: center; vertical-align: middle;"/>
                             </div>



                                <div class="form-group col-sm-6">
                                    <label for="checkbox" class="alart-label"><b>Alert Type : </b></label>
                                    <div class="clearfix"></div>
                                    <label for="checkbox8" class="checkbox-inline">SMS:</label>
                                   <form:checkbox class="i-checks"  path="alertbysms" id="checkbox8" style="text-align: center; vertical-align: middle;" />

                                    <label for="checkbox9" class="checkbox-inline">Email:</label>
                                    <form:checkbox class="i-checks"   path="alertbymail" id="checkbox9" style="text-align: center; vertical-align: middle;" />

                                </div>
                            </div>
                            <div class="rowx wrapper white-bg">
                                <div class="row">
                                    
                                      <div class="form-group col-sm-6">
                                                                           <label>Vehicle No:</label>
                                	<form:select id="vehicleList" class="form-control" required="true" path="vehicleNo" items="${vehicles}" itemLabel="vehicleNo" itemValue="vehicleNo"></form:select>
								
						
                                          
                                            
                            </div>  
                                    
                                  
                                </div>
                            </div>
                            
                            	<div class="form-group col-sm-6">
								<label>Assitant Name. :</label>
								<form:input path="assistantName" id="assistantName" class="form-control" />
							</div>
							
							<div class="form-group col-sm-6">
								<label>Assistant Mobile No. :</label>
								<form:input path="assistantNo" id="assistantNo" class="form-control" />
							</div>
							
                            
                                                    <br />
                             <div style="text-align: center">
                               <c:if test="${routeSchedule.editFlag==true }">
							
                                <input name="action" type="submit" onclick=" return validate()" class="btn  btn-primary" id="Submit1" value="Update Route" />
                            </c:if>
                             <c:if test="${routeSchedule.editFlag==false }">
							     <input name="action" type="submit"  class="btn  btn-primary" id="Submit1" value="Add Route Schedule" />
                           </c:if>
                            
                                <input name="button3" type="button" class="btn btn-danger" onclick="location.href = 'ViewRouteScheduleDetails'" id="button3" value="Close" />
                            </div>
                        </div>

</form:form>
                </div>
            </div>
        </div>

 <jsp:directive.include file="footer.jsp" />

    <!-- Mainly scripts -->
    <script type="text/javascript" src="html/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="html/js/tether.min.js"></script>
<script type="text/javascript" src="html/js/bootstrap.js"></script>
<script type="text/javascript" src="html/js/angular.min.js"></script>
<script type="text/javascript" src="html/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="html/js/dataTables.bootstrap.min.js"></script>

<script type="text/javascript" src="html/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="html/js/bootstrap-timepicker.js"></script>
<script type="text/javascript" src="html/js/icheck.min.js"></script>
    <script type="text/javascript">

  	 $('#hdr_settings').addClass("dropdown active");
 		$('#hdr_settings_link').click();
 		$('#hdr_routeScheduling').addClass("active");

        $('#startDate').datepicker({
            autoclose: true
        });
        $('#endDate').datepicker({
            autoclose: true
        });
        
    	
		$(document).ready(function(){
		    $('input.timepicker').timepicker({
		    	 scrollbar: true
		    });
		});
       
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
        
        $('#errorMessage').fadeOut(5000);
		function validate(){
			$('#primaryKey').prop('disabled', false);
			return true;
		}
		
		 $('.deleterow').click(function () {
             this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);
             var rows = document.getElementById("rows");
             rows.value = document.getElementById("rows").value - 1;
		 });       
	

    </script>
</body>
</html>


<script
	src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script type="text/javascript" src="html/js/bootstrap-timepicker.js"></script>


</html>
