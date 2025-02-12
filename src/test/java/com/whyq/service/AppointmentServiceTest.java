package com.whyq.service;


import com.whyq.entity.Appointment;
import com.whyq.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    
   

    @Test
    void testServeAppointment_Success() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setServed(false);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        appointmentService.serveAppointment(1L);

        assertTrue(appointment.isServed());
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void testGetAppointmentsByUser() {
        appointmentService.getAppointmentsByUser("user@example.com");

        verify(appointmentRepository, times(1)).findByUserEmail("user@example.com");
    }
}