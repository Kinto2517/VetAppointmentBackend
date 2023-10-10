package com.kinto2517.vetappointmentbackend.dto;

public record ClientMainSaveRequest(
        String firstName,
        String lastName,
        String phoneNumber
) {
}
