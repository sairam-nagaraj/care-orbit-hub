package com.orbit.care.care_orbit_hub.controller;

import com.orbit.care.care_orbit_hub.dto.AppointmentDTO;
import com.orbit.care.care_orbit_hub.dto.PatientDTO;
import com.orbit.care.care_orbit_hub.service.PatientService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PatientController {

    public static final String baseUriPath = "/v1/patients";
    public static final String uriPathWithId = baseUriPath +"/{patientId}";
    public static final String appointmentsUriPath = baseUriPath +"/{patientId}/appointments";
    public static final String defaultPageSize = "25";
    public static final String defaultPageNumber = "0";

    private final PagedResourcesAssembler<PatientDTO> pagedResourcesAssembler;

    private final PatientService patientService;

    // public PagedModel<EntityModel<PatientDTO>>
    @GetMapping(baseUriPath)
    public PagedModel<EntityModel<PatientDTO>> listAllPatients(@RequestParam(required = false, defaultValue = "") String name,
                                                              @RequestParam(required = false, defaultValue = "") String idNumber,
                                                              @RequestParam(required = false, defaultValue = "") String city,
                                                              @RequestParam(required = false, defaultValue = defaultPageNumber) @Validated @Min(message = "A page number should start with at least 0.", value = 0) Integer pageNumber,
                                                              @RequestParam(required = false, defaultValue = defaultPageSize) @Validated @Max(message = "A page can contain a maximum of 100 records.", value = 100) Integer pageSize){
        log.debug("Patient Controller called - list all patients");
        return pagedResourcesAssembler.toModel(patientService.listPatients(name, idNumber, city, pageNumber, pageSize));
    }

    @GetMapping(uriPathWithId)
    public PatientDTO getPatientById(@PathVariable(name= "patientId") UUID id){
        log.debug("Patient Controller called - list patient with id " +id);
        return patientService.getPatient(id);
    }

    @GetMapping(appointmentsUriPath)
    public List<AppointmentDTO> getPatientsAppointments(@PathVariable(name= "patientId") UUID id){
        log.debug("Patient Controller called - list appointments for patient with id " +id);
        return patientService.getPatientAppointments(id);
    }

    @PostMapping(baseUriPath)
    public ResponseEntity addPatient(@Validated @RequestBody PatientDTO patient){
        log.debug("Patient Controller called - add patient");
        UUID savedId = patientService.addPatient(patient);

        HttpHeaders headers = new HttpHeaders();
        headers.add("location", savedId.toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
    }

    @PutMapping(uriPathWithId)
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
    }
}
