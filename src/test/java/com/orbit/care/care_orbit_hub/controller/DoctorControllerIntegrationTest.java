package com.orbit.care.care_orbit_hub.controller;

import com.orbit.care.care_orbit_hub.dto.DoctorDTO;
import com.orbit.care.care_orbit_hub.entity.DoctorEntity;
import com.orbit.care.care_orbit_hub.exception.NotFoundException;
import com.orbit.care.care_orbit_hub.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@ActiveProfiles({"test"})
class DoctorControllerIntegrationTest {

    @Autowired
    DoctorController doctorController;

    @Autowired
    DoctorRepository doctorRepository;

    @Test
    @DisplayName("List all Doctors")
    void listAllDoctors() {
        List<DoctorDTO> doctorsList = doctorController.listAllDoctors();
        assertThat(doctorsList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void listAllDoctorsEmptyList() {
        doctorRepository.deleteAll();
        List<DoctorDTO> doctorsList = doctorController.listAllDoctors();
        assertThat(doctorsList.size()).isEqualTo(0);
    }

    @Transactional
    @Test
    void getDoctorById() {
        DoctorEntity doctorEntity = doctorRepository.findAll().get(0);
        DoctorDTO doctorDTO = doctorController.getDoctorById(doctorEntity.getId());
        assertThat(doctorDTO).isNotNull();
    }

    @Test
    void getDoctorByIdExceptionCase() {
        assertThrows(NotFoundException.class, ()->{
            DoctorDTO doctorDTO = doctorController.getDoctorById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void addDoctor() {
        DoctorDTO newDoctor = DoctorDTO.builder()
                .id(UUID.randomUUID())
                .name("Sheobalak")
                .email("Sheobalak@test.com")
                .cellphone(5252696344l)
                .location("Johannesburg")
                .build();

        ResponseEntity savedDoctor = doctorController.addDoctor(newDoctor);

        assertThat(savedDoctor.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(savedDoctor.getHeaders().getLocation()).isNotNull();
        log.info("saved location "+ savedDoctor.getHeaders().getLocation());

    }

    @Test
    void updateDoctor() {
        DoctorDTO doctorDTO = doctorController.listAllDoctors().get(0);
        UUID refId = doctorDTO.getId();
        doctorDTO.setId(null);
        doctorDTO.setName("Updated");
        ResponseEntity responseEntity = doctorController.updateDoctor(refId, doctorDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void updateDoctorForUnknownId() {
        assertThrows(NotFoundException.class, () -> {
            DoctorDTO doctorDTO = doctorController.listAllDoctors().get(0);
            doctorDTO.setId(null);
            doctorDTO.setName("Updated");
            ResponseEntity responseEntity = doctorController.updateDoctor(UUID.randomUUID(), doctorDTO);
        });
    }

    @Test
    @Transactional
    @Rollback
    void deleteDoctor() {
        UUID recordIdToDelete = doctorRepository.findAll().get(0).getId();
        ResponseEntity responseEntity = doctorController.deleteDoctor(recordIdToDelete);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Transactional
    @Rollback
    void deleteDoctorWithRandomId() {
        assertThrows(NotFoundException.class, () -> {
            UUID recordIdToDelete = UUID.randomUUID();
            ResponseEntity responseEntity = doctorController.deleteDoctor(recordIdToDelete);
        });
    }

    @Test
    void testDeleteDoctor() {
    }
}