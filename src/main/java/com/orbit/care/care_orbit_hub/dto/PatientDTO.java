package com.orbit.care.care_orbit_hub.dto;

import com.orbit.care.care_orbit_hub.entity.MedicalAppointmentEntity;
import com.orbit.care.care_orbit_hub.enums.IdentityType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class PatientDTO {

    private UUID id;

    @NotNull(message = "Person name is mandatory")
    @NotBlank(message = "Person name is mandatory")
    private String name;

    private IdentityType idType;

    private String idNumber;

    private Integer age;

    private Long cellphone;

    private String medicalHistory;

    @Valid
    private AddressDTO address;

    /*private Set<AppointmentDTO> appointmentSet;*/

    private Set<AdditionalProperties> links = new HashSet<>();
}
