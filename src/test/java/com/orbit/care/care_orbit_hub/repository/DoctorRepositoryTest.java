package com.orbit.care.care_orbit_hub.repository;

import com.orbit.care.care_orbit_hub.entity.DoctorEntity;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    void saveNewDoctorWithEmptyName(){
        assertThrows(ConstraintViolationException.class, () -> {
            DoctorEntity savedDoctor = doctorRepository.save(DoctorEntity.builder()
                            .name("")
                            .build());
                    doctorRepository.flush();
                });

    }

}