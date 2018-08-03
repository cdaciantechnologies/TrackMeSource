package com.trackme.spring.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trackme.constants.Constant;
import com.trackme.spring.dao.RouteScheduleDAO;
import com.trackme.spring.model.Route;
import com.trackme.spring.model.RouteSchedule;

@Service("routeScheduleService")
public class RouteScheduleServiceImpl implements RouteScheduleService{

	@Autowired
	private RouteScheduleDAO routeScheduleDAO;
	
	@Override
	@Transactional
	public void addRouteScheduleDetails(RouteSchedule routeSchedule) {
		routeScheduleDAO.addRouteScheduleDetails(routeSchedule);
	}

	@Override
	@Transactional
	public void updateRouteScheduleDetails(RouteSchedule routeSchedule) {
		routeScheduleDAO.updateRouteScheduleDetails(routeSchedule);
	}

	@Override
	@Transactional
	public List<RouteSchedule> listRouteScheduleDetails() {
		
		return routeScheduleDAO.listRouteScheduleDetails();
	}

	@Override
	@Transactional
	public RouteSchedule getRouteScheduleDetailsById(Integer routeScheduleId) {
		return routeScheduleDAO.getRouteScheduleDetailsById(routeScheduleId);
	}

	@Override
	@Transactional
	public void removeRouteScheduleDetails(Integer routeId) {
	RouteSchedule routeSchedule = routeScheduleDAO.getRouteScheduleDetailsById(routeId);
			routeSchedule.setStatus(Constant.STATUS_INACTIVE);
	routeScheduleDAO.updateRouteScheduleDetails(routeSchedule);
	}

	@Override
	@Transactional
	public List<RouteSchedule> listCurrentActiveSchedule() {
		
		List<RouteSchedule> routeSchedules=routeScheduleDAO.listRouteScheduleDetails();
		List<RouteSchedule> routeActiveSchedules= new ArrayList<>();
		if(routeSchedules !=null){
		Iterator<RouteSchedule> iter= 	routeSchedules.iterator()  ;
		while(iter.hasNext()){
				RouteSchedule routeSchedule= iter.next();
				if(checkIfScheduleActive(routeSchedule))
					routeActiveSchedules.add(routeSchedule);
			}
		}
		return routeActiveSchedules;
	}
	
	public boolean checkIfScheduleActive(RouteSchedule routeSchedule){
		Date date=new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		Date fromDate= routeSchedule.getStartDate();
		Date toDate= routeSchedule.getEndDate();
		if(!(fromDate.before(date) && date.before(toDate))) return false;
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek){
			case 1 :if(routeSchedule.isSunday())break;	else return false;
			case 2 :if(routeSchedule.isMonday())break;	else return false;
			case 3 :if(routeSchedule.isTuesday())break;	else return false;
			case 4 :if(routeSchedule.isWednesday())break;	else return false;
			case 5 :if(routeSchedule.isThursday())break;	else return false;
			case 6 :if(routeSchedule.isFriday())break;	else return false;
			case 7 :if(routeSchedule.isSaturday())break;	else return false;
			}
		Date fromTime= routeSchedule.getStartTimeSave();
		Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(fromTime);
		 int FromHour = calendar1.get(Calendar.HOUR_OF_DAY);
		 int FromMinute = calendar1.get(Calendar.MINUTE);
		Date toTime= routeSchedule.getEndTimeSave();
		Calendar calendar2 = Calendar.getInstance();
	    calendar2.setTime(toTime);
		 int toHour = calendar2.get(Calendar.HOUR_OF_DAY);
		 int toMinute = calendar2.get(Calendar.MINUTE);
		 int hour = c.get(Calendar.HOUR_OF_DAY);
		 int minute = c.get(Calendar.MINUTE);
		 
		if( hour <FromHour  || toHour< hour) return false;
		if( hour ==FromHour  && minute < FromMinute) return false;
		if(toHour== hour && toMinute<minute) return false;	  
	
		return true;
	}
}
