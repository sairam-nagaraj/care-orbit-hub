package com.orbit.care.care_orbit_hub.service;

import com.orbit.care.care_orbit_hub.dto.PatientDTO;
import com.orbit.care.care_orbit_hub.entity.PatientEntity;
import com.orbit.care.care_orbit_hub.exception.NotFoundException;
import com.orbit.care.care_orbit_hub.mapper.PatientsMapper;
import com.orbit.care.care_orbit_hub.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class PatientServiceJpaImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final PatientsMapper patientsMapper;


    @Override
    public PatientDTO getPatient(UUID id) {
        log.info("---"+patientRepository.findById(id).toString());
        PatientEntity pe = patientRepository.findById(id).orElseThrow(NotFoundException::new);
        return patientsMapper.mapPatientEntityToPatientDTO(pe);
    }

    @Override
    public Page<PatientDTO> listPatients(String name, String idNumber, String city, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<PatientEntity> resultList;
        if(StringUtils.hasText(name) || StringUtils.hasText(idNumber) || StringUtils.hasText(city)){
            resultList = listPatientsByName(name, idNumber, city, pageRequest);
        }else{
            resultList = patientRepository.findAll(pageRequest);
        }
        return resultList.map(patientsMapper::mapPatientEntityToPatientDTO);
    }

    @Override
    public UUID addPatient(PatientDTO patient) {
        return patientRepository.save(patientsMapper.mapPatientDtoToPatientEntity(patient)).getId();
    }

    @Override
    public void updatePatient(UUID id, PatientDTO patient) {
        patientRepository.findById(id).ifPresentOrElse(existingPatient -> {
            existingPatient.setName(patient.getName());
            existingPatient.setCellphone(patient.getCellphone());
            existingPatient.setAge(patient.getAge());
            patientRepository.save(existingPatient);
        }, () -> {
            throw new NotFoundException();
        });

    }

    @Override
    public void deletePatient(UUID id) {
        patientRepository.findById(id).ifPresentOrElse(foundPatient -> {
                    patientRepository.deleteById(id);
                }, () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void patchPatient(UUID id, PatientDTO patient) {

    }

    private Page<PatientEntity> listPatientsByName(String name, String idNumber, String city, Pageable pageable) {
        log.debug("Inside listPatientsByName " +name);
        return patientRepository.findByNameLikeIgnoreCaseAndIdNumberLikeIgnoreCaseAndCityLikeIgnoreCase('%'+name+"%", '%'+idNumber+"%", '%'+city+"%", pageable);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize){
        Sort customSort = Sort.by(Sort.Order.desc("id"));
        return PageRequest.of(pageNumber, pageSize, customSort);
    }
}
