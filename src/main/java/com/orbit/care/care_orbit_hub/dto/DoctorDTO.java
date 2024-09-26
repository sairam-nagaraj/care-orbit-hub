package com.orbit.care.care_orbit_hub.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DoctorDTO {

    private UUID id;

    private String name;

    private String email;

    private Long cellphone;

    private String location;
}
