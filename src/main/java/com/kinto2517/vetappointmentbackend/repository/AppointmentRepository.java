package com.kinto2517.vetappointmentbackend.repository;

import com.kinto2517.vetappointmentbackend.entity.Appointment;
import com.kinto2517.vetappointmentbackend.entity.Client;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByVetDoctor(VetDoctor vetDoctor);

    List<Appointment> findByClient(Client client);
}