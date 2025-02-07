package com.whyq.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.whyq.dto.LoginDTO;
import com.whyq.dto.SalonOwnerDTO;
import com.whyq.entity.SalonOwner;
import com.whyq.entity.SalonService;
import com.whyq.repository.SalonOwnerRepository;
import com.whyq.repository.ServiceRepository;

@Service
public class SalonOwnerService {
    
    private static final Logger log = LoggerFactory.getLogger(SalonOwnerService.class);

    @Autowired
    private SalonOwnerRepository salonOwnerRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //  Register a new salon owner
    public SalonOwner registerSalonOwner(SalonOwnerDTO salonOwnerDTO) {
        log.info("registerSalonOwner() - Registering salon owner: {}", salonOwnerDTO.getEmail());
        try {
            if (salonOwnerRepository.existsById(salonOwnerDTO.getEmail())) {
                throw new RuntimeException("Salon owner with this email already exists!");
            }

            List<SalonService> services = serviceRepository.findAllById(salonOwnerDTO.getServiceIds());

            SalonOwner salonOwner = new SalonOwner();
            salonOwner.setEmail(salonOwnerDTO.getEmail());
            salonOwner.setOwnerName(salonOwnerDTO.getOwnerName());
            salonOwner.setSalonName(salonOwnerDTO.getSalonName());
            salonOwner.setContactNumber(salonOwnerDTO.getContactNumber());
            salonOwner.setAddress(salonOwnerDTO.getAddress());
            salonOwner.setPincode(salonOwnerDTO.getPincode());
            salonOwner.setPassword(passwordEncoder.encode(salonOwnerDTO.getPassword()));
            salonOwner.setServices(services);

            salonOwnerRepository.save(salonOwner);
            log.info("registerSalonOwner() - Salon owner registered successfully: {}", salonOwner);
            return salonOwner;
        } catch (Exception e) {
            log.error("registerSalonOwner() - Error registering salon owner: {}", e.getMessage());
            throw new RuntimeException("Error registering salon owner: " + e.getMessage());
        }
    }

    //  Authenticate salon owner login
    public SalonOwner authenticateOwner(LoginDTO loginDTO) {
        log.info("authenticateOwner() - Authenticating salon owner: {}", loginDTO.getEmail());
        try {
            SalonOwner owner = salonOwnerRepository.findByEmail(loginDTO.getEmail());
            if (owner == null || !passwordEncoder.matches(loginDTO.getPassword(), owner.getPassword())) {
                log.warn("authenticateOwner() - Invalid credentials for: {}", loginDTO.getEmail());
                return null; // Invalid credentials
            }
            log.info("authenticateOwner() - Authentication successful for: {}", loginDTO.getEmail());
            return owner;
        } catch (Exception e) {
            log.error("authenticateOwner() - Error during authentication: {}", e.getMessage());
            throw new RuntimeException("Error during authentication: " + e.getMessage());
        }
    }

    //  Fetch salon owner profile
    public SalonOwnerDTO getSalonOwnerProfile(String email) {
        log.info("getSalonOwnerProfile() - Fetching profile for: {}", email);
        try {
            SalonOwner salonOwner = salonOwnerRepository.findByEmail(email);
            if (salonOwner == null) {
                throw new RuntimeException("Salon owner not found!");
            }

            SalonOwnerDTO dto = new SalonOwnerDTO();
            dto.setEmail(salonOwner.getEmail());
            dto.setOwnerName(salonOwner.getOwnerName());
            dto.setSalonName(salonOwner.getSalonName());
            dto.setContactNumber(salonOwner.getContactNumber());
            dto.setAddress(salonOwner.getAddress());
            dto.setPincode(salonOwner.getPincode());

            List<Long> serviceIds = salonOwner.getServices().stream().map(SalonService::getId).toList();
            dto.setServiceIds(serviceIds);

            log.info("getSalonOwnerProfile() - Profile retrieved successfully for: {}", email);
            return dto;
        } catch (Exception e) {
            log.error("getSalonOwnerProfile() - Error fetching profile: {}", e.getMessage());
            throw new RuntimeException("Error fetching profile: " + e.getMessage());
        }
    }

    //  Update salon owner details
    public void updateSalonOwner(SalonOwnerDTO salonOwnerDTO) {
        log.info("updateSalonOwner() - Updating profile for: {}", salonOwnerDTO.getEmail());
        try {
            SalonOwner existingOwner = salonOwnerRepository.findByEmail(salonOwnerDTO.getEmail());

            if (existingOwner != null) {
                existingOwner.setOwnerName(salonOwnerDTO.getOwnerName());
                existingOwner.setSalonName(salonOwnerDTO.getSalonName());
                existingOwner.setContactNumber(salonOwnerDTO.getContactNumber());
                existingOwner.setAddress(salonOwnerDTO.getAddress());
                existingOwner.setPincode(salonOwnerDTO.getPincode());

                salonOwnerRepository.save(existingOwner);
                log.info("updateSalonOwner() - Profile updated successfully for: {}", salonOwnerDTO.getEmail());
            } else {
                throw new RuntimeException("Salon owner not found!");
            }
        } catch (Exception e) {
            log.error("updateSalonOwner() - Error updating profile: {}", e.getMessage());
            throw new RuntimeException("Error updating profile: " + e.getMessage());
        }
    }

    //  Fetch salons by pincode
    public List<SalonOwner> getSalonsByPincode(String pincode) {
        log.info("getSalonsByPincode() - Fetching salons for pincode: {}", pincode);
        try {
            return salonOwnerRepository.findByPincode(pincode);
        } catch (Exception e) {
            log.error("getSalonsByPincode() - Error fetching salons: {}", e.getMessage());
            throw new RuntimeException("Error fetching salons: " + e.getMessage());
        }
    }

    //  Delete salon owner
    public boolean deleteSalonOwnerByEmail(String email) {
        log.info("deleteSalonOwnerByEmail() - Deleting salon owner: {}", email);
        try {
            if (salonOwnerRepository.existsById(email)) {
                salonOwnerRepository.deleteById(email);
                log.info("deleteSalonOwnerByEmail() - Salon owner deleted successfully: {}", email);
                return true;
            } else {
                log.warn("deleteSalonOwnerByEmail() - No salon owner found for: {}", email);
                return false;
            }
        } catch (Exception e) {
            log.error("deleteSalonOwnerByEmail() - Error deleting salon owner: {}", e.getMessage());
            throw new RuntimeException("Error deleting salon owner: " + e.getMessage());
        }
    }
}