package com.kinto2517.vetappointmentbackend.dto;

import java.time.Instant;
import java.util.Date;

public record AvailabilityDTO(
        Long id,
        Instant startTime,
        Instant endTime,
        boolean booked,
        String vetDoctorFullName

) {
}
