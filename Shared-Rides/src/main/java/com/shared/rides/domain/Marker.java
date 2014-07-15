package com.shared.rides.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

/*
 Clase que representa los diferentes puntos de ubicación que va a servir
 para interactuar con la API.
 */

@Entity
@Table(name="Marker")
public class Marker implements Serializable{

	private long markerId;	
	private double latitude;
	private double longitude;
	
//-----------CONSTRUCTOR 
	
	public Marker(){
	}

//-----------GETTERS & SETTERS 

	@Id
	@GeneratedValue
	@Column(name="id", nullable = false)
	public long getMarkerId() {
		return markerId;
	}
	public void setMarkerId(long markerId) {
		this.markerId = markerId;
	}
	
	@Column(name="lat", nullable = false)
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	@Column(name="lon", nullable = false)
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


//-----------------------
	
}
