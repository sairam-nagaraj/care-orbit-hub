package com.orbit.care.care_orbit_hub.service;

import com.orbit.care.care_orbit_hub.dto.DoctorDTO;
import com.orbit.care.care_orbit_hub.entity.DoctorEntity;
import com.orbit.care.care_orbit_hub.exception.NotFoundException;
import com.orbit.care.care_orbit_hub.mapper.DoctorMapper;
import com.orbit.care.care_orbit_hub.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class DoctorServiceJpaImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final DoctorMapper doctorsMapper;


    @Override
    public DoctorDTO getDoctor(UUID id) {
        log.info("---"+ doctorRepository.findById(id).toString());
        DoctorEntity pe = doctorRepository.findById(id).orElseThrow(NotFoundException::new);
        return doctorsMapper.mapDoctorEntityToDoctorDTO(pe);
    }

    @Override
    public List<DoctorDTO> listDoctors() {
        return doctorRepository.findAll().stream().map(doctorsMapper::mapDoctorEntityToDoctorDTO).collect(Collectors.toList());
    }

    @Override
    public UUID addDoctor(DoctorDTO doctor) {
        return doctorRepository.save(doctorsMapper.mapDoctorDtoToDoctorEntity(doctor)).getId();
    }

    @Override
    public void updateDoctor(UUID id, DoctorDTO doctorDTO) {
        doctorRepository.findById(id).ifPresentOrElse(existingDoctor -> {
            existingDoctor.setName(doctorDTO.getName());
            existingDoctor.setCellphone(doctorDTO.getCellphone());
            doctorRepository.save(existingDoctor);
        }, () -> {
            throw new NotFoundException();
        });

    }

    @Override
    public void deleteDoctor(UUID id) {
        doctorRepository.findById(id).ifPresentOrElse(foundDoctor -> {
                    doctorRepository.deleteById(id);
                }, () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void patchDoctor(UUID id, DoctorDTO doctorDTO) {

    }
}
