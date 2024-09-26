package com.orbit.care.care_orbit_hub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbit.care.care_orbit_hub.dto.DoctorDTO;
import com.orbit.care.care_orbit_hub.exception.NotFoundException;
import com.orbit.care.care_orbit_hub.service.DoctorService;
import com.orbit.care.care_orbit_hub.service.DoctorServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @MockBean
    private DoctorService doctorService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<UUID> uuidCaptor;

    @Captor
    ArgumentCaptor<DoctorDTO> doctorCaptor;

    private DoctorServiceImpl doctorServiceImpl = new DoctorServiceImpl();

    @Test
    void getDoctorById() throws Exception {

        DoctorDTO testDoctor = doctorServiceImpl.listDoctors().get(0);

        given(doctorService.getDoctor(testDoctor.getId())).willReturn(testDoctor);

        mockMvc.perform(get(DoctorController.uriPathWithId, testDoctor.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testDoctor.getId().toString())));
    }

    @Test
    void getDoctorByIdNotFoundExceptionCase() throws Exception {

        given(doctorService.getDoctor(any())).willThrow(NotFoundException.class);

        mockMvc.perform(get(DoctorController.uriPathWithId, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void getAllDoctors() throws Exception {

        given(doctorService.listDoctors()).willReturn(doctorServiceImpl.listDoctors());

        mockMvc.perform(get(DoctorController.baseUriPath)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", greaterThan(1)));
    }

    @Test
    void addNewDoctor() throws Exception {

        DoctorDTO newDoctor =  DoctorDTO.builder()
                .id(UUID.randomUUID())
                .name("Buffay")
                .email("Buffay@test.com")
                .cellphone(8252696344l)
                .location("Dainfern")
                .build();

        UUID returnId = UUID.randomUUID();

        given(doctorService.addDoctor(any(DoctorDTO.class))).willReturn(returnId);

        mockMvc.perform(post(DoctorController.baseUriPath)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDoctor))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("location", returnId.toString()));
    }

    @Test
    void updateDoctor() throws Exception {

        DoctorDTO Doctor = doctorServiceImpl.listDoctors().get(0);
        Doctor.setName(Doctor.getName() + "- Updated");

        mockMvc.perform(put(DoctorController.uriPathWithId, Doctor.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Doctor))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(doctorService).updateDoctor(uuidCaptor.capture(), doctorCaptor.capture());

        assertThat(uuidCaptor.getValue()).isEqualTo(Doctor.getId());
        assertThat(doctorCaptor.getValue().getName()).isEqualTo(Doctor.getName());
    }

    @Test
    void deleteDoctor() throws Exception {

        DoctorDTO Doctor = doctorServiceImpl.listDoctors().get(0);

        mockMvc.perform(delete(DoctorController.uriPathWithId, Doctor.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Doctor))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(doctorService).deleteDoctor(uuidCaptor.capture());

        assertThat(uuidCaptor.getValue()).isEqualTo(Doctor.getId());
    }

    @Test
    void patchDoctor() throws Exception {

        DoctorDTO Doctor = doctorServiceImpl.listDoctors().get(0);

        Map<String, String> request = new HashMap<>();
        request.put("name", Doctor.getName() + "- Patched");

        mockMvc.perform(patch(DoctorController.uriPathWithId, Doctor.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(doctorService).patchDoctor(uuidCaptor.capture(), doctorCaptor.capture());

        assertThat(uuidCaptor.getValue()).isEqualTo(Doctor.getId());
        assertThat(doctorCaptor.getValue().getName()).isEqualTo(request.get("name"));
    }
}