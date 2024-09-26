package com.orbit.care.care_orbit_hub.entity;

import com.orbit.care.care_orbit_hub.enums.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity(name="coh_appointments")
public class MedicalAppointmentEntity {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, unique = true, updatable = false, columnDefinition = "varchar(36)")
    private UUID id;

    @NotNull
    @NotBlank
    private String date;

    @NotNull
    @NotBlank
    private String time;

    private AppointmentStatus status;

    @ManyToOne
    private DoctorEntity doctorEntity;

    @ManyToOne
    private PatientEntity patientEntity;

    @OneToOne
    private MedicalRecordEntity recordEntity;

    public void setDoctorEntity(DoctorEntity doctorEntity){
        this.doctorEntity = (doctorEntity == null)?(doctorEntity = new DoctorEntity()):doctorEntity;
        doctorEntity.getAppointmentSet().add(this);
    }

    public void setPatientEntity(PatientEntity patientEntity){
        this.patientEntity = (patientEntity == null)?(patientEntity = new PatientEntity()):patientEntity;
        patientEntity.getAppointmentSet().add(this);
    }

    public MedicalAppointmentEntity(UUID id, String date, String time, AppointmentStatus status, DoctorEntity doctorEntity, PatientEntity patientEntity, MedicalRecordEntity recordEntity) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.status = status;
        this.setDoctorEntity(doctorEntity);
        this.setPatientEntity(patientEntity);
        this.recordEntity = recordEntity;
    }
}
