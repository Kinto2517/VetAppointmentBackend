package com.kinto2517.vetappointmentbackend.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "vetdoctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class VetDoctor extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String fullName;

    private String phoneNumber;

    private String city;

    @Lob
    private byte[] profilePicture;

    private String description;


    // One-to-Many relationship with Appointment entities
    @OneToMany(mappedBy = "vetDoctor")
    private Set<Appointment> appointments = new HashSet<>();

    // One-to-Many relationship with Rating entities
    @OneToMany(mappedBy = "vetDoctor")
    private Set<Rating> ratings = new HashSet<>();

    // Many-to-Many relationship with Specialization entities
    @ManyToMany
    @JoinTable(
            name = "vetdoctor_specializations",
            joinColumns = @JoinColumn(name = "vetdoctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Set<Specialization> specializations = new HashSet<>();

    // One-to-Many relationship with Notification entities
    @OneToMany(mappedBy = "vetDoctor")
    private Set<Notification> notifications = new HashSet<>();

    // One-to-Many relationship with Availability entities
    @OneToMany(mappedBy = "vetDoctor")
    private Set<Availability> availabilities = new HashSet<>();

    // One-to-Many relationship with Experience entities
    @OneToMany(mappedBy = "vetDoctor")
    private Set<Experience> experiences = new HashSet<>();

    // One-to-Many relationship with ChatMessage receiver entities
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatMessage> receivedMessages = new HashSet<>();

    // One-to-Many relationship with ChatMessage sender entities
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatMessage> sentMessages = new HashSet<>();


    // One-to-Many relationship with Education entities
    @OneToMany(mappedBy = "vetDoctor")
    private Set<Education> educations = new HashSet<>();

}
