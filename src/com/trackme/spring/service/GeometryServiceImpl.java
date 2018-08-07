package com.trackme.spring.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackme.spring.dao.MapLatlngDAO;
import com.trackme.spring.model.LocationsForRoute;
import com.trackme.spring.model.Route;
import com.trackme.spring.model.RouteSchedule;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;


@Service("geoFencingServiceImpl")
public class GeometryServiceImpl {
	final GeometryFactory gf = new GeometryFactory();
	@Autowired
	RouteScheduleService routeScheduleService;
	
	@Autowired
	RouteService routeService;   
	
	@Autowired
	MapLatlngService mapLatlngService;
	
	@Autowired 
	RouteNotificationServiceImpl routeNotificationServiceImpl;
	//getListOfActiveSchedules
	void test(){
	List<RouteSchedule> routes=	routeScheduleService.listCurrentActiveSchedule(); 
	Iterator<RouteSchedule> iter= routes.iterator();
	while(iter.hasNext()){
		RouteSchedule routeSchedule= iter.next();
		String vehicleNo= routeSchedule.getVehicleNo();
		Map<String,Object> currentLocation= mapLatlngService.getCurrentLocationOfVehicle(vehicleNo);
		  Route route= routeSchedule.getRouteId();
		List<LocationsForRoute> locationsForRoutes = route.getLocationsForRoute();
		Iterator<LocationsForRoute> lrIter= locationsForRoutes.iterator();
		while(lrIter.hasNext()){
			LocationsForRoute locationsForRoute= lrIter.next();
				if (!routeNotificationServiceImpl.checkVehicleTrakEntry(routeSchedule.getId(),
						locationsForRoute.getLocation().getId(), routeSchedule.getVehicleNo())){
					if(CheckPointLocation(locationsForRoute.getLocation().getGeometry(),currentLocation)){
					String indatetime="";
					String outdatetime="";
					if(currentLocation.get("locationtime")!=null)
						indatetime= currentLocation.get("locationtime").toString();
					routeNotificationServiceImpl.insertIntoVehRouteTracking(routeSchedule.getId(), 
							locationsForRoute.getLocation().getId(), routeSchedule.getVehicleNo(), indatetime
							, outdatetime, false, false);
						}
				}else if (routeNotificationServiceImpl.checkVehicleTrakEntryForOnlyIn(routeSchedule.getId(),
						locationsForRoute.getLocation().getId(), routeSchedule.getVehicleNo())){
					if(!CheckPointLocation(locationsForRoute.getLocation().getGeometry(),currentLocation)){
						String outdatetime="NA";
						if(currentLocation.get("locationtime")!=null)
							outdatetime= currentLocation.get("locationtime").toString();
						routeNotificationServiceImpl.updateVehRouteTrackingOuttime(routeSchedule.getId(), 
								locationsForRoute.getLocation().getId(), routeSchedule.getVehicleNo()
								, outdatetime);
							}
					
				}
			
		}
	}
	  
	}
	
	public boolean CheckPointLocation(Geometry geam, Map<String,Object> currentLocation ){
		final GeometryFactory gf = new GeometryFactory();	
	Double lat= 	(double) Double.parseDouble((String)currentLocation.get("lat"));
	Double longi=	(double) Double.parseDouble((String)currentLocation.get("long"));
		final Coordinate coord = new Coordinate(lat, longi);
		 final Point point = gf.createPoint(coord);
		 final Polygon polygon =gf.createPolygon(geam.getCoordinates());
		return point.within(polygon);
	}
	public boolean  checkPointInsideGeometry(){
		
		try{
		test();
		routeNotificationServiceImpl.getGeoFenceInDetails();
		}
		catch(Exception e){
			System.out.println("error::::"+e.getMessage() );
		}
		return true;
		}
	
	
}
