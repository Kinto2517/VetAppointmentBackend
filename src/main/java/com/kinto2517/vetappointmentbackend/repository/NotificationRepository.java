package com.kinto2517.vetappointmentbackend.repository;

import com.kinto2517.vetappointmentbackend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}