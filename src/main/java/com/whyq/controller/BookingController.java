package com.whyq.controller;

import com.whyq.dto.AppointmentDTO;
import com.whyq.entity.Appointment;
import com.whyq.entity.SalonService;
import com.whyq.service.AppointmentService;
import com.whyq.service.SalonsService;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookingController {
	
	private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private SalonsService salonsService;

    @GetMapping("/bookAppointment/{salonEmail}")
    public String bookAppointment(@PathVariable String salonEmail, Model model, HttpSession session) {
        model.addAttribute("salonEmail", salonEmail);
        session.setAttribute("salonEmail", salonEmail);
        model.addAttribute("services", salonsService.getServicesBySalonEmail(salonEmail));
        return "appointmentBooking";
    }

    @PostMapping("/confirmBooking")
    public String confirmBooking(@ModelAttribute AppointmentDTO appointmentDTO, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        appointmentDTO.setUserEmail(userEmail);

        Appointment newAppointment = appointmentService.bookAppointment(appointmentDTO);
        
        // Store the latest booking ID in session
        session.setAttribute("lastBookingId", newAppointment.getId());

        return "redirect:/bookingSuccess";
    }


    @GetMapping("/bookingSuccess")
    public String bookingSuccess(Model model, HttpSession session) {
        Long lastBookingId = (Long) session.getAttribute("lastBookingId"); // Get last booking ID
        String salonEmail = session.getAttribute("salonEmail").toString();
        
        String salonName = salonsService.getSalonNameByEmail(salonEmail);
        
        model.addAttribute("salonName",salonName);
        
        if (lastBookingId == null) {
            return "redirect:/fetchSalon"; // Redirect if no recent booking exists
        }

        // Fetch exact last appointment
        Appointment latestAppointment = appointmentService.getAppointmentById(lastBookingId);
        
        // Remove from session after fetching to prevent incorrect future references
        session.removeAttribute("lastBookingId");

        model.addAttribute("appointment", latestAppointment);
        
        // Fetch Salon Address using email
        String salonAddress = salonsService.getSalonAddressByEmail(latestAppointment.getSalonEmail());
        model.addAttribute("salonAddress", salonAddress);

        return "bookingSuccess";
    }

    @GetMapping("/myBookings")
    public String myBookings(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");

        if (userEmail == null) {
            return "redirect:/login"; // Redirect to login if session is missing
        }

        List<Appointment> appointments = appointmentService.getAppointmentsByUser(userEmail);

        // Fetch and populate salon names & addresses dynamically
        appointments.forEach(appointment -> {
            String salonName = salonsService.getSalonNameByEmail(appointment.getSalonEmail());
            String salonAddress = salonsService.getSalonAddressByEmail(appointment.getSalonEmail());
//            SalonService bookedService = appointment.getService();
            appointment.setSalonName(salonName);
            appointment.setSalonAddress(salonAddress);
        });

        model.addAttribute("appointments", appointments);
        return "myBookings";
    }

    // NEW ENDPOINT: Mark Appointment as Served
    @PostMapping("/serveAppointment/{appointmentId}")
    public String serveAppointment(@PathVariable Long appointmentId) {
        appointmentService.serveAppointment(appointmentId);
        return "redirect:/dashboard"; // Redirect back to owner's dashboard
    }
    
    
    @PostMapping("/deleteAppointment/{appointmentId}")
    public String deleteAppointment(@PathVariable Long appointmentId, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");

        if (appointmentService.deleteAppointment(appointmentId, userEmail)) {
            return "redirect:/myBookings"; // Redirect to My Bookings page after deletion
        } else {
            return "error"; // Redirect to an error page if unauthorized
        }
    }
    
    
    
    
    @PostMapping("/addWalkinCustomer")
    public String addWalkinCustomer(@ModelAttribute AppointmentDTO appointmentDTO, HttpSession session) {
        
        // Get the salon owner's email from session
        String salonEmail = (String) session.getAttribute("ownerEmail");

        if (salonEmail == null) {
            return "redirect:/login"; // If no session, redirect to login
        }

        // Set the salon email to the DTO
        appointmentDTO.setSalonEmail(salonEmail);

        // Mark this as a walk-in appointment (no registered user)
        appointmentDTO.setUserEmail(null); // Since it's a walk-in customer

        // Call the service method to handle appointment creation
        appointmentService.bookWalkinAppointment(appointmentDTO);

        return "redirect:/dashboard"; // Redirect back to refresh the queue
    }
    
    
}