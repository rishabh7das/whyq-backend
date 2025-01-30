package com.whyq.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "services")
public class SalonService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String serviceName;

    @ManyToMany(mappedBy = "services")
    private List<SalonOwner> salonOwners;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<SalonOwner> getSalonOwners() {
		return salonOwners;
	}

	public void setSalonOwners(List<SalonOwner> salonOwners) {
		this.salonOwners = salonOwners;
	}

	public SalonService() {
		
	}

    // Getters, Setters, Constructors
    
    
    
}