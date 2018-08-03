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
			System.out.println("sdfsdf ****** in "+ CheckPointLocation(locationsForRoute.getLocation().getGeometry(),currentLocation));
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
		/*final GeometryFactory gf = new GeometryFactory();

	    final ArrayList<Coordinate> points = new ArrayList<Coordinate>();
	    points.add(new Coordinate(-10, -10));
	    points.add(new Coordinate(-10, 10));
	    points.add(new Coordinate(10, 10));
	    points.add(new Coordinate(10, -10));
	    points.add(new Coordinate(-10, -10));
	    final Polygon polygon = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
	        .toArray(new Coordinate[points.size()])), gf), null);

	    final Coordinate coord = new Coordinate(0, 0);
	    final Point point = gf.createPoint(coord);
System.out.println("I**************Inside " +point.within(polygon));
	    return point.within(polygon);
	
	*/
		test();
		return true;
		}
	
	
}
