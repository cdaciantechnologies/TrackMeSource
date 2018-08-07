package com.trackme.spring.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trackme.spring.model.UserMaster;

@Service("routeNotificationServiceImpl")
public class RouteNotificationServiceImpl {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	FirebaseService firebaseService;

	public static String queryGeneralEnter = "select um.notificationid, lc.locationdescription from vehicleroutetracking vt join vehiclemaster vm on vm.vehicleno=vt.vehicleno  join routeschedule rs  on rs.id=vt.routescheduleid   join location lc on lc.id=vt.locationid  join student st on vt.routescheduleid=st.pickuprouteschedule  join usermaster um on (um.username like fathermobileno  or um.username like mothermobileno   or um.username like gaurdianmobileno)   where  st.status ='Active' and inreadstatus = false and (select count(*) from locationsforroute lr where lr.locationid=vt.locationid and lr.route = rs.routeid) >0";

	public static String queryPickupEnter = "select um.notificationid, lc.locationdescription from vehicleroutetracking vt join vehiclemaster vm on vm.vehicleno=vt.vehicleno  join routeschedule rs  on rs.id=vt.routescheduleid join location lc on lc.id=vt.locationid  join student st on vt.routescheduleid=st.pickuprouteschedule  join usermaster um on (um.username like fathermobileno  or um.username like mothermobileno   or um.username like gaurdianmobileno)   where  st.status ='Active' and inreadstatus = false and st.pickuplocation= vt.locationid ";

	public static String queryDropEnter = "select um.notificationid, lc.locationdescription from vehicleroutetracking vt join vehiclemaster vm on vm.vehicleno=vt.vehicleno  join routeschedule rs  on rs.id=vt.routescheduleid join location lc on lc.id=vt.locationid  join student st on  vt.routescheduleid=st.droprouteschedule join usermaster um on (um.username like fathermobileno  or um.username like mothermobileno   or um.username like gaurdianmobileno)   where  st.status ='Active' and inreadstatus = false and st.droplocation =vt.locationid ";

	public static String queryEnterUpdate = "update vehicleroutetracking set inreadstatus=true where  inreadstatus=false ";

	public static String queryPickUpExit="select um.notificationid, lc.locationdescription from vehicleroutetracking vt join vehiclemaster vm on vm.vehicleno=vt.vehicleno  join routeschedule rs  on rs.id=vt.routescheduleid  join location lc on lc.id=vt.locationid  join student st on vt.routescheduleid=st.pickuprouteschedule join usermaster um on (um.username like fathermobileno  or um.username like mothermobileno   or um.username like gaurdianmobileno)    where  st.status ='Active' and inreadstatus = true and (outtime is not null and outtime!='' ) and (select count(*) from locationsforroute lr where lr.locationid=vt.locationid and lr.route = rs.routeid) >0";
	
	public static String queryDropExit="select um.notificationid, lc.locationdescription from vehicleroutetracking vt join vehiclemaster vm on vm.vehicleno=vt.vehicleno  join routeschedule rs  on rs.id=vt.routescheduleid  join location lc on lc.id=vt.locationid  join student st on vt.routescheduleid=st.droprouteschedule join usermaster um on (um.username like fathermobileno  or um.username like mothermobileno   or um.username like gaurdianmobileno)    where  st.status ='Active' and inreadstatus = true and (outtime is not null and outtime!='' ) and (select count(*) from locationsforroute lr where lr.locationid=vt.locationid and lr.route = rs.routeid) >0 " ;
	
	public static String queryExitUpdate="update vehicleroutetracking set outreadstatus=true where  outtime is not null and outtime !='' ";
	
	public static String queryForDeleteNotified="delete from vehicleroutetracking where outreadstatus=true and inreadstatus= true";
	
	public boolean insertIntoVehRouteTracking(Integer scheduleId, Integer locationId, String vehicle, String intime,
			String outtime, boolean inread, boolean outread) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO public.vehicleroutetracking( "
				+ " routescheduleid, vehicleno, locationid, intime, inreadstatus, "
				+ " outtime, outreadstatus ) VALUES ( ");
		query.append(scheduleId + ",'" + vehicle + "',");
		query.append(locationId + ",'" + intime + "',");
		query.append(inread + ",'" + outtime + "',");
		query.append(outread);
		query.append(");");
		int total = jdbcTemplate.update(query.toString());

