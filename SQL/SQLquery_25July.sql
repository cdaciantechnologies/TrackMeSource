ALTER TABLE public.locationsforroute
  DROP CONSTRAINT locationfk;

ALTER TABLE public.location
  DROP CONSTRAINT location_pk;
  
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
  