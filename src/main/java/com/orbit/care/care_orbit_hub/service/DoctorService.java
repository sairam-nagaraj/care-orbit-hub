package com.orbit.care.care_orbit_hub.service;

import com.orbit.care.care_orbit_hub.dto.DoctorDTO;

import java.util.List;
import java.util.UUID;

public interface DoctorService {

    public DoctorDTO getDoctor(UUID id);

    public List<DoctorDTO> listDoctors();

    public UUID addDoctor(DoctorDTO doctor);

    void updateDoctor(UUID id, DoctorDTO doctor);

    void deleteDoctor(UUID id);

    void patchDoctor(UUID id, DoctorDTO doctor);
    
}