		return true;
	}

	public boolean updateVehRouteTrackingOuttime(Integer scheduleId, Integer locationId, String vehicle,
			String outtime) {
		StringBuilder query = new StringBuilder();
		query.append("update vehicleroutetracking set outtime='" + outtime + "' " + "where " + " routescheduleid= "
				+ scheduleId + " and vehicleno='" + vehicle + "' " + " and locationid=" + locationId);

		int total = jdbcTemplate.update(query.toString());

		return true;
	}

	@Transactional
	public boolean checkVehicleTrakEntry(Integer scheduleId, Integer locationId, String vehicle) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("select routescheduleid from vehicleroutetracking " + "where " + " routescheduleid= " + scheduleId
				+ " and vehicleno='" + vehicle + "' " + " and locationid=" + locationId);
		String query = strBuf.toString();
		Query sqlQuery = session.createSQLQuery(query);
		sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> vehicleLatlngList = sqlQuery.list();
		if (vehicleLatlngList == null || vehicleLatlngList.isEmpty()) {
			return false;
		}
		return true;

	}

	@Transactional
	public boolean checkVehicleTrakEntryForOnlyIn(Integer scheduleId, Integer locationId, String vehicle) {
		Session session = this.sessionFactory.getCurrentSession();
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("select routescheduleid  from vehicleroutetracking where" + " routescheduleid= " + scheduleId
				+ " and vehicleno='" + vehicle + "' " + " and (outtime is null or outtime='') " + " and locationid="
				+ locationId);
		String query = strBuf.toString();
		Query sqlQuery = session.createSQLQuery(query);
		sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> vehicleLatlngList = sqlQuery.list();
		if (vehicleLatlngList == null || vehicleLatlngList.isEmpty()) {
			return false;
		}
		return true;

	}

	@Transactional
	public void updateVehicleroutetrackingEnterDetails() {
		jdbcTemplate.update(queryEnterUpdate);

	}

	@Transactional
	public List vehicleroutetrackingEnterPickupDetails() {
		List<Map<String, Object>> outVehicles = null;

		outVehicles = jdbcTemplate.queryForList(queryPickupEnter);

		return outVehicles;

	}

	@Transactional
	public List vehicleroutetrackingEnterDropDetails() {
		List<Map<String, Object>> outVehicles = null;

		outVehicles = jdbcTemplate.queryForList(queryDropEnter);

		return outVehicles;

	}

	@Transactional
	public List vehicleroutetrackingEnterGeneralDetails() {
		List<Map<String, Object>> outVehicles = null;

		outVehicles = jdbcTemplate.queryForList(queryGeneralEnter);

		return outVehicles;

	}
	
	
	@Transactional
	public List vehicleroutetrackingExitPickUpDetails() {
		List<Map<String, Object>> outVehicles = null;

		outVehicles = jdbcTemplate.queryForList(queryPickUpExit);

		return outVehicles;

	}
	
	@Transactional
	public List vehicleroutetrackingExitDropDetails() {
		List<Map<String, Object>> outVehicles = null;

		outVehicles = jdbcTemplate.queryForList(queryDropExit);

		return outVehicles;

	}
	
	
	@Transactional
	public void updateVehicleroutetrackingExitDetails() {
		jdbcTemplate.update(queryExitUpdate);

	}
	
	@Transactional
	public void deleteNotifiedVehicleroutetrackingDetails() {
		jdbcTemplate.update(queryForDeleteNotified);

	}

	@Transactional
	public void getGeoFenceInDetails() {
		
		String title = "VTS notifation";	
		List<Map<String, Object>> generalEnterList = vehicleroutetrackingEnterGeneralDetails();
		List<Map<String, Object>> pickUpEnterList = vehicleroutetrackingEnterPickupDetails();
		List<Map<String, Object>> dropEnterList = vehicleroutetrackingEnterDropDetails();
		List<Map<String, Object>> pickupExitList = vehicleroutetrackingExitPickUpDetails();
		List<Map<String, Object>> dropExitList = vehicleroutetrackingExitDropDetails();
		updateVehicleroutetrackingEnterDetails();
		updateVehicleroutetrackingExitDetails();
		deleteNotifiedVehicleroutetrackingDetails();
		sendNotificationToList(generalEnterList,title,"School bus reached at ");
		sendNotificationToList(pickUpEnterList,title,"School bus reached at your pickup location");
		sendNotificationToList(dropEnterList,title,"School bus reached at your drop location ");
		sendNotificationToList(pickupExitList,title,"School bus started from location ");
		sendNotificationToList(dropExitList,title,"School bus started from location ");
		
		
	}
	
	public  void sendNotificationToList (List<Map<String, Object>> userMasters, String title, String body) {
		String notificationId="";
		String geofenceName="";
			if (userMasters != null) {
				Iterator<Map<String, Object>> iter = userMasters.iterator();
				while (iter.hasNext()) {
					Map user = iter.next();
					 notificationId = (String) user.get("notificationid");
					geofenceName = (String) user.get("location");
					System.out.println("**********" + notificationId + body + geofenceName);
					if (notificationId != null && notificationId != "") {
						firebaseService.pushNotification(notificationId, body + geofenceName, title, "");
					}
				}
			}
			
	}

}
