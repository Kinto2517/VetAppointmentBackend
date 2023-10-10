package com.kinto2517.vetappointmentbackend.repository;

import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VetDoctorRepository extends JpaRepository<VetDoctor, Long> {

    Optional<VetDoctor> findByUsername(String username);
 }