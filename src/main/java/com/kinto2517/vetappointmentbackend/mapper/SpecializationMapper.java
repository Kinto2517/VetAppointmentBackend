package com.kinto2517.vetappointmentbackend.mapper;

import com.kinto2517.vetappointmentbackend.dto.SpecializationDTO;
import com.kinto2517.vetappointmentbackend.dto.SpecializationSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.Specialization;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecializationMapper {

    SpecializationMapper INSTANCE = Mappers.getMapper(SpecializationMapper.class);

    SpecializationDTO specializationToSpecializationDTO(Specialization specialization);
    Specialization saveRequestToSpecialization(SpecializationSaveRequest specializationSaveRequest);
    List<SpecializationDTO> specializationsToSpecializationDTOs(List<Specialization> specializations);

}
