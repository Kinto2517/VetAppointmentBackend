package com.kinto2517.vetappointmentbackend.service.impl;

import com.kinto2517.vetappointmentbackend.controller.VetDoctorController;
import com.kinto2517.vetappointmentbackend.dto.AppointmentConferenceSaveRequest;
import com.kinto2517.vetappointmentbackend.dto.AppointmentDTO;
import com.kinto2517.vetappointmentbackend.entity.Appointment;
import com.kinto2517.vetappointmentbackend.entity.Client;
import com.kinto2517.vetappointmentbackend.entity.VetDoctor;
import com.kinto2517.vetappointmentbackend.entity.VideoConference;
import com.kinto2517.vetappointmentbackend.repository.AppointmentRepository;
import com.kinto2517.vetappointmentbackend.repository.ClientRepository;
import com.kinto2517.vetappointmentbackend.repository.VetDoctorRepository;
import com.kinto2517.vetappointmentbackend.repository.VideoConferenceRepository;
import com.kinto2517.vetappointmentbackend.service.AppointmentConferenceService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentConferenceServiceImpl implements AppointmentConferenceService {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final VetDoctorRepository vetDoctorRepository;
    private final VideoConferenceRepository videoConferenceRepository;
    private static final Logger logger = LoggerFactory.getLogger(VetDoctorController.class);

    @Autowired
    public AppointmentConferenceServiceImpl(AppointmentRepository appointmentRepository,
                                            ClientRepository clientRepository,
                                            VetDoctorRepository vetDoctorRepository,
                                            VideoConferenceRepository videoConferenceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.clientRepository = clientRepository;
        this.vetDoctorRepository = vetDoctorRepository;
        this.videoConferenceRepository = videoConferenceRepository;
    }


    @Override
    @Transactional
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(appointment -> new AppointmentDTO(appointment.getId(),
                        appointment.getStartTime(),
                        appointment.getEndTime(),
                        appointment.getVideoConference().getMeetingId(),
                        appointment.getVetDoctor().getFullName(),
                        appointment.getClient().getFirstName(),
                        appointment.getClient().getLastName()))
                .collect(Collectors.toList());

        return appointmentDTOs;
    }

    @Override
    public Void deleteAppointment(Long id) {
        Optional<Appointment> existingAppointment = appointmentRepository.findById(id);

        if (existingAppointment.isPresent()) {
             appointmentRepository.delete(existingAppointment.get());
             return null;
        } else {
            throw new EntityNotFoundException("Appointment with ID " + id + " not found");
        }

    }

    @Override
    @Transactional
    public List<AppointmentDTO> getClientAppointments(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        List<Appointment> appointments = appointmentRepository.findByClient(client);

        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(appointment -> new AppointmentDTO(appointment.getId(),
                        appointment.getStartTime(),
                        appointment.getEndTime(),
                        appointment.getVideoConference().getMeetingId(),
                        appointment.getVetDoctor().getFullName(),
                        appointment.getClient().getFirstName(),
                        appointment.getClient().getLastName()))
                .collect(Collectors.toList());

        return appointmentDTOs;
    }

    @Override
    @Transactional
    public List<AppointmentDTO> getVetDoctorAppointments(Long vetDoctorId) {
        VetDoctor vetDoctor = vetDoctorRepository.findById(vetDoctorId).orElseThrow();
        List<Appointment> appointments = appointmentRepository.findByVetDoctor(vetDoctor);

        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(appointment -> new AppointmentDTO(appointment.getId(),
                        appointment.getStartTime(),
                        appointment.getEndTime(),
                        appointment.getVideoConference().getMeetingId(),
                        appointment.getVetDoctor().getFullName(),
                        appointment.getClient().getFirstName(),
                        appointment.getClient().getLastName()))
                .collect(Collectors.toList());

        return appointmentDTOs;
    }

    @Override
    @Transactional
    public AppointmentDTO createAppointment(Long clientId, Long vetDoctorId, AppointmentConferenceSaveRequest appointmentConferenceSaveRequest) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        VetDoctor vetDoctor = vetDoctorRepository.findById(vetDoctorId).orElseThrow();

        logger.info("Client: {}", client);
        logger.info("VetDoctor: {}", vetDoctor);

        Appointment appointment = new Appointment();

        appointment.setClient(client);
        appointment.setVetDoctor(vetDoctor);
        appointment.setStartTime(appointmentConferenceSaveRequest.startTime());
        appointment.setEndTime(appointmentConferenceSaveRequest.endTime());

        VideoConference videoConference = new VideoConference();
        videoConference.setTitle("[Company] Zoom Call With " + vetDoctor.getFullName() + " at " + appointmentConferenceSaveRequest.startTime());
        videoConference.setMeetingId(appointmentConferenceSaveRequest.meetingId());

        videoConference.setAppointment(appointment);
        appointment.setVideoConference(videoConference);

        videoConferenceRepository.save(videoConference);
        appointmentRepository.save(appointment);

        logger.info("Appointment: {}", appointment);
        logger.info("VideoConference: {}", videoConference);


        return new AppointmentDTO(appointment.getId(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getVideoConference().getMeetingId(),
                appointment.getVetDoctor().getFullName(),
                appointment.getClient().getFirstName(),
                appointment.getClient().getLastName());

    }
}
