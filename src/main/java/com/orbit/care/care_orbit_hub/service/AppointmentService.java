package com.orbit.care.care_orbit_hub.service;

import com.orbit.care.care_orbit_hub.dto.AppointmentDTO;

import java.util.UUID;

public interface AppointmentService {
    UUID createAppointment(AppointmentDTO appointmentDTO);

    AppointmentDTO getAppointment(UUID id);
}
