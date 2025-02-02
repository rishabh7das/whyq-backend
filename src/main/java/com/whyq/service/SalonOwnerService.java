package com.whyq.service;

import java.util.List;

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

    @Autowired
    private SalonOwnerRepository salonOwnerRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerSalonOwner(SalonOwnerDTO salonOwnerDTO) {
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
    }
    
    
    public SalonOwner authenticateOwner(LoginDTO loginDTO) {
        SalonOwner owner = salonOwnerRepository.findByEmail(loginDTO.getEmail());
        
//        if(passwordEncoder.matches(loginDTO.getPassword(), owner.getPassword())) {
//        	System.out.println("password matched");
//        }else {
//        	System.out.println("not matched");
//        }
        System.out.println(owner);
        if(owner == null) return null;
        if (owner != null && passwordEncoder.matches(loginDTO.getPassword(), owner.getPassword())) {
            return owner; // Login successful
        }
        return null; // Invalid credentials
    }
    
//    public SalonOwner getByEmailId(String email) {
//    	
//    	SalonOwner owner = salonOwnerRepository.findByEmail(email);
//    	
//    	return owner;
//    }
    
    public SalonOwnerDTO getSalonOwnerProfile(String email) {
        SalonOwner salonOwner = salonOwnerRepository.findByEmail(email);

        if (salonOwner == null) {
            throw new RuntimeException("Salon owner not found!");
        }

        // Convert entity to DTO
        SalonOwnerDTO dto = new SalonOwnerDTO();
        dto.setEmail(salonOwner.getEmail());
        dto.setOwnerName(salonOwner.getOwnerName());
        dto.setSalonName(salonOwner.getSalonName());
        dto.setContactNumber(salonOwner.getContactNumber());
        dto.setAddress(salonOwner.getAddress());
        dto.setPincode(salonOwner.getPincode());

        // Extract service IDs
        List<Long> serviceIds = salonOwner.getServices().stream()
                                         .map(SalonService::getId)
                                         .toList();
        dto.setServiceIds(serviceIds);

        return dto;
    }
    
    
    public void updateSalonOwner(SalonOwnerDTO salonOwnerDTO) {
        SalonOwner existingOwner = salonOwnerRepository.findByEmail(salonOwnerDTO.getEmail());

        if (existingOwner != null) {
            existingOwner.setOwnerName(salonOwnerDTO.getOwnerName());
            existingOwner.setSalonName(salonOwnerDTO.getSalonName());
            existingOwner.setContactNumber(salonOwnerDTO.getContactNumber());
            existingOwner.setAddress(salonOwnerDTO.getAddress());
            existingOwner.setPincode(salonOwnerDTO.getPincode());

            // Update selected services
//            List<SalonService> updatedServices = serviceRepository.findAllById(salonOwnerDTO.getServiceIds());
//            existingOwner.setServices(updatedServices);

            salonOwnerRepository.save(existingOwner);
        }
    }
    
    
    
    public List<SalonOwner> getSalonsByPincode(String pincode) {
        return salonOwnerRepository.findByPincode(pincode);
    }
    
    
    
    public boolean deleteSalonOwnerByEmail(String email) {
        if (salonOwnerRepository.existsById(email)) {
            salonOwnerRepository.deleteById(email);
            return true;
        }
        return false;
    }
}
