
CREATE TABLE public.studentcopy
(
  studentid text,
  studentname text,
  std text,
  division text,
  fathername text,
  fathermobileno text,
  mothername text,
  mothermobileno text,
  gaurdianname text,
  gaurdianmobileno text,
  createdby text,
  createddate date,
  modifiedby text,
  modifieddate date,
  routename text,
  status text,
  ScheduleName text,
  dropschedulename text,
  PickupLocation text,
  DropLocation text
);

ALTER TABLE public.student
   ALTER COLUMN pickuprouteschedule DROP NOT NULL;
ALTER TABLE public.student
   ALTER COLUMN droprouteschedule DROP NOT NULL;
ALTER TABLE public.student
   ALTER COLUMN pickuplocation DROP NOT NULL;
ALTER TABLE public.student
   ALTER COLUMN droplocation DROP NOT NULL;
