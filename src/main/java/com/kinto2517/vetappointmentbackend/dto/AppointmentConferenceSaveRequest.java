package com.kinto2517.vetappointmentbackend.dto;

import java.time.Instant;
import java.util.Date;

public record AppointmentConferenceSaveRequest(
        Instant startTime,
        Instant endTime,
        String meetingId

) {
}
