package com.kinto2517.vetappointmentbackend.service.impl;

import com.kinto2517.vetappointmentbackend.dto.RatingDTO;
import com.kinto2517.vetappointmentbackend.dto.RatingSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.Client;
import com.kinto2517.vetappointmentbackend.entity.Rating;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import com.kinto2517.vetappointmentbackend.mapper.RatingMapper;
import com.kinto2517.vetappointmentbackend.repository.ClientRepository;
import com.kinto2517.vetappointmentbackend.repository.RatingRepository;
import com.kinto2517.vetappointmentbackend.repository.VetDoctorRepository;
import com.kinto2517.vetappointmentbackend.service.RatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ClientRepository clientRepository;
    private final VetDoctorRepository vetDoctorRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, ClientRepository clientRepository, VetDoctorRepository vetDoctorRepository) {
        this.ratingRepository = ratingRepository;
        this.clientRepository = clientRepository;
        this.vetDoctorRepository = vetDoctorRepository;
    }

    @Override
    public RatingDTO createRating(Long clientid, Long vetdoctorid, RatingSaveRequest ratingSaveRequest) {
        Client client = clientRepository.findById(clientid).orElseThrow();
        VetDoctor vetDoctor = vetDoctorRepository.findById(vetdoctorid).orElseThrow();
        Rating rating = new Rating();
        rating.setClient(client);
        rating.setVetDoctor(vetDoctor);
        rating.setRatingValue(ratingSaveRequest.rating());
        rating.setReviewText(ratingSaveRequest.comment());
        Rating savedRating = ratingRepository.save(rating);

        return RatingMapper.INSTANCE.ratingToRatingDTO(savedRating);
    }
}
