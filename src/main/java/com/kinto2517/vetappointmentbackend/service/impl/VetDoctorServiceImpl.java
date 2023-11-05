package com.kinto2517.vetappointmentbackend.service.impl;

import com.kinto2517.vetappointmentbackend.dto.*;
import com.kinto2517.vetappointmentbackend.entity.Education;
import com.kinto2517.vetappointmentbackend.entity.Experience;
import com.kinto2517.vetappointmentbackend.entity.Specialization;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import com.kinto2517.vetappointmentbackend.mapper.EducationMapper;
import com.kinto2517.vetappointmentbackend.mapper.ExperienceMapper;
import com.kinto2517.vetappointmentbackend.mapper.SpecializationMapper;
import com.kinto2517.vetappointmentbackend.mapper.VetDoctorMapper;
import com.kinto2517.vetappointmentbackend.repository.EducationRepository;
import com.kinto2517.vetappointmentbackend.repository.ExperienceRepository;
import com.kinto2517.vetappointmentbackend.repository.SpecializationRepository;
import com.kinto2517.vetappointmentbackend.repository.VetDoctorRepository;
import com.kinto2517.vetappointmentbackend.request.PasswordChangeRequest;
import com.kinto2517.vetappointmentbackend.service.VetDoctorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class VetDoctorServiceImpl implements VetDoctorService {

    private final VetDoctorRepository vetDoctorRepository;
    private final SpecializationRepository specializationRepository;
    private final ExperienceRepository experienceRepository;
    private final EducationRepository educationRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public VetDoctorServiceImpl(VetDoctorRepository vetDoctorRepository, SpecializationRepository specializationRepository, ExperienceRepository experienceRepository, EducationRepository educationRepository, PasswordEncoder passwordEncoder) {
        this.vetDoctorRepository = vetDoctorRepository;
        this.specializationRepository = specializationRepository;
        this.experienceRepository = experienceRepository;
        this.educationRepository = educationRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public List<VetDoctorDTO> getAllVetDoctors() {
        if (vetDoctorRepository.findAll().isEmpty()) {
            throw new RuntimeException("There are no vet doctors");
        }
        List<VetDoctor> vetDoctors = vetDoctorRepository.findAll();
        return VetDoctorMapper.INSTANCE.vetDoctorsToVetDoctorDTOs(vetDoctors);
    }

    @Override
    public VetDoctorDTO getVetDoctorById(Long id) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow();
        return VetDoctorMapper.INSTANCE.vetDoctorToVetDoctorDTO(vetDoctor);
    }

    @Override
    public VetDoctorDTO saveVetDoctor(VetDoctorSaveRequest vetDoctorSaveRequest) {
        VetDoctor vetDoctor = VetDoctorMapper.INSTANCE.vetDoctorSaveRequestToVetDoctor(vetDoctorSaveRequest);
        VetDoctor savedVetDoctor = vetDoctorRepository.save(vetDoctor);
        return VetDoctorMapper.INSTANCE.vetDoctorToVetDoctorDTO(savedVetDoctor);
    }

    @Override
    public VetDoctorDTO updateVetDoctor(Long id, VetDoctorMainSaveRequest vetDoctorMainSaveRequest) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow();

        vetDoctor.setFullName(vetDoctorMainSaveRequest.fullName());
        vetDoctor.setCity(vetDoctorMainSaveRequest.city());
        vetDoctor.setPhoneNumber(vetDoctorMainSaveRequest.phoneNumber());
        vetDoctor.setDescription(vetDoctorMainSaveRequest.description());

        VetDoctor savedVetDoctor = vetDoctorRepository.save(vetDoctor);
        return VetDoctorMapper.INSTANCE.vetDoctorToVetDoctorDTO(savedVetDoctor);
    }

    @Override
    public void deleteVetDoctor(Long id) {
        if (!vetDoctorRepository.existsById(id)) {
            throw new RuntimeException("VetDoctor with id " + id + " does not exist");
        }
        vetDoctorRepository.deleteById(id);
    }

    @Override
    public void addProfilePicture(Long id, MultipartFile file) throws IOException {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow();
        vetDoctor.setProfilePicture(file.getBytes());
        vetDoctorRepository.save(vetDoctor);
    }

    @Override
    @Transactional
    public void addSpecializations(Long id, List<Long> specializationIds) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow();
        Set<Specialization> existingSpecializations = vetDoctor.getSpecializations();
        existingSpecializations.removeIf(existingSpec -> !specializationIds.contains(existingSpec.getId()));

        specializationIds.forEach(newSpecId -> {
            if (existingSpecializations.stream().noneMatch(existingSpec -> existingSpec.getId().equals(newSpecId))) {
                Specialization newSpecialization = specializationRepository.findById(newSpecId)
                        .orElseThrow(() -> new RuntimeException("Specialization with id " + newSpecId + " not found"));
                existingSpecializations.add(newSpecialization);
            }
        });

        vetDoctor.setSpecializations(existingSpecializations);
        vetDoctorRepository.save(vetDoctor);
    }

    @Override
    @Transactional
    public List<SpecializationDTO> getSpecializations(Long id) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow();
        List<Specialization> specializations = new ArrayList<>(vetDoctor.getSpecializations());
        return SpecializationMapper.INSTANCE.specializationsToSpecializationDTOs(specializations);
    }

    @Override
    @Transactional
    public ExperienceDTO addExperience(Long id, ExperienceSaveRequest experienceSaveRequest) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow();
        Experience experience = ExperienceMapper.INSTANCE.experienceSaveRequestToExperience(experienceSaveRequest);
        vetDoctor.getExperiences().add(experience);
        experience.setVetDoctor(vetDoctor);
        Experience savedExperience = experienceRepository.save(experience);
        vetDoctorRepository.save(vetDoctor);
        return ExperienceMapper.INSTANCE.experienceToExperienceDTO(savedExperience);
    }

    @Override
    @Transactional
    public List<ExperienceDTO> getExperiences(Long id) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow();
        List<Experience> experiences = new ArrayList<>(vetDoctor.getExperiences());
        return ExperienceMapper.INSTANCE.experiencesToExperienceDTOs(experiences);
    }

    @Override
    @Transactional
    public ExperienceDTO updateExperience(Long id, Long experienceId, ExperienceSaveRequest experienceSaveRequest) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Experience experience = vetDoctor.getExperiences().stream()
                .filter(exp -> exp.getId().equals(experienceId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Experience with id " + experienceId + " not found"));

        Experience updatedExperience = ExperienceMapper.INSTANCE.experienceSaveRequestToExperience(experienceSaveRequest);
        updatedExperience.setId(experience.getId());
        updatedExperience.setVetDoctor(vetDoctor);

        Experience savedExperience = experienceRepository.save(updatedExperience);

        return ExperienceMapper.INSTANCE.experienceToExperienceDTO(savedExperience);
    }

    @Override
    @Transactional
    public void deleteExperience(Long id, Long experienceId) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Experience experience = vetDoctor.getExperiences().stream()
                .filter(exp -> exp.getId().equals(experienceId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Experience with id " + experienceId + " not found"));

        vetDoctor.getExperiences().remove(experience);
        experienceRepository.delete(experience);
    }

    @Override
    @Transactional
    public EducationDTO addEducation(Long id, EducationSaveRequest educationSaveRequest) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow();
        Education education = EducationMapper.INSTANCE.educationSaveRequestToEducation(educationSaveRequest);
        vetDoctor.getEducations().add(education);
        education.setVetDoctor(vetDoctor);
        Education savedEducation = educationRepository.save(education);
        vetDoctorRepository.save(vetDoctor);
        return EducationMapper.INSTANCE.educationToEducationDTO(savedEducation);
    }

    @Override
    @Transactional
    public List<EducationDTO> getEducations(Long id) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow();
        List<Education> educations = new ArrayList<>(vetDoctor.getEducations());
        return EducationMapper.INSTANCE.educationsToEducationDTOs(educations);
    }

    @Override
    @Transactional
    public EducationDTO updateEducation(Long id, Long educationId, EducationSaveRequest educationSaveRequest) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Education education = vetDoctor.getEducations().stream()
                .filter(edu -> edu.getId().equals(educationId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Education with id " + educationId + " not found"));

        Education updatedEducation = EducationMapper.INSTANCE.educationSaveRequestToEducation(educationSaveRequest);
        updatedEducation.setId(education.getId());
        updatedEducation.setVetDoctor(vetDoctor);

        Education savedEducation = educationRepository.save(updatedEducation);

        return EducationMapper.INSTANCE.educationToEducationDTO(savedEducation);
    }

    @Override
    @Transactional
    public void deleteEducation(Long id, Long educationId) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Education education = vetDoctor.getEducations().stream()
                .filter(edu -> edu.getId().equals(educationId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Education with id " + educationId + " not found"));

        vetDoctor.getEducations().remove(education);
        educationRepository.delete(education);
    }

    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest, Principal principal) {
        VetDoctor vetDoctor = (VetDoctor) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), vetDoctor.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        if (!passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getNewPasswordConfirmation())) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        vetDoctor.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        vetDoctorRepository.save(vetDoctor);
    }
}
