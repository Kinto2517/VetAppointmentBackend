package com.kinto2517.vetappointmentbackend.dto;

import java.util.Date;

public record RatingDTO(
        Long id,
        int ratingValue,
        String reviewText

) {
}
