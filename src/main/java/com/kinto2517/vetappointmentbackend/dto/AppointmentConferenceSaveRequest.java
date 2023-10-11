package com.kinto2517.vetappointmentbackend.dto;

import java.util.Date;

public record AppointmentConferenceSaveRequest(
        Date startTime,
        Date endTime,
        String meetingId

) {
}
