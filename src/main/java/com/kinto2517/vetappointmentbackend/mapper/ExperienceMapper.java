package com.kinto2517.vetappointmentbackend.mapper;

import com.kinto2517.vetappointmentbackend.dto.ClientDTO;
import com.kinto2517.vetappointmentbackend.dto.ClientSaveRequest;
import com.kinto2517.vetappointmentbackend.dto.ExperienceDTO;
import com.kinto2517.vetappointmentbackend.dto.ExperienceSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.Client;
import com.kinto2517.vetappointmentbackend.entity.Experience;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExperienceMapper {

    ExperienceMapper INSTANCE = Mappers.getMapper(ExperienceMapper.class);

    ExperienceDTO experienceToExperienceDTO(Experience experience);

    Experience experienceSaveRequestToExperience(ExperienceSaveRequest experienceSaveRequest);

    List<ExperienceDTO> experiencesToExperienceDTOs(List<Experience> experiences);
}
