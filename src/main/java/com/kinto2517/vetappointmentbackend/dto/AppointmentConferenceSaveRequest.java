package com.kinto2517.vetappointmentbackend.dto;

import java.util.Date;

public record AppointmentConferenceSaveRequest(
        Long id,
        Date startTime,
        Date endTime,
        String title,
        String meetingId

) {
}
