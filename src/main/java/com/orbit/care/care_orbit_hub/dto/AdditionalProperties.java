package com.orbit.care.care_orbit_hub.dto;

import com.orbit.care.care_orbit_hub.enums.LinksParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AdditionalProperties {

    private String param;

    private String value;
}
