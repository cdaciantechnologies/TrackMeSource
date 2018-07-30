package com.trackme.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="LocationsForRoute")
public class LocationsForRoute
{
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
@Column(name="id")
  private Integer id;
@ManyToOne 
@JoinColumn(name="locationId")
  private Location location;
@ManyToOne 
@JsonManagedReference
@JoinColumn(name="route")
  private Route route;

@Column(name="sequence")
private Integer sequence;

@Column(name="notification")
private boolean notification;

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public Location getLocation() {
	return location;
}

public void setLocation(Location location) {
	this.location = location;
}

public Route getRoute() {
	return route;
}

public void setRoute(Route route) {
	this.route = route;
}

public Integer getSequence() {
	return sequence;
}

public void setSequence(Integer sequence) {
	this.sequence = sequence;
}

public boolean isNotification() {
	return notification;
}

public void setNotification(boolean notification) {
	this.notification = notification;
}



}
