package com.trackme.spring.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.trackme.constants.Constant;
import com.trackme.spring.model.UserMaster;


@Repository("UserMasterDAO")
public class UserMasterDAOImpl implements UserMasterDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserMasterDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addUserMaster(UserMaster p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("UserMaster saved successfully, UserMaster Details="+p);
	}

	@Override
	public void updateUserMaster(UserMaster p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("UserMaster updated successfully, UserMaster Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMaster> listUserMasters() {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserMaster> UserMastersList = session.createQuery("from UserMaster where status like '"+Constant.STATUS_ACTIVE+"' ").list();
		
		return UserMastersList;
	}

	@Override
	public UserMaster getUserMasterById(String userName) {
		try{
		Session session = this.sessionFactory.getCurrentSession();		
		UserMaster p = (UserMaster) session.load(UserMaster.class, userName);
		logger.info("UserMaster loaded successfully, UserMaster details="+p);
		return p;
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public void removeUserMaster(String userName) {
		Session session = this.sessionFactory.getCurrentSession();
		UserMaster p = (UserMaster) session.load(UserMaster.class, userName);
		if(null != p){
			session.delete(p);
		}
		logger.info("UserMaster deleted successfully, UserMaster details="+p);
	}
	
	@Override
	public List getRouteInfoForParents(String username) {

		List routeInfo = null;

		String query = "select s.studentid as studentId, s.studentname as studentName," + 
				"	pickloc.locationdescription as pickupLocation, droploc.locationdescription as dropLocation," + 
				"	pr.routename as pickupRoute, dr.routename as dropRoute," + 
				"	pickdm.drivername as pickupDriverName, pickdm.contact1 as pickupDriverContact," + 
				"	dropdm.drivername as dropDriverName, dropdm.contact1 as dropDriverContact, " + 
				"	pickvm.vehicleno as pickupVehicleNo," + 
				"	dropvm.vehicleno as dropVehicleNo," + 
				"	pickrs.starttime as pickupScheduleStarttime," + 
				"	droprs.starttime as dropScheduleStarttime " + 
				" 	from student s " + 
				"	join location pickloc on s.pickuplocation = pickloc.id " + 
				"	join location droploc on s.droplocation = droploc.id" + 
				"	join routeschedule pickrs on s.pickuprouteschedule = pickrs.id " + 
				"	join routeschedule droprs on s.pickuprouteschedule = droprs.id " + 
				"	join route pr on pickrs.routeid = pr.id" + 
				"	join route dr on droprs.routeid = dr.id" + 
				"	left join vehiclemaster pickvm on pickrs.vehicleno = pickvm.vehicleno " + 
				"	left join vehiclemaster dropvm on droprs.vehicleno = dropvm.vehicleno " + 
				"	left join driverconf pickdc on (pickdc.status='Active' and pickrs.vehicleno = pickdc.vehicleno)" + 
				"	left join driverconf dropdc on (dropdc.status='Active' and droprs.vehicleno = dropdc.vehicleno )" + 
				"	left join drivermaster pickdm on pickdc.driverid = pickdm.id" + 
				"	left join drivermaster dropdm on dropdc .driverid = dropdm.id"+
				"	where s.status='Active' and  s.fathermobileno= '"+ username +"'"+" or s.mothermobileno = '" + username + "'" +" or s.gaurdianmobileno = '" + username  + "'";

		routeInfo = jdbcTemplate.queryForList(query);

		return routeInfo;

	}
	
	@Override
	public List getPickUpRouteInfoForParents(String username) {

		List routeInfo = null;

		String query = 
				" select s.studentid as studentId, s.studentname as studentName,	pickloc.locationdescription as pickupLocation, "+
				" 	pr.routename as pickupRoute,	pickdm.drivername as pickupDriverName, pickdm.contact1 as pickupDriverContact,pickvm.vehicleno as pickupVehicleNo,	 "+
				"  	pickrs.starttime as pickupScheduleStarttime ,	 "+
				" 'Assistant Name' as assistantname,'999999999' as assistantno  "+
				" 	from student s 	join location pickloc on s.pickuplocation = pickloc.id  "+
				" 	join routeschedule pickrs on s.pickuprouteschedule = pickrs.id  "+
				" 		join route pr on pickrs.routeid = pr.id	 "+
				" 	left join vehiclemaster pickvm on pickrs.vehicleno = pickvm.vehicleno 	 "+
				" 	left join driverconf pickdc on (pickdc.status='Active' and pickrs.vehicleno = pickdc.vehicleno)		 "+
				" 	left join drivermaster pickdm on pickdc.driverid = pickdm.id   "+		
				"	where s.status='Active' and  s.fathermobileno= '"+ username +"'"+" or s.mothermobileno = '" + username + "'" +" or s.gaurdianmobileno = '" + username  + "'";

		routeInfo = jdbcTemplate.queryForList(query);

		return routeInfo;

	}
	
	
	@Override
	public List getDropRouteInfoForParents(String username) {

		List routeInfo = null;

		String query = 
   "  select s.studentid as studentId, s.studentname as studentName,	 droploc.locationdescription as dropLocation ,dr.routename as dropRoute, "+
   " 	dropdm.drivername as dropDriverName, dropdm.contact1 as dropDriverContact, dropvm.vehicleno as dropVehicleNo, droprs.starttime as dropScheduleStarttime , "+
   " 		'Assistant Name' as assistantname,'999999999' as assistantno "+	
   " 	from student s 		join location droploc on s.droplocation = droploc.id "+	
   "        join routeschedule droprs on s.pickuprouteschedule = droprs.id  "+
   " 		join route dr on droprs.routeid = dr.id	"+
   " 			left join vehiclemaster dropvm on droprs.vehicleno = dropvm.vehicleno "+	
   " 		left join driverconf dropdc on (dropdc.status='Active' and droprs.vehicleno = dropdc.vehicleno )	"+
   " 			left join drivermaster dropdm on dropdc .driverid = dropdm.id	"+
	 			"	where s.status='Active' and  s.fathermobileno= '"+ username +"'"+" or s.mothermobileno = '" + username + "'" +" or s.gaurdianmobileno = '" + username  + "'";

		routeInfo = jdbcTemplate.queryForList(query);

		return routeInfo;

	}
	
}
