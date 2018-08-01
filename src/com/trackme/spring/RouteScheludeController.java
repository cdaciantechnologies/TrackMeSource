package com.trackme.spring;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackme.constants.Constant;
import com.trackme.spring.model.RouteSchedule;
import com.trackme.spring.model.UserMaster;
import com.trackme.spring.service.RouteScheduleService;
import com.trackme.spring.service.RouteService;
import com.trackme.spring.service.VehicleMasterService;


@Controller
public class RouteScheludeController extends BaseController{

	private RouteScheduleService routeScheduleService;
	
	@Autowired
	private VehicleMasterService vehicleMasterService;
	
	@Autowired
	private RouteService routeService;
	
	@Autowired(required=true)
	@Qualifier(value="routeScheduleService")
	public void setRouteScheduleService(RouteScheduleService routeScheduleService) {
		this.routeScheduleService = routeScheduleService;
	}

	@RequestMapping(value = "/ViewRouteScheduleDetails", method = RequestMethod.GET)
	public String viewRouteScheduleDetails(Model model) {	
		model.addAttribute("RouteSchedule", new RouteSchedule());
	    List<RouteSchedule> routeSchedule=this.routeScheduleService.listRouteScheduleDetails();	
		ObjectMapper objectMapper = new ObjectMapper();
		String routeScheduleJSON=null;
		try {
			routeScheduleJSON = objectMapper.writeValueAsString(routeSchedule);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("RouteScheduleJSON", routeScheduleJSON);
		return "routeschedule_details";
	}
	
	
	
	@RequestMapping(value = "/addRouteSchedulesView", method = RequestMethod.GET)
	public String routeView(Model model, HttpServletRequest request, HttpServletResponse response) {	
		model.addAttribute("routeSchedule", new RouteSchedule());
		model.addAttribute("vehicles",vehicleMasterService.listVehicleMasters());
		model.addAttribute("routes",routeService.listRouteDetails());
		
		return "routeSchedule_master_entry";
	}
	
	@RequestMapping(value = "/editRouteSchedulesView", method = RequestMethod.GET)
	public String editRouteSchedule(Model model,@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {	
		Integer routeScheduleId = Integer.parseInt(id);
		RouteSchedule routeSchedule=this.routeScheduleService.getRouteScheduleDetailsById(routeScheduleId);
		routeSchedule.setEditFlag(true);
		model.addAttribute("routeSchedule", routeSchedule);
		model.addAttribute("vehicles",vehicleMasterService.listVehicleMasters());
		model.addAttribute("routes",routeService.listRouteDetails());			
		return "routeSchedule_master_entry";
	}
	
	//For add and update VehicleMaster both
	@RequestMapping(value= "/AddOrUpdateRouteScheduleRecord", method = RequestMethod.POST)
	public String addRoute(@ModelAttribute("routeSchedule") RouteSchedule routeSchedule, Model model, HttpServletRequest request, HttpServletResponse response){		
		//Add Driver
		RouteSchedule routeScheduleExist=this.routeScheduleService.getRouteScheduleDetailsById(routeSchedule.getId());
		if(routeScheduleExist==null){
			UserMaster currentUser=(UserMaster) request.getSession().getAttribute(Constant.CURRENT_USER);
			routeSchedule.setCreatedby(currentUser.getUserName());
			routeSchedule.setCreatedDate(new Date());
			routeSchedule.setStatus(Constant.STATUS_ACTIVE);
		routeScheduleService.addRouteScheduleDetails(routeSchedule);
		addSuccessMessage("RouteSchedule details added successfully.");
		
		} else{
			if(routeSchedule.isEditFlag()){
				UserMaster currentUser=(UserMaster) request.getSession().getAttribute(Constant.CURRENT_USER);
				routeSchedule.setModifiedby(currentUser.getUserName());
				routeSchedule.setModifiedDate(new Date());
				routeSchedule.setStatus(Constant.STATUS_ACTIVE);
				routeScheduleService.updateRouteScheduleDetails(routeSchedule);	
			addSuccessMessage("RouteSchedule details updated successfully.");
			}else{
				addErrorMessage("RouteSchedule already exists. Please enter unique value.");
				addSuccessOrErrorMessageToModel(model);
				model.addAttribute("vehicles",vehicleMasterService.listVehicleMasters());
					
				model.addAttribute("routeSchedule", routeSchedule);
				   return "route_master_entry";
			}
		}
		addSuccessOrErrorMessageToModel(model);
		return viewRouteScheduleDetails(model);
		
	}
	
	@RequestMapping("/RemoveRouteScheduleRecord")
    public String removeRouteSchedule(@RequestParam("id") String routeName, Model model, HttpServletRequest request, HttpServletResponse response){
		Integer routeId= Integer.parseInt(routeName);
		routeScheduleService.removeRouteScheduleDetails(routeId);
	     addSuccessMessage("RouteSchedule details removed successfully.");
	        addSuccessOrErrorMessageToModel(model);
			
	        return viewRouteScheduleDetails(model);
	    
    }
	
}
