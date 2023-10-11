package com.kinto2517.vetappointmentbackend.mapper;

import com.kinto2517.vetappointmentbackend.dto.RatingDTO;
import com.kinto2517.vetappointmentbackend.dto.RatingSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatingMapper {

    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    RatingDTO ratingToRatingDTO(Rating rating);

    Rating ratingSaveRequestToRating(RatingSaveRequest ratingSaveRequest);

    List<RatingDTO> ratingsToRatingDTOs(List<Rating> ratings);

}
