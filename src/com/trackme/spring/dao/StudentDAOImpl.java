package com.trackme.spring.dao;

import java.util.List;

import javax.management.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.trackme.constants.Constant;
import com.trackme.spring.model.Student;
import com.trackme.spring.model.UserMaster;


@Repository
public class StudentDAOImpl implements StudentDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	
	static String QueryToCopy="insert into student (StudentId ,StudentName ,STD ,Division ,FatherName ,FatherMobileNo "+
			 " ,MotherName ,MotherMobileNo ,GaurdianName ,GaurdianMobileNo ,PickupLocation "+
			 " ,DropLocation ,pickuprouteschedule,droprouteschedule ,status,createddate,createdby,modifieddate,modifiedby ) "+
			 " select StudentId ,StudentName ,STD ,Division ,FatherName ,FatherMobileNo "+
			 " ,MotherName ,MotherMobileNo ,GaurdianName ,GaurdianMobileNo , "+
			 " (select lc1.id  from location lc1 where lc1.status= 'Active' and  lc1.locationdescription= PickupLocation  FETCH FIRST 1 ROWS ONLY) as PickupLocation, "+ 
			 " (select lc2.id  from location lc2 where lc2.status= 'Active' and  lc2.locationdescription= DropLocation  FETCH FIRST 1 ROWS ONLY) as DropLocation , "+
			 " (select rc1.id from routeschedule rc1 where rc1.schedulename= ScheduleName and rc1.status='Active'  FETCH FIRST 1 ROWS ONLY) as pickuprouteschedule, "+
			 " (select rc2.id from routeschedule rc2 where rc2.schedulename= dropschedulename and rc2.status='Active' FETCH FIRST 1 ROWS ONLY) as droprouteschedule, "+
			 " status,createddate,createdby,modifieddate,modifiedby from studentcopy ";

	static String QueryToDelete="delete from studentcopy";

	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}


	@Override
	public void addStudent(Student student) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(student);
		logger.info("student saved successfully, student Details="+student);
	}

	@Override
	public void updateStudent(Student student) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(student);
		session.flush();
		logger.info("student updated successfully, student Details="+student);
	}

	@Override
	public List<Student> listStudent() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Student> studentList = session.createQuery("from Student where status like '"+Constant.STATUS_ACTIVE+"' ").list();
		
		return studentList;
	}

	@Override
	public Student getStudentById(String id1) {
		try{
			 if(id1!=null && id1 !=""){
			Integer id= Integer.parseInt(id1);
			Session session = this.sessionFactory.getCurrentSession();
			String q ="from Student where id="+id;
	 		org.hibernate.Query query =session.createQuery(q);
			Student student =(Student) query.list().get(0);
			return student;
			 }else{
				 return null;
			 }
		  
		}catch(Exception e){
			return null;
		}
			
			
	}

	@Override
	public void removeStudent(String id) {
		Session session = this.sessionFactory.getCurrentSession();
		Student p = (Student) session.load(Student.class, Integer.parseInt(id));
		if(null != p){
			session.delete(p);
		}
		logger.info("Student deleted successfully, Student details="+p);
		
	}


	@Override
	public String uploadStudentRecord(String filepath) {
		Integer total=null;
		try{			
		StringBuilder queryStr=new StringBuilder();
		queryStr.append("copy studentcopy("+
"StudentId ,StudentName ,STD ,Division ,FatherName "+
",FatherMobileNo ,MotherName ,MotherMobileNo ,GaurdianName "+
 ",GaurdianMobileNo ,PickupLocation ,DropLocation ,ScheduleName,dropschedulename ,status,createddate,"+
 "createdby,modifieddate,modifiedby "+
") FROM '"+filepath+"' WITH CSV HEADER; ");
		//Session session = this.sessionFactory.getCurrentSession();
		//org.hibernate.Query query =session.createQuery(queryStr.toString());
	//total=query.executeUpdate();
		
	jdbcTemplate.update(queryStr.toString()) ;
	
	total =  jdbcTemplate.update(QueryToCopy) ;
	jdbcTemplate.update(QueryToDelete) ;
	
		return total.toString();
		}catch(Exception e){
			return null;
		}
	}
	
	
	

}
