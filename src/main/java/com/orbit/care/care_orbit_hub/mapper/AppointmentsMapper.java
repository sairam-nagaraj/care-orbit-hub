package com.orbit.care.care_orbit_hub.mapper;

import com.orbit.care.care_orbit_hub.dto.AppointmentDTO;
import com.orbit.care.care_orbit_hub.dto.DoctorDTO;
import com.orbit.care.care_orbit_hub.entity.DoctorEntity;
import com.orbit.care.care_orbit_hub.entity.MedicalAppointmentEntity;
import com.orbit.care.care_orbit_hub.entity.MedicalRecordEntity;
import com.orbit.care.care_orbit_hub.entity.PatientEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AppointmentsMapper {

    MedicalAppointmentEntity mapAppointmentDtoToMedicalAppointmentEntity(AppointmentDTO appointmentDTO);

    @Mapping(source = "doctorEntity.id", target = "doctorId")
    @Mapping(source = "patientEntity.id", target = "patientId")
    AppointmentDTO mapAppointmentEntityToAppointmentDTO(MedicalAppointmentEntity appointmentEntity);

    @AfterMapping
    default void afterMappingMedicalAppointmentEntity(@MappingTarget MedicalAppointmentEntity entity) {
        entity.setDoctorEntity(new DoctorEntity());
        entity.setPatientEntity(new PatientEntity());
        entity.setRecordEntity(new MedicalRecordEntity());
    }
}
