package com.whyq.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail; // Email of user who booked
    private String salonEmail; // Email of the salon owner

    @ManyToOne
    @JoinColumn(name = "service_id")
    private SalonService service;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    
    @Transient
    private String salonName;
    
    @Transient
    private String salonAddress;
    
    private String customerName;
    
    
    
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	private boolean isServed = false;
    
    
    
    @Override
	public String toString() {
		return "Appointment [id=" + id + ", userEmail=" + userEmail + ", salonEmail=" + salonEmail + ", service="
				+ service + ", appointmentDate=" + appointmentDate + ", appointmentTime=" + appointmentTime
				+ ", salonName=" + salonName + ", salonAddress=" + salonAddress + ", isServed=" + isServed + "]";
	}
	public String getSalonName() {
		return salonName;
	}
	public void setSalonName(String salonName) {
		this.salonName = salonName;
	}
	public String getSalonAddress() {
		return salonAddress;
	}
	public void setSalonAddress(String salonAddress) {
		this.salonAddress = salonAddress;
	}
	public boolean isServed() {
		return isServed;
	}
	public void setServed(boolean isServed) {
		this.isServed = isServed;
	}
	// Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getSalonEmail() { return salonEmail; }
    public void setSalonEmail(String salonEmail) { this.salonEmail = salonEmail; }

    public SalonService getService() { return service; }
    public void setService(SalonService service) { this.service = service; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
}