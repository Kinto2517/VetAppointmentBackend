package com.kinto2517.vetappointmentbackend.controller;

import com.kinto2517.vetappointmentbackend.dto.*;
import com.kinto2517.vetappointmentbackend.entity.Client;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import com.kinto2517.vetappointmentbackend.service.AppointmentConferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentConferenceService appointmentConferenceService;
    private static final Logger logger = LoggerFactory.getLogger(VetDoctorController.class);


    @Autowired
    public AppointmentController(AppointmentConferenceService appointmentConferenceService) {
        this.appointmentConferenceService = appointmentConferenceService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('CLIENT') or hasRole('VET_DOCTOR')")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(appointmentConferenceService.getAllAppointments());
    }

    @PostMapping("/create-appointment/{client_id}/{vet_doctor_id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<AppointmentDTO> createAppointment(@PathVariable Long client_id,
                                                            @PathVariable Long vet_doctor_id,
                                                            @RequestBody AppointmentConferenceSaveRequest appointmentConferenceSaveRequest,
                                                            Authentication authentication
    ) {
        Client authenticatedClient = (Client) authentication.getPrincipal();
        if (!authenticatedClient.getId().equals(client_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(appointmentConferenceService.createAppointment(client_id, vet_doctor_id, appointmentConferenceSaveRequest));
    }

    @DeleteMapping("/delete-appointment/{client_id}/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long client_id,
                                                  @PathVariable Long id,
                                                  Authentication authentication
    ) {
        Client authenticatedClient = (Client) authentication.getPrincipal();
        if (!authenticatedClient.getId().equals(client_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentConferenceService.deleteAppointment(id));
    }

    @GetMapping("/get-client-appointments/{client_id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<AppointmentDTO>> getClientAppointments(@PathVariable Long client_id,
                                                                      Authentication authentication
    ) {
        Client authenticatedClient = (Client) authentication.getPrincipal();
        if (!authenticatedClient.getId().equals(client_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentConferenceService.getClientAppointments(client_id));
    }

    @GetMapping("/get-vetdoctor-appointments/{vet_doctor_id}")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<List<AppointmentDTO>> getVetDoctorAppointments(@PathVariable Long vet_doctor_id,
                                                                         Authentication authentication
    ) {
        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();
        if (!authenticatedVetDoctor.getId().equals(vet_doctor_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentConferenceService.getVetDoctorAppointments(vet_doctor_id));
    }

    @PostMapping("/create-availability/{vet_doctor_id}")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<AvailabilityDTO> createAvailability(@PathVariable Long vet_doctor_id,
                                                              @RequestBody AvailabilitySaveRequest availabilitySaveRequest,
                                                              Authentication authentication
    ) {
        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();
        if (!authenticatedVetDoctor.getId().equals(vet_doctor_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentConferenceService.createAvailability(vet_doctor_id, availabilitySaveRequest));
    }

    @DeleteMapping("/delete-availability/{vet_doctor_id}/{id}")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long vet_doctor_id,
                                                    @PathVariable Long id,
                                                    Authentication authentication
    ) {
        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();
        if (!authenticatedVetDoctor.getId().equals(vet_doctor_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentConferenceService.deleteAvailability(id));
    }

    @GetMapping("/get-vetdoctor-availabilities/{vet_doctor_id}")
    @PreAuthorize("hasRole('VETDOCTOR') or hasRole('CLIENT')")
    public ResponseEntity<List<AvailabilityDTO>> getVetDoctorAvailabilities(@PathVariable Long vet_doctor_id,
                                                                            Authentication authentication
    ) {
        return ResponseEntity.ok(appointmentConferenceService.getVetDoctorAvailabilities(vet_doctor_id));
    }


    /** NOTIFICATIONS **/
    @GetMapping("/get-client-notifications/{client_id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<NotificationDTO>> getClientNotifications(@PathVariable Long client_id,
                                                                        Authentication authentication
    ) {
        Client authenticatedClient = (Client) authentication.getPrincipal();
        if (!authenticatedClient.getId().equals(client_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentConferenceService.getClientNotifications(client_id));
    }

    @DeleteMapping("/delete-client-notification/{client_id}/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> deleteClientNotification(@PathVariable Long client_id,
                                                         @PathVariable Long id,
                                                         Authentication authentication
    ) {
        Client authenticatedClient = (Client) authentication.getPrincipal();
        if (!authenticatedClient.getId().equals(client_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentConferenceService.deleteClientNotification(id));
    }

    @GetMapping("/get-vetdoctor-notifications/{vet_doctor_id}")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<List<NotificationDTO>> getVetDoctorNotifications(@PathVariable Long vet_doctor_id,
                                                                           Authentication authentication
    ) {
        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();
        if (!authenticatedVetDoctor.getId().equals(vet_doctor_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentConferenceService.getVetDoctorNotifications(vet_doctor_id));
    }

    @DeleteMapping("/delete-vetdoctor-notification/{vet_doctor_id}/{id}")
    @PreAuthorize("hasRole('VETDOCTOR')")
    public ResponseEntity<Void> deleteVetDoctorNotification(@PathVariable Long vet_doctor_id,
                                                            @PathVariable Long id,
                                                            Authentication authentication
    ) {
        VetDoctor authenticatedVetDoctor = (VetDoctor) authentication.getPrincipal();
        if (!authenticatedVetDoctor.getId().equals(vet_doctor_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentConferenceService.deleteVetDoctorNotification(id));
    }


}
