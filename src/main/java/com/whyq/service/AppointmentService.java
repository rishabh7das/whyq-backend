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

    // ✅ Book an appointment
    public Appointment bookAppointment(AppointmentDTO appointmentDTO) {
        log.info("bookAppointment() - Booking appointment for User: {}, Salon: {}", appointmentDTO.getUserEmail(), appointmentDTO.getSalonEmail());

        try {
            Appointment appointment = new Appointment();
            appointment.setUserEmail(appointmentDTO.getUserEmail());
            appointment.setSalonEmail(appointmentDTO.getSalonEmail());

            String customerName = userService.getUserNameByEmail(appointmentDTO.getUserEmail());
            appointment.setCustomerName(customerName);

            SalonService service = serviceRepository.findById(appointmentDTO.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found for ID: " + appointmentDTO.getServiceId()));

            appointment.setService(service);
            appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
            appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
            appointment.setServed(false); // New bookings are initially unserved

            appointmentRepository.save(appointment);
            log.info("bookAppointment() - Appointment booked successfully: {}", appointment);
            return appointment;
        } catch (Exception e) {
            log.error("bookAppointment() - Error booking appointment: {}", e.getMessage());
            throw new RuntimeException("Error booking appointment: " + e.getMessage());
        }
    }

    // ✅ Fetch all appointments for a user
    public List<Appointment> getAppointmentsByUser(String userEmail) {
        log.info("getAppointmentsByUser() - Fetching appointments for User: {}", userEmail);
        try {
            return appointmentRepository.findByUserEmail(userEmail);
        } catch (Exception e) {
            log.error("getAppointmentsByUser() - Error fetching appointments: {}", e.getMessage());
            throw new RuntimeException("Error fetching appointments: " + e.getMessage());
        }
    }

    // ✅ Mark an appointment as served
    public void serveAppointment(Long appointmentId) {
        log.info("serveAppointment() - Serving appointment ID: {}", appointmentId);
        try {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment Not Found for ID: " + appointmentId));

            appointment.setServed(true);
            appointmentRepository.save(appointment);
            log.info("serveAppointment() - Appointment marked as served: {}", appointment);
        } catch (Exception e) {
            log.error("serveAppointment() - Error serving appointment: {}", e.getMessage());
            throw new RuntimeException("Error serving appointment: " + e.getMessage());
        }
    }

    // ✅ Fetch unserved appointments for a salon owner
    public List<Appointment> getPendingAppointments(String salonEmail) {
        log.info("getPendingAppointments() - Fetching unserved appointments for Salon: {}", salonEmail);
        try {
            return appointmentRepository.findBySalonEmailAndIsServedFalse(salonEmail);
        } catch (Exception e) {
            log.error("getPendingAppointments() - Error fetching unserved appointments: {}", e.getMessage());
            throw new RuntimeException("Error fetching unserved appointments: " + e.getMessage());
        }
    }

    // ✅ Delete an appointment
    public boolean deleteAppointment(Long appointmentId, String userEmail) {
        log.info("deleteAppointment() - Deleting appointment ID: {} for User: {}", appointmentId, userEmail);
        try {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment Not Found for ID: " + appointmentId));

            if (!appointment.getUserEmail().equals(userEmail)) {
                log.warn("deleteAppointment() - Unauthorized deletion attempt by User: {}", userEmail);
                return false;
            }

            appointmentRepository.delete(appointment);
            log.info("deleteAppointment() - Appointment deleted successfully: {}", appointmentId);
            return true;
        } catch (Exception e) {
            log.error("deleteAppointment() - Error deleting appointment: {}", e.getMessage());
            throw new RuntimeException("Error deleting appointment: " + e.getMessage());
        }
    }

    // ✅ Fetch an appointment by ID
    public Appointment getAppointmentById(Long id) {
        log.info("getAppointmentById() - Fetching appointment ID: {}", id);
        try {
            return appointmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Appointment not found for ID: " + id));
        } catch (Exception e) {
            log.error("getAppointmentById() - Error fetching appointment: {}", e.getMessage());
            throw new RuntimeException("Error fetching appointment: " + e.getMessage());
        }
    }

    // ✅ Fetch unserved appointments sorted by date and time
    public List<Appointment> getUnservedAppointments(String salonEmail) {
        log.info("getUnservedAppointments() - Fetching unserved appointments for Salon: {}", salonEmail);
        try {
            return appointmentRepository.findBySalonEmailAndIsServedFalseOrderByAppointmentDateAscAppointmentTimeAsc(salonEmail);
        } catch (Exception e) {
            log.error("getUnservedAppointments() - Error fetching unserved appointments: {}", e.getMessage());
            throw new RuntimeException("Error fetching unserved appointments: " + e.getMessage());
        }
    }

    // ✅ Book a walk-in appointment
    public void bookWalkinAppointment(AppointmentDTO appointmentDTO) {
        log.info("bookWalkinAppointment() - Booking walk-in appointment for Salon: {}", appointmentDTO.getSalonEmail());
        try {
            Appointment appointment = new Appointment();

            if (appointmentDTO.getUserEmail() == null || appointmentDTO.getUserEmail().isEmpty()) {
                appointment.setCustomerName(appointmentDTO.getCustomerName()); // Walk-in customer
            } else {
                appointment.setUserEmail(appointmentDTO.getUserEmail()); // Registered user
            }

            appointment.setSalonEmail(appointmentDTO.getSalonEmail());

            SalonService service = serviceRepository.findById(appointmentDTO.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found for ID: " + appointmentDTO.getServiceId()));

            appointment.setService(service);
            appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
            appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
            appointment.setServed(false); // Initially, walk-ins are not served

            appointmentRepository.save(appointment);
            log.info("bookWalkinAppointment() - Walk-in appointment booked successfully: {}", appointment);
        } catch (Exception e) {
            log.error("bookWalkinAppointment() - Error booking walk-in appointment: {}", e.getMessage());
            throw new RuntimeException("Error booking walk-in appointment: " + e.getMessage());
        }
    }
}