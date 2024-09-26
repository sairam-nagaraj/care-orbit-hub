package com.orbit.care.care_orbit_hub.service;

import com.orbit.care.care_orbit_hub.dto.AppointmentDTO;
import com.orbit.care.care_orbit_hub.entity.MedicalAppointmentEntity;
import com.orbit.care.care_orbit_hub.mapper.AppointmentsMapper;
import com.orbit.care.care_orbit_hub.repository.DoctorRepository;
import com.orbit.care.care_orbit_hub.repository.MedicalAppointmentRepository;
import com.orbit.care.care_orbit_hub.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public class AppointmentServiceImpl implements AppointmentService {

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    private final MedicalAppointmentRepository medicalAppointmentRepository;

    private final AppointmentsMapper appointmentsMapper;


    @Override
    public UUID createAppointment(AppointmentDTO appointmentDTO) {
        log.debug("What the heck");
        MedicalAppointmentEntity medicalAppointmentEntity = appointmentsMapper.mapAppointmentDtoToMedicalAppointmentEntity(appointmentDTO);
        System.out.println(patientRepository.findById(appointmentDTO.getPatientId()).get());
        medicalAppointmentEntity.setPatientEntity(patientRepository.findById(appointmentDTO.getPatientId()).get());
        System.out.println(doctorRepository.findById(appointmentDTO.getDoctorId()).get());
        medicalAppointmentEntity.setDoctorEntity(doctorRepository.findById(appointmentDTO.getDoctorId()).get());
        medicalAppointmentEntity.setRecordEntity(null);
        return medicalAppointmentRepository.save(medicalAppointmentEntity).getId();
    }
}
