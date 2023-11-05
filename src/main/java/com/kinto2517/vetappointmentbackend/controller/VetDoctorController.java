package com.kinto2517.vetappointmentbackend.controller;

import com.kinto2517.vetappointmentbackend.dto.*;
import com.kinto2517.vetappointmentbackend.entity.Education;
import com.kinto2517.vetappointmentbackend.entity.Specialization;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import com.kinto2517.vetappointmentbackend.request.PasswordChangeRequest;
import com.kinto2517.vetappointmentbackend.service.VetDoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/vetdoctors")
public class VetDoctorController {

    private final VetDoctorService vetDoctorService;
    private static final Logger logger = LoggerFactory.getLogger(VetDoctorController.class);

    @Autowired
    public VetDoctorController(VetDoctorService vetDoctorService) {
        this.vetDoctorService = vetDoctorService;
    }

    /**
     * Get All VetDoctors (Temp, will be deleted)
     **/
    @GetMapping("/all")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<List<VetDoctorDTO>> getAllVetDoctors() {
        return ResponseEntity.ok(vetDoctorService.getAllVetDoctors());
    }

    /**
     * Update Current VetDoctor
     **/

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<VetDoctorDTO> updateVetDoctor(@PathVariable Long id,
                                                        @RequestBody VetDoctorMainSaveRequest vetDoctorMainSaveRequest,
                                                        Authentication authentication) {

        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedVetDoctor.getUsername());

        if (!authenticatedVetDoctor.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(vetDoctorService.updateVetDoctor(id, vetDoctorMainSaveRequest));
    }

    /**
     * Change Current VetDoctor Password
     */

