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

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackme.constants.Constant;
import com.trackme.spring.model.DeviceMaster;
import com.trackme.spring.model.Location;
import com.trackme.spring.model.UserMaster;
import com.trackme.spring.service.DeviceMasterService;
import com.trackme.spring.service.LocationService;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

@Controller
public class LocationController extends BaseController{
	
	private LocationService locationService;
	
	
	
	public LocationService getLocationService() {
		return locationService;
	}

	@Autowired(required=true)
	@Qualifier(value="locationService")
	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}



	
	@RequestMapping(value = "/LocationMasters", method = RequestMethod.GET)
	public String listLocationMasters(Model model, HttpServletRequest request, HttpServletResponse response) {	
		model.addAttribute("location", new Location());
	    List<Location> locationMasterList=this.locationService.listLocations();		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JtsModule());
		String locationJSON=null;
		try {
			locationJSON = objectMapper.writeValueAsString(locationMasterList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("locationJSON", locationJSON);
		return "location_master_view";
	}
	
	
	@RequestMapping(value = "/addLocationView", method = RequestMethod.GET)
	public String locationView(Model model, HttpServletRequest request, HttpServletResponse response) {	
		model.addAttribute("location", new Location());
		return "location_master_entry";
	}
	
	
	@RequestMapping(value = "/EditLocationView", method = RequestMethod.GET)
	public String editLocation(Model model,@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response) {	
		Location location=this.locationService.getLocationByName(name);
		location.setEditFlag(true);
		model.addAttribute("location", location);
		return "location_master_entry";
	}
	
	
	
	//For add and update VehicleMaster both
		@RequestMapping(value= "/AddOrUpdateLocationRecord", method = RequestMethod.POST)
		public String addLocation(@ModelAttribute("location") Location location, Model model, HttpServletRequest request, HttpServletResponse response){		
			//Add Driver
		System.out.println("location:::"+location.getVertices());
		 String[] split = null;
			Location locationExist=this.locationService.getLocationByName(location.getLocationName());
			if(locationExist==null){
				UserMaster currentUser=(UserMaster) request.getSession().getAttribute(Constant.CURRENT_USER);
				location.setCreatedBy(currentUser.getUserName());
				location.setCreatedDate(new Date());
				//Adding geofence details
				try{
					 split=location.getVertices().replaceAll("\\[", "").replaceAll("\\]","").split(",");
					
					Coordinate[] coordinates = new Coordinate[split.length/2];
					int index = 0;  
					for(int i=0;i<split.length;i+=2)
					{
					    coordinates[index]=new Coordinate(Double.parseDouble(split[i]),Double.parseDouble( split[i+1]));
					    index++;
					}
					
					Geometry geometry = new GeometryFactory().createLineString(coordinates);
				
					location.setGeometry(geometry );
					locationService.addLocation(location);
					addSuccessMessage("Location details added successfully.");
					}catch(Exception ex){
						addSuccessMessage("GeoFence vertices must not be empty.");
						ex.printStackTrace();
					}
			} else{
				if(location.isEditFlag()){
					UserMaster currentUser=(UserMaster) request.getSession().getAttribute(Constant.CURRENT_USER);
					location.setModifiedBy(currentUser.getUserName());
					location.setModifiedDate(new Date());
				
					locationService.updateLocation(location);	
				addSuccessMessage("Location details updated successfully.");
				}else{
					addErrorMessage("Location Name already exists. Please enter unique value.");
					addSuccessOrErrorMessageToModel(model);
					model.addAttribute("location", location);
					   return "location_master_entry";
				}
			}
			addSuccessOrErrorMessageToModel(model);
			return listLocationMasters(model,request,response);
			
		}
		
	
	@RequestMapping("/RemoveLocationRecord")
    public String removeDeviceMaster(@RequestParam("name") String name, Model model, HttpServletRequest request, HttpServletResponse response){
		
		locationService.removeLocation(name);
	     addSuccessMessage("Location details inactivated successfully.");
	        addSuccessOrErrorMessageToModel(model);
			
	        return listLocationMasters(model,request,response);
	    
    }
 

	


}
