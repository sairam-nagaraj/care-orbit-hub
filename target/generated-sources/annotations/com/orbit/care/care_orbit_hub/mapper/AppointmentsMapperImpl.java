package com.orbit.care.care_orbit_hub.mapper;

import com.orbit.care.care_orbit_hub.dto.AppointmentDTO;
import com.orbit.care.care_orbit_hub.entity.DoctorEntity;
import com.orbit.care.care_orbit_hub.entity.MedicalAppointmentEntity;
import com.orbit.care.care_orbit_hub.entity.PatientEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-26T11:39:32+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class AppointmentsMapperImpl implements AppointmentsMapper {

    @Override
    public MedicalAppointmentEntity mapAppointmentDtoToMedicalAppointmentEntity(AppointmentDTO appointmentDTO) {
        if ( appointmentDTO == null ) {
            return null;
        }

        MedicalAppointmentEntity.MedicalAppointmentEntityBuilder medicalAppointmentEntity = MedicalAppointmentEntity.builder();

        medicalAppointmentEntity.id( appointmentDTO.getId() );
        medicalAppointmentEntity.date( appointmentDTO.getDate() );
        medicalAppointmentEntity.time( appointmentDTO.getTime() );
        medicalAppointmentEntity.status( appointmentDTO.getStatus() );

        MedicalAppointmentEntity medicalAppointmentEntityResult = medicalAppointmentEntity.build();

        afterMappingMedicalAppointmentEntity( medicalAppointmentEntityResult );

        return medicalAppointmentEntityResult;
    }

    @Override
    public AppointmentDTO mapAppointmentEntityToAppointmentDTO(MedicalAppointmentEntity appointmentEntity) {
        if ( appointmentEntity == null ) {
            return null;
        }

        AppointmentDTO.AppointmentDTOBuilder appointmentDTO = AppointmentDTO.builder();

        appointmentDTO.doctorId( appointmentEntityDoctorEntityId( appointmentEntity ) );
        appointmentDTO.patientId( appointmentEntityPatientEntityId( appointmentEntity ) );
        appointmentDTO.id( appointmentEntity.getId() );
        appointmentDTO.date( appointmentEntity.getDate() );
        appointmentDTO.time( appointmentEntity.getTime() );
        appointmentDTO.status( appointmentEntity.getStatus() );

        return appointmentDTO.build();
    }

    private UUID appointmentEntityDoctorEntityId(MedicalAppointmentEntity medicalAppointmentEntity) {
        DoctorEntity doctorEntity = medicalAppointmentEntity.getDoctorEntity();
        if ( doctorEntity == null ) {
            return null;
        }
        return doctorEntity.getId();
    }

    private UUID appointmentEntityPatientEntityId(MedicalAppointmentEntity medicalAppointmentEntity) {
        PatientEntity patientEntity = medicalAppointmentEntity.getPatientEntity();
        if ( patientEntity == null ) {
            return null;
        }
        return patientEntity.getId();
    }
}
