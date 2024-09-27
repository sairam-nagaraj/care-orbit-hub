package com.orbit.care.care_orbit_hub.dto;

import com.orbit.care.care_orbit_hub.enums.AppointmentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class AppointmentDTO {

    private UUID id;

    @NotNull
    @NotBlank
    private String date;

    @NotNull
    @NotBlank
    private String time;

    private AppointmentStatus status;

    private UUID doctorId;

    private UUID patientId;

    private Set<AdditionalProperties> links = new HashSet<>();
}
