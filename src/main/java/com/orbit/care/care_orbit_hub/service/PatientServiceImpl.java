package com.orbit.care.care_orbit_hub.service;

import com.orbit.care.care_orbit_hub.enums.IdentityType;
import com.orbit.care.care_orbit_hub.dto.PatientDTO;
import com.orbit.care.care_orbit_hub.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {

    private List<PatientDTO> patients;

    public PatientServiceImpl() {
        PatientDTO p1 = PatientDTO.builder()
                .id(UUID.randomUUID())
                .idType(IdentityType.Passport)
                .idNumber("JOH1234")
                .age(25)
                .name("John")
                .medicalHistory("None")
                .build();
        PatientDTO p2 = PatientDTO.builder()
                .id(UUID.randomUUID())
                .idType(IdentityType.Passport)
                .idNumber("RAN1234")
                .age(45)
                .name("Randall")
                .medicalHistory("None")
                .build();
        PatientDTO p3 = PatientDTO.builder()
                .id(UUID.randomUUID())
                .idType(IdentityType.Passport)
                .idNumber("PAU1234")
                .age(35)
                .name("Paul")
                .medicalHistory("None")
                .build();

        patients = new ArrayList<>();
        patients.addAll(Arrays.asList(p1, p2, p3));
    }


    @Override
    public Page<PatientDTO> listPatients(String name, String idNumber, String city, Integer pageNumber, Integer pageSize) {

        log.debug("List Patients Service Called");

        return new PageImpl<>(patients);
    }

    @Override
    public PatientDTO getPatient(UUID id) {

        log.debug("Patient by Patient id called - with reference " + id);

        try {
            return Optional.of(patients.stream().filter(item -> item.getId().equals(id)).collect(Collectors.toList()).get(0)).orElseThrow(NotFoundException::new);
        }catch(IndexOutOfBoundsException ex){
            throw new NotFoundException();
        }
    }
    @Override
    public UUID addPatient(PatientDTO newPatient) {
        newPatient.setId(UUID.randomUUID());
        patients.add(newPatient);
        return newPatient.getId();
    }

    @Override
    public void updatePatient(UUID id, PatientDTO patient) {
        patients = patients.stream().map(patientItem -> {
                        if (patientItem.getId().equals(id)) {
                            patientItem = patient;
                            patientItem.setId(id);
                        }
                        return patientItem;
                    }).collect(Collectors.toList());
    }

    @Override
    public void deletePatient(UUID id) {
        patients = patients.stream().filter(patientItem -> !patientItem.getId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public void patchPatient(UUID id, PatientDTO patient) {
        patients = patients.stream().map(patientItem -> {
            if (patientItem.getId().equals(id)) {
                if(patient.getCellphone() != null)  patientItem.setCellphone(patient.getCellphone());
                if(StringUtils.hasText(patient.getName())) patientItem.setName(patient.getName());
            }
            return patientItem;
        }).collect(Collectors.toList());
    }
}
