package com.kinto2517.vetappointmentbackend.repository;

import com.kinto2517.vetappointmentbackend.entity.Client;
import com.kinto2517.vetappointmentbackend.entity.Notification;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.client = ?1 AND n.vetDoctor IS NULL")
    List<Notification> findByClientAndVetDoctorNull(Client client);

    @Query("SELECT n FROM Notification n WHERE n.vetDoctor = ?1 AND n.client IS NULL")
    List<Notification> findByVetDoctorAndClientNull(VetDoctor vetDoctor);
}