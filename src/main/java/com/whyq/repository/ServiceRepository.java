package com.whyq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.whyq.entity.SalonService;

@Repository
public interface ServiceRepository extends JpaRepository<SalonService, Long> {
}