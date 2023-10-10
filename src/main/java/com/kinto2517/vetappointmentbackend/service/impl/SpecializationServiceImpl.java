package com.kinto2517.vetappointmentbackend.service.impl;

import com.kinto2517.vetappointmentbackend.dto.SpecializationDTO;
import com.kinto2517.vetappointmentbackend.dto.SpecializationSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.Specialization;
import com.kinto2517.vetappointmentbackend.mapper.SpecializationMapper;
import com.kinto2517.vetappointmentbackend.repository.SpecializationRepository;
import com.kinto2517.vetappointmentbackend.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;

    @Autowired
    public SpecializationServiceImpl(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }


    @Override
    public List<SpecializationDTO> getAllSpecializations() {
        if (specializationRepository.findAll().isEmpty()) {
            throw new RuntimeException("There are no specializations");
        }
        List<Specialization> specializations = specializationRepository.findAll();
        return SpecializationMapper.INSTANCE.specializationsToSpecializationDTOs(specializations);
    }

    @Override
    public SpecializationDTO addSpecialization(SpecializationSaveRequest specializationSaveRequest) {
        Specialization specialization = SpecializationMapper.INSTANCE.saveRequestToSpecialization(specializationSaveRequest);
        Specialization savedSpecialization = specializationRepository.save(specialization);
        return SpecializationMapper.INSTANCE.specializationToSpecializationDTO(savedSpecialization);
    }


}
