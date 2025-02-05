package com.whyq.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.whyq.dto.SalonOwnerDTO;
import com.whyq.entity.SalonOwner;
import com.whyq.entity.SalonService;
import com.whyq.repository.SalonOwnerRepository;
import com.whyq.repository.ServiceRepository;

@ExtendWith(MockitoExtension.class)
public class SalonOwnerServiceTest {
	
	@Mock
	private SalonOwnerRepository salonOwnerRepository;
	
	@Mock 
	private ServiceRepository serviceRepository;
	
	@Mock
    private BCryptPasswordEncoder passwordEncoder;
	
	@InjectMocks
	private SalonOwnerService salonOwnerService;
	
	@Test
	public void testRegisterSalonOwner() {
		SalonOwnerDTO dto = new SalonOwnerDTO();
		dto.setEmail("rishabh@gmail.com");
		dto.setSalonName("Hero");
		dto.setAddress("Morabadi road");
		dto.setPassword("Rishabh1");
		dto.setPincode("834009");
		dto.setOwnerName("Rajan");
		dto.setContactNumber("9388382829");
		
		SalonOwner salonOwner = new SalonOwner();
        salonOwner.setEmail(dto.getEmail());
        salonOwner.setSalonName(dto.getSalonName());
        salonOwner.setAddress(dto.getAddress());
        salonOwner.setPassword(dto.getPassword());
        salonOwner.setPincode(dto.getPincode());
        salonOwner.setOwnerName(dto.getOwnerName());
        salonOwner.setContactNumber(dto.getContactNumber());
//        salonOwner.setServices(Arrays.asList(new SalonService()));
        List<SalonService> ss = new ArrayList<>();
        ss.add(new SalonService());
        when(serviceRepository.findAllById(dto.getServiceIds())).thenReturn(null);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("Rishabh1");
        when(salonOwnerRepository.save(any(SalonOwner.class))).thenReturn(salonOwner);
        when(salonOwnerRepository.existsById("rishabh@gmail.com")).thenReturn(false);
        SalonOwner created = salonOwnerService.registerSalonOwner(dto);
        assertEquals("rishabh@gmail.com" , created.getEmail());
//        verify(salonOwnerRepository,times(1)).save(salonOwner);
	}
	
	@Test
	public void testGetSalonsByPincode() {
		
		SalonOwnerDTO dto = new SalonOwnerDTO();
		dto.setEmail("rishabh@gmail.com");
		dto.setSalonName("Hero");
		dto.setAddress("Morabadi road");
		dto.setPassword("Rishabh1");
		dto.setPincode("834009");
		dto.setOwnerName("Rajan");
		dto.setContactNumber("9388382829");
		
		SalonOwner salonOwner = new SalonOwner();
        salonOwner.setEmail(dto.getEmail());
        salonOwner.setSalonName(dto.getSalonName());
        salonOwner.setAddress(dto.getAddress());
        salonOwner.setPassword(dto.getPassword());
        salonOwner.setPincode(dto.getPincode());
        salonOwner.setOwnerName(dto.getOwnerName());
        salonOwner.setContactNumber(dto.getContactNumber());
		
		List<SalonOwner> so = new ArrayList<>();
		so.add(salonOwner);
		when(salonOwnerRepository.findByPincode("843009")).thenReturn(so);
		
		assertEquals(so, salonOwnerService.getSalonsByPincode("843009"));
//		verify(salonOwnerRepository).findByPincode("834009");
		
		
	}
	

}
