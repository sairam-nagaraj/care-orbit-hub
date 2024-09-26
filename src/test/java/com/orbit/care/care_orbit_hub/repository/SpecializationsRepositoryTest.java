package com.orbit.care.care_orbit_hub.repository;

import com.orbit.care.care_orbit_hub.entity.DoctorEntity;
import com.orbit.care.care_orbit_hub.entity.SpecializationEntitiy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("mysql")
class SpecializationsRepositoryTest {

    @Autowired
    SpecializationsRepository specializationsRepository;

    @Autowired
    DoctorRepository doctorRepository;

    DoctorEntity doctorEntity;

    @BeforeEach
    void setup(){
        doctorEntity = doctorRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void testSpecializationAdd(){
        SpecializationEntitiy specializationEntitiy = SpecializationEntitiy.builder()
                .description("Dentist")
                .build();
        specializationEntitiy.addDoctor(doctorEntity);
        SpecializationEntitiy savedEntity = specializationsRepository.save(specializationEntitiy);

        System.out.println(savedEntity);
    }

}