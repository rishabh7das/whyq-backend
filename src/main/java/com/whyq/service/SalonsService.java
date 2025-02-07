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

    //  Fetch all services offered by a specific salon using EMAIL
    public List<SalonService> getServicesBySalonEmail(String salonEmail) {
        log.info("getServicesBySalonEmail() - Fetching services for salon: {}", salonEmail);
        try {
            SalonOwner salonOwner = salonOwnerRepository.findByEmail(salonEmail);
            if (salonOwner == null) {
                log.warn("getServicesBySalonEmail() - Salon Owner Not Found: {}", salonEmail);
                throw new RuntimeException("Salon Owner Not Found");
            }
            log.info("getServicesBySalonEmail() - Services retrieved successfully for: {}", salonEmail);
            return salonOwner.getServices(); // Fetch services associated with the salon
        } catch (Exception e) {
            log.error("getServicesBySalonEmail() - Error fetching services: {}", e.getMessage());
            throw new RuntimeException("Error fetching services: " + e.getMessage());
        }
    }

    //  Fetch all available services (For dropdown in registration & profile edit)
    public List<SalonService> getAllAvailableServices() {
        log.info("getAllAvailableServices() - Fetching all available services");
        try {
            return serviceRepository.findAll(); // Fetch all predefined services from DB
        } catch (Exception e) {
            log.error("getAllAvailableServices() - Error fetching services: {}", e.getMessage());
            throw new RuntimeException("Error fetching services: " + e.getMessage());
        }
    }

    //  Fetch salon address using email
    public String getSalonAddressByEmail(String email) {
        log.info("getSalonAddressByEmail() - Fetching address for salon: {}", email);
        try {
            return salonOwnerRepository.findById(email)
                    .map(SalonOwner::getAddress)
                    .orElseThrow(() -> {
                        log.warn("getSalonAddressByEmail() - Salon Owner not found: {}", email);
                        return new RuntimeException("Salon Owner not found with email: " + email);
                    });
        } catch (Exception e) {
            log.error("getSalonAddressByEmail() - Error fetching address: {}", e.getMessage());
            throw new RuntimeException("Error fetching address: " + e.getMessage());
        }
    }

    //  Fetch salon name using email
    public String getSalonNameByEmail(String salonEmail) {
        log.info("getSalonNameByEmail() - Fetching name for salon: {}", salonEmail);
        try {
            SalonOwner salonOwner = salonOwnerRepository.findById(salonEmail)
                    .orElseThrow(() -> {
                        log.warn("getSalonNameByEmail() - Salon Owner not found: {}", salonEmail);
                        return new RuntimeException("Salon Owner Not Found");
                    });
            log.info("getSalonNameByEmail() - Salon Name retrieved successfully: {}", salonOwner.getSalonName());
            return salonOwner.getSalonName();
        } catch (Exception e) {
            log.error("getSalonNameByEmail() - Error fetching salon name: {}", e.getMessage());
            throw new RuntimeException("Error fetching salon name: " + e.getMessage());
        }
    }
}