    @PatchMapping("/change-password")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest,
                                               Principal principal) {

        vetDoctorService.changePassword(passwordChangeRequest, principal);

        return ResponseEntity.ok().build();
    }

    /**  PROFILE PICTURE - VET DOCTOR **/


    /**
     * Add Profile Picture to VetDoctor
     **/

    @PutMapping("/{id}/add-profile-picture")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<String> addProfilePicture(@PathVariable Long id,
                                                    @RequestParam("file") MultipartFile file,
                                                    Authentication authentication) throws IOException {


        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedVetDoctor.getUsername());
        logger.info("User Roles: {}", authenticatedVetDoctor.getRole());

        if (!authenticatedVetDoctor.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }

        if (!isValidImage(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Image");
        }

        vetDoctorService.addProfilePicture(id, file);

        return ResponseEntity.status(HttpStatus.OK).body("Profile picture updated successfully");
    }

    private boolean isValidImage(MultipartFile file) {
        try {
            return Objects.requireNonNull(file.getContentType()).startsWith("image/") &&
                    (Objects.requireNonNull(file.getContentType()).endsWith("png") ||
                            file.getContentType().endsWith("jpg") ||
                            file.getContentType().endsWith("jpeg"));
        } catch (Exception e) {
            return false;
        }

    }

    /**  SPECIALIZATION - VET DOCTOR **/


    /**
     * Update Specializations of VetDoctor
     **/

    @PutMapping("/{id}/update-specializations")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<Void> addSpecializations(@PathVariable Long id,
                                                   @RequestBody List<Long> specializationIds,
                                                   Authentication authentication) {

        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedVetDoctor.getUsername());
        logger.info("User Roles: {}", authenticatedVetDoctor.getRole());

        if (!authenticatedVetDoctor.getId().equals(id)) {
            logger.info("User ID doesnt match");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        vetDoctorService.addSpecializations(id, specializationIds);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Get Specializations of Current VetDoctor
     **/
    @GetMapping("/{id}/specializations")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<List<SpecializationDTO>> getSpecializations(@PathVariable Long id,
                                                                      Authentication authentication) {

        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedVetDoctor.getUsername());
        logger.info("User Roles: {}", authenticatedVetDoctor.getRole());
        logger.info("Authenticated User ID: {}", authenticatedVetDoctor.getId());

        if (!authenticatedVetDoctor.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(vetDoctorService.getSpecializations(id));
    }


    /**  EXPERIENCE - VET DOCTOR **/

    /**
     * Add Experience to VetDoctor
     **/
    @PostMapping("/{id}/add-experience")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<ExperienceDTO> addExperience(@PathVariable Long id,
                                                       @RequestBody ExperienceSaveRequest experienceSaveRequest,
                                                       Authentication authentication) {

        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedVetDoctor.getUsername());
        logger.info("User Roles: {}", authenticatedVetDoctor.getRole());
        logger.info("Authenticated User ID: {}", authenticatedVetDoctor.getId());

        if (!authenticatedVetDoctor.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(vetDoctorService.addExperience(id, experienceSaveRequest));
    }

    /**
     * Get Experiences of Current VetDoctor
     **/
    @GetMapping("/{id}/experiences")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<List<ExperienceDTO>> getExperiences(@PathVariable Long id,
                                                               Authentication authentication) {

        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedVetDoctor.getUsername());
        logger.info("User Roles: {}", authenticatedVetDoctor.getRole());
        logger.info("Authenticated User ID: {}", authenticatedVetDoctor.getId());

        if (!authenticatedVetDoctor.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(vetDoctorService.getExperiences(id));
    }

    /**
     * Update Experiences of VetDoctor
     **/
    @PutMapping("/{id}/update-experience/{experienceId}")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<ExperienceDTO> updateExperience(@PathVariable Long id,
                                                          @PathVariable Long experienceId,
                                                          @RequestBody ExperienceSaveRequest experienceSaveRequest,
                                                          Authentication authentication) {

        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedVetDoctor.getUsername());
        logger.info("User Roles: {}", authenticatedVetDoctor.getRole());
        logger.info("Authenticated User ID: {}", authenticatedVetDoctor.getId());

        if (!authenticatedVetDoctor.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(vetDoctorService.updateExperience(id, experienceId, experienceSaveRequest));
    }

    /**
     * Delete Experience of VetDoctor
     **/
    @DeleteMapping("/{id}/delete-experience/{experienceId}")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id,
                                                  @PathVariable Long experienceId,
                                                  Authentication authentication) {

        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedVetDoctor.getUsername());
        logger.info("User Roles: {}", authenticatedVetDoctor.getRole());
        logger.info("Authenticated User ID: {}", authenticatedVetDoctor.getId());

        if (!authenticatedVetDoctor.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        vetDoctorService.deleteExperience(id, experienceId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**  EDUCATION - VET DOCTOR **/


    /**
     * Add Education to VetDoctor
     */
    @PostMapping("/{id}/add-education")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<EducationDTO> addEducation(@PathVariable Long id,
                                                     @RequestBody EducationSaveRequest educationSaveRequest,
                                                     Authentication authentication) {

        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedVetDoctor.getUsername());
        logger.info("User Roles: {}", authenticatedVetDoctor.getRole());
        logger.info("Authenticated User ID: {}", authenticatedVetDoctor.getId());

        if (!authenticatedVetDoctor.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(vetDoctorService.addEducation(id, educationSaveRequest));
    }

    /**
     * Get Educations of Current VetDoctor
     **/

    @GetMapping("/{id}/educations")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<List<EducationDTO>> getEducations(@PathVariable Long id,
                                                             Authentication authentication) {

        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedVetDoctor.getUsername());
        logger.info("User Roles: {}", authenticatedVetDoctor.getRole());
        logger.info("Authenticated User ID: {}", authenticatedVetDoctor.getId());

        if (!authenticatedVetDoctor.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(vetDoctorService.getEducations(id));
    }

    /**
     * Update Educations of VetDoctor
     **/

    @PutMapping("/{id}/update-education/{educationId}")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<EducationDTO> updateEducation(@PathVariable Long id,
                                                        @PathVariable Long educationId,
                                                        @RequestBody EducationSaveRequest educationSaveRequest,
                                                        Authentication authentication) {

        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedVetDoctor.getUsername());
        logger.info("User Roles: {}", authenticatedVetDoctor.getRole());
        logger.info("Authenticated User ID: {}", authenticatedVetDoctor.getId());

        if (!authenticatedVetDoctor.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(vetDoctorService.updateEducation(id, educationId, educationSaveRequest));
    }


    /**
     * Delete Education of VetDoctor
     **/

    @DeleteMapping("/{id}/delete-education/{educationId}")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id,
                                                 @PathVariable Long educationId,
                                                 Authentication authentication) {

        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();

        logger.info("Authenticated User: {}", authenticatedVetDoctor.getUsername());
        logger.info("User Roles: {}", authenticatedVetDoctor.getRole());
        logger.info("Authenticated User ID: {}", authenticatedVetDoctor.getId());

        if (!authenticatedVetDoctor.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        vetDoctorService.deleteEducation(id, educationId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
