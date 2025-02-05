package com.whyq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whyq.entity.SalonOwner;
import com.whyq.entity.SalonService;
import com.whyq.repository.SalonOwnerRepository;
import com.whyq.repository.ServiceRepository;

import java.util.List;

@Service
public class SalonsService {
	
	private static final Logger log = LoggerFactory.getLogger(SalonsService.class);

    @Autowired
    private SalonOwnerRepository salonOwnerRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // Fetch all services offered by a specific salon using EMAIL
    public List<SalonService> getServicesBySalonEmail(String salonEmail) {
//    	System.out.println("**********"+salonEmail+"**********");
        SalonOwner salonOwner = salonOwnerRepository.findByEmail(salonEmail);
//                .orElseThrow(() -> new RuntimeException("Salon Owner Not Found"));
        return salonOwner.getServices(); // Fetch services associated with the salon
    }

    // Fetch all available services (For dropdown in registration & profile edit)
    public List<SalonService> getAllAvailableServices() {
        return serviceRepository.findAll(); // Fetch all predefined services from DB
    }
    
    public String getSalonAddressByEmail(String email) {
        return salonOwnerRepository.findById(email)
                .map(SalonOwner::getAddress)
                .orElseThrow(() -> new RuntimeException("Salon Owner not found with email: " + email));
    }

    
    public String getSalonNameByEmail(String salonEmail) {
        SalonOwner salonOwner = salonOwnerRepository.findById(salonEmail)
                .orElseThrow(() -> new RuntimeException("Salon Owner Not Found"));
        return salonOwner.getSalonName();
    }

    

   
}