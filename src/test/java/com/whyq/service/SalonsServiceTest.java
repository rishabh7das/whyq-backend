package com.whyq.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.whyq.entity.SalonOwner;
import com.whyq.repository.SalonOwnerRepository;

class SalonsServiceTest {

    @Mock
    private SalonOwnerRepository salonOwnerRepository;

    @InjectMocks
    private SalonsService salonsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSalonNameByEmail_Success() {
        SalonOwner owner = new SalonOwner();
        owner.setEmail("salon@example.com");
        owner.setSalonName("Luxury Salon");

        when(salonOwnerRepository.findById("salon@example.com")).thenReturn(Optional.of(owner));

        String salonName = salonsService.getSalonNameByEmail("salon@example.com");
        assertEquals("Luxury Salon", salonName);
    }

    @Test
    void testGetSalonNameByEmail_NotFound() {
        when(salonOwnerRepository.findById("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> salonsService.getSalonNameByEmail("unknown@example.com"));
    }
}