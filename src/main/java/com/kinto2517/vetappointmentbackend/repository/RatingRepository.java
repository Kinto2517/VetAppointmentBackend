package com.kinto2517.vetappointmentbackend.repository;

import com.kinto2517.vetappointmentbackend.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}