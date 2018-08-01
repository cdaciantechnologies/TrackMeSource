package com.trackme.spring.service;

import java.util.List;
import com.trackme.spring.model.RouteSchedule;

public interface RouteScheduleService {

	public void addRouteScheduleDetails(RouteSchedule routeSchedule);
	public void updateRouteScheduleDetails(RouteSchedule routeSchedule);
	public List<RouteSchedule> listRouteScheduleDetails();
	public RouteSchedule getRouteScheduleDetailsById(Integer routeScheduleId);
	public void removeRouteScheduleDetails(Integer routeId);
	
	public List<RouteSchedule>  listCurrentActiveSchedule();

}
