package com.kinto2517.vetappointmentbackend.dto;

public record VetDoctorDTO(
        Long id,
        String fullName,
        String phoneNumber,
        String city,
        String description,
        String username,
        String password
) {
}
