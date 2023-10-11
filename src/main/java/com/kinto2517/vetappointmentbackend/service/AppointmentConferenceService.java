package com.kinto2517.vetappointmentbackend.service;

import com.kinto2517.vetappointmentbackend.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentConferenceService {
    List<AppointmentDTO> getAllAppointments();

    AppointmentDTO createAppointment();

    Void deleteAppointment(Long id);
}
