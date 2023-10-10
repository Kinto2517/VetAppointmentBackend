package com.kinto2517.vetappointmentbackend.dto;

public record VetDoctorMainSaveRequest(
        String fullName,
        String phoneNumber,
        String city,
        String description
) {
}
