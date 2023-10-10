package com.kinto2517.vetappointmentbackend.service;

import com.kinto2517.vetappointmentbackend.dto.SpecializationDTO;
import com.kinto2517.vetappointmentbackend.dto.SpecializationSaveRequest;

import java.util.List;
import java.util.Set;

public interface SpecializationService {
    List<SpecializationDTO> getAllSpecializations();

    SpecializationDTO addSpecialization(SpecializationSaveRequest specializationSaveRequest);
}
