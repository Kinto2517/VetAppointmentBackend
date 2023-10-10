package com.kinto2517.vetappointmentbackend.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date startTime;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date endTime;

    private boolean booked;

}
