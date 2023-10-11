package com.kinto2517.vetappointmentbackend.controller;

import com.kinto2517.vetappointmentbackend.dto.AppointmentConferenceSaveRequest;
import com.kinto2517.vetappointmentbackend.dto.AppointmentDTO;
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


}
