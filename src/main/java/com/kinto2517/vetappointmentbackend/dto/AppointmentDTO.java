package com.kinto2517.vetappointmentbackend.dto;

import java.time.Instant;
import java.util.Date;

public record AppointmentDTO(
        Long id,
        Instant startTime,
        Instant endTime,
        String meetingId,
        String vetDoctorFullName,
        String clientFirstName,
        String clientLastName
) {
}
