ALTER TABLE public.locationsforroute
  DROP CONSTRAINT locationfk;

ALTER TABLE public.location
  DROP CONSTRAINT location_pk;
 
 ALTER TABLE public.location
  ADD COLUMN id integer NOT NULL; 
  
 ALTER TABLE public.location
  ADD CONSTRAINT location_pk PRIMARY KEY (id); 
  
  ALTER TABLE public.locationsforroute
  DROP COLUMN locationid;
  ALTER TABLE public.locationsforroute
  ADD COLUMN locationid integer NOT NULL;
  
  ALTER TABLE public.locationsforroute
   ALTER COLUMN locationid SET NOT NULL;
ALTER TABLE public.locationsforroute
  ADD CONSTRAINT locationfk FOREIGN KEY (locationid) REFERENCES public.location (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
  
  ALTER TABLE public.locationsforroute
  DROP CONSTRAINT fk_route;
  
   
  DROP TABLE public.locations_for_route
  
  ALTER TABLE public.route
  DROP CONSTRAINT route_pk;
  
  delete from route
  
  ALTER TABLE public.route ALTER COLUMN id SET NOT NULL;
ALTER TABLE public.route
  ADD CONSTRAINT route_pk PRIMARY KEY(id);
  
  ALTER TABLE public.route
  DROP COLUMN locations;
 
 ALTER TABLE public.locationsforroute
  DROP COLUMN route;

  ALTER TABLE public.locationsforroute
  ADD COLUMN route integer;


      ALTER TABLE public.locationsforroute ALTER COLUMN id SET NOT NULL;
	  ALTER TABLE public.locationsforroute
  ADD CONSTRAINT route_fk FOREIGN KEY (route) REFERENCES public.route (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
	
  
  ALTER TABLE public.locationsforroute
   ALTER COLUMN route SET NOT NULL;
  
  ALTER TABLE public.locationsforroute
  ADD COLUMN notification boolean NOT NULL DEFAULT true;
  
  
delete from routeschedule
  ALTER TABLE public.routeschedule
  DROP COLUMN routename;
ALTER TABLE public.routeschedule
  ADD COLUMN id integer NOT NULL;
ALTER TABLE public.routeschedule
  ADD COLUMN routeid integer NOT NULL;
ALTER TABLE public.routeschedule
  DROP CONSTRAINT routeschedule_pk;
ALTER TABLE public.routeschedule
  ADD CONSTRAINT "routeSchedule_PK" PRIMARY KEY (id);
ALTER TABLE public.routeschedule
  ADD CONSTRAINT "routeid_FK" FOREIGN KEY (routeid) REFERENCES public.route (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

drop table routevehicle   
  
  
  ALTER TABLE public.student
  DROP COLUMN schedulename;
ALTER TABLE public.student
  DROP COLUMN dropschedulename;
  delete from student; 
  
ALTER TABLE public.student
  ADD COLUMN pickuprouteschedule integer NOT NULL;
ALTER TABLE public.student
  ADD COLUMN droprouteschedule integer NOT NULL;
ALTER TABLE public.student
  ADD CONSTRAINT "pickuprouteschedule_FK" FOREIGN KEY (pickuprouteschedule) REFERENCES public.routeschedule (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE public.student
  ADD CONSTRAINT "droprouteschedule_FK" FOREIGN KEY (droprouteschedule) REFERENCES public.routeschedule (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

  ALTER TABLE public.student
  DROP COLUMN pickuplocation;
ALTER TABLE public.student
  DROP COLUMN droplocation;
ALTER TABLE public.student
  ADD COLUMN pickuplocation integer NOT NULL;
ALTER TABLE public.student
  ADD COLUMN droplocation integer NOT NULL;
ALTER TABLE public.student
  ADD CONSTRAINT "pickuplocation_FK" FOREIGN KEY (pickuplocation) REFERENCES public.location (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE public.student
  ADD CONSTRAINT "droplocation_FK" FOREIGN KEY (droplocation) REFERENCES public.location (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
  
CREATE TABLE public.vehicleroutetracking
(
   routescheduleid integer, 
   vehicleno text, 
   locationid integer, 
   intime text, 
   inreadstatus boolean, 
   outtime text, 
   outreadstatus boolean
) 
WITH (
  OIDS = FALSE
)
;



select s.studentid as studentId, s.studentname as studentName,
	pickloc.locationdescription as pickupLocation, droploc.locationdescription as dropLocation,
	pr.routename as pickupRoute, dr.routename as dropRoute,
	pickdm.drivername as pickupDriverName, pickdm.contact1 as pickupDriverContact,
	dropdm.drivername as dropDriverName, dropdm.contact1 as dropDriverContact,
	pickvm.vehicleno as pickupVehicleNo,
	dropvm.vehicleno as dropVehicleNo,
	pickrs.starttime as pickupScheduleStarttime,
	droprs.starttime as dropScheduleStarttime
 from student s 
	join location pickloc on s.pickuplocation = pickloc.id
	join location droploc on s.droplocation = droploc.id
	join routeschedule pickrs on s.pickuprouteschedule = pickrs.id
	join routeschedule droprs on s.pickuprouteschedule = droprs.id
	join route pr on pickrs.routeid = pr.id
	join route dr on droprs.routeid = dr.id
	join vehiclemaster pickvm on pickrs.vehicleno = pickvm.vehicleno 
	join vehiclemaster dropvm on droprs.vehicleno = dropvm.vehicleno 
	join driverconf pickdc on pickrs.vehicleno = pickdc.vehicleno
	join driverconf dropdc on droprs.vehicleno = dropdc.vehicleno
	join drivermaster pickdm on pickdc.driverid = pickdm.id
	join drivermaster dropdm on dropdc .driverid = dropdm.id