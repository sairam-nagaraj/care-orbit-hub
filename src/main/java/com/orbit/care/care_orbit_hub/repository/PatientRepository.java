package com.orbit.care.care_orbit_hub.repository;

import com.orbit.care.care_orbit_hub.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<PatientEntity, UUID> {

    public Page<PatientEntity> findByNameLikeIgnoreCaseAndIdNumberLikeIgnoreCaseAndCityLikeIgnoreCase(String name, String idNumber, String city, Pageable pageable);
}
