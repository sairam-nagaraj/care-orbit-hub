package com.orbit.care.care_orbit_hub.controller;

import com.orbit.care.care_orbit_hub.dto.AppointmentDTO;
import com.orbit.care.care_orbit_hub.dto.PatientDTO;
import com.orbit.care.care_orbit_hub.service.AppointmentService;
import com.orbit.care.care_orbit_hub.service.PatientService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AppointmentController {

    public static final String baseUriPath = "/v1/appointments";
    public static final String uriPathWithId = baseUriPath +"/{appointmentId}";
    public static final String defaultPageSize = "25";
    public static final String defaultPageNumber = "0";

    //private final PagedResourcesAssembler<PatientDTO> pagedResourcesAssembler;

    private final AppointmentService appointmentService;

   /* @GetMapping(uriPathWithId)
    public PatientDTO getPatientById(@PathVariable(name= "patientId") UUID id){
        log.debug("Patient Controller called - list patient with id " +id);
        return patientService.getPatient(id);
    }*/

    @PostMapping(baseUriPath)
    public ResponseEntity createAppointment(@Validated @RequestBody AppointmentDTO appointmentDTO){
        log.debug("Appointment Controller called - create appointment");
        UUID savedId = appointmentService.createAppointment(appointmentDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("location", savedId.toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
    }

    /*@PutMapping(uriPathWithId)
    public ResponseEntity updatePatient(@PathVariable(name= "patientId") UUID id, @Validated @RequestBody PatientDTO patient){
        log.debug("Patient Controller called - update patient");
        patientService.updatePatient(id, patient);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(uriPathWithId)
    public ResponseEntity deletePatient(@PathVariable(name= "patientId") UUID id){
        log.debug("Patient Controller called - delete patient");
        patientService.deletePatient(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(uriPathWithId)
    public ResponseEntity patchPatient(@PathVariable(name= "patientId") UUID id, @Validated @RequestBody PatientDTO patient){
        log.debug("Patient Controller called - patch patient");
        patientService.patchPatient(id, patient);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/
}
