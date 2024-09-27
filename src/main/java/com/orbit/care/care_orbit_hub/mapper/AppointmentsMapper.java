package com.orbit.care.care_orbit_hub.mapper;

import com.orbit.care.care_orbit_hub.controller.AppointmentController;
import com.orbit.care.care_orbit_hub.controller.DoctorController;
import com.orbit.care.care_orbit_hub.controller.PatientController;
import com.orbit.care.care_orbit_hub.dto.AdditionalProperties;
import com.orbit.care.care_orbit_hub.dto.AppointmentDTO;
import com.orbit.care.care_orbit_hub.dto.DoctorDTO;
import com.orbit.care.care_orbit_hub.entity.DoctorEntity;
import com.orbit.care.care_orbit_hub.entity.MedicalAppointmentEntity;
import com.orbit.care.care_orbit_hub.entity.MedicalRecordEntity;
import com.orbit.care.care_orbit_hub.entity.PatientEntity;
import com.orbit.care.care_orbit_hub.enums.LinksParam;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.HashSet;

@Mapper(componentModel = "spring")
public interface AppointmentsMapper {

    MedicalAppointmentEntity mapAppointmentDtoToMedicalAppointmentEntity(AppointmentDTO appointmentDTO);

    @Mapping(source = "doctorEntity.id", target = "doctorId")
    @Mapping(source = "patientEntity.id", target = "patientId")
    @Mapping(target = "links", ignore = true)
    AppointmentDTO mapAppointmentEntityToAppointmentDTO(MedicalAppointmentEntity appointmentEntity);

    @AfterMapping
    default void afterMappingMedicalAppointmentEntity(@MappingTarget MedicalAppointmentEntity entity) {
        entity.setDoctorEntity(new DoctorEntity());
        entity.setPatientEntity(new PatientEntity());
        entity.setRecordEntity(new MedicalRecordEntity());
    }

    @AfterMapping
    default void setLinksOnAppointmentEntitiy(@MappingTarget AppointmentDTO appointmentDTO) {
        appointmentDTO.setLinks(new HashSet<>());
        appointmentDTO.getLinks().add(new AdditionalProperties(LinksParam.self, AppointmentController.baseUriPath + "/" + appointmentDTO.getId()));
        appointmentDTO.getLinks().add(new AdditionalProperties(LinksParam.patient, PatientController.baseUriPath + "/" + appointmentDTO.getPatientId()));
        appointmentDTO.getLinks().add(new AdditionalProperties(LinksParam.doctor, DoctorController.baseUriPath + "/" + appointmentDTO.getDoctorId()));
    }
}
