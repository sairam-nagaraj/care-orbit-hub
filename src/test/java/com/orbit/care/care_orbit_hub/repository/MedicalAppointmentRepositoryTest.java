package com.orbit.care.care_orbit_hub.repository;

import com.orbit.care.care_orbit_hub.bootstrap.BootstrapAppData;
import com.orbit.care.care_orbit_hub.entity.DoctorEntity;
import com.orbit.care.care_orbit_hub.entity.MedicalAppointmentEntity;
import com.orbit.care.care_orbit_hub.entity.PatientEntity;
import com.orbit.care.care_orbit_hub.enums.AppointmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({BootstrapAppData.class})
@ActiveProfiles({"test"})
class MedicalAppointmentRepositoryTest {

    @Autowired
    MedicalAppointmentRepository medicalAppointmentRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    DoctorEntity doctorEntity;

    PatientEntity patientEntity;

    @BeforeEach
    void getDoctorsData(){
        doctorEntity = doctorRepository.findAll().get(0);
        patientEntity = patientRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void insertMedicalAppointmentRecord(){
        MedicalAppointmentEntity medicalAppointmentEntity = MedicalAppointmentEntity.builder()
                .doctorEntity(doctorEntity)
                .patientEntity(patientEntity)
                .date("23/09/2024")
                .time("13:00 hrs")
                .status(AppointmentStatus.Scheduled)
                .build();

        MedicalAppointmentEntity savedEntity = medicalAppointmentRepository.saveAndFlush(medicalAppointmentEntity);

        assertThat(savedEntity.getId()).isNotNull();
    }

}