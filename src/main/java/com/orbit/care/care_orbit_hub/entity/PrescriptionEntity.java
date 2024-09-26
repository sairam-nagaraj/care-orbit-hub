package com.orbit.care.care_orbit_hub.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "coh_prescription")
public class PrescriptionEntity {
    @Id
    //@GeneratedValue(generator = UuidGenerator.Style.RANDOM)
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, unique = true, updatable = false, columnDefinition = "varchar(36)")
    private UUID id;

    @NotNull
    @NotBlank
    private String medication;

    @NotNull
    @NotBlank
    private String dosage;

    @NotNull
    @NotBlank
    private String details;

    @ManyToOne
    private MedicalRecordEntity recordEntity;
}
