package com.kinto2517.vetappointmentbackend.dto;

import java.util.Date;

public record RatingSaveRequest(
        int ratingValue,
        String reviewText

) {
}
