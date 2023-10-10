package com.kinto2517.vetappointmentbackend.mapper;

import com.kinto2517.vetappointmentbackend.dto.PetDTO;
import com.kinto2517.vetappointmentbackend.dto.PetSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.Pet;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetMapper {

    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    PetDTO petToPetDTO(Pet pet);
    Pet petSaveRequestToPet(PetSaveRequest petSaveRequest);
    List<PetDTO> petsToPetDTOs(List<Pet> pets);


}
