package com.whyq.entity;

import jakarta.persistence.*;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "salon_owners")
public class SalonOwner {

    @Id
    @Column(nullable = false, unique = true)
    private String email;  
    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String salonName;

    @Column(nullable = false, unique = true)
    private String contactNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 6)
    private String pincode;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
        name = "salon_services",
        joinColumns = @JoinColumn(name = "salon_owner_email", referencedColumnName = "email"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<SalonService> services;

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

	public List<SalonService> getServices() {
		return services;
	}

	public void setServices(List<SalonService> services) {
		this.services = services;
	}

	@Override
	public String toString() {
		return "SalonOwner [email=" + email + ", ownerName=" + ownerName + ", salonName=" + salonName
				+ ", contactNumber=" + contactNumber + ", address=" + address + ", pincode=" + pincode + ", password="
				+ password + ", services=" + services + "]";
	}

	public SalonOwner() {
		
	}

    // Getters, Setters, Constructors
    
    
    
    
    
}