package com.kinto2517.vetappointmentbackend.service;

import com.kinto2517.vetappointmentbackend.dto.*;

import java.util.List;

public interface AppointmentConferenceService {
    List<AppointmentDTO> getAllAppointments();

    Void deleteAppointment(Long id);

    List<AppointmentDTO> getClientAppointments(Long clientId);

    List<AppointmentDTO> getVetDoctorAppointments(Long vetDoctorId);

    AppointmentDTO createAppointment(Long clientId, Long vetDoctorId, AppointmentConferenceSaveRequest appointmentConferenceSaveRequest);

    AvailabilityDTO createAvailability(Long vetDoctorId, AvailabilitySaveRequest availabilitySaveRequest);

    List<AvailabilityDTO> getVetDoctorAvailabilities(Long vetDoctorId);

    Void deleteAvailability(Long id);

    List<NotificationDTO> getClientNotifications(Long clientId);

    List<NotificationDTO> getVetDoctorNotifications(Long vetDoctorId);

    Void deleteVetDoctorNotification(Long id);

    Void deleteClientNotification(Long id);
}
