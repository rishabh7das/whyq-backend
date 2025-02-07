package com.whyq.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDTO {
    private String salonEmail;  // Email of salon owner
    private Long serviceId;     // ID of the service chosen
    private String userEmail;   // Email of user booking
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String customerName;
    
    

    // Getters and Setters
    
    
    
    public String getSalonEmail() { return salonEmail; }
    
    public AppointmentDTO() {
		
	}

	public AppointmentDTO(String salonEmail, Long serviceId, String userEmail, LocalDate appointmentDate,
			LocalTime appointmentTime, String customerName) {
		super();
		this.salonEmail = salonEmail;
		this.serviceId = serviceId;
		this.userEmail = userEmail;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.customerName = customerName;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setSalonEmail(String salonEmail) { this.salonEmail = salonEmail; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
	@Override
	public String toString() {
		return "AppointmentDTO [salonEmail=" + salonEmail + ", serviceId=" + serviceId + ", userEmail=" + userEmail
				+ ", appointmentDate=" + appointmentDate + ", appointmentTime=" + appointmentTime + "]";
	}
	
	
    
    
}

