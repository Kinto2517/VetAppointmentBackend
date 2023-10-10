package com.kinto2517.vetappointmentbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "educations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vetdoctor_id", nullable = false)
    private VetDoctor vetDoctor;

    private String institution;

    private String degree;

    private String fieldOfStudy;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date graduationDate;

    @Column(length = 500)
    private String description;

}
