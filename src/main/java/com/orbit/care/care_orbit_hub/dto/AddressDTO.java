package com.orbit.care.care_orbit_hub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {
    private String line1;
    private String line2;
    @NotNull(message = "City in address is mandatory")
    @NotBlank(message = "City in address is mandatory")
    private String city;
    private String state;
    private Integer code;
}
