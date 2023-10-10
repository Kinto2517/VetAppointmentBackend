package com.kinto2517.vetappointmentbackend.entity;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Client extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NonNull
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "phone_number",  length = 20)
    private String phoneNumber;


    // One-to-Many relationship with Pet entities
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pet> pets = new HashSet<>();

    // One-to-Many relationship with Appointment entities
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments = new HashSet<>();

    // One-to-Many relationship with Notification entities
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications = new HashSet<>();

    // One-to-Many relationship with ChatMessage receiver entities
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatMessage> receivedMessages = new HashSet<>();

    // One-to-Many relationship with ChatMessage sender entities
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatMessage> sentMessages = new HashSet<>();


    // One-to-Many relationship with Review entities
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rating> ratings = new HashSet<>();
}
