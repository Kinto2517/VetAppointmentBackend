package com.kinto2517.vetappointmentbackend.service;

import com.kinto2517.vetappointmentbackend.dto.AppointmentConferenceSaveRequest;
import com.kinto2517.vetappointmentbackend.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentConferenceService {
    List<AppointmentDTO> getAllAppointments();

    Void deleteAppointment(Long id);

    List<AppointmentDTO> getClientAppointments(Long clientId);

    List<AppointmentDTO> getVetDoctorAppointments(Long vetDoctorId);

    AppointmentDTO createAppointment(Long clientId, Long vetDoctorId, AppointmentConferenceSaveRequest appointmentConferenceSaveRequest);
}
