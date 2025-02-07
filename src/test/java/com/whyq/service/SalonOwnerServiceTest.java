package com.whyq.service;


import com.whyq.dto.LoginDTO;
import com.whyq.entity.SalonOwner;
import com.whyq.repository.SalonOwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalonOwnerServiceTest {

    @Mock
    private SalonOwnerRepository salonOwnerRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private SalonOwnerService salonOwnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   

    @Test
    void testAuthenticateOwner_Success() {
        SalonOwner owner = new SalonOwner();
        owner.setEmail("test@example.com");
        owner.setPassword("encodedPassword");

        when(salonOwnerRepository.findByEmail(owner.getEmail())).thenReturn(owner);
        when(passwordEncoder.matches("password", owner.getPassword())).thenReturn(true);

        SalonOwner authenticatedOwner = salonOwnerService.authenticateOwner(new LoginDTO("test@example.com", "password"));
        assertNotNull(authenticatedOwner);
    }

    @Test
    void testAuthenticateOwner_Failure() {
        when(salonOwnerRepository.findByEmail("unknown@example.com")).thenReturn(null);

        assertNull(salonOwnerService.authenticateOwner(new LoginDTO("unknown@example.com", "password")));
    }

}