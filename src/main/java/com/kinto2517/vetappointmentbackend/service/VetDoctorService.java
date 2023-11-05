package com.kinto2517.vetappointmentbackend.service;

import com.kinto2517.vetappointmentbackend.dto.*;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import com.kinto2517.vetappointmentbackend.request.PasswordChangeRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface VetDoctorService {

    List<VetDoctorDTO> getAllVetDoctors();

    VetDoctorDTO getVetDoctorById(Long id);

    VetDoctorDTO saveVetDoctor(VetDoctorSaveRequest vetDoctorSaveRequest);

    VetDoctorDTO updateVetDoctor(Long id, VetDoctorMainSaveRequest vetDoctorMainSaveRequest);

    void deleteVetDoctor(Long id);

    void addProfilePicture(Long id, MultipartFile file) throws IOException;

    void addSpecializations(Long id, List<Long> specializationIds);

    List<SpecializationDTO> getSpecializations(Long id);

    ExperienceDTO addExperience(Long id, ExperienceSaveRequest experienceSaveRequest);

    List<ExperienceDTO> getExperiences(Long id);

    ExperienceDTO updateExperience(Long id, Long experienceId, ExperienceSaveRequest experienceSaveRequest);

    void deleteExperience(Long id, Long experienceId);

    EducationDTO addEducation(Long id, EducationSaveRequest educationSaveRequest);

    List<EducationDTO> getEducations(Long id);

    EducationDTO updateEducation(Long id, Long educationId, EducationSaveRequest educationSaveRequest);

    void deleteEducation(Long id, Long educationId);

    void changePassword(PasswordChangeRequest passwordChangeRequest, Principal principal);
}
