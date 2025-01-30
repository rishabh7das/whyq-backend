package com.whyq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.whyq.entity.SalonOwner;

@Repository
public interface SalonOwnerRepository extends JpaRepository<SalonOwner, String> {
	SalonOwner findByEmail(String email);
}