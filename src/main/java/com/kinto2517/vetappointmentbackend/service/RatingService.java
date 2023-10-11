package com.kinto2517.vetappointmentbackend.service;

import com.kinto2517.vetappointmentbackend.dto.RatingDTO;
import com.kinto2517.vetappointmentbackend.dto.RatingSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.Rating;

import java.util.List;

public interface RatingService {

    RatingDTO createRating(Long clientid, Long vetdoctorid, RatingSaveRequest ratingSaveRequest);

    List<RatingDTO> getRatingsByVetDoctorId(Long vetdoctorid);

    Double getAverageRatingByVetDoctorId(Long vetdoctorid);
}
