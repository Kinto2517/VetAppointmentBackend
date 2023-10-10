package com.kinto2517.vetappointmentbackend.mapper;

import com.kinto2517.vetappointmentbackend.dto.VetDoctorDTO;
import com.kinto2517.vetappointmentbackend.dto.VetDoctorSaveRequest;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VetDoctorMapper {

    VetDoctorMapper INSTANCE = Mappers.getMapper(VetDoctorMapper.class);

    VetDoctorDTO vetDoctorToVetDoctorDTO(VetDoctor vetDoctor);
    VetDoctor vetDoctorSaveRequestToVetDoctor(VetDoctorSaveRequest vetDoctorSaveRequest);
    List<VetDoctorDTO> vetDoctorsToVetDoctorDTOs(List<VetDoctor> vetDoctors);


}
