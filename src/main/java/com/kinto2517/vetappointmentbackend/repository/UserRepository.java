package com.kinto2517.vetappointmentbackend.repository;

import com.kinto2517.vetappointmentbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}