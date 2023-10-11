package com.kinto2517.vetappointmentbackend.dto;

import java.util.Date;

public record AppointmentDTO(
        Long id,
        Date startTime,
        Date endTime
) {
}
