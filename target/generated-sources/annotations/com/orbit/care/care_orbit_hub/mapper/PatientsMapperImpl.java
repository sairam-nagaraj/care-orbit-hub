package com.orbit.care.care_orbit_hub.mapper;

import com.orbit.care.care_orbit_hub.dto.AddressDTO;
import com.orbit.care.care_orbit_hub.dto.AppointmentDTO;
import com.orbit.care.care_orbit_hub.dto.PatientDTO;
import com.orbit.care.care_orbit_hub.entity.MedicalAppointmentEntity;
import com.orbit.care.care_orbit_hub.entity.PatientEntity;
import com.orbit.care.care_orbit_hub.enums.IdentityType;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-26T11:39:31+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class PatientsMapperImpl implements PatientsMapper {

    @Autowired
    private AppointmentsMapper appointmentsMapper;

    @Override
    public PatientEntity mapPatientDtoToPatientEntity(PatientDTO patientDTO) {
        if ( patientDTO == null ) {
            return null;
        }

        PatientEntity.PatientEntityBuilder patientEntity = PatientEntity.builder();

        patientEntity.addressLine1( patientDTOAddressLine1( patientDTO ) );
        patientEntity.addressLine2( patientDTOAddressLine2( patientDTO ) );
        patientEntity.city( patientDTOAddressCity( patientDTO ) );
        patientEntity.state( patientDTOAddressState( patientDTO ) );
        patientEntity.postalCode( patientDTOAddressCode( patientDTO ) );
        patientEntity.id( patientDTO.getId() );
        patientEntity.name( patientDTO.getName() );
        if ( patientDTO.getIdType() != null ) {
            patientEntity.idType( patientDTO.getIdType().name() );
        }
        patientEntity.idNumber( patientDTO.getIdNumber() );
        patientEntity.age( patientDTO.getAge() );
        patientEntity.cellphone( patientDTO.getCellphone() );
        patientEntity.medicalHistory( patientDTO.getMedicalHistory() );
        patientEntity.appointmentSet( appointmentDTOSetToMedicalAppointmentEntitySet( patientDTO.getAppointmentSet() ) );

        patientEntity.version( 1 );

        return patientEntity.build();
    }

    @Override
    public PatientDTO mapPatientEntityToPatientDTO(PatientEntity patientEntity) {
        if ( patientEntity == null ) {
            return null;
        }

        PatientDTO.PatientDTOBuilder patientDTO = PatientDTO.builder();

        patientDTO.address( patientEntityToAddressDTO( patientEntity ) );
        patientDTO.appointmentSet( medicalAppointmentEntitySetToAppointmentDTOSet( patientEntity.getAppointmentSet() ) );
        patientDTO.id( patientEntity.getId() );
        patientDTO.name( patientEntity.getName() );
        if ( patientEntity.getIdType() != null ) {
            patientDTO.idType( Enum.valueOf( IdentityType.class, patientEntity.getIdType() ) );
        }
        patientDTO.idNumber( patientEntity.getIdNumber() );
        patientDTO.age( patientEntity.getAge() );
        patientDTO.cellphone( patientEntity.getCellphone() );
        patientDTO.medicalHistory( patientEntity.getMedicalHistory() );

        return patientDTO.build();
    }

    private String patientDTOAddressLine1(PatientDTO patientDTO) {
        AddressDTO address = patientDTO.getAddress();
        if ( address == null ) {
            return null;
        }
        return address.getLine1();
    }

    private String patientDTOAddressLine2(PatientDTO patientDTO) {
        AddressDTO address = patientDTO.getAddress();
        if ( address == null ) {
            return null;
        }
        return address.getLine2();
    }

    private String patientDTOAddressCity(PatientDTO patientDTO) {
        AddressDTO address = patientDTO.getAddress();
        if ( address == null ) {
            return null;
        }
        return address.getCity();
    }

    private String patientDTOAddressState(PatientDTO patientDTO) {
        AddressDTO address = patientDTO.getAddress();
        if ( address == null ) {
            return null;
        }
        return address.getState();
    }

    private Integer patientDTOAddressCode(PatientDTO patientDTO) {
        AddressDTO address = patientDTO.getAddress();
        if ( address == null ) {
            return null;
        }
        return address.getCode();
    }

    protected Set<MedicalAppointmentEntity> appointmentDTOSetToMedicalAppointmentEntitySet(Set<AppointmentDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<MedicalAppointmentEntity> set1 = new LinkedHashSet<MedicalAppointmentEntity>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AppointmentDTO appointmentDTO : set ) {
            set1.add( appointmentsMapper.mapAppointmentDtoToMedicalAppointmentEntity( appointmentDTO ) );
        }

        return set1;
    }

    protected AddressDTO patientEntityToAddressDTO(PatientEntity patientEntity) {
        if ( patientEntity == null ) {
            return null;
        }

        AddressDTO.AddressDTOBuilder addressDTO = AddressDTO.builder();

        addressDTO.line1( patientEntity.getAddressLine1() );
        addressDTO.line2( patientEntity.getAddressLine2() );
        addressDTO.city( patientEntity.getCity() );
        addressDTO.state( patientEntity.getState() );
        addressDTO.code( patientEntity.getPostalCode() );

        return addressDTO.build();
    }

    protected Set<AppointmentDTO> medicalAppointmentEntitySetToAppointmentDTOSet(Set<MedicalAppointmentEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<AppointmentDTO> set1 = new LinkedHashSet<AppointmentDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( MedicalAppointmentEntity medicalAppointmentEntity : set ) {
            set1.add( appointmentsMapper.mapAppointmentEntityToAppointmentDTO( medicalAppointmentEntity ) );
        }

        return set1;
    }
}
