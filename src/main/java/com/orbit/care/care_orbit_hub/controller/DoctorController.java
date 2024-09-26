package com.orbit.care.care_orbit_hub.controller;

import com.orbit.care.care_orbit_hub.dto.DoctorDTO;
import com.orbit.care.care_orbit_hub.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DoctorController {

    public static final String baseUriPath = "/v1/doctors";
    public static final String uriPathWithId = baseUriPath +"/{doctorId}";

    private final DoctorService doctorService;

    private final PagedResourcesAssembler<DoctorDTO> doctorDTOPagedResourcesAssembler;

    @GetMapping(baseUriPath)
    public List<DoctorDTO> listAllDoctors(){
        log.debug("Doctor Controller called - list all Doctors");
        return doctorService.listDoctors();
    }

    @GetMapping(uriPathWithId)
    public DoctorDTO getDoctorById(@PathVariable(name= "doctorId") UUID id){
        log.debug("Doctor Controller called - list Doctor with id " +id);
        return doctorService.getDoctor(id);
    }

    @PostMapping(baseUriPath)
    public ResponseEntity addDoctor(@RequestBody DoctorDTO doctor){
        log.debug("Doctor Controller called - add Doctor");
        UUID savedId = doctorService.addDoctor(doctor);

        HttpHeaders headers = new HttpHeaders();
        headers.add("location", savedId.toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
    }

    @PutMapping(uriPathWithId)
    public ResponseEntity updateDoctor(@PathVariable(name= "doctorId") UUID id, @RequestBody DoctorDTO doctor){
        log.debug("Doctor Controller called - update Doctor");
        doctorService.updateDoctor(id, doctor);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(uriPathWithId)
    public ResponseEntity deleteDoctor(@PathVariable(name= "doctorId") UUID id){
        log.debug("Doctor Controller called - delete Doctor");
        doctorService.deleteDoctor(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(uriPathWithId)
    public ResponseEntity deleteDoctor(@PathVariable(name= "doctorId") UUID id, @RequestBody DoctorDTO doctor){
        log.debug("Doctor Controller called - patch Doctor");
        doctorService.patchDoctor(id, doctor);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
