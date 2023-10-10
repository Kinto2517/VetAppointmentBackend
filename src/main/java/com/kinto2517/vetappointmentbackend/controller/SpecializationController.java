package com.kinto2517.vetappointmentbackend.controller;

import com.kinto2517.vetappointmentbackend.dto.SpecializationDTO;
import com.kinto2517.vetappointmentbackend.dto.SpecializationSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.Specialization;
import com.kinto2517.vetappointmentbackend.service.SpecializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/specializations")
@RestController
public class SpecializationController {

    private final SpecializationService specializationService;
    private final Logger logger = LoggerFactory.getLogger(SpecializationController.class);


    @Autowired
    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SpecializationDTO>> getAllSpecializations(){
        return ResponseEntity.ok(specializationService.getAllSpecializations());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpecializationDTO> addSpecialization(SpecializationSaveRequest specializationSaveRequest){
        return ResponseEntity.ok(specializationService.addSpecialization(specializationSaveRequest));
    }

}
