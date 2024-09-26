package com.orbit.care.care_orbit_hub.controller;


import com.orbit.care.care_orbit_hub.dto.AddressDTO;
import com.orbit.care.care_orbit_hub.enums.IdentityType;
import com.orbit.care.care_orbit_hub.dto.PatientDTO;
import com.orbit.care.care_orbit_hub.entity.PatientEntity;
import com.orbit.care.care_orbit_hub.exception.NotFoundException;
import com.orbit.care.care_orbit_hub.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@ActiveProfiles({"test"})
class PatientControllerIntegrationTest {

    @Autowired
    PatientController patientController;

    @Autowired
    PatientRepository patientRepository;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void beforeEachTest(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Transactional
    @Test
    @DisplayName("List all Patients")
    void listAllPatients() {
        PagedModel<EntityModel<PatientDTO>> patientsList = patientController.listAllPatients(null, null, null, 0, 25);
        assertThat(patientsList.getContent().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("List all Patients By Name")
    void listAllPatientsByName() throws Exception {
        mockMvc.perform(get(PatientController.baseUriPath)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("name", "testPatient1"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$._embedded.patientDTOList.length()", equalTo(1)));

    }

    @Test
    @DisplayName("List all Patients By Multiple query params")
    void listAllPatientsByMultipleQueryParams() throws Exception {
        mockMvc.perform(get(PatientController.baseUriPath)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("name", "testPatient")
                        .queryParam("idNumber", "Test")
                        .queryParam("city", "city"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$._embedded.patientDTOList.length()", equalTo(2)));
    }

    @Test
    @DisplayName("List all Patients with Pagination")
    void listAllPatientsWithPagination() throws Exception {
        mockMvc.perform(get(PatientController.baseUriPath)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("pageNumber", "0")
                        .queryParam("pageSize", "50"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$._embedded.patientDTOList.length()", lessThanOrEqualTo(50)));
    }

    @Test
    @Transactional
    @Rollback
    void listAllPatientsEmptyList() {
        patientRepository.deleteAll();
        PagedModel<EntityModel<PatientDTO>> patientsList = patientController.listAllPatients(null, null, null, 0, 25);
        assertThat(patientsList.getContent().size()).isEqualTo(0);
    }

    @Transactional
    @Test
    void getPatientById() {
        PatientEntity patientEntity = patientRepository.findAll().get(0);
        PatientDTO patientDTO = patientController.getPatientById(patientEntity.getId());
        assertThat(patientDTO).isNotNull();
    }

    @Test
    void getPatientByIdExceptionCase() {
        assertThrows(NotFoundException.class, ()->{
            PatientDTO patientDTO = patientController.getPatientById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void addPatient() {
        PatientDTO newPatient = PatientDTO.builder()
                .name("testPatient4")
                .idType(IdentityType.Passport)
                .idNumber("Test4")
                .age(20)
                .cellphone(7885514234l)
                .address(AddressDTO.builder()
                        .state("Johannesburg")
                        .line1("4, test street")
                        .city("Laudium")
                        .code(2110).build())
                .build();

        ResponseEntity savedPatient = patientController.addPatient(newPatient);

        assertThat(savedPatient.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(savedPatient.getHeaders().getLocation()).isNotNull();
        log.info("saved location "+ savedPatient.getHeaders().getLocation());

    }

    @Transactional
    @Test
    void updatePatient() {
        PatientDTO patientDTO = patientController.listAllPatients(null, null, null, 0, 25).getContent().stream().collect(Collectors.toList()).get(0).getContent();
        UUID refId = patientDTO.getId();
        patientDTO.setId(null);
        patientDTO.setName("Updated");
        ResponseEntity responseEntity = patientController.updatePatient(refId, patientDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @Test
    void updatePatientForUnknownId() {
        assertThrows(NotFoundException.class, () -> {
            PatientDTO patientDTO = patientController.listAllPatients(null, null, null, 0, 25).getContent().stream().collect(Collectors.toList()).get(0).getContent();
            patientDTO.setId(null);
            patientDTO.setName("Updated");
            ResponseEntity responseEntity = patientController.updatePatient(UUID.randomUUID(), patientDTO);
        });
    }

    @Test
    @Transactional
    @Rollback
    void deletePatient() {
        UUID recordIdToDelete = patientRepository.findAll().get(0).getId();
        ResponseEntity responseEntity = patientController.deletePatient(recordIdToDelete);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Transactional
    @Rollback
    void deletePatientWithRandomId() {
        assertThrows(NotFoundException.class, () -> {
            UUID recordIdToDelete = UUID.randomUUID();
            ResponseEntity responseEntity = patientController.deletePatient(recordIdToDelete);
        });
    }

    @Test
    void testDeletePatient() {
    }
}