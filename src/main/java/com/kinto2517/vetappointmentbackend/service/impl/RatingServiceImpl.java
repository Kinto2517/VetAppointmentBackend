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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    @Transactional
    public RatingDTO createRating(Long clientid, Long vetdoctorid, RatingSaveRequest ratingSaveRequest) {
        Client client = clientRepository.findById(clientid).orElseThrow();
        VetDoctor vetDoctor = vetDoctorRepository.findById(vetdoctorid).orElseThrow();
        Rating rating = RatingMapper.INSTANCE.ratingSaveRequestToRating(ratingSaveRequest);
        rating.setClient(client);
        rating.setVetDoctor(vetDoctor);
        rating.setRatingDate(new Date());
        Rating savedRating = ratingRepository.save(rating);

        return RatingMapper.INSTANCE.ratingToRatingDTO(savedRating);
    }

    @Override
    @Transactional
    public List<RatingDTO> getRatingsByVetDoctorId(Long vetdoctorid) {
        List<Rating> ratings = ratingRepository.findByVetDoctorId(vetdoctorid);
        return RatingMapper.INSTANCE.ratingsToRatingDTOs(ratings);
    }

    @Override
    @Transactional
    public Double getAverageRatingByVetDoctorId(Long vetdoctorid) {
        List<Rating> ratings = ratingRepository.findByVetDoctorId(vetdoctorid);
        Double averageRating = ratings.stream().mapToDouble(Rating::getRatingValue).average().orElse(0.0);
        return averageRating;
    }
}
