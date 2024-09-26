package com.orbit.care.care_orbit_hub.bootstrap;

import com.orbit.care.care_orbit_hub.dto.AddressDTO;
import com.orbit.care.care_orbit_hub.dto.DoctorDTO;
import com.orbit.care.care_orbit_hub.enums.IdentityType;
import com.orbit.care.care_orbit_hub.dto.PatientDTO;
import com.orbit.care.care_orbit_hub.mapper.DoctorMapper;
import com.orbit.care.care_orbit_hub.mapper.PatientsMapper;
import com.orbit.care.care_orbit_hub.repository.DoctorRepository;
import com.orbit.care.care_orbit_hub.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("test")
public class BootstrapAppData implements CommandLineRunner {

    private final PatientRepository patientRepository;

    private final DoctorRepository doctorRepository;

    private final PatientsMapper patientsMapper;
    private final DoctorMapper doctorsMapper;

    @Override
    public void run(String... args) throws Exception {
        log.debug("Is running in test??");
        loadPatientsData();
        loadDoctorsData();
    }

    private void loadPatientsData() {
        PatientDTO p1 = PatientDTO.builder()
                .name("testPatient1")
                .idType(IdentityType.Passport)
                .idNumber("Test1")
                .age(23)
                .cellphone(7885514231l)
                .address(AddressDTO.builder()
                        .state("Johannesburg")
                        .line1("1, test street")
                        .city("testCity")
                        .code(2001).build())
                .build();
        PatientDTO p2 = PatientDTO.builder()
                .name("testPatient2")
                .idType(IdentityType.Passport)
                .idNumber("Test2")
                .age(24)
                .cellphone(7885514232l)
                .address(AddressDTO.builder()
                        .state("Johannesburg")
                        .line1("2, test street")
                        .city("testCity")
                        .code(2190).build())
                .build();
        PatientDTO p3 = PatientDTO.builder()
                .name("testPatient3")
                .idType(IdentityType.Passport)
                .idNumber("Passport3")
                .age(33)
                .cellphone(7885514233l)
                .address(AddressDTO.builder()
                        .state("Johannesburg")
                        .line1("1, test street")
                        .city("testCity")
                        .code(2194).build())
                .build();
        patientRepository.save(patientsMapper.mapPatientDtoToPatientEntity(p1));
        patientRepository.save(patientsMapper.mapPatientDtoToPatientEntity(p2));
        patientRepository.save(patientsMapper.mapPatientDtoToPatientEntity(p3));
    }

    private void loadDoctorsData() {

        DoctorDTO d1 = DoctorDTO.builder()
                .id(UUID.randomUUID())
                .name("Sheobalak")
                .email("Sheobalak@test.com")
                .cellphone(5252696344l)
                .location("Johannesburg")
                .build();
        DoctorDTO d2 = DoctorDTO.builder()
                .id(UUID.randomUUID())
                .name("Francis")
                .email("Francis@test.com")
                .cellphone(6252696344l)
                .location("Pretoria")
                .build();
        DoctorDTO d3 = DoctorDTO.builder()
                .id(UUID.randomUUID())
                .name("Geller")
                .email("Geller@test.com")
                .cellphone(7252696344l)
                .location("Sandton")
                .build();

        doctorRepository.saveAndFlush(doctorsMapper.mapDoctorDtoToDoctorEntity(d1));
        doctorRepository.saveAndFlush(doctorsMapper.mapDoctorDtoToDoctorEntity(d2));
        doctorRepository.saveAndFlush(doctorsMapper.mapDoctorDtoToDoctorEntity(d3));
    }
}
