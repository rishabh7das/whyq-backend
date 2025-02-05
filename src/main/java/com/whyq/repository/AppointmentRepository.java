package com.whyq.repository;

import com.whyq.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByUserEmail(String userEmail);

    List<Appointment> findBySalonEmailAndIsServedFalse(String salonEmail); 
    
    List<Appointment>findBySalonEmailAndIsServedFalseOrderByAppointmentDateAscAppointmentTimeAsc(String salonEmail);
    
    }

