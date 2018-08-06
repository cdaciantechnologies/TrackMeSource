package com.trackme.spring.service;

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

@Service("routeNotificationServiceImpl")
public class RouteNotificationServiceImpl {
	@Autowired
	private JdbcTemplate jdbcTemplate;  

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean insertIntoVehRouteTracking(Integer scheduleId, Integer locationId, String vehicle, String intime,
			String outtime, boolean inread, boolean outread) {
		StringBuilder query =new StringBuilder();
		query.append("INSERT INTO public.vehicleroutetracking( "+
            " routescheduleid, vehicleno, locationid, intime, inreadstatus, "+
            " outtime, outreadstatus ) VALUES ( " );
          query.append(scheduleId +",'"+vehicle+"',");
          query.append( locationId +",'"+intime+"'," );	
          query.append( inread +",'"+outtime+"'," );
          query.append( outread );
            		query.append(");");
		int total =jdbcTemplate.update(query.toString()) ;
			
		return true;
	}

	
	public boolean updateVehRouteTrackingOuttime(Integer scheduleId, Integer locationId, String vehicle, String outtime) {
		StringBuilder query =new StringBuilder();
		query.append("update vehicleroutetracking set outtime='"+outtime+"' "
		+ "where "
		+ " routescheduleid= "+scheduleId
		+ " and vehicleno='"+vehicle+"' "
		+ " and locationid="+ locationId);				

		int total =jdbcTemplate.update(query.toString()) ;
			
		return true;
	}

	
	
	@Transactional
	public boolean checkVehicleTrakEntry(Integer scheduleId, Integer locationId, String vehicle){
			Session session = this.sessionFactory.getCurrentSession();
			StringBuffer strBuf= new StringBuffer();
			strBuf.append("select routescheduleid from vehicleroutetracking "
					+ "where "
					+ " routescheduleid= "+scheduleId
					+ " and vehicleno='"+vehicle+"' "
					+ " and locationid="+ locationId);				
			String query = strBuf.toString();
			Query sqlQuery = session.createSQLQuery(query);
			sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String, Object>>	vehicleLatlngList = sqlQuery.list();
			if(vehicleLatlngList==null || vehicleLatlngList.isEmpty() ){
				 return false;
				}
			return true;

		
	}


	@Transactional
	public boolean checkVehicleTrakEntryForOnlyIn(Integer scheduleId, Integer locationId, String vehicle){
			Session session = this.sessionFactory.getCurrentSession();
			StringBuffer strBuf= new StringBuffer();
			strBuf.append("select routescheduleid  from vehicleroutetracking where"
					+ " routescheduleid= "+scheduleId
					+ " and vehicleno='"+vehicle+"' "
					+" and (outtime is null or outtime='') "
					+ " and locationid="+ locationId);				
			String query = strBuf.toString();
			Query sqlQuery = session.createSQLQuery(query);
			sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String, Object>>	vehicleLatlngList = sqlQuery.list();
			if(vehicleLatlngList==null || vehicleLatlngList.isEmpty() ){
				 return false;
				}
			return true;

		
	}

	
}
