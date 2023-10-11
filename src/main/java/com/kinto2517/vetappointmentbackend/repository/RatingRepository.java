package com.kinto2517.vetappointmentbackend.repository;

import com.kinto2517.vetappointmentbackend.entity.Rating;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r from Rating r where r.vetDoctor.id = ?1")
    List<Rating> findByVetDoctorId(Long vetdoctorid);
}