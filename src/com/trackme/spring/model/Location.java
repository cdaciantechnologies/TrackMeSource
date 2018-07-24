package com.trackme.spring.model;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.util.StringUtils;

import com.trackme.constants.Constant;
import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(name = "location")

public class Location {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "locationname")
	private String locationName;
	@Column(name = "latitude")
	private String latitude;
	@Column(name = "longitude")
	private String longitude;
	@Column(name = "radiuslocation")
	private String radiusLocation;
	@Column(name = "radiusreferlocation")
	private String radiusReferLocation;
	@Column(name = "locationdescription")
	private String locationDescription;
	@Column(name = "address")
	private String address;
	@Column(name = "status")
	private String status;

	@Column(columnDefinition = "Geometry")
	@Type(type = "org.hibernate.spatial.GeometryType")
	private Geometry geometry;
	
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	@Transient
	private String vertices;

	public String getVertices() {
		return vertices;
	}

	public void setVertices(String vertices) {
		this.vertices = vertices;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getRadiusLocation() {
		return this.radiusLocation;
	}

	public void setRadiusLocation(String radiusLocation) {
		this.radiusLocation = radiusLocation;
	}

	public String getRadiusReferLocation() {
		return this.radiusReferLocation;
	}

	public void setRadiusReferLocation(String radiusReferLocation) {
		this.radiusReferLocation = radiusReferLocation;
	}

	public String getLocationName() {
		return this.locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationDescription() {
		return this.locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "createddate")
	private Date createdDate;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/*
	 * public Geometry getGeometry() { return geometry; }
	 * 
	 * public void setGeometry(Geometry geometry) { this.geometry = geometry; }
	 */

	@Column(name = "createdBy")
	private String createdBy;

	@Column(name = "modifiedBy")
	private String modifiedBy;

	@Column(name = "modifiedDate")
	private Date modifiedDate;
	@Transient
	private String createdDateShow;
	@Transient
	private String modifiedDateShow;

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {

		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedDateShow() {
		if (this.createdDate != null) {
			return Constant.dateFormater.format(this.createdDate);
		} else {
			return "";
		}
		// return createdDateShow;
	}

	public void setCreatedDateShow(String createdDateShow) {
		if (StringUtils.isEmpty(createdDateShow))
			this.createdDate = null;
		else
			try {
				this.createdDate = Constant.dateFormater.parse(createdDateShow);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		this.createdDateShow = createdDateShow;
	}

	public String getModifiedDateShow() {
		if (this.modifiedDate != null) {
			return Constant.dateFormater.format(this.modifiedDate);
		} else {
			return "";
		}
		// return modifiedDateShow;
	}

	public void setModifiedDateShow(String modifiedDateShow) {
		if (StringUtils.isEmpty(modifiedDateShow))
			this.modifiedDate = null;
		else
			try {
				this.modifiedDate = Constant.dateFormater.parse(modifiedDateShow);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		this.modifiedDateShow = modifiedDateShow;
	}

	@Transient
	boolean editFlag;

	public boolean isEditFlag() {
		return editFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
