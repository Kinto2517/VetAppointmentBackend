package com.kinto2517.vetappointmentbackend.dto;

public record VetDoctorSaveRequest(
        String fullName,
        String phoneNumber,
        String city,
        String description,
        String username,
        String password,
        String email
) {
}
