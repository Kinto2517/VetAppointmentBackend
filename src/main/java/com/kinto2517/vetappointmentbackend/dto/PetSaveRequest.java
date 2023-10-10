package com.kinto2517.vetappointmentbackend.dto;

import java.util.Date;

public record PetSaveRequest (
        String name,
        String species,
        String breed,
        int age,
        String gender,
        double weight,
        Date dateOfBirth,
        String color,
        String medicalHistory,
        String allergies,
        String behavioralNotes,
        String dietaryInformation,
        String currentHealthStatus,
        String microchipInformation,
        String insuranceInformation

) {
}
