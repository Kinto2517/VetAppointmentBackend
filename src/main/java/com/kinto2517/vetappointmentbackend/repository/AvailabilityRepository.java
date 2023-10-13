package com.kinto2517.vetappointmentbackend.repository;

import com.kinto2517.vetappointmentbackend.entity.Availability;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByVetDoctor(VetDoctor vetDoctor);
}