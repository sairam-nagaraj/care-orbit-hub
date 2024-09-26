package com.orbit.care.care_orbit_hub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "coh_specialization")
public class SpecializationEntitiy {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, unique = true, updatable = false, columnDefinition = "varchar(36)")
    private UUID id;

    private String description;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "coh_doctor_specialization",
            joinColumns = @JoinColumn(name = "specialization_id"),
            inverseJoinColumns = @JoinColumn(name= "doctor_id"))
    private Set<DoctorEntity> doctors = new HashSet<>();

    public void addDoctor(DoctorEntity doctor){
        this.doctors.add(doctor);
        doctor.getSpecializations().add(this);
    }

    public void removeCategory(DoctorEntity doctor){
        this.doctors.remove(doctor);
        doctor.getSpecializations().remove(this);
    }
}
