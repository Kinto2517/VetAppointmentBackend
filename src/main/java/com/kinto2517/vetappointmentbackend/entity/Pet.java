package com.kinto2517.vetappointmentbackend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // General Information
    private String name;
    private String species;
    private String breed;
    private int age;
    private String gender;
    private double weight;
    private Date dateOfBirth;
    private String color;

    // Medical Information
    private String medicalHistory;
    private String allergies;
    private String behavioralNotes;
    private String dietaryInformation;
    private String currentHealthStatus;

    // Owner Information
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client owner;

    @Lob
    private byte[] photos;

    // Microchip Information
    private String microchipInformation;

    // Insurance Information
    private String insuranceInformation;

}