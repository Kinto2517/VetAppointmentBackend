package com.kinto2517.vetappointmentbackend.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.Date;

@Entity
@Table(name = "experiences")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vetdoctor_id", nullable = false)
    private VetDoctor vetDoctor;

    private String organization;

    private String jobTitle;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(length = 500)
    private String description;

}

