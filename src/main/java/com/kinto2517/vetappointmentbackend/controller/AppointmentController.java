package com.kinto2517.vetappointmentbackend.controller;

import com.kinto2517.vetappointmentbackend.dto.AppointmentDTO;
import com.kinto2517.vetappointmentbackend.service.AppointmentConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@PreAuthorize("hasRole('CLIENT')" + "|| hasRole('VET_DOCTOR')")
public class AppointmentController {

    private final AppointmentConferenceService appointmentConferenceService;

    @Autowired
    public AppointmentController(AppointmentConferenceService appointmentConferenceService) {
        this.appointmentConferenceService = appointmentConferenceService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(appointmentConferenceService.getAllAppointments());
    }

    @PostMapping("/create-appointment/{client_id}/{vet_doctor_id}")
    public ResponseEntity<AppointmentDTO> createAppointment() {
        return ResponseEntity.ok(appointmentConferenceService.createAppointment());
    }

    @DeleteMapping("/delete-appointment/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentConferenceService.deleteAppointment(id));
    }




}
