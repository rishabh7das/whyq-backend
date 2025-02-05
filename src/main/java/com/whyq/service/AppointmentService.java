package com.whyq.service;

import com.whyq.dto.AppointmentDTO;
import com.whyq.entity.Appointment;
import com.whyq.entity.SalonService;
import com.whyq.repository.AppointmentRepository;
import com.whyq.repository.ServiceRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {
	
	private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private UserService userService;

    public Appointment bookAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        appointment.setUserEmail(appointmentDTO.getUserEmail());
        appointment.setSalonEmail(appointmentDTO.getSalonEmail());
        String customerName = userService.getUserNameByEmail(appointmentDTO.getUserEmail());
        appointment.setCustomerName(customerName);
        SalonService service = serviceRepository.findById(appointmentDTO.getServiceId()).orElse(null);
        appointment.setService(service);

        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());

        appointmentRepository.save(appointment);
        return appointment;
    }

    public List<Appointment> getAppointmentsByUser(String userEmail) {
        return appointmentRepository.findByUserEmail(userEmail);
    }

    // NEW METHOD: Mark Appointment as Served
    public void serveAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment Not Found"));
        appointment.setServed(true); // Mark as served
        appointmentRepository.save(appointment);
    }

    // Fetch Unserved Appointments for Salon Owner
    public List<Appointment> getPendingAppointments(String salonEmail) {
        return appointmentRepository.findBySalonEmailAndIsServedFalse(salonEmail);
    }
    
    
    public boolean deleteAppointment(Long appointmentId, String userEmail) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment Not Found"));

        // Ensure the logged-in user is the owner of the booking
        if (!appointment.getUserEmail().equals(userEmail)) {
            return false; // Unauthorized deletion attempt
        }

        appointmentRepository.delete(appointment);
        return true;
    }
    
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }
    
    
    
    public List<Appointment> getUnservedAppointments(String salonEmail) {
        return appointmentRepository.findBySalonEmailAndIsServedFalseOrderByAppointmentDateAscAppointmentTimeAsc(salonEmail);
    }
    
    
    
    public void bookWalkinAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        
        // If userEmail is null, it's a walk-in customer
        if (appointmentDTO.getUserEmail() == null || appointmentDTO.getUserEmail().isEmpty()) {
            appointment.setCustomerName(appointmentDTO.getCustomerName()); // Walk-in customer
        } else {
            appointment.setUserEmail(appointmentDTO.getUserEmail()); // Registered user
        }

        appointment.setSalonEmail(appointmentDTO.getSalonEmail());

        SalonService service = serviceRepository.findById(appointmentDTO.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        appointment.setService(service);
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
        appointment.setServed(false); // Initially, walk-ins are not served

        appointmentRepository.save(appointment);
    }

}

