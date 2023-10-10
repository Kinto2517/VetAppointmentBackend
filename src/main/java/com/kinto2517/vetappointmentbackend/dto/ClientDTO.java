package com.kinto2517.vetappointmentbackend.dto;

public record ClientDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String username,
        String password
) {
}
