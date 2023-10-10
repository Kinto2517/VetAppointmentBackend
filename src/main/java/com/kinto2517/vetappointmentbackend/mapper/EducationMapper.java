package com.kinto2517.vetappointmentbackend.mapper;

import com.kinto2517.vetappointmentbackend.dto.ClientDTO;
import com.kinto2517.vetappointmentbackend.dto.ClientSaveRequest;
import com.kinto2517.vetappointmentbackend.dto.EducationDTO;
import com.kinto2517.vetappointmentbackend.dto.EducationSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.Client;
import com.kinto2517.vetappointmentbackend.entity.Education;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EducationMapper {

    EducationMapper INSTANCE = Mappers.getMapper(EducationMapper.class);

    EducationDTO educationToEducationDTO(Education education);

    Education educationSaveRequestToEducation(EducationSaveRequest educationSaveRequest);

    List<EducationDTO> educationsToEducationDTOs(List<Education> educations);

}
