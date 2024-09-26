package com.orbit.care.care_orbit_hub.mapper;

import com.orbit.care.care_orbit_hub.dto.DoctorDTO;
import com.orbit.care.care_orbit_hub.entity.DoctorEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-26T11:39:32+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class DoctorMapperImpl implements DoctorMapper {

    @Override
    public DoctorEntity mapDoctorDtoToDoctorEntity(DoctorDTO doctorDTO) {
        if ( doctorDTO == null ) {
            return null;
        }

        DoctorEntity.DoctorEntityBuilder doctorEntity = DoctorEntity.builder();

        doctorEntity.id( doctorDTO.getId() );
        doctorEntity.name( doctorDTO.getName() );
        doctorEntity.email( doctorDTO.getEmail() );
        doctorEntity.cellphone( doctorDTO.getCellphone() );
        doctorEntity.location( doctorDTO.getLocation() );

        doctorEntity.version( 1 );

        return doctorEntity.build();
    }

    @Override
    public DoctorDTO mapDoctorEntityToDoctorDTO(DoctorEntity doctorEntity) {
        if ( doctorEntity == null ) {
            return null;
        }

        DoctorDTO.DoctorDTOBuilder doctorDTO = DoctorDTO.builder();

        doctorDTO.id( doctorEntity.getId() );
        doctorDTO.name( doctorEntity.getName() );
        doctorDTO.email( doctorEntity.getEmail() );
        doctorDTO.cellphone( doctorEntity.getCellphone() );
        doctorDTO.location( doctorEntity.getLocation() );

        return doctorDTO.build();
    }
}
