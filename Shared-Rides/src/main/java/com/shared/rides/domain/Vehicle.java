package com.shared.rides.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="Vehicle")
public class Vehicle implements Serializable{
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int vehicleId;
	
	@Column (name="model")
	private String model;
	
	@Column (name="licensePlate")
	private String licensePlate;
	
	@Column (name="seats")
	private int seats;
	
	@Column(name="brand")
	private String brand;
	
//-----------CONSTRUCTOR
	
	public Vehicle(){
		
	}
	
//-----------GETTERS & SETTERS 
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}


	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	
//----------------------	

}