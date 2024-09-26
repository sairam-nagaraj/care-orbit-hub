package com.orbit.care.care_orbit_hub.repository;

import com.orbit.care.care_orbit_hub.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<DoctorEntity, UUID> {
}
