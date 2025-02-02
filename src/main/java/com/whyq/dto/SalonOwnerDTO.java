package com.whyq.dto;

import java.util.List;

public class SalonOwnerDTO {
	private String email;
	private String ownerName;
	private String salonName;
	private String contactNumber;
	private String address;
	private String pincode;
	private String password;
	private List<Long> serviceIds;

	public SalonOwnerDTO() {

	}

// Getters & Setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getSalonName() {
		return salonName;
	}

	public void setSalonName(String salonName) {
		this.salonName = salonName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Long> getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(List<Long> serviceIds) {
		this.serviceIds = serviceIds;
	}

	@Override
	public String toString() {
		return "SalonOwnerDTO [email=" + email + ", ownerName=" + ownerName + ", salonName=" + salonName
				+ ", contactNumber=" + contactNumber + ", address=" + address + ", pincode=" + pincode + ", serviceIds="
				+ serviceIds + "]";
	}
	
	

}