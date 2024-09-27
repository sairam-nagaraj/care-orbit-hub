package com.orbit.care.care_orbit_hub.mapper;

import com.orbit.care.care_orbit_hub.controller.DoctorController;
import com.orbit.care.care_orbit_hub.dto.AdditionalProperties;
import com.orbit.care.care_orbit_hub.dto.DoctorDTO;
import com.orbit.care.care_orbit_hub.entity.DoctorEntity;
import com.orbit.care.care_orbit_hub.enums.LinksParam;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "version", constant = "1")
    DoctorEntity mapDoctorDtoToDoctorEntity(DoctorDTO doctorDTO);

    @Mapping(target = "links", ignore = true)
    DoctorDTO mapDoctorEntityToDoctorDTO(DoctorEntity doctorEntity);

    @AfterMapping
    default void doctorLinksMapping(@MappingTarget DoctorDTO doctorDTO){
        doctorDTO.setLinks(new HashSet<>());
        doctorDTO.getLinks().add(new AdditionalProperties(LinksParam.self, DoctorController.baseUriPath + "/" + doctorDTO.getId()));
        doctorDTO.getLinks().add(new AdditionalProperties(LinksParam.appointments, DoctorController.baseUriPath + "/" + doctorDTO.getId() + "/appointments"));
    }
}
