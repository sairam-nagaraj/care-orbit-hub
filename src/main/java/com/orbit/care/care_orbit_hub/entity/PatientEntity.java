package com.orbit.care.care_orbit_hub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "coh_patients")
public class PatientEntity {

    @Id
    //@GeneratedValue(generator = UuidGenerator.Style.RANDOM)
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, unique = true, updatable = false, columnDefinition = "varchar(36)")
    private UUID id;

    @Version
    private Integer version = 1;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String idType;

    @NotNull
    @NotBlank
    private String idNumber;

    private Integer age;

    private Long cellphone;

    private String medicalHistory;

    private String addressLine1;

    private String addressLine2;

    @NotNull
    @NotBlank
    private String city;

    private String state;

    private Integer postalCode;

    @OneToMany(mappedBy = "patientEntity")
    Set<MedicalAppointmentEntity> appointmentSet = new HashSet<>();

}
