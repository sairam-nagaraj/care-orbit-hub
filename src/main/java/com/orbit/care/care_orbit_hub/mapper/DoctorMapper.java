package com.orbit.care.care_orbit_hub.mapper;

import com.orbit.care.care_orbit_hub.dto.DoctorDTO;
import com.orbit.care.care_orbit_hub.entity.DoctorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "version", constant = "1")
    DoctorEntity mapDoctorDtoToDoctorEntity(DoctorDTO doctorDTO);
    
    DoctorDTO mapDoctorEntityToDoctorDTO(DoctorEntity doctorEntity);
}
