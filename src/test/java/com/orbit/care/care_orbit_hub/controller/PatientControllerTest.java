package com.orbit.care.care_orbit_hub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbit.care.care_orbit_hub.dto.AddressDTO;
import com.orbit.care.care_orbit_hub.enums.IdentityType;
import com.orbit.care.care_orbit_hub.dto.PatientDTO;
import com.orbit.care.care_orbit_hub.exception.NotFoundException;
import com.orbit.care.care_orbit_hub.service.PatientService;
import com.orbit.care.care_orbit_hub.service.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

/* Unit Tests - Web MVC to check only the class at test, this is not going to give complete coverage. */
@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @MockBean
    private PatientService patientService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<UUID> uuidCaptor;

    @Captor
    ArgumentCaptor<PatientDTO> patientCaptor;

    private PatientServiceImpl patientServiceImpl = new PatientServiceImpl();

    @Test
    void getPatientById() throws Exception {

        PatientDTO testPatient = patientServiceImpl.listPatients(null, null, null, null, null).getContent().get(0);

        given(patientService.getPatient(testPatient.getId())).willReturn(testPatient);

        mockMvc.perform(get(PatientController.uriPathWithId, testPatient.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testPatient.getId().toString())));
    }

    @Test
    void getPatientByIdNotFoundExceptionCase() throws Exception {

        given(patientService.getPatient(any())).willThrow(NotFoundException.class);

        mockMvc.perform(get(PatientController.uriPathWithId, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void getAllPatients() throws Exception {

        given(patientService.listPatients(any(), any(), any(), any(), any())).willReturn(patientServiceImpl.listPatients(null, null, null, 0, 25));

        mockMvc.perform(get(PatientController.baseUriPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._embedded.patientDTOList.length()", greaterThan(1)));
    }

    @Test
    void addNewPatient() throws Exception {

        PatientDTO newPatient = PatientDTO.builder()
                .idType(IdentityType.Passport)
                .idNumber("BEN1234")
                .age(37)
                .name("Ben")
                .medicalHistory("None")
                .build();

        UUID returnId = UUID.randomUUID();

        given(patientService.addPatient(any(PatientDTO.class))).willReturn(returnId);

        mockMvc.perform(post(PatientController.baseUriPath)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPatient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("location", returnId.toString()));
    }

    @Test
    void addNewPatientWithNullName() throws Exception {

        PatientDTO newPatient = PatientDTO.builder()
                .idType(IdentityType.Passport)
                .idNumber("BEN1234")
                .age(37)
                .name(null)
                .medicalHistory("None")
                .address(AddressDTO.builder().build())
                .build();

        /*UUID returnId = UUID.randomUUID();

        given(patientService.addPatient(any(PatientDTO.class))).willReturn(returnId);*/

        MvcResult result = mockMvc.perform(post(PatientController.baseUriPath)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPatient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void updatePatient() throws Exception {

        PatientDTO patient = patientServiceImpl.listPatients(null, null, null, null, null).getContent().get(0);
        patient.setName(patient.getName() + "- Updated");

        mockMvc.perform(put(PatientController.uriPathWithId, patient.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(patientService).updatePatient(uuidCaptor.capture(), patientCaptor.capture());

        assertThat(uuidCaptor.getValue()).isEqualTo(patient.getId());
        assertThat(patientCaptor.getValue().getName()).isEqualTo(patient.getName());
    }

    @Test
    void deletePatient() throws Exception {

        PatientDTO patient = patientServiceImpl.listPatients(null, null, null, null, null).getContent().get(0);

        mockMvc.perform(delete(PatientController.uriPathWithId, patient.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(patientService).deletePatient(uuidCaptor.capture());

        assertThat(uuidCaptor.getValue()).isEqualTo(patient.getId());
    }

    @Test
    void patchPatient() throws Exception {

        PatientDTO patient = patientServiceImpl.listPatients(null, null, null, null, null).getContent().get(0);

        Map<String, String> request = new HashMap<>();
        request.put("name", patient.getName() + "- Patched");

        mockMvc.perform(patch(PatientController.uriPathWithId, patient.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(patientService).patchPatient(uuidCaptor.capture(), patientCaptor.capture());

        assertThat(uuidCaptor.getValue()).isEqualTo(patient.getId());
        assertThat(patientCaptor.getValue().getName()).isEqualTo(request.get("name"));
    }
}