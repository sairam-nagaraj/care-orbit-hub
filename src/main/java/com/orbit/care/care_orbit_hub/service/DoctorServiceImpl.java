package com.orbit.care.care_orbit_hub.service;

import com.orbit.care.care_orbit_hub.dto.DoctorDTO;
import com.orbit.care.care_orbit_hub.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {

    private List<DoctorDTO> doctors;

    public DoctorServiceImpl() {
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

        doctors = new ArrayList<>();
        doctors.addAll(Arrays.asList(d1, d2, d3));
    }


    @Override
    public List<DoctorDTO> listDoctors() {

        log.debug("List Doctors Service Called");

        return doctors;
    }

    @Override
    public DoctorDTO getDoctor(UUID id) {

        log.debug("Doctor by Doctor id called - with reference " + id);

        try {
            return Optional.of(doctors.stream().filter(item -> item.getId().equals(id)).collect(Collectors.toList()).get(0)).orElseThrow(NotFoundException::new);
        }catch(IndexOutOfBoundsException ex){
            throw new NotFoundException();
        }
    }
    @Override
    public UUID addDoctor(DoctorDTO newDoctor) {
        newDoctor.setId(UUID.randomUUID());
        doctors.add(newDoctor);
        return newDoctor.getId();
    }

    @Override
    public void updateDoctor(UUID id, DoctorDTO doctor) {
        doctors = doctors.stream().map(doctorItem -> {
                        if (doctorItem.getId().equals(id)) {
                            doctorItem = doctor;
                            doctorItem.setId(id);
                        }
                        return doctorItem;
                    }).collect(Collectors.toList());
    }

    @Override
    public void deleteDoctor(UUID id) {
        doctors = doctors.stream().filter(DoctorItem -> !DoctorItem.getId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public void patchDoctor(UUID id, DoctorDTO doctor) {
        doctors = doctors.stream().map(doctorItem -> {
            if (doctorItem.getId().equals(id)) {
                if(doctor.getCellphone() != null)  doctorItem.setCellphone(doctor.getCellphone());
                if(StringUtils.hasText(doctor.getName())) doctorItem.setName(doctor.getName());
            }
            return doctorItem;
        }).collect(Collectors.toList());
    }
}
