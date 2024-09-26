package com.orbit.care.care_orbit_hub.repository;

import com.orbit.care.care_orbit_hub.entity.MedicalAppointmentEntity;
import com.orbit.care.care_orbit_hub.entity.SpecializationEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpecializationsRepository extends JpaRepository<SpecializationEntitiy, UUID> {

}
