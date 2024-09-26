package com.orbit.care.care_orbit_hub.mapper;

import com.orbit.care.care_orbit_hub.dto.PatientDTO;
import com.orbit.care.care_orbit_hub.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {AppointmentsMapper.class})
public interface PatientsMapper {

    @Mapping(source = "address.line1", target = "addressLine1")
    @Mapping(source = "address.line2", target = "addressLine2")
    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.state", target = "state")
    @Mapping(source = "address.code", target = "postalCode")
    @Mapping(target = "version", constant = "1")
    PatientEntity mapPatientDtoToPatientEntity(PatientDTO patientDTO);

    @Mapping(source = "addressLine1", target = "address.line1")
    @Mapping(source = "addressLine2", target = "address.line2")
    @Mapping(source = "city", target = "address.city")
    @Mapping(source = "state", target = "address.state")
    @Mapping(source = "postalCode", target = "address.code")
    @Mapping(source = "appointmentSet", target = "appointmentSet")
    PatientDTO mapPatientEntityToPatientDTO(PatientEntity patientEntity);
}
