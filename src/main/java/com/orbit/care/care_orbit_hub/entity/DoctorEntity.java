package com.orbit.care.care_orbit_hub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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
@Entity(name = "coh_doctors")
public class DoctorEntity {

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

    private String email;
    @NotNull
    private Long cellphone;
    @NotNull
    @NotBlank
    private String location;

    @Builder.Default
    @OneToMany(mappedBy = "doctorEntity")
    private Set<MedicalAppointmentEntity> appointmentSet = new HashSet<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "coh_doctor_specialization",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name= "specialization_id"))
    private Set<SpecializationEntitiy> specializations = new HashSet<>();

}
