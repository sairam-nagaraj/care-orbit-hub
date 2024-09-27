package com.orbit.care.care_orbit_hub.service;

import com.orbit.care.care_orbit_hub.dto.AppointmentDTO;
import com.orbit.care.care_orbit_hub.dto.PatientDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    public PatientDTO getPatient(UUID id);

    public Page<PatientDTO> listPatients(String name, String idNumber, String city, Integer pageNumber, Integer pageSize);

    public UUID addPatient(PatientDTO patient);

    void updatePatient(UUID id, PatientDTO patient);

    void deletePatient(UUID id);

    void patchPatient(UUID id, PatientDTO patient);

    List<AppointmentDTO> getPatientAppointments(UUID id);
}
