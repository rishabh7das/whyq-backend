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
        
        if(passwordEncoder.matches(loginDTO.getPassword(), owner.getPassword())) {
        	System.out.println("password matched");
        }else {
        	System.out.println("not matched");
        }
        
        if (owner != null && passwordEncoder.matches(loginDTO.getPassword(), owner.getPassword())) {
            return owner; // Login successful
        }
        return null; // Invalid credentials
    }
    
    public SalonOwner getByEmailId(String email) {
    	
    	SalonOwner owner = salonOwnerRepository.findByEmail(email);
    	
    	return owner;
    }
}
