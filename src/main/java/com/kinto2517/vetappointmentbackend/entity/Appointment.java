package com.kinto2517.vetappointmentbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vetdoctor_id", nullable = false)
    private VetDoctor vetDoctor;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date endTime;

    @OneToOne(mappedBy = "appointment")
    private VideoConference videoConference;

}
