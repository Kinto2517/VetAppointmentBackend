package com.kinto2517.vetappointmentbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "availabilities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vetdoctor_id", nullable = false)
    private VetDoctor vetDoctor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Instant startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Instant endTime;

    private boolean booked;

}